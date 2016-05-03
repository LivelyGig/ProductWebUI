package synereo.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.handlers.{RefreshConnections, RefreshMessages}
import shared.models.MessagesModel
import shared.RootModels.MessagesRootModel
import synereo.client.SYNEREOMain
import synereo.client.components._
import synereo.client.css.{SynereoCommanStylesCSS, DashboardCSS}
import synereo.client.modalpopups.FullPostViewModal
import synereo.client.services.SYNEREOCircuit
import scala.scalajs.js
import scalacss.Attrs.clear
import scalacss.ScalaCssReact._
import org.querki.jquery._
import scala.scalajs.js
import org.querki.jquery._
import org.scalajs.dom._
import js.{Date, UndefOr}
import scala.scalajs.js.timers._
import org.scalajs.dom.window
import synereo.client.components.{Icon, GlobalStyles}
import synereo.client.components.Bootstrap.Modal
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._

/**
  * Created by Mandar on 3/11/2016.
  */
object Dashboard {
  var lastPos: Double = 50
  var newPos: Double = 50
  var timer: js.Any = 0
  var delta: Double = 50
  var delay: Double = 50
  val dashboardContainerMain: js.Object = "#dashboardContainerMain"
  val FeedTimeOut = 1500
  val loginLoader: js.Object = "#loginLoader"
  val loadingScreen: js.Object = "#loadingScreen"

  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(ShowFullPostView: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props) = {
      if (props.proxy().isEmpty) {
        //        SYNEREOCircuit.dispatch(RefreshConnections())
        props.proxy.dispatch(RefreshMessages())
        //        props.proxy.dispatch(RefreshMessages())
      } else {
        Callback.empty
      }
    }

    def closeFullViewModalPopUp(): Callback = {
      //      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
      t.modState(s => s.copy(ShowFullPostView = false))
      //      jQuery(t.getDOMNode()).modal("hide")
      //      Callback.empty
    }

    def openFullViewModalPopUP(e: ReactEvent): Callback = {
      t.modState(s => s.copy(ShowFullPostView = true))
    }

    def toggleTopbar = Callback {
      val topBtn: js.Object = "#TopbarContainer"
      $(topBtn).toggleClass("topbar-left topbar-lg-show")
    }

    def clearScrollPositions() = {
      lastPos = 0
      delta = 0
    }

    def handleScroll(e: ReactEvent): Callback = {
      clearScrollPositions
      Callback.empty
    }


    def modifyCardSize(e: ReactEvent): Callback = {
      var clickedElement = e.target
      Callback.empty
    }

    def handleMouseEnterEvent(e: ReactEvent): Callback = {
      val Li = e.target
      val collapsiblePost: js.Object = $(Li).find(".collapse")
      setTimeout(FeedTimeOut) {
        if (!$(collapsiblePost).hasClass("in")) {
          $(Li).find(".glance-view-button").trigger("click")
        }
      }
      Callback.empty
    }

    //    def handleMouseLeaveEvent(e: ReactEvent): Callback = {
    //      val Li = e.target
    //      setTimeout(1500) {
    //        //        println("completed 1500ms")
    //        $(Li).find(".glance-view-button").trigger("click")
    //      }
    //      CallbackTo.pure(Nil)
    //    }

