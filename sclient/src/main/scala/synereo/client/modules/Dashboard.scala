package synereo.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react.{ReactElement, _}
import japgolly.scalajs.react.vdom.prefix_<^.{<, _}
import org.querki.jquery._
import shared.models.{MessagePost, MessagePostContent}
import synereo.client.rootmodels.MessagesRootModel
import synereo.client.components._
import synereo.client.css.{DashboardCSS, SynereoCommanStylesCSS, UserProfileViewCSS}
import synereo.client.modalpopups.{AmplifyPostForm, FullPostViewModal, NewMessageForm, ServerErrorModal}

import scalacss.ScalaCssReact._
import scala.scalajs.js
import synereo.client.components.{FeedViewRightAnimC, Icon}

import scala.language.reflectiveCalls
import org.widok.moment.Moment
import shared.dtos.{ApiResponse, ErrorResponse, SendAmpsResponse}
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

//scalastyle:off
object Dashboard {

  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]], loginLoader: String = "loginLoader")

  case class State(ShowFullPostView: Boolean = false,
                   preventFullPostView: Boolean = true,
                   showErrorModal: Boolean = false)

  class DashboardBackend(t: BackendScope[Dashboard.Props, Dashboard.State]) {

    def serverError(): Callback = {
      t.modState(s => s.copy(showErrorModal = false))
    }
  }


  val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State())
    .backend(new DashboardBackend(_))
    .renderPS((t, props, state) => {
      val getServerError = SYNEREOCircuit.zoom(_.appRootModel).value
      <.div(^.id := "dashboardContainerMain", ^.className := "container-fluid")(
        <.div(^.className := "container-fluid", DashboardCSS.Style.homeFeedMainContainer)(
          <.div(^.className := "row")(
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12", DashboardCSS.Style.paddingLRZero)(
              <.div(
                props.proxy().renderFailed(ex => /*<.div(
                      //                      <.span(^.id := "loginLoader", SynereoCommanStylesCSS.Style.loading, ^.className := "", Icon.spinnerIconPulse),
                      <.div(SynereoCommanStylesCSS.Style.renderFailedMessage)(s"We are encountering problems with serving the request!${ex.getMessage}")
                    )*/
                  if (!getServerError.isServerError)
                    ServerErrorModal(ServerErrorModal.Props(t.backend.serverError))
                  else
                    <.span()
                ),
                props.proxy().render(
                  messagesRootModel =>
                    HomeFeedList(messagesRootModel.messagesModelList)
                ),
                props.proxy().renderPending(ex => <.div(
                  <.div(^.id := s"#${props.loginLoader}", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)
                )
                ),
                <.div(
                  if (props.proxy().isEmpty) {
                    <.div(^.id := s"#${props.loginLoader}", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)
                  } else {
                    <.span()
                  }
                )
              )
            )
          )
        )
      )
    })
    .build

  def apply(proxy: ModelProxy[Pot[MessagesRootModel]]) = component(Props(proxy))
}


object HomeFeedList {

  private val searchesProxy = SYNEREOCircuit.connect(_.searches)

  case class Props(messages: Seq[MessagePost])

  case class State(showFullPostView: Boolean = false,
                   showAmplifyPostForm: Boolean = false,
                   showForwardPostForm: Boolean = false,
                   messagePost: MessagePost = new MessagePost(postContent = new MessagePostContent()),
                   fromSender: String = "",
                   toReceiver: String = "",
                   senderAddress: String = "",
                   showReplyPostForm: Boolean = false)

  class HomeFeedListBackend(t: BackendScope[HomeFeedList.Props, HomeFeedList.State]) {
    def feedViewRightStatusAnimDiv(id: String, clasName: String) = Callback {
      val animDivId: js.Object = s"#$id"

      if ($(animDivId).hasClass("SynereoCommanStylesCSS_Style-feedViewLftAnimDivDisplayNone")) {
        $(s".$clasName".asInstanceOf[js.Object]).addClass("SynereoCommanStylesCSS_Style-feedViewLftAnimDivDisplayNone")
        $(animDivId).removeClass("SynereoCommanStylesCSS_Style-feedViewLftAnimDivDisplayNone")
        $(animDivId).addClass("SynereoCommanStylesCSS_Style-feedViewLftAnimDivDisplayInitial")
      } else {
        $(animDivId).addClass("SynereoCommanStylesCSS_Style-feedViewLftAnimDivDisplayNone")
        $(animDivId).removeClass("SynereoCommanStylesCSS_Style-feedViewLftAnimDivDisplayInitial")
      }
    }

