package synereo.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.{MessagePost}
import synereo.client.rootmodels.MessagesRootModel
import synereo.client.components._
import synereo.client.css.{DashboardCSS, PostFullViewCSS, SynereoCommanStylesCSS}
import synereo.client.modalpopups._
import scalacss.ScalaCssReact._
import scala.scalajs.js
import synereo.client.components.Icon
import scala.language.reflectiveCalls
import org.widok.moment.Moment
import synereo.client.services.SYNEREOCircuit
import synereo.client.modulebackends.{DashboardBackend, HomeFeedListBackend}


/**
  * Created by Mandar on 3/11/2016.
  */

//scalastyle:off
object Dashboard {


  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(ShowFullPostView: Boolean = false, preventFullPostView: Boolean = true, showErrorModal: Boolean = false)


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
          if (S.ShowFullPostView && S.preventFullPostView) FullPostViewModal(FullPostViewModal.Props(t.backend.closeFullViewModalPopUp))
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

  case class State(ShowFullPostView: Boolean = false, preventFullPostView: Boolean = true, showAmplifyPostForm: Boolean = false, showForwardPostForm: Boolean = false)

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
                    <.img(^.src := message.postContent.imgSrc, ^.className := "img-responsive", DashboardCSS.Style.cardImage)
                  }else {
                    <.span("")
                  },
                  <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                    <.h3(message.postContent.subject, DashboardCSS.Style.cardHeading),
                    <.div(DashboardCSS.Style.cardText)(
                      if (message.postContent.imgSrc != "" && message.postContent.imgSrc.size >= 80659) {
                        <.div(DashboardCSS.Style.cardText /*, ^.onClick ==> openFullViewModalPopUP*/)(
                          if (messageText.length == 1) {
                            messageText(0)
                          } else
                            for {b <- 0 to messageText.length - 1 if b <= 30} yield {
                              messageText(b) + " "
                            }
                        )
                      }else{
                      <.div(^.className := "col-md-9 col-sm-9 col-xs-12", PostFullViewCSS.Style.marginLeft15PX)(
                          <.div(DashboardCSS.Style.cardText /*, ^.onClick ==> openFullViewModalPopUP*/)(
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
                  <.div(/*, ^.onClick ==> openFullViewModalPopUP*/)(
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
                "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-${message.uid}" /*, ^.className := "glance-view-button", ^.onClick ==> preventFullViewModalPopUP*/)(
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
        <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer /*, ^.onScroll ==> handleScroll*/)(
          P.messages map renderMessages
        )
      )

    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(messages: Seq[MessagePost]) = MessagesList(Props(messages))

}