package livelygig.client.modules

import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.Button
import livelygig.client.components.Bootstrap.CommonStyle
import livelygig.client.components.Bootstrap.Modal
import livelygig.client.components.GlobalStyles
import livelygig.client.components.Icon
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css.MessagesCSS
import livelygig.client.css.ProjectCSS
import livelygig.client.logger._
import livelygig.client.models.UserModel
import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
import japgolly.scalajs.react.extra.router.RouterCtl
import livelygig.client.LGMain.{Loc}
import livelygig.client.services.ApiResponseMsg
import livelygig.client.services.ApiResponseMsg
import livelygig.client.services.ApiResponseMsg
import livelygig.client.services.CoreApi
import livelygig.client.services.CoreApi._
import livelygig.client.services.CoreApi._
import livelygig.client.services.CoreApi._
import livelygig.client.services.CoreApi._
import org.scalajs.dom._
import scala.scalajs.js
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.logger._
import livelygig.client.services._
import livelygig.client.css.{HeaderCSS, DashBoardCSS,ProjectCSS,MessagesCSS}
import scala.concurrent.ExecutionContext.Implicits.global

object LegalModal {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc])

  case class State(showPostProject: Boolean = false)


  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showPostProject = true))
    }
    def addLoginForm() : Callback = {
      t.modState(s => s.copy(showPostProject = true))
    }
    def addNewLoginForm() : Callback = {
      t.modState(s => s.copy(showPostProject = true))
    }

    def addNewAgent(userModel: UserModel, addNewAgent: Boolean = false): Callback = {
      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${addNewAgent}")
      if(addNewAgent){
        createUser(userModel).onComplete {
          case Success(s) =>
            log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiResponseMsg.CreateUserWaiting){
              t.modState(s => s.copy(showPostProject = true)).runNow()
            } else {
              log.debug(s"createUser msg : ${s.content}")
              t.modState(s => s.copy(/*showRegistrationFailed = true*/)).runNow()
            }
          case Failure(s) =>
            log.debug(s"createUserFailure: ${s}")
            t.modState(s => s.copy(/*showErrorModal = true*/)).runNow()
          // now you need to refresh the UI
        }
        t.modState(s => s.copy(showPostProject = true))
      } else {
        t.modState(s => s.copy(showPostProject = true))
      }
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn)(

        Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(DashBoardCSS.Style.footLegalStyle)),"Legal"),
        if (S.showPostProject) LegalModalForm(LegalModalForm.Props(B.addNewAgent))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object LegalModalForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (UserModel, Boolean) => Callback)
  case class State(userModel: UserModel, postProject: Boolean = false)


  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }
    def updateName(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
    }
    def updateEmail(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
    }
    def updatePassword(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
    }
    def toggleBTCWallet(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postProject = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postProject)
      props.submitHandler(state.userModel, state.postProject)
    }

    def render(s: State, p: Props) = {
      if (s.postProject){
        jQuery(t.getDOMNode()).modal("hide")
      }
      val headerText = "Legal"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("Legal"))
          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.ul()(
              <.li()(<.a(^.href:="#")("Privacy Policy")),
              <.li()(<.a(^.href:="#")("End User Agreement")),
              <.li()(<.a(^.href:="#")("Terms of Service")),
              <.li()(<.a(^.href:="#")("Trademarks")),
              <.li()(<.a(^.href:="#")("Copyright"))
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
//              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
//              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State(new UserModel("","","",false)))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      val P = scope.props
      val S=scope.state
      val B=scope.backend

      def hideModal = Callback {
        if (S.postProject) {
          def hide = Callback {
            jQuery(scope.getDOMNode()).modal("hide")
          }
        }
      }
    })
    .componentDidUpdate(scope=> Callback{

    })
    .build
  def apply(props: Props) = component(props)
}

