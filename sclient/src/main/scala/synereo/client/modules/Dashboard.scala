package synereo.client.modules

import java.util.UUID
import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.sessionitems.SessionItems
import synereo.client.handlers.RefreshMessages
import org.scalajs.dom
import shared.models.{MessagePost, MessagePostContent}
import shared.RootModels.MessagesRootModel
import synereo.client.components._
import synereo.client.css.{DashboardCSS, PostFullViewCSS, SynereoCommanStylesCSS}
import synereo.client.modalpopups.{FullPostViewModal, NewMessage}

import scala.scalajs.js.timers._
import scalacss.ScalaCssReact._
import scala.scalajs.js
import org.querki.jquery._

import scala.scalajs.js.timers._
import synereo.client.components.Icon

import scala.language.reflectiveCalls
import org.scalajs.dom.window
import org.widok.moment.Moment
import synereo.client.services.SYNEREOCircuit

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

  case class State(postMessage: MessagePost, ShowFullPostView: Boolean = false, isMessagePosted: Boolean = false, preventFullPostView: Boolean = true)

  class Backend(t: BackendScope[Props, State]) {
    //scalastyle:off
    def postMessage(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      $(messageLoader).removeClass("hidden")
      $(messageLoader).addClass("hidden")
      t.modState(s => s.copy(isMessagePosted = true, postMessage = s.postMessage.copy(postContent = MessagePostContent("", ""))))
    }

    def mounted(props: Props) = {
      if (props.proxy().isEmpty) {
        props.proxy.dispatch(RefreshMessages())
        //        props.proxy.dispatch(RefreshMessages())
      } else {
        Callback.empty
      }
    }

    def updateContent(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(postContent = s.postMessage.postContent.copy(text = value))))
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

    //scalastyle:off
    def render(s: State, p: Props) = {
//      <.div(^.id := "dashboardContainerMain", ^.className := "container-fluid", DashboardCSS.Style.dashboardContainerMain)(
//        <.div(^.className := "row")(
//          //Left Sidebar
//          <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ",
//            ^.onMouseEnter --> Callback {
//              $(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")
//            },
//            ^.onMouseLeave --> Callback {
//              $(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")
//            }
//          )(
//            //            Footer(Footer.Props(c, r.page))
//            Sidebar(Sidebar.Props())
//          )
//        ),
      <.div(^.id := "dashboardContainerMain", ^.className := "container-fluid")(
        <.div(^.className := "container-fluid", DashboardCSS.Style.homeFeedMainContainer)(
          <.div(^.className := "row")(
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-sm-12 col-md-12 col-lg-12")(
                  <.div(^.className := "text-center")(<.span(^.id := "messageLoader", ^.color.white, ^.className := "hidden", Icon.spinnerIconPulse)),
                  <.div(
                    p.proxy().render(
                      messagesRootModel =>
                        HomeFeedList(messagesRootModel.messagesModelList)
                    ),
                    p.proxy().renderFailed(ex => <.div(
                      //                      <.span(^.id := "loginLoader", SynereoCommanStylesCSS.Style.loading, ^.className := "", Icon.spinnerIconPulse),
                      <.div(SynereoCommanStylesCSS.Style.renderFailedMessage)("We are encountering problems with serving the request!")
                    )
                    ),
                    p.proxy().renderPending(ex => <.div(
                      <.div(^.id := "loginLoader", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)
                    )
                    )
                    //                    p.proxy().renderPending(ex => <.div(<.span(^.id := "loginLoader", SynereoCommanStylesCSS.Style.messagesLoadingWaitCursor, ^.className := "", Icon.spinnerIconPulse)))
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
    .initialState_P(p => State(new MessagePost("", "", "", "", Nil, new MessagePostContent("", ""))))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[Pot[MessagesRootModel]]) = component(Props(proxy))
}

object HomeFeedList {

  val dashboardContainerMain: js.Object = "#dashboardContainerMain"
  val FeedTimeOut = 1500

  case class Props(messages: Seq[MessagePost])

  case class State(postMessage: MessagePost, ShowFullPostView: Boolean = false, isMessagePosted: Boolean = false, preventFullPostView: Boolean = true)

  class Backend(t: BackendScope[Props, State]) {

    def mounted() = Callback {

    }


    def updateContent(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(postContent = s.postMessage.postContent.copy(text = value))))
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


    def render(props: Props) = {

      def renderMessages(message: MessagePost) = {
//        val getMessageText = message.postContent.text.split(" ")
//        println("get = " + getMessageText.)
//        val getLen = getMessageText.length
//        println("getLen =" + getLen)
//        var a = 0


        val userId = window.sessionStorage.getItem(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI).split("/")(2)
        //        println("UserID = " + userId + "\n")
        //        println("Connections = " + message.connections + "\n")
        var selfConnectionId = message.connections(0).source.split("/")(2)

        //        var selfcnxn = message.connections(1).target.split("/")(2)
        //        println("Connection at 1 = " + message.connections(1))

        //        val value = SYNEREOCircuit.zoom(_.connections).value.get.connectionsResponse
        //        println("Connections = " + value)

        //        for(a <- value){
        //          if(a.connection.source.split("/")(2) == userId){
        //            println("We are in source of connection  UserID " + a.name+ "\n")
        //          }
        //          if(a.connection.source.split("/")(2) == selfcnxn){
        //            println("We are in source of connection  selfCnxn" + a.name+ "\n")
        //          }
        //          if(a.connection.target.split("/")(2) == selfcnxn ){
        //            println("We are in target of connection " + a.name+ "\n")
        //          }
        //          if(a.connection.target.split("/")(2) == userId){
        //            println("We are in targrt of connection " + a.name+ "\n")
        //          }
        //        }

        var toReceiver = "unknown"
        var fromSender = "unknown"
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
          fromSender = "me"
          // ToDo: Look up name of Sender and use friendly name
          toReceiver = "me"
        }

        <.li(^.id := s"home-feed-card-${message.uid}", ^.className := "media", DashboardCSS.Style.CardHolderLiElement, ^.onMouseEnter ==> handleMouseEnterEvent /*, ^.onMouseLeave ==> handleMouseLeaveEvent*/)(
          <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
            <.div(^.className := "")(
              <.div(^.className := "col-md-1")(
                <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
              ),
              <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                <.div(DashboardCSS.Style.userNameDescription)(
                  <.span(fromSender),
                  <.span(MIcon.chevronRight),
                  <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                  <.div("data-toggle".reactAttr := "tooltip", "title".reactAttr := message.created, "data-placement".reactAttr := "right")(Moment(message.created).format("LLL").toLocaleString)
                ),
                <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
              )
            ),
            <.div(^.className := "")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12")(
                  //                  <.img(^.src := "./assets/synereo-images/blogpostimg.png", ^.className := "img-responsive", DashboardCSS.Style.cardImage),
                  <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                    <.h3(message.postContent.subject, DashboardCSS.Style.cardHeading),
                    <.div(DashboardCSS.Style.cardText)(

                        <.div(^.className := "col-md-8 col-sm-8 col-xs-12", PostFullViewCSS.Style.marginLeft15PX)(
                          //                          <.div(message.postContent.text),
                          //                          for {b <- 1 to getLen} yield {
                          //                            if (b < (getLen.toInt / 2)) {
                          //                              <.div(DashboardCSS.Style.cardText, ^.onClick ==> openFullViewModalPopUP)(
                          //                                <.div(getMessageText(b))
                          //                              )
                          //                            }
                          //                            else {
                          //                              <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                          //                                <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, ^.onClick ==> openFullViewModalPopUP)(
                          //                                  <.div(getMessageText(b))
                          //                                ),
                          //                                <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          //                                  <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                          //                                  <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                          //                                  <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                          //                                  <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                          //                                  <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                          //                                )
                          //                              )
                          //                            }
                          //                          }

                          <.div(DashboardCSS.Style.cardText, ^.onClick ==> openFullViewModalPopUP)(
//                            if(getMessageText != null){
//                              for {b <- 1 to getLen} yield {
//                                if (b < (getLen.toInt / 2)) {
//                                  getMessageText(b) + " "
//                                }
//                                else ""
//                              }
//                            }else{
//                              message.postContent.text
//                            }
                            message.postContent.text
                          ),
                          <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                            <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, ^.onClick ==> openFullViewModalPopUP)(
//                              for {b <- 1 to getLen} yield {
//                                if (b > (getLen.toInt / 2)) {
//                                  getMessageText(b) + " "
//                                }
//                                else ""
//                              }


                              <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                                <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                                <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                                <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                                <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                                <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                              )
                            )

                          )
                        ) ,
                      <.div(^.className := "col-md-4 col-sm-4 col-xs-12")(
                        if (message.postContent.imgSrc != "") {
                          <.img(^.src := message.postContent.imgSrc, ^.height := "100.px", ^.width := "100.px", DashboardCSS.Style.imgBorder)
                        } else {
                          <.div("")
                        }

                      )
                    )

                    //                      <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                    //                        <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                    //                        <.div(DashboardCSS.Style.cardText, ^.onClick ==> openFullViewModalPopUP)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                    //                          "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                    //                        <.div(^.id := s"collapse-post-${message.uid}", ^.className := "collapse", DashboardCSS.Style.cardText)(
                    //                          <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, ^.onClick ==> openFullViewModalPopUP)(
                    //                            "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
                    //                          ),
                    //                          <.div(^.className := "col-md-12 text-uppercase", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                    //                            <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Iceland"),
                    //                            <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("SXSW"),
                    //                            <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Travel"),
                    //                            <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Landscape"),
                    //                            <.button(^.`type` := "button", ^.className := "btn btn-primary text-uppercase", DashboardCSS.Style.cardPostTagBtn)("Lorem")
                    //                          )
                    //                        ),
                    //                        <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                    //                          "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-${message.uid}", ^.className := "glance-view-button", ^.onClick ==> preventFullViewModalPopUP)(
                    //                          (MIcon.moreHoriz)
                    //                        )
                    //                      )
                  )
                )
              )
            )
            ,
            <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                "data-toggle".reactAttr := "collapse", "data-target".reactAttr := s"#collapse-post-${message.uid}", ^.className := "glance-view-button", ^.onClick ==> preventFullViewModalPopUP)(
                (MIcon.moreHoriz)
              )
            )
          ))
      }

      <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer, ^.onScroll ==> handleScroll)(
        props.messages map renderMessages
      )


    }
  }

  val MessagesList = ReactComponentB[Props]("ProjectList")


    // val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State(new MessagePost("", "", "", "", Nil, new MessagePostContent("", ""))))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(messages: Seq[MessagePost]) =
    MessagesList(Props(messages))

}