    def render(s: State, p: Props) = {
      <.div(^.id := "dashboardContainerMain", ^.className := "container-fluid", DashboardCSS.Style.dashboardContainerMain, ^.onScroll ==> handleScroll)(
        <.div(^.className := "row")(
          //Left Sidebar
          <.div(^.id := "searchContainer", ^.className := "col-md-2 col-sm-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ")(
            //            Footer(Footer.Props(c, r.page))
            Sidebar(Sidebar.Props())
          )),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
            <.div(^.className := "pull-right", DashboardCSS.Style.profileActionContainer)(
              <.div(^.id := "TopbarContainer", ^.className := "col-md-2 col-sm-2 topbar topbar-animate")(
                TopMenuBar(TopMenuBar.Props()),
                <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", DashboardCSS.Style.profileActionButton, ^.onClick --> toggleTopbar)(
                  <.img(^.src := "./assets/synereo-images/ampsIcon.PNG"), <.span("543")
                )
              )
            )
          )
        ),
        <.div(^.className := "container-fluid", DashboardCSS.Style.homeFeedMainContainer)(
          <.div(^.className := "row")(
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "card-shadow", DashboardCSS.Style.userPostForm)(
                <.form(/*^.onSubmit ==> submitForm*/)(
                  <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                  <.input(^.tpe := "text", DashboardCSS.Style.UserInput, ^.className := "form-control", ^.placeholder := "contribute your thoughts..."),
                  //                  <.button(^.tpe := "submit")(<.span()(Icon.camera))
                  <.button(^.tpe := "submit", ^.className := "btn pull-right", DashboardCSS.Style.userInputSubmitButton)(Icon.camera)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-sm-12 col-md-12 col-lg-12")(
                  //                  <.div(
                  //                    p.proxy().render(
                  //                      messagesRootModel =>
                  //                        HomeFeedList(messagesRootModel.messagesModelList)
                  //                    ),
                  //                    p.proxy().renderFailed(ex => <.div()(<.span(Icon.warning), " Error loading")),
                  //                    p.proxy().renderPending(ex => <.div()(
                  //                      <.img(^.src := "./assets/images/processing.gif")))
                  //                  )
                  <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer /*, ^.onClick ==> modifyCardSize*/)(
                    for (i <- 1 to 50) yield {
                      if (i % 2 != 0) {
                        <.li(^.id := s"home-feed-card-$i", ^.className := "media", DashboardCSS.Style.CardHolderLiElement, ^.onMouseEnter ==> handleMouseEnterEvent /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
                          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                            <.div(^.className := "")(
                              <.div(^.className := "col-md-1")(
                                <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                              ),
                              <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                <.div(DashboardCSS.Style.userNameDescription)(
                                  <.span("James Gosling"),
                                  <.span(MIcon.chevronRight),
                                  <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                                  <.span("just now")
                                ),
                                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                              )
                            ),
                            <.div(^.className := "row")(
                              <.div(^.className := "col-md-12")(
                                <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                                  <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                                  <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                    "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                                  <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                                    "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-$i", ^.className := "glance-view-button")(
                                    (MIcon.moreHoriz)
                                  ),
                                  <.div(^.id := s"collapse-post-$i", ^.className := "collapse", DashboardCSS.Style.cardText, ^.onClick ==> openFullViewModalPopUP)(
                                    <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                      "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
                                    ),
                                    <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                                    )
                                  )
                                )
                              )
                            )
                          )
                        )
                      }
                      else {
                        <.li(^.id := s"home-feed-card-$i", ^.className := "media", DashboardCSS.Style.CardHolderLiElement, ^.onMouseEnter ==> handleMouseEnterEvent /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
                          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                            <.div(^.className := "")(
                              <.div(^.className := "col-md-1")(
                                <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                              ),
                              <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                <.div(DashboardCSS.Style.userNameDescription)(
                                  <.span("James Gosling"),
                                  <.span(MIcon.chevronRight),
                                  <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                                  <.span("just now")
                                ),
                                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                              )
                            ),
                            <.div(^.className := "")(
                              <.div(^.className := "row")(
                                <.div(^.className := "col-md-12")(
                                  <.img(^.src := "./assets/synereo-images/blogpostimg.png", ^.className := "img-responsive", DashboardCSS.Style.cardImage),
                                  <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                                    <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                                    <.div(DashboardCSS.Style.cardText)(
                                      "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
                                      <.br(),
                                      <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                                        "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-$i", ^.className := "glance-view-button")(
                                        (MIcon.moreHoriz)
                                      )
                                    ),
                                    <.div(^.id := s"collapse-post-$i", ^.className := "collapse", DashboardCSS.Style.cardText, ^.onClick ==> openFullViewModalPopUP)(
                                      <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
                                      ),
                                      <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                                      )
                                    )
                                  )
                                )
                              )
                            )
                          )
                        )
                      }
                    }
                  )
                )
              )
            )
          )
        ),
        <.div(
          //        if (s.showErrorModal) ErrorModal(ErrorModal.Props(closeLoginErrorPopup, s.loginErrorMessage))
          if (s.ShowFullPostView) FullPostViewModal(FullPostViewModal.Props(closeFullViewModalPopUp))
          else Seq.empty[ReactElement]
        )
      )
    }
  }

  val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[Pot[MessagesRootModel]]) = component(Props(proxy))
}

object HomeFeedList {

  case class Props(messages: Seq[MessagesModel])

  private val MessagesList = ReactComponentB[Props]("ProjectList")
    .render_P(p => {
      def renderMessages(message: MessagesModel) = {
        <.li(^.id := "home-feed-card", ^.className := "media", DashboardCSS.Style.CardHolderLiElement /*, ^.onMouseEnter ==> handleMouseEnterEvent , ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
            <.div(^.className := "")(
              <.div(^.className := "col-md-1")(
                <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
              ),
              <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                <.div(DashboardCSS.Style.userNameDescription)(
                  <.span("James Gosling"),
                  <.span(MIcon.chevronRight),
                  <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                  <.span("just now")
                ),
                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
              )
            ),
            <.div(^.className := "")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12")(
                  <.img(^.src := "./assets/synereo-images/blogpostimg.png", ^.className := "img-responsive", DashboardCSS.Style.cardImage),
                  <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                    <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                    <.div(DashboardCSS.Style.cardText)(
                      message.text,
                      <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                    )
                  )
                )
              )
            )
          )
        )
      }
      <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer /*, ^.onClick ==> modifyCardSize*/)(
        p.messages map renderMessages
        /*for (i <- 1 to 50) yield {
          if (i % 2 != 0) {
            <.li(^.id := s"home-feed-card-$i", ^.className := "media", DashboardCSS.Style.CardHolderLiElement /*, ^.onMouseEnter ==> handleMouseEnterEvent , ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
              <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                <.div(^.className := "")(
                  <.div(^.className := "col-md-1")(
                    <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                  ),
                  <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12")(
                    <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                      <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                      <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                        "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-$i", ^.className := "glance-view-button")(
                        (MIcon.moreHoriz)
                      ),
                      <.div(^.id := s"collapse-post-$i", ^.className := "collapse", DashboardCSS.Style.cardText /*, ^.onClick ==> openFullViewModalPopUP*/)(
                        <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
                        ),
                        <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                          <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                          <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                          <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                          <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                        )
                      )
                    )
                  )
                )
              )
            )
          }
          else {
            <.span()
          }
        }*/
      )


    })
    .build

  def apply(messages: Seq[MessagesModel]) =
    MessagesList(Props(messages))

}