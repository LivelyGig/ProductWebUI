package synereo.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.sessionitems.SessionItems
import shared.models.{MessagePost, MessagePostContent}
import synereo.client.rootmodels.MessagesRootModel
import synereo.client.components._
import synereo.client.css.{DashboardCSS, PostFullViewCSS, SynereoCommanStylesCSS}
import synereo.client.modalpopups.{AmplifyPostForm, AmplifyPostModal, FullPostViewModal, ServerErrorModal}

import scalacss.ScalaCssReact._
import scala.scalajs.js
import org.querki.jquery._
import org.scalajs.dom._

import scala.scalajs.js.timers._
import synereo.client.components.Icon

import scala.language.reflectiveCalls
import org.widok.moment.Moment
import synereo.client.handlers.ShowServerError
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._


/**
  * Created by Mandar on 3/11/2016.
  */
//scalastyle:off
object Dashboard {
  val messageLoader: js.Object = "#messageLoader"
  val searchContainer: js.Object = "#searchContainer"
  val dashboardContainerMain: js.Object = "#dashboardContainerMain"
  val FeedTimeOut = 1500


  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(/*postMessage: MessagePost,isMessagePosted: Boolean = false,  */ ShowFullPostView: Boolean = false, preventFullPostView: Boolean = true, showErrorModal: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    //scalastyle:off

    def updated(props: Props): Callback = Callback {
      if (props.proxy().isFailed) {
        SYNEREOCircuit.dispatch(ShowServerError("Failed to connect to the server!"))
      }
      //
    }

    //    def updateContent(e: ReactEventI) = {
    //      val value = e.target.value
    //      t.modState(s => s.copy(postMessage = s.postMessage.copy(postContent = s.postMessage.postContent.copy(text = value))))
    //    }

    def closeFullViewModalPopUp(): Callback = {
      $(dashboardContainerMain).addClass("SynereoCommanStylesCSS_Style-overflowYScroll")
      t.modState(s => s.copy(ShowFullPostView = false))
    }

    def openFullViewModalPopUP(e: ReactEvent): Callback = {
      $(dashboardContainerMain).removeClass("SynereoCommanStylesCSS_Style-overflowYScroll")
      t.modState(s => s.copy(ShowFullPostView = true, preventFullPostView = true))
    }

    def preventFullViewModalPopUP(e: ReactEvent): Callback = {
      val targetLi = e.target
      setTimeout(500) {
        $(targetLi).find(".glance-view-button").addClass(".hide")
      }
      t.modState(s => s.copy(ShowFullPostView = false))
    }

    def handleScroll(e: ReactEvent): Callback = {
      Callback.empty
    }

    def handleMouseEnterEvent(e: ReactEvent): Callback = {
      val targetLi = e.target
      val collapsiblePost: js.Object = $(targetLi).find(".collapse")
      setTimeout(FeedTimeOut) {
        if (!$(collapsiblePost).hasClass("in")) {
          $(collapsiblePost).addClass("in")
        }
      }
      Callback.empty
    }


    def serverError(): Callback = {
      // SYNEREOCircuit.dispatch(ShowServerError(""))
      t.modState(s => s.copy(showErrorModal = false))
    }

    //scalastyle:off
    def render(s: State, p: Props) = {
      val getServerError = SYNEREOCircuit.zoom(_.appRootModel).value

      <.div(^.id := "dashboardContainerMain", ^.className := "container-fluid")(

        <.div(^.className := "container-fluid", DashboardCSS.Style.homeFeedMainContainer)(
          <.div(^.className := "row")(
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-sm-12 col-md-12 col-lg-12")(
                  <.div(^.className := "text-center")(<.span(^.id := "messageLoader", ^.color.white, ^.className := "hidden", Icon.spinnerIconPulse)),
                  <.div(
                    p.proxy().renderFailed(ex => /*<.div(
                      //                      <.span(^.id := "loginLoader", SynereoCommanStylesCSS.Style.loading, ^.className := "", Icon.spinnerIconPulse),
                      <.div(SynereoCommanStylesCSS.Style.renderFailedMessage)(s"We are encountering problems with serving the request!${ex.getMessage}")
                    )*/

                      if (!getServerError.isServerError)
                        ServerErrorModal(ServerErrorModal.Props(serverError))
                      else
                        <.div()
                    ),

                    p.proxy().render(
                      messagesRootModel =>
                        HomeFeedList(messagesRootModel.messagesModelList)
                    ),
                    p.proxy().renderPending(ex => <.div(
                      <.div(^.id := "loginLoader", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)
                    )
                    ),
                    <.div(
                      if (p.proxy().isEmpty) {
                        <.div(^.id := "loginLoader", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)
                      } else {
                        <.span()
                      }
                    )
                  ),
                  <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer, ^.onScroll ==> handleScroll)(
                    for (i <- 1 to 50) yield {
                      if (i % 2 != 0) {
                        <.li(^.id := s"home-feed-card-$i", ^.className := "media", DashboardCSS.Style.CardHolderLiElement, ^.onMouseEnter ==> handleMouseEnterEvent /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
                          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                            <.div(^.className := "", ^.onClick ==> openFullViewModalPopUP)(
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
                                  <.div(DashboardCSS.Style.cardText, ^.onClick ==> openFullViewModalPopUP)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                    "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                                  <.div(^.id := s"collapse-post-$i", ^.className := "collapse", DashboardCSS.Style.cardText)(
                                    <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, ^.onClick ==> openFullViewModalPopUP)(
                                      "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
                                    ),
                                    <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                                      <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                                    )
                                  ),
                                  <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                                    "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-$i", ^.className := "glance-view-button", ^.onClick ==> preventFullViewModalPopUP)(
                                    (MIcon.moreHoriz)
                                  )
                                )
                              )
                            )
                          )
                        )
                      } else {
                        <.li(^.id := s"home-feed-card-$i", ^.className := "media", DashboardCSS.Style.CardHolderLiElement, ^.onMouseEnter ==> handleMouseEnterEvent /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
                          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                            <.div(^.className := "", ^.onClick ==> openFullViewModalPopUP)(
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
                                    <.div(DashboardCSS.Style.cardText, ^.onClick ==> openFullViewModalPopUP)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                      "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                                    <.div(^.id := s"collapse-post-$i", ^.className := "collapse", DashboardCSS.Style.cardText)(
                                      <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, ^.onClick ==> openFullViewModalPopUP)(
                                        // "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
                                      ),
                                      <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                                      )
                                    ),
                                    <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                                      "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-$i", ^.className := "glance-view-button", ^.onClick ==> preventFullViewModalPopUP)(
                                      (MIcon.moreHoriz)
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
          if (s.ShowFullPostView && s.preventFullPostView) FullPostViewModal(FullPostViewModal.Props(closeFullViewModalPopUp))
          else Seq.empty[ReactElement]
        )
      )
    }
  }

  val component = ReactComponentB[Props]("Dashboard")
    //    .initialState_P(p => State(new MessagePost("", "", "", "", Nil, new MessagePostContent("", ""))))
    .initialState_P(p => State())
    .renderBackend[Backend]
    //    .componentDidUpdate(scope => scope.$.backend.updated(scope.currentProps))
    .build

  def apply(proxy: ModelProxy[Pot[MessagesRootModel]]) = component(Props(proxy))
}


