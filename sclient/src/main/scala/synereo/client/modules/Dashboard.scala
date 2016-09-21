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
import shared.dtos.{ApiResponse, ErrorResponse, SendAmpsResponse}
import synereo.client.handlers.ShowServerError
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.scalajs.js.timers._
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

//scalastyle:off
object Dashboard {


  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(ShowFullPostView: Boolean = false, preventFullPostView: Boolean = true, showErrorModal: Boolean = false)


  class DashboardBackend(t: BackendScope[Dashboard.Props, Dashboard.State]) {
    //scalastyle:off

    val messageLoader: js.Object = "#messageLoader"
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
      t.modState(s => s.copy(showErrorModal = false))
    }

    /*   def showrightPost(showrightPost: Boolean = false): Callback = {
         if (showrightPost) {
           println(s"ShowrightPost: ${showrightPost}")
           t.modState(s => s.copy(showrightPost = true))
         }
         else {
           println(s"ShowrightPost: ${showrightPost}")
           t.modState(s => s.copy(showrightPost = false))
         }
       }*/
  }

  val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State())
    .backend(new DashboardBackend(_))
    .renderPS((t, P, S) => {
      val getServerError = SYNEREOCircuit.zoom(_.appRootModel).value

      <.div(^.id := "dashboardContainerMain", ^.className := "container-fluid")(
        <.div(^.className := "container-fluid", DashboardCSS.Style.homeFeedMainContainer)(
          <.div(^.className := "row")(
            //            <.div(^.className := "col-lg-3 col-md-3 col-sm-3 col-xs-12")("Hello"),
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12", DashboardCSS.Style.paddingLeftRight)(
              <.div(^.className := "row")(
                <.div(^.className := "col-sm-12 col-md-12 col-lg-12", DashboardCSS.Style.paddingLeftRight)(
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
                        HomeFeedList(messagesRootModel.messagesModelList /*, t.backend.showrightPost*/)
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
                              <.div(^.className := "col-md-1 pull-left")(
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
                              <.div(^.className := "col-md-1 pull-left")(
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
            ) /*,
            <.div(^.className := "col-lg-3 col-md-3 col-sm-3 col-xs-12")(

            )*/
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
  val rightPost: js.Object = "#rightPost"
  val FeedTimeOut = 1500
  var collapsiblePost = ""
  val searchesProxy = SYNEREOCircuit.connect(_.searches)

  case class Props(messages: Seq[MessagePost] /*, rightPost: (Boolean) => Callback*/)

  case class State(showFullPostView: Boolean = false, showAmplifyPostForm: Boolean = false, showForwardPostForm: Boolean = false,
                   messagePost: MessagePost = new MessagePost(postContent = new MessagePostContent()),
                   fromSender: String = "", toReceiver: String = "", senderAddress: String = "", showLovePost: Boolean = false,
                   showCommentPost: Boolean = false,
                   showCirclePost: Boolean = false,
                   showSharePost: Boolean = false
                  )

  class HomeFeedListBackend(t: BackendScope[HomeFeedList.Props, HomeFeedList.State]) {

    def mounted() = Callback {

    }

    def getAllMessagePostDetails(message: MessagePost): (String, Seq[String], String, String, String) = {
      val selfConnectionId = message.connections(0).source.split("/")(2)
      val connections = SYNEREOCircuit.zoom(_.connections).value.connectionsResponse
      var senderName = "unknown"
      var imgContentOfMessagePost = ""
      var sendAmpsTo = ""
      var fromSenderUID = ""
      var receiverNames: Seq[String] = Seq()
      val userId = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)
      if (userId == selfConnectionId) {
        fromSenderUID = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)
        senderName = "me"
        sendAmpsTo = userId
        for (b <- message.connections; a <- connections; if (a.connection.source.split("/")(2) == b.source.split("/")(2) && a.connection.target.split("/")(2) == b.target.split("/")(2))) yield {
          receiverNames :+= a.name
        }

      } else {
        for (b <- message.connections; a <- connections; if (a.connection.source.split("/")(2) == b.target.split("/")(2) && a.connection.target.split("/")(2) == b.source.split("/")(2))) yield {
          senderName = a.name
          fromSenderUID = a.connection.source.split("/")(2)
          imgContentOfMessagePost = a.imgSrc
          sendAmpsTo = a.connection.target.split("/")(2)
        }
        // ToDo: Look up name of Sender and use friendly name
        receiverNames :+= "me"
      }
      (senderName, receiverNames, imgContentOfMessagePost, sendAmpsTo, fromSenderUID)
    }

    def filterLabelStrings(value: Seq[String]): Seq[String] = {
      value.filter(
        _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
      ).map(_.replace("#", "")).distinct
    }

    def amplifyPost(e: ReactEventI): Callback = {
      logger.log.debug("amplifyPost called")
      val senderAddress = e.target.id
      t.modState(state => state.copy(showAmplifyPostForm = true, senderAddress = senderAddress))
    }

    def postAmplified(amount: String, to: String, isAmplified: Boolean): Callback = {
      logger.log.debug("postAmplified called ")
      if (isAmplified) {
        logger.log.debug(s"Sending $amount AMPs to $to")
        CoreApi.sendAmps(amount, to).onComplete {
          case Success(res) =>
            Try(upickle.default.read[ApiResponse[SendAmpsResponse]](res)).toOption match {
              case Some(v) => logger.log.debug(v.content.transaction)
              case None =>
                Try(upickle.default.read[ApiResponse[ErrorResponse]](res)).toOption match {
                  case Some(v) => logger.log.debug(v.content.reason)
                  case None => logger.log.debug("Failed to parse the response on sending AMPs")
                }
            }
          case Failure(res) =>
            logger.log.debug(s"sending AMPs failed: $res")
        }
      }
      t.modState(s => s.copy(showAmplifyPostForm = false))
    }

    def forwardPost(message: MessagePost): Callback = {
      logger.log.debug("forwarding a Post")
      t.modState(state => state.copy(showForwardPostForm = true, messagePost = message))
    }

    def postForwarded(): Callback = {
      logger.log.debug("postForwarded")
      t.modState(state => state.copy(showForwardPostForm = false))
    }


    def closeFullViewModalPopUp(): Callback = {
      $(dashboardContainerMain).addClass("SynereoCommanStylesCSS_Style-overflowYScroll")
      t.modState(s => s.copy(showFullPostView = false))
    }

    def openFullViewModalPopUP(message: MessagePost, fromSender: String, toReceiver: String): Callback = {
      $(dashboardContainerMain).removeClass("SynereoCommanStylesCSS_Style-overflowYScroll")
      t.modState(s => s.copy(showFullPostView = true, messagePost = message, fromSender = fromSender, toReceiver = toReceiver))
    }

    def preventFullViewModalPopUP(message: MessagePost): Callback = {
      if ($(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).find("#rightPost").hasClass("hidden")) {
        $(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).find("#rightPost").removeClass("hidden")
        println( $(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).height() +   $(s"#collapse-post-${message.uid}".asInstanceOf[js.Object]).height())
        $(rightPost).height($(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).height() /*+   $(s"#collapse-post-${message.uid}".asInstanceOf[js.Object]).height()*/)
      } else {
        $(rightPost).height($(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).height()/* +   $(s"#collapse-post-${message.uid}".asInstanceOf[js.Object]).height()*/)
        $(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).find("#rightPost").addClass("hidden")
      }

      t.modState(s => s.copy(showFullPostView = false))
    }

    def handleScroll(e: ReactEvent): Callback = {
      Callback.empty
    }


    def handleMouseEnterEvent(e: ReactEvent): Callback = {
      val targetLi = e.target
      //      val collapsiblePost: js.Object = $(targetLi).find(".collapse")
      //      setTimeout(FeedTimeOut) {
      //        if (!$(collapsiblePost).hasClass("in")) {
      //          $(collapsiblePost).addClass("in")
      //          $(lftPost).show()
      //        }
      //      }
      val collapsedPost: js.Object = $(targetLi).find(".collapse")
      if ($(collapsedPost).hasClass("in")) {
        collapsiblePost = $(targetLi).height().toString
        //println(s"Height = ${collapsiblePost}")
//        $(rightPost).height(collapsiblePost)
      }
      Callback.empty

    }

    def showRightPost(getRightPost: String): Callback = {
      getRightPost match {
        case "showLovePost" => {
          t.modState(s => s.copy(showLovePost = true, showSharePost = false, showCirclePost = false, showCommentPost = false))
        }
        case "showCommentPost" => {
          t.modState(s => s.copy(showCommentPost = true, showSharePost = false, showCirclePost = false, showLovePost = false))
        }
        case "showCirclePost" => {
          t.modState(s => s.copy(showCirclePost = true, showCommentPost = false, showLovePost = false, showSharePost = false))
        }
        case "showSharePost" => {
          t.modState(s => s.copy(showSharePost = true, showCirclePost = false, showCommentPost = false, showLovePost = false))
        }
        case _ => {
          t.modState(s => s.copy(showSharePost = false, showCirclePost = false, showCommentPost = false, showLovePost = false))
        }
      }
    }
  }

  val MessagesList = ReactComponentB[Props]("MessagesList")
    .initialState_P(p => State())
    .backend(new HomeFeedListBackend(_))
    .renderPS((t, P, S) => {
      def renderMessages(message: MessagePost) = {
        val allWordsFrmMessageText = message.postContent.text.split(" ")
        val (senderName, receiverNames, imgContentOfMessagePost, sendAmpsTo, fromSenderUID) = t.backend.getAllMessagePostDetails(message: MessagePost)

        <.li(^.id := s"home-feed-card-${message.uid}", ^.className := "media", DashboardCSS.Style.CardHolderLiElement /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/ , ^.onMouseEnter ==> t.backend.handleMouseEnterEvent)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-3 col-sm-3")(
              ""
            ),
            <.div(^.className := "col-md-6 col-sm-6")(
              <.div(^.className := "card-shadow", DashboardCSS.Style.userPost /*, ^.onMouseEnter ==> mouseEntered*/)(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-1 pull-left")(
                    if (imgContentOfMessagePost.length != 0)
                      <.img(^.className := "media-object", ^.src := imgContentOfMessagePost, ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                    else if (senderName.equals("me"))
                      <.img(^.className := "media-object", ^.src := SYNEREOCircuit.zoom(_.user).value.imgSrc, ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                    else
                      <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                  ),
                  <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                    <.div(DashboardCSS.Style.userNameDescription, ^.className := "pull-left")(
                      <.span(^.className := "fromSenderTooltip", "data-toggle".reactAttr := "tooltip", "title".reactAttr := fromSenderUID, "data-placement".reactAttr := "right")(s"From  : ${senderName}"),
                      <.div("data-toggle".reactAttr := "tooltip", "title".reactAttr := message.created, "data-placement".reactAttr := "right")(Moment(message.created).format("LLL").toLocaleString)
                    ),
                    <.div(DashboardCSS.Style.userNameDescription, SynereoCommanStylesCSS.Style.paddingLeft15p)(
                      <.span(s"To  : ${receiverNames.mkString(", ")}")
                    ),
                    <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert),
                    if (senderName.equals("me")) {
                      <.span()
                    } else {
                      <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.ampTokenBtn,
                        "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Amplify Post", "data-placement".reactAttr := "right",
                        ^.onClick ==> t.backend.amplifyPost)(
                        <.img(^.id := sendAmpsTo, ^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg)
                      )
                    },
                    <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.ampTokenBtn,
                      "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Forward Post", "data-placement".reactAttr := "right",
                      ^.onClick --> t.backend.forwardPost(message))(
                      <.span(Icon.mailForward)
                    )
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12")(
                    if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size > 80659) {
                      // getMessage = message
                      <.img(^.src := message.postContent.imgSrc, ^.className := "img-responsive", DashboardCSS.Style.cardImage, ^.onClick --> t.backend.openFullViewModalPopUP(message, senderName, receiverNames.mkString(", ")))
                    } else {
                      // getMessage = null
                      <.span("")
                    },
                    <.div(DashboardCSS.Style.cardDescriptionContainerDiv, DashboardCSS.Style.cardPaddingBottom)(
                      <.h3(message.postContent.subject, DashboardCSS.Style.cardHeading),
                      <.div(DashboardCSS.Style.cardText)(
                        if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size > 80659) {
                          <.div(DashboardCSS.Style.cardText, ^.onClick --> t.backend.openFullViewModalPopUP(message, senderName, receiverNames.mkString(", ")))(
                            if (allWordsFrmMessageText.length == 1) {
                              allWordsFrmMessageText(0)
                            } else
                              for {b <- 0 to allWordsFrmMessageText.length - 1 if b <= 30} yield {
                                allWordsFrmMessageText(b) + " "
                              }
                          )
                        } else if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size < 80659) {
                          <.div(^.className := "col-md-9 col-sm-9 col-xs-12", PostFullViewCSS.Style.marginLeft15PX)(
                            <.div(DashboardCSS.Style.cardText, ^.onClick --> t.backend.openFullViewModalPopUP(message, senderName, receiverNames.mkString(", ")))(
                              if (allWordsFrmMessageText.length == 1) {
                                allWordsFrmMessageText(0)
                              } else
                                for {b <- 0 to allWordsFrmMessageText.length - 1 if b <= 30} yield {
                                  allWordsFrmMessageText(b) + " "
                                }
                            )
                          )
                        }
                        else if (message.postContent.imgSrc == "") {
                          <.div(DashboardCSS.Style.cardText, ^.onClick --> t.backend.openFullViewModalPopUP(message, senderName, receiverNames.mkString(", ")))(
                            if (allWordsFrmMessageText.length == 1) {
                              allWordsFrmMessageText(0)
                            } else
                              for {b <- 0 to allWordsFrmMessageText.length - 1 if b <= 30} yield {
                                allWordsFrmMessageText(b) + " "
                              }
                          )

                        } else {
                          <.div()
                        },
                        if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size < 80659) {
                          <.div(^.className := "col-md-3 col-sm-3 col-xs-12")(
                            <.img(^.src := message.postContent.imgSrc, ^.height := "100.px", ^.width := "100.px", DashboardCSS.Style.imgBorder, ^.onClick --> t.backend.openFullViewModalPopUP(message, senderName, receiverNames.mkString(", ")))
                          )
                        } else {
                          Seq.empty[ReactElement]
                        }
                      )
                    )
                  )
                ),
                <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                  <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                    <.div(^.onClick --> t.backend.openFullViewModalPopUP(message, senderName, receiverNames.mkString(", ")))(
                      for {b <- 1 to allWordsFrmMessageText.length - 1 if b > 30} yield {
                        allWordsFrmMessageText(b) + " "
                      },
                      <.div(^.className := "col-md-12 text-uppercase")(
                        //                      for {label <- t.backend.filterLabelStrings(message.postContent.text.split(" +"))} yield {
                        //                        <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)(label)
                        //                      }
                      )
                    )
                  )
                ),
                <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                  if (allWordsFrmMessageText.length > 30) {
                    <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                      "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-${message.uid}", ^.className := "glance-view-button", ^.onClick --> t.backend.preventFullViewModalPopUP(message))(
                      (MIcon.moreHoriz)
                    )
                  }
                  else {
                    <.span()
                  }
                )
              )
            ),
            <.div(^.id := "rightPost", ^.className := "col-md-2 col-sm-3 hidden", SynereoCommanStylesCSS.Style.lftHeightPost)(

              <.div(^.id := "trans")(
                <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop20px)(
                  <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/Love.svg", ^.className := "rightPost", ^.onClick --> t.backend.showRightPost("showLovePost"))),
                  //                    <.div(^.className := "col-md-2" /*,DashboardCSS.Style.userNameDescription)("120"*/),
                  //                    if (S.showLovePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(),
                  //                    if (S.showLovePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(),
                  //                    if (S.showLovePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(),
                  //                    if (S.showLovePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div()
                  if (S.showLovePost) <.div(^.className := "col-md-8", SynereoCommanStylesCSS.Style.lovePost)() else <.div()
                ),
                <.div(^.className := "row", if (!S.showLovePost)SynereoCommanStylesCSS.Style.marginTop20px else ^.marginTop:="0.px")(
                  <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/Comment.svg", ^.className := "rightPost", ^.onClick --> t.backend.showRightPost("showCommentPost"))),
                  if (S.showCommentPost)  <.div(^.className := "col-md-2" ,DashboardCSS.Style.postDescription)("10") else <.div(^.className := "col-md-2")(),
                  if (S.showCommentPost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showCommentPost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showCommentPost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showCommentPost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")()
                ),
                <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop20px)(
                  <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/Amp_circle.gif", ^.className := "rightPost", ^.onClick --> t.backend.showRightPost("showCirclePost"))),
                  if (S.showCirclePost) <.div(^.className := "col-md-2" ,DashboardCSS.Style.postDescription)("12") else <.div(^.className := "col-md-2")(),
                  if (S.showCirclePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showCirclePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showCirclePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showCirclePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")()
                ),
                <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop20px)(
                  <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/Share.svg", ^.className := "rightPost", ^.onClick --> t.backend.showRightPost("showSharePost"))),
                  if (S.showSharePost)  <.div(^.className := "col-md-2" ,DashboardCSS.Style.postDescription)("1") else <.div(^.className := "col-md-2")(),
                  if (S.showSharePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showSharePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showSharePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")(),
                  if (S.showSharePost) <.div(^.className := "col-md-2")(<.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", ^.className := "rightPost")) else <.div(^.className := "col-md-2")()
                )
              )
            )
          )
        )
      }
      <.div(
        <.div(
          if (S.showAmplifyPostForm) {
            AmplifyPostForm(AmplifyPostForm.Props(t.backend.postAmplified, S.senderAddress))
          }
          else if (S.showForwardPostForm) {
            searchesProxy(searchesProxy => NewMessageForm(NewMessageForm.Props(t.backend.postForwarded, "New Message", searchesProxy, S.messagePost)))
          }
          else {
            <.span()
          }
        ),
        <.div(
          if (S.showFullPostView) FullPostViewModal(FullPostViewModal.Props(t.backend.closeFullViewModalPopUp, S.messagePost, S.fromSender, S.toReceiver))
          else Seq.empty[ReactElement]
        ),
        <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer, ^.onScroll ==> t.backend.handleScroll)(
          P.messages map renderMessages
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
//    .componentDidUpdate(scope => scope.$.backend.preventFullViewModalPopUP(MessagePost()))
    .build

  def apply(messages: Seq[MessagePost] /*, rightPost: (Boolean) => Callback*/) = MessagesList(Props(messages /*, rightPost*/))
}