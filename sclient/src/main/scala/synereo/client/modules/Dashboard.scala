package synereo.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import shared.models.{MessagePost, MessagePostContent}
import synereo.client.rootmodels.MessagesRootModel
import synereo.client.components._
import synereo.client.css.{DashboardCSS, PostFullViewCSS, SynereoCommanStylesCSS}
import synereo.client.modalpopups._
import diode.AnyAction._
import scalacss.ScalaCssReact._
import scala.scalajs.js
import synereo.client.components.Icon

import scala.language.reflectiveCalls
import org.widok.moment.Moment
import synereo.client.handlers.ShowServerError
import synereo.client.logger
import synereo.client.services.SYNEREOCircuit
import scala.scalajs.js.timers._


/**
  * Created by Mandar on 3/11/2016.
  */

//scalastyle:off
object Dashboard {


  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(ShowFullPostView: Boolean = false, preventFullPostView: Boolean = true, showErrorModal: Boolean = false)


  class DashboardBackend(t: BackendScope[Dashboard.Props, Dashboard.State]) {
    //scalastyle:off

    val messageLoader: js.Object = "#messageLoader"
    val searchContainer: js.Object = "#searchContainer"
    val dashboardContainerMain: js.Object = "#dashboardContainerMain"
    val FeedTimeOut = 1500


    def updated(props: Dashboard.Props): Callback = Callback {
      if (props.proxy().isFailed) {
        SYNEREOCircuit.dispatch(ShowServerError("Failed to connect to the server!"))
      }
      //
    }

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

  }
  val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State())
    .backend(new DashboardBackend(_))
    .renderPS((t, P, S) => {
      val getServerError = SYNEREOCircuit.zoom(_.appRootModel).value

      <.div(^.id := "dashboardContainerMain", ^.className := "container-fluid")(
        <.div(^.className := "container-fluid", DashboardCSS.Style.homeFeedMainContainer)(
          <.div(^.className := "row")(
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-sm-12 col-md-12 col-lg-12")(
                  <.div(^.className := "text-center")(<.span(^.id := "messageLoader", ^.color.white, ^.className := "hidden", Icon.spinnerIconPulse)),
                  <.div(
                    P.proxy().renderFailed(ex => /*<.div(
                      //                      <.span(^.id := "loginLoader", SynereoCommanStylesCSS.Style.loading, ^.className := "", Icon.spinnerIconPulse),
                      <.div(SynereoCommanStylesCSS.Style.renderFailedMessage)(s"We are encountering problems with serving the request!${ex.getMessage}")
                    )*/

                      if (!getServerError.isServerError)
                        ServerErrorModal(ServerErrorModal.Props(t.backend.serverError))
                      else
                        <.div()
                    ),
                    P.proxy().render(
                      messagesRootModel =>
                        HomeFeedList(messagesRootModel.messagesModelList)
                    ),
                    P.proxy().renderPending(ex => <.div(
                      <.div(^.id := "loginLoader", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)
                    )
                    ),
                    <.div(
                      if (P.proxy().isEmpty) {
                        <.div(^.id := "loginLoader", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)
                      } else {
                        <.span()
                      }
                    )
                  ),
                  <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer, ^.onScroll ==> t.backend.handleScroll)(
                    for (i <- 1 to 6) yield {
                      if (i % 2 != 0) {
                        <.li(^.id := s"home-feed-card-$i", ^.className := "media", DashboardCSS.Style.CardHolderLiElement, ^.onMouseEnter ==> t.backend.handleMouseEnterEvent /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
                          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                            <.div(^.className := "", ^.onClick ==> t.backend.openFullViewModalPopUP)(
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
                                  <.div(DashboardCSS.Style.cardText, ^.onClick ==> t.backend.openFullViewModalPopUP)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                    "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                                  <.div(^.id := s"collapse-post-$i", ^.className := "collapse", DashboardCSS.Style.cardText)(
                                    <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, ^.onClick ==> t.backend.openFullViewModalPopUP)(
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
                                    "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-$i", ^.className := "glance-view-button", ^.onClick ==> t.backend.preventFullViewModalPopUP)(
                                    (MIcon.moreHoriz)
                                  )
                                )
                              )
                            )
                          )
                        )
                      } else {
                        <.li(^.id := s"home-feed-card-$i", ^.className := "media", DashboardCSS.Style.CardHolderLiElement, ^.onMouseEnter ==> t.backend.handleMouseEnterEvent /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
                          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                            <.div(^.className := "", ^.onClick ==> t.backend.openFullViewModalPopUP)(
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
                                    <.div(DashboardCSS.Style.cardText, ^.onClick ==> t.backend.openFullViewModalPopUP)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                      "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                                    <.div(^.id := s"collapse-post-$i", ^.className := "collapse", DashboardCSS.Style.cardText)(
                                      <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, ^.onClick ==> t.backend.openFullViewModalPopUP)(
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
                                      "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-$i", ^.className := "glance-view-button", ^.onClick ==> t.backend.preventFullViewModalPopUP)(
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
          if (S.ShowFullPostView) FullPostViewModal(FullPostViewModal.Props(t.backend.closeFullViewModalPopUp, MessagePost("", "", "", "", Nil, MessagePostContent("", "", ""))))
          else Seq.empty[ReactElement]
        )
      )
    })
    //    .componentDidUpdate(scope => scope.$.backend.updated(scope.currentProps))
    .build

  def apply(proxy: ModelProxy[Pot[MessagesRootModel]]) = component(Props(proxy))
}