    def amplifyPost(senderAddress: String): Callback = {
      t.modState(state => state.copy(showAmplifyPostForm = true, senderAddress = senderAddress))
    }

    def postAmplified(amount: String, to: String, isAmplified: Boolean): Callback = {
      logger.log.debug("postAmplified called")
      if (isAmplified) {
        logger.log.debug(s"Sending $amount AMPs to $to")
        CoreApi.sendAmps(amount, to).onComplete {
          case Success(res) =>
            if (res.contains("OK")) logger.log.debug(s"$amount AMPs were successfully sent.")
            else logger.log.debug(s"Failed to parse the response on sending AMPs: $res.")
          case Failure(res) =>
            logger.log.debug(s"Sending AMPs failed: $res.")
        }
      }
      t.modState(s => s.copy(showAmplifyPostForm = false))
    }

    def forwardPost(message: MessagePost): Callback = {
      //      logger.log.debug("forwarding a Post")
      t.modState(state => state.copy(showForwardPostForm = true, messagePost = message))
    }

    def replyPost(message: MessagePost): Callback = {
      //      logger.log.debug("replying a Post")
      t.modState(state => state.copy(showReplyPostForm = true, messagePost = message))
    }

    def postForwarded(): Callback = {
      //      logger.log.debug("postForwarded")
      t.modState(state => state.copy(showForwardPostForm = false))
    }

    def postReplyed(): Callback = {
      //      logger.log.debug("postRepled")
      t.modState(state => state.copy(showReplyPostForm = false))
    }


    def closeFullViewModalPopUp(): Callback = {
      t.modState(s => s.copy(showFullPostView = false))
    }

    def openFullViewModalPopUP(message: MessagePost, fromSender: String, toReceiver: String): Callback = {
      t.modState(s => s.copy(showFullPostView = true, messagePost = message, fromSender = fromSender, toReceiver = toReceiver))
    }

    def showPeekView(message: MessagePost): Callback = {
      if ($(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).find("#collapsePost").hasClass("hidden")) {
        $(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).find("#collapsePost").removeClass("hidden")
      } else {
        $(s"#home-feed-card-${message.uid}".asInstanceOf[js.Object]).find("#collapsePost").addClass("hidden")
      }
      t.modState(s => s.copy(showFullPostView = false))
    }

  }