object HomeFeedList {

  val dashboardContainerMain: js.Object = "#dashboardContainerMain"
  val FeedTimeOut = 1500

  case class Props(messages: Seq[MessagePost])

  case class State(ShowFullPostView: Boolean = false, preventFullPostView: Boolean = true, showAmplifyPostForm: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {

    def mounted() = Callback {

    }

    //    def closeFullViewModalPopUp(): Callback = {
    //      $(dashboardContainerMain).addClass("SynereoCommanStylesCSS_Style-overflowYScroll")
    //      t.modState(s => s.copy(ShowFullPostView = false))
    //    }
    //
    //    def openFullViewModalPopUP(e: ReactEvent): Callback = {
    //      $(dashboardContainerMain).removeClass("SynereoCommanStylesCSS_Style-overflowYScroll")
    //      t.modState(s => s.copy(ShowFullPostView = true, preventFullPostView = true))
    //    }

    //    def preventFullViewModalPopUP(e: ReactEvent): Callback = {
    //      val targetLi = e.target
    //      setTimeout(1500) {
    //        $(targetLi).find(".glance-view-button").addClass(".hide")
    //      }
    //      t.modState(s => s.copy(ShowFullPostView = false))
    //    }

    //    def handleScroll(e: ReactEvent): Callback = {
    //      Callback.empty
    //    }

    //    def mouseEntered(e: ReactEvent): Callback = {
    //      //      println("mouse entered some where ")
    //      val targetLi = e.target
    //      val collapsiblePost: js.Object = $(targetLi).find(".collapse")
    //      setTimeout(FeedTimeOut) {
    //        if (!$(collapsiblePost).hasClass("in")) {
    //          $(collapsiblePost).addClass("in")
    //        }
    //      }
    //      Callback.empty
    //    }


    def filterLabelStrings(value: Seq[String]): Seq[String] = {
      value.filter(
        _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
      ).map(_.replace("#", "")).toSet.toSeq
    }

    def amplifyPost(e: ReactEventI): Callback = {
      println("inside amplify post method ")
      t.modState(state => state.copy(showAmplifyPostForm = true))
    }

    def postAmplified(): Callback = {
      println("inside post amplified method ")
      t.modState(s => s.copy(showAmplifyPostForm = false))
    }

    def render(props: Props, state: State) = {

      def renderMessages(message: MessagePost) = {
        val messageText = message.postContent.text.split(" ")
        val selfConnectionId = message.connections(0).source.split("/")(2)
        val connections = SYNEREOCircuit.zoom(_.connections).value.connectionsResponse
        var fromSender = "unknown"
        var toReceiver = "unknown"
        var img = ""
        val userId = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)
        if (userId == selfConnectionId) {
          fromSender = "me"
          // get other party ID, if there is one
          if (message.connections.size > 1) {
            if (message.connections(1).source.split("/")(2) == userId) {
              toReceiver = message.connections(1).target.split("/")(2)
            } else {
              toReceiver = message.connections(1).source.split("/")(2)
            }
            // ToDo: look up name of Receiver and use friendly name
          } else {
            toReceiver = "self"
          }
        } else {
          for (b <- message.connections;
               a <- connections
               if a.connection.source.split("/")(2) == b.target.split("/")(2) && a.connection.target.split("/")(2) == b.source.split("/")(2)
          ) yield {
            fromSender = a.name
            img = a.imgSrc
          }

          //   fromSender = selfConnectionId1
          // ToDo: Look up name of Sender and use friendly name
          toReceiver = "me"
        }
        <.li(^.id := s"home-feed-card-${message.uid}", ^.className := "media", DashboardCSS.Style.CardHolderLiElement /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost /*, ^.onMouseEnter ==> mouseEntered*/)(
            <.div(^.className := "")(
              <.div(^.className := "col-md-1")(
                if (img.length != 0)
                  <.img(^.className := "media-object", ^.src := img, ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                else if (fromSender.equals("me"))
                  <.img(^.className := "media-object", ^.src := SYNEREOCircuit.zoom(_.user).value.imgSrc, ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                else
                  <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
              ),
              <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                <.div(DashboardCSS.Style.userNameDescription)(
                  <.span(fromSender),
                  <.div("data-toggle".reactAttr := "tooltip", "title".reactAttr := message.created, "data-placement".reactAttr := "right")(Moment(message.created).format("LLL").toLocaleString)
                ),
                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert),
                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.ampTokenBtn, ^.onClick ==> amplifyPost)(
                  <.img(^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg)
                )
              )
            ),
            <.div(^.className := "")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12")(
                  //                  <.img(^.src := "./assets/synereo-images/blogpostimg.png", ^.className := "img-responsive", DashboardCSS.Style.cardImage),
                  <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                    <.h3(message.postContent.subject, DashboardCSS.Style.cardHeading),
                    <.div(DashboardCSS.Style.cardText)(
                      <.div(^.className := "col-md-9 col-sm-9 col-xs-12", PostFullViewCSS.Style.marginLeft15PX)(
                        <.div(DashboardCSS.Style.cardText /*, ^.onClick ==> openFullViewModalPopUP*/)(
                          if (messageText.length == 1) {
                            messageText(0)
                          } else
                            for {b <- 0 to messageText.length - 1 if b <= 30} yield {
                              messageText(b) + " "
                            }
                        )
                      ),
                      <.div(^.className := "col-md-3 col-sm-3 col-xs-12")(
                        if (message.postContent.imgSrc != "") {
                          <.img(^.src := message.postContent.imgSrc, ^.height := "100.px", ^.width := "100.px", DashboardCSS.Style.imgBorder)
                        } else {
                          <.div("")
                        }
                      )
                    )
                    //                    <.div(^.className := "row text-right")(
                    //                      <.button(^.className := "btn btn-default", DashboardCSS.Style.ampTokenBtn, ^.onClick ==> amplifyPost)(
                    //                        <.img(^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg)
                    //                      )
                    //                        AmplifyPostModal (AmplifyPostModal.Props("", Seq(DashboardCSS.Style.ampTokenBtn),
                    //                        <.img(^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg), "", message.uid))
                    //                    )
                  )
                )
              ),
              <.div(^.className := "col-md-12")(
                <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                  <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero /*, ^.onClick ==> openFullViewModalPopUP*/)(
                    for {b <- 1 to messageText.length if b >= 30} yield {
                      messageText(b) + " "
                    },
                    <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                      for {label <- filterLabelStrings(message.postContent.text.split(" +"))} yield {
                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)(label)
                      }
                    )
                  )
                )
              )
            ),
            <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-${message.uid}" /*, ^.className := "glance-view-button", ^.onClick ==> preventFullViewModalPopUP*/)(
                (MIcon.moreHoriz)
              )
            )
          )
        )
      }
      <.div(
        <.div(
          if (state.showAmplifyPostForm) {
            AmplifyPostForm(AmplifyPostForm.Props(postAmplified))
          } else {
            <.span
          }
        ),
        <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer /*, ^.onScroll ==> handleScroll*/)(
          props.messages map renderMessages
        )
      )
    }
  }

  val MessagesList = ReactComponentB[Props]("MessagesList")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(messages: Seq[MessagePost]) = MessagesList(Props(messages))

}