object HomeFeedList {

  val dashboardContainerMain: js.Object = "#dashboardContainerMain"
  val FeedTimeOut = 1500
  val getSearches = SYNEREOCircuit.connect(_.searches)

  case class Props(messages: Seq[MessagePost])

  case class State(showFullPostView: Boolean = false, showAmplifyPostForm: Boolean = false, showForwardPostForm: Boolean = false, messagePostForFullView:MessagePost = new MessagePost(postContent = new MessagePostContent()))
  class HomeFeedListBackend(t: BackendScope[HomeFeedList.Props, HomeFeedList.State]) {

    val messageLoader: js.Object = "#messageLoader"
    val searchContainer: js.Object = "#searchContainer"
    val dashboardContainerMain: js.Object = "#dashboardContainerMain"
    val FeedTimeOut = 1500


    def mounted() = Callback {

    }


    def filterLabelStrings(value: Seq[String]): Seq[String] = {
      value.filter(
        _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
      ).map(_.replace("#", "")).toSet.toSeq
    }

    def amplifyPost(e: ReactEventI): Callback = {
      logger.log.debug("amplifyPost called")
      t.modState(state => state.copy(showAmplifyPostForm = true))
    }

    def postAmplified(): Callback = {
      logger.log.debug("postAmplified called ")
      t.modState(s => s.copy(showAmplifyPostForm = false))
    }

    def forwardPost(e: ReactEventI): Callback = {
      logger.log.debug("forwardPost called ")
      t.modState(state => state.copy(showForwardPostForm = true))
    }

    def postForwarded(): Callback = {
      logger.log.debug("postForwarded called")
      t.modState(state => state.copy(showForwardPostForm = false))
    }



    def closeFullViewModalPopUp(): Callback = {
      $(dashboardContainerMain).addClass("SynereoCommanStylesCSS_Style-overflowYScroll")
      t.modState(s => s.copy(showFullPostView = false))
    }

    def openFullViewModalPopUP(getMessagePost : MessagePost): Callback = {
      $(dashboardContainerMain).removeClass("SynereoCommanStylesCSS_Style-overflowYScroll")
      t.modState(s => s.copy(showFullPostView = true, messagePostForFullView = getMessagePost))
    }

    def preventFullViewModalPopUP(e: ReactEvent): Callback = {
      val targetLi = e.target
      setTimeout(500) {
        $(targetLi).find(".glance-view-button").addClass(".hide")
      }
      t.modState(s => s.copy(showFullPostView = false))
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


  }


