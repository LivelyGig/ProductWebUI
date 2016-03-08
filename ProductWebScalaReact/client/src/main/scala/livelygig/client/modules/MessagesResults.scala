package livelygig.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import livelygig.client.handlers.{RefreshMessages, RefreshProjects}
import livelygig.client.rootmodels.{MessagesRootModel, ProjectsRootModel}
import livelygig.client.css.{HeaderCSS, DashBoardCSS, LftcontainerCSS}
import livelygig.client.modals.PostNewMessage
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.{DashBoardCSS, HeaderCSS, MessagesCSS, ProjectCSS}
import livelygig.client.models.MessagesModel
import livelygig.client.modules.ConnectionList.ConnectionListProps
import scala.util.{Failure, Success}
import livelygig.client.modals.NewMessage
import scalacss.ScalaCssReact._


object MessagesResults {

  case class Props (proxy : ModelProxy[Pot[MessagesRootModel]])
  case class State(selectedItem: Option[MessagesModel] = None)
  class Backend($: BackendScope[Props, _]) {
    def mounted(props: Props) =
      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshMessages()))
  }

  val component = ReactComponentB[Props]("Messages")
    .backend(new Backend(_))
    .renderPS((B, P, S ) => {
      <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "row")(
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12", ^.paddingRight := "0px"/*, ^.paddingTop := "12px"*/)(
            <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle, ^.verticalAlign := "middle"),
            <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown", ^.verticalAlign := "middle")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className := "dropdown-menu")(
                <.li()(<.a()("Hide")),
                <.li()(<.a()("Favorite")),
                <.li()(<.a()("Unhide")),
                <.li()(<.a()("Unfavorite"))
              )
            ), //dropdown class
            <.div(DashBoardCSS.Style.rsltCountHolderDiv, ^.margin := "0px"/*, ^.paddingTop := "19px"*/)("2,352 Results")
          ),
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12")(

            <.div(^.display := "inline-block")(
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.borderWidth := "0px", ^.paddingTop := "0px", ^.paddingBottom := "2px")("By Date ")(
                  <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
                ),
                <.ul(^.className := "dropdown-menu")(
                  <.li()(<.a()("By Date")),
                  <.li()(<.a()("By Experience")),
                  <.li()(<.a()("By Reputation")),
                  <.li()(<.a()("By Rate")),
                  <.li()(<.a()("By Projects Completed"))
                )
              ),
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.padding := "0px"/*, ^.paddingBottom := "2px"*/, ^.border := "0px")("Newest ")(
                  <.span(Icon.longArrowDown))
              )
            ),
            <.div(^.className := "pull-right"/*, ^.paddingTop := "10px"*/)(
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")( <.span(^.className:="icon-List1")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")( <.span(^.className:="icon-List2")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")( <.span(^.className:="icon-List3"))
            )
          )
        ), //col-12
        <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.className := "rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.height := "100%", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
            P.proxy().render(messagesRootModel =>
              MessagesList(messagesRootModel.messagesModelList)
            ),
            P.proxy().renderFailed(ex => <.div(<.span(Icon.warning), " Error loading")),
            if (P.proxy().isEmpty) {
              if (!P.proxy().isFailed) {
                <.div(^.height := "100%", DashBoardCSS.Style.verticalImg)(
                  <.img(^.src := "./assets/images/processing.gif")
                )
              } else {
                <.div()
              }
            } else {
              <.div()
            }
          )
        ) //gigConversation
       /* <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.className:="rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
            <.ul(^.className := "media-list")(
              for (i <- 1 to 50) yield {
                <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p /*, DashBoardCSS.Style.rsltContentBackground*/)(
                  // if even row  DashBoardCSS.Style.rsltContentBackground
                  <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
                  <.span(^.className := "checkbox-lbl"),
                  <.div(DashBoardCSS.Style.profileNameHolder)("From : Pam   To : Abed , RS7851  12/04/2015  11:30am"),
                  <.div(^.className := "media-body")(
                    "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                    <.div(/*^.className := "col-md-12 col-sm-12 */ /*profile-action-buttons*/)(
                      <.button(^.tpe := "button", ^.className := "btn profile-action-buttons", HeaderCSS.Style.rsltContainerIconBtn, HeaderCSS.Style.floatBtn, ^.title := "Hide", Icon.remove),
                      <.button(^.tpe := "button", ^.className := "btn profile-action-buttons", HeaderCSS.Style.rsltContainerIconBtn, HeaderCSS.Style.floatBtn, ^.title := "Favorite", Icon.star),
                      NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.mailForward, "Forward")),
                      // ToDo:  minor bug here or in NewMessage?  The Reply icon draws too close to Forward.  Perhaps because two NewMessage objects next to each other? Style displayInitialButton has negative margins
                      NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.mailReply, "Reply"))
                    )
                  ) //media-body
                ) //li
              }
            )
          )
        )*/
      ) //gigConversation

    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build
  def apply(proxy: ModelProxy[Pot[MessagesRootModel]]) = component(Props(proxy))
}

object MessagesList {
  case class Props(messages: Seq[MessagesModel])

  private val MessagesList = ReactComponentB[Props]("ProjectList")
    .render_P(p => {
      def renderMessages(message: MessagesModel) = {
        <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p /*, DashBoardCSS.Style.rsltContentBackground*/)(
          // if even row  DashBoardCSS.Style.rsltContentBackground
          <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
          <.span(^.className := "checkbox-lbl"),
          <.div(DashBoardCSS.Style.profileNameHolder)(s"From : Pam   To : Abed , RS7851  12/04/2015  11:30am"),
          <.div(^.className := "media-body")(
            message.text,
            <.div(^.className := "col-md-12 col-sm-12 /*profile-action-buttons*/")(
              <.button(^.tpe := "button", ^.className := "btn profile-action-buttons", HeaderCSS.Style.rsltContainerIconBtn, HeaderCSS.Style.floatBtn, ^.title := "Hide", Icon.userTimes),
              <.button(^.tpe := "button", ^.className := "btn profile-action-buttons", HeaderCSS.Style.rsltContainerIconBtn, HeaderCSS.Style.floatBtn, ^.title := "Favorite", Icon.star),
              //<.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Hide")(),
              //<.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Favorite")(),
              //NewMessage(NewMessage.Props("Forward",Seq(HeaderCSS.Style.createNewProjectBtn),"","Forward")),
              //NewMessage(NewMessage.Props("Reply",Seq(HeaderCSS.Style.createNewProjectBtn),"","Reply"))
              NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.mailForward,"Forward")),
              NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.mailReply,"Reply" ))
              /* <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Forward")()*/
            )
          ) //media-body
        ) //li
      }
      <.div(^.className := "rsltSectionContainer")(
        <.ul(^.className := "media-list")(p.messages map renderMessages)
      )
    })
    .build

  def apply(messages: Seq[MessagesModel]) =
    MessagesList(Props(messages))

}
