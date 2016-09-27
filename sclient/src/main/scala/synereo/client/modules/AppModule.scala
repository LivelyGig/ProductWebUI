package synereo.client.modules


import japgolly.scalajs.react.{Callback, ReactComponentB, _}
import diode.react.ModelProxy
import synereo.client.css.ConnectionsCSS
import synereo.client.services.SYNEREOCircuit
import org.querki.jquery._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.rootmodels.AppRootModel
import synereo.client.handlers._
import synereo.client.logger
import synereo.client.modalpopups.{AboutInfoModal, NodeSettingModal, ProfileImageUploaderForm, ServerErrorModal}
import org.scalajs.dom.window

import scalacss.ScalaCssReact._
import scala.scalajs.js
import japgolly.scalajs.react.{Callback, _}
import synereo.client.logger
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import japgolly.scalajs.react
import org.scalajs.dom


/**
  * Created by a4tech on 5/24/2016.
  */
//scalastyle:off
object AppModule {
  val PEOPLE_VIEW = "people"
  val ACCOUNT_VIEW = "account"
  val DASHBOARD_VIEW = "dashboard"
  val POSTFULL_VIEW = "postfullview"
  val USERPROFILE_VIEW = "userprofile"
  val TIMELINE_VIEW = "timeline"
  val NOTIFICATIONS_VIEW = "notifications"
  val MARKETPLACE_VIEW = "marketplace"

  val userProxy = SYNEREOCircuit.connect(_.user)
  val connectionProxy = SYNEREOCircuit.connect(_.connections)
  val messagesProxy = SYNEREOCircuit.connect(_.messages)
  val introductionProxy = SYNEREOCircuit.connect(_.introduction)
  val erroProxy = SYNEREOCircuit.connect(_.appRootModel)

  val searchContainer: js.Object = "#searchContainer"

  case class Props(view: String, proxy: ModelProxy[AppRootModel])

  case class State(showErrorModal: Boolean = false, showProfileImageUploadModal: Boolean = false,
                   showNodeSettingModal: Boolean = false, showAboutInfoModal: Boolean = false)


  class AppModuleBackend(t: BackendScope[Props, State]) {
    /**
      * do not try to change these callback methods using react.Callback or other ways of callbacks, popups wont work then
      *
      * @return
      */
    def hideProfileImageModal(): Callback = {
      logger.log.debug("hideProfileImageModal")
      SYNEREOCircuit.dispatch(ToggleImageUploadModal())
      t.modState(s => s.copy(showProfileImageUploadModal = false))
    }

    def hideAboutInfoModal(): Callback = {
      SYNEREOCircuit.dispatch(ToggleAboutInfoModal())
      t.modState(s => s.copy(showAboutInfoModal = false))
    }

    def hideNodeSettingModal(): Callback = {
      SYNEREOCircuit.dispatch(ToggleNodeSettingModal())
      t.modState(s => s.copy(showNodeSettingModal = false))
    }

    def serverError(): Callback = {
      SYNEREOCircuit.dispatch(ShowServerError(""))
      t.modState(s => s.copy(showErrorModal = false))
    }

    def mounted(props: AppModule.Props) = Callback {
      logger.log.debug("app module mounted")
      val userSessionUri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      if (userSessionUri.length < 1) {
        SYNEREOCircuit.dispatch(LogoutUser())
      }

    }

    def didUpdate(): Callback = Callback {
      //      SYNEREOCircuit.dispatch(CloseAllPopUp())
      //      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      //      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
      //      $("[role='dialog']".asInstanceOf[js.Object]).remove()
      //      ReactDOM.unmountComponentAtNode(org.scalajs.dom.document.getElementById("naviContainer"));
    }
  }

  private val component = ReactComponentB[Props]("AppModule")
    .initialState_P(p => (State()))
    .backend(new AppModuleBackend(_))
    .renderPS((t, P, S) => {
      <.div(
        <.div(^.id := "appContainer", ConnectionsCSS.Style.connectionsContainerMain)(
          <.div()(
            //Left Sidebar
            <.div(^.id := "searchContainer", ^.className := "sidebar sidebar-left sidebar-animate sidebar-lg-show ",
              ^.onMouseEnter --> Callback {
                $(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")
              },
              ^.onMouseLeave --> Callback {
                $(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")
              }
            )(Sidebar(Sidebar.Props())),
            if (P.proxy().isServerError) {
              ServerErrorModal(ServerErrorModal.Props(t.backend.serverError))
            }
            else if (P.proxy().showProfileImageUploadModal) {
              userProxy(userProxy => ProfileImageUploaderForm(ProfileImageUploaderForm.Props(t.backend.hideProfileImageModal, "Profile Image Uploader", userProxy)))
            }
            else if (P.proxy().showNodeSettingModal) {
              NodeSettingModal(NodeSettingModal.Props(t.backend.hideNodeSettingModal))
            }
            else if (P.proxy().showAboutInfoModal) {
              AboutInfoModal(AboutInfoModal.Props(t.backend.hideAboutInfoModal))
            }
            else
              Seq.empty[ReactElement]
          ),
          <.div(
            P.view match {
              case PEOPLE_VIEW => connectionProxy(s => ConnectionsResults(s))
              case ACCOUNT_VIEW => userProxy(s => AccountInfo())
              case DASHBOARD_VIEW => messagesProxy(s => Dashboard(s))
              case USERPROFILE_VIEW => userProxy(s => UserProfileView(s))
              case POSTFULL_VIEW => PostFullView(PostFullView.Props())
              case TIMELINE_VIEW => TimelineView(TimelineView.Props())
              case NOTIFICATIONS_VIEW => introductionProxy(s => NotificationView(s))
              case MARKETPLACE_VIEW => MarketPlaceFull(MarketPlaceFull.Props())
            }
          )
        )
      )
    })
    .componentDidUpdate(scope => scope.$.backend.didUpdate())
    .componentWillMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}