  val MessagesList = ReactComponentB[Props]("MessagesList")
    .initialState_P(p => State())
    .backend(new HomeFeedListBackend(_))
    .renderPS((t, props, state) => {
      def renderMessages(message: MessagePost) = {
        <.li(^.id := s"home-feed-card-${message.uid}", ^.className := "media", DashboardCSS.Style.CardHolderLiElement)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-3 col-sm-2", SynereoCommanStylesCSS.Style.feedViewPostLeftDiv)(
              FeedViewLeftAnimC(FeedViewLeftAnimC.Props())
            ),
            <.div(^.className := "col-md-6 col-sm-8 showRightPost", ^.onClick --> t.backend.feedViewRightStatusAnimDiv(message.uid, "feedViewPost"))(
              <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-6",DashboardCSS.Style.dispalyFlex)(
                    <.div()(
                    if (message.sender.connection.target.split("/")(2) == SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)) {
                      <.img(^.className := "media-object", ^.src := SYNEREOCircuit.zoom(_.user).value.imgSrc, ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                    } else {
                      <.img(^.className := "media-object", ^.src := message.sender.imgSrc, ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                    }),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span(^.className := "fromSenderTooltip", "data-toggle".reactAttr := "tooltip", "title".reactAttr :=
                        (if (message.sender.name == "me")
                          message.sender.connection.source.split("/")(2)
                        else
                          message.sender.connection.target.split("/")(2)),
                        "data-placement".reactAttr := "right")(s"From  : ${message.sender.name}"),
                      <.div("data-toggle".reactAttr := "tooltip", "title".reactAttr := message.created, "data-placement".reactAttr := "right")(Moment(message.created).format("LLL").toLocaleString)
                    )
                  ),
                  <.div(^.className := "col-md-6", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                    <.div(DashboardCSS.Style.userNameDescription, DashboardCSS.Style.marginLeftPostView)(
                      <.span(s"To  : ${message.receivers.map(_.name).mkString(", ")}")
                    ),
                    <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                  )
                ),

                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12")(
                    if (message.postContent.imgSrc != "" /*&& message.postContent.imgSrc.size > 80659*/ ) {
                      <.div(
                        <.div(DashboardCSS.Style.cardPostImage)(
                          <.div(DashboardCSS.Style.cardImageContainer)(
                            <.img(^.src := message.postContent.imgSrc, ^.className := "img-responsive", DashboardCSS.Style.cardImage, ^.onClick --> t.backend.openFullViewModalPopUP(message, message.sender.name, message.receivers.map(_.name).mkString(", ")))
                          )
                        ),
                        <.div(DashboardCSS.Style.cardDescriptionContainerDiv, DashboardCSS.Style.cardPaddingBottom, ^.onClick --> t.backend.openFullViewModalPopUP(message, message.sender.name, message.receivers.map(_.name).mkString(", ")))(
                          <.h3(message.postContent.subject, DashboardCSS.Style.cardHeading),
                          <.div(^.id := "collapsePost", ^.className := "textOverflowPost", DashboardCSS.Style.cardText)(message.postContent.text),
                          <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                            message.postContent.text
                          )
                        )
                      )
                    } else {
                      <.div(
                        <.div(DashboardCSS.Style.cardDescriptionContainerDiv, DashboardCSS.Style.cardPaddingBottom, ^.onClick --> t.backend.openFullViewModalPopUP(message, message.sender.name, message.receivers.map(_.name).mkString(", ")))(
                          <.h3(message.postContent.subject, DashboardCSS.Style.cardHeading),
                          <.div(^.id := "collapsePost", ^.className := "textOverflowPost", DashboardCSS.Style.cardText)(message.postContent.text),
                          <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                            message.postContent.text
                          )
                        )
                      )
                    })),
                <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                  if (message.postContent.text.length > 105) {
                    <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                      "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-${message.uid}", ^.className := "glance-view-button", ^.onClick --> t.backend.showPeekView(message))(
                      (MIcon.moreHoriz)
                    )
                  }
                  else {
                    Seq.empty[ReactElement]
                  }
                )
              )
            ),
            <.div(^.id := message.uid, ^.className := "col-md-3 col-sm-2 feedViewPost", SynereoCommanStylesCSS.Style.feedViewLftHeightPost, SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone)(
              FeedViewRightAnimC(FeedViewRightAnimC.Props(message, t.backend.feedViewRightStatusAnimDiv, t.backend.replyPost, t.backend.forwardPost, t.backend.amplifyPost))
            )
          )
        )
      }
      <.div(
        <.div(
          if (state.showAmplifyPostForm) {
            AmplifyPostForm(AmplifyPostForm.Props(t.backend.postAmplified, state.senderAddress))
          } else if (state.showForwardPostForm) {
            searchesProxy(searchesProxy => NewMessageForm(NewMessageForm.Props(t.backend.postForwarded, "New Message", searchesProxy, state.messagePost,false, true)))
          } else if (state.showReplyPostForm) {
            searchesProxy(searchesProxy => NewMessageForm(NewMessageForm.Props(t.backend.postReplyed, "Reply", searchesProxy, state.messagePost, state.showReplyPostForm)))
          } else if (state.showFullPostView) {
            FullPostViewModal(FullPostViewModal.Props(t.backend.closeFullViewModalPopUp, state.messagePost, state.fromSender, state.toReceiver))
          } else {
            Seq.empty[ReactElement]
          }
        ),
        <.div(
          if (props.messages.isEmpty)
            <.div(^.className := "row text-center",DashboardCSS.Style.noMsg)("No results returned. Waiting for new results.")
          else
            <.div()
        ),
        //homeFeedMediaList id is important see synereo_app.js
        <.ul(^.id := "homeFeedMediaList", ^.className := "media-list ", DashboardCSS.Style.homeFeedContainer)(
          props.messages map renderMessages
        )
      )
    })
    .build

  def apply(messages: Seq[MessagePost]) = MessagesList(Props(messages))
}