  val MessagesList = ReactComponentB[Props]("MessagesList")
    .initialState_P(p => State())
    .backend(new HomeFeedListBackend(_))
    .renderPS((t, P, S) => {
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
          for (b <- message.connections; a <- connections; if (a.connection.source.split("/")(2) == b.source.split("/")(2) && a.connection.target.split("/")(2) == b.target.split("/")(2))) yield
            toReceiver = a.name

        } else {
          for (b <- message.connections; a <- connections; if (a.connection.source.split("/")(2) == b.target.split("/")(2) && a.connection.target.split("/")(2) == b.source.split("/")(2))) yield {
            fromSender = a.name
            img = a.imgSrc
          }
          //   fromSender = selfConnectionId1
          // ToDo: Look up name of Sender and use friendly name
          toReceiver = "me"
        }



        <.li(^.id := s"home-feed-card-${message.uid}", ^.className := "media", DashboardCSS.Style.CardHolderLiElement /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/ , ^.onMouseEnter ==> t.backend.handleMouseEnterEvent)(
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
                <.div(DashboardCSS.Style.userNameDescription, ^.className := "pull-left")(
                  <.span(s"From  : ${   fromSender}"),
                  <.div("data-toggle".reactAttr := "tooltip", "title".reactAttr := message.created, "data-placement".reactAttr := "right")(Moment(message.created).format("LLL").toLocaleString)
                ),
                <.div(DashboardCSS.Style.userNameDescription)(
                  <.span(s"To  : ${   toReceiver}")
                ),
                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert),
                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.ampTokenBtn,
                  "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Amplify Post", "data-placement".reactAttr := "right",
                  ^.onClick ==> t.backend.amplifyPost)(
                  <.img(^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg)
                ),
                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.ampTokenBtn,
                  "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Forward Post", "data-placement".reactAttr := "right",
                  ^.onClick ==> t.backend.forwardPost)(
                  <.span(Icon.mailForward)
                )
              )
            ),
            <.div(^.className := "")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12")(
                  if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size > 80659) {
                    // getMessage = message
                    <.img(^.src := message.postContent.imgSrc, ^.className := "img-responsive", DashboardCSS.Style.cardImage)
                  } else {
                    // getMessage = null
                    <.span("")
                  },
                  <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                    <.h3(message.postContent.subject, DashboardCSS.Style.cardHeading),
                    <.div(DashboardCSS.Style.cardText)(
                      if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size > 80659) {

                        <.div(DashboardCSS.Style.cardText, ^.onClick --> t.backend.openFullViewModalPopUP(message))(
                          if (messageText.length == 1) {
                            messageText(0)
                          } else
                            for {b <- 0 to messageText.length - 1 if b <= 30} yield {
                              messageText(b) + " "
                            }
                        )
                      } else {
                        <.div(^.className := "col-md-9 col-sm-9 col-xs-12", PostFullViewCSS.Style.marginLeft15PX)(
                          <.div(DashboardCSS.Style.cardText, ^.onClick --> t.backend.openFullViewModalPopUP(message))(
                            if (messageText.length == 1) {
                              messageText(0)
                            } else
                              for {b <- 0 to messageText.length - 1 if b <= 30} yield {
                                messageText(b) + " "
                              }
                          )
                        )
                      },
                      if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size < 80659) {
                        <.div(^.className := "col-md-3 col-sm-3 col-xs-12")(
                          //println(s"message.postContent.imgSrc ${message.postContent.imgSrc.size}")
                          <.img(^.src := message.postContent.imgSrc, ^.height := "100.px", ^.width := "100.px", DashboardCSS.Style.imgBorder)
                        )
                      } else {
                        <.span("")
                      }
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
              <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                  <.div(^.onClick --> t.backend.openFullViewModalPopUP(message))(
                    for {b <- 2 to messageText.length if b >= 30} yield {
                      messageText(b) + " "
                    },
                    <.div(^.className := "col-md-12 text-uppercase")(
                      //                      for {label <- t.backend.filterLabelStrings(message.postContent.text.split(" +"))} yield {
                      //                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)(label)
                      //                      }
                    )
                  )
                )
              )
            ),
            <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-${message.uid}", ^.className := "glance-view-button", ^.onClick ==> t.backend.preventFullViewModalPopUP)(
                (MIcon.moreHoriz)
              )
            )
          )
        )
      }
      <.div(
        <.div(
          if (S.showAmplifyPostForm) {
            AmplifyPostForm(AmplifyPostForm.Props(t.backend.postAmplified))
          }
          else if (S.showForwardPostForm) {
            getSearches(searchesProxy => NewMessageForm(NewMessageForm.Props(t.backend.postForwarded, "New Message", searchesProxy)))
          }
          else {
            <.span()
          }
        ),
        <.div(
          if (S.showFullPostView) FullPostViewModal(FullPostViewModal.Props(t.backend.closeFullViewModalPopUp, S.messagePostForFullView))
          else Seq.empty[ReactElement]
        ),
        <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer, ^.onScroll ==> t.backend.handleScroll)(
          P.messages map renderMessages
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(messages: Seq[MessagePost]) = MessagesList(Props(messages))

}