package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import livelygig.client.handlers.RefreshConnections
import livelygig.client.LGMain.Loc
import livelygig.client.rootmodels.ConnectionsRootModel
import livelygig.client.components.Bootstrap.Panel
import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import livelygig.client.components.Icon
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.modals.NewRecommendation
import livelygig.client.models.ConnectionsModel

//import livelygig.client.services.{ConnectionsRootModel, RefreshConnections}
import scalacss.ScalaCssReact._

object ConnectionsResults {

  case class Props(proxy: ModelProxy[Pot[ConnectionsRootModel]])
  case class State(selectedItem: Option[ConnectionsModel] = None)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) =
      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Connection")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
                <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer,DashBoardCSS.Style.verticalImg)(
                  <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "row")(
                    <.div(^.className := "col-md-4 col-sm-4 col-xs-4", ^.paddingRight := "0px", ^.paddingTop := "12px")(
                      <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle, ^.verticalAlign := "middle"),
                      //                      <.span(DashBoardCSS.Style.MarginLeftchkproduct, ^.className:="checkbox-lbl"),
                      <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown", ^.verticalAlign := "middle")(
                        <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                          <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
                        ),
                        <.ul(^.className := "dropdown-menu")(
                          <.li()(<.a(^.href := "#")("Hide")),
                          <.li()(<.a(^.href := "#")("Favorite")),
                          <.li()(<.a(^.href := "#")("Unhide")),
                          <.li()(<.a(^.href := "#")("Unfavorite"))
                        )
                      ) //dropdown class
                    ),
                    <.div(^.className := "col-md-8 col-sm-8 col-xs-8", ^.paddingLeft := "0px")(
                      <.div(DashBoardCSS.Style.rsltCountHolderDiv, ^.margin := "0px", ^.paddingTop := "19px")("2,352 Results"),
                      <.div(^.display := "inline-block")(
                        <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                          <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.borderWidth := "0px", ^.paddingTop := "0px", ^.paddingBottom := "2px")("By Date ")(
                            <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
                          ),
                          <.ul(^.className := "dropdown-menu")(
                            <.li()(<.a(^.href := "#")("By Date")),
                            <.li()(<.a(^.href := "#")("By Experience")),
                            <.li()(<.a(^.href := "#")("By Reputation")),
                            <.li()(<.a(^.href := "#")("By Rate")),
                            <.li()(<.a(^.href := "#")("By Projects Completed"))
                          )
                        ),
                        <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                          <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.padding := "0px", ^.paddingBottom := "2px", ^.border := "0px")("Newest ")(
                            <.span(Icon.longArrowDown))
                        )
                      ),
                      <.div(^.className := "pull-right", ^.paddingTop := "10px")(
                        // todo: icon buttons should be different.  Earlier mockup on s3 had <span class="icon-List1">  2  3  ?
                        <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")( <.span(^.className:="icon-List1")),
                        <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")( <.span(^.className:="icon-List2")),
                        <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")( <.span(^.className:="icon-List3"))
                      )
                    )
                  ), //col-12
              //    <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
                  <.div(^.className := "container-fluid", ^.id := "resultsConnectionsContainer",DashBoardCSS.Style.verticalImg)(
                    P.proxy().render(connectionsRootModel =>
                      ConnectionList(connectionsRootModel.connectionsResponse)
                    ),
//                    P.proxy().renderPending(_ > 5, _ => "Loading..."),
                    P.proxy().renderFailed(ex =>  <.div( <.span(Icon.warning)," Error loading")),
                    if (P.proxy().isEmpty) {
                      if (!P.proxy().isFailed) {
                        <.div("Loading")
                        <.img(^.src:="./assets/images/processing.gif")
                      } else {
                        <.div()
                      }
                    } else {
                      <.div("data loaded")
                    }
                  )


      ) //mainContainer
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(proxy: ModelProxy[Pot[ConnectionsRootModel]]) = component(Props(proxy))
}

object ConnectionList {

  case class ConnectionListProps(connections: Seq[ConnectionsModel])

   val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionList")
    .render_P(p => {
      def renderConnections(connection: ConnectionsModel) = {
        <.li(^.className := "media  profile-description", DashBoardCSS.Style.rsltpaddingTop10p)(
          <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
          <.span(^.className := "checkbox-lbl"),
          if (!connection.name.isEmpty) {
            <.div(DashBoardCSS.Style.profileNameHolder)(connection.name)
          } else {
            <.span()
          },
          <.div(^.className := "col-md-12")(
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Software Developer"),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Pune, India"),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Connected since 2014-01-02"),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Profiles: ",
              <.a()(^.href := "", "title".reactAttr := "Videographer")("Videographer"),
              " | ",
              <.a()(^.href := "", "title".reactAttr := "Web Developer")("Web Developer"),
              " | ",
              <.a()(^.href := "", "title".reactAttr := "Janal, LLC")("Janal, LLC")
            ),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("My Groups: ",
              <.a()(^.href := "", "title".reactAttr := "Film Industry")("Film Industry"),
              ", ",
              <.a()(^.href := "", "title".reactAttr := "Full Stack Developers")("Full Stack Developers"),
              ", ",
              <.a()(^.href := "", "title".reactAttr := "...")(",,,")
            )
          ),
          <.div(^.className := "media-left")(
            <.img(DashBoardCSS.Style.profileImg, ^.src := connection.imgSrc, ^.borderRadius := "25px", ^.alt := "Connection Source: " + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label)
          ),
          <.div(^.className := "media-body")(
            <.div(^.className := "col-md-12 col-sm-12 ")(
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Hide")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Favorite")(),
              <.button(HeaderCSS.Style.rsltContainerBtn,^.className := "btn profile-action-buttons")("Recommend")(),
              // ToDo: Above should use something like:
              // NewRecommendation(NewRecommendation.Props(ctl, "Recommend")),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Introduce")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Message")()
              // ToDo: Above should use something like:
              // NewMessage(NewMessage.Props(ctl, "Message")
            )
          )
        )
      }
      <.div( ^.className:="rsltSectionContainer",^.className := "col-md-12 col-sm-12 col-xs-12",/*DashBoardCSS.Style.verticalImg,*/ ^.paddingLeft := "0px", ^.paddingRight := "0px")(
        <.ul(^.className := "media-list")(p.connections map renderConnections)
      )
    })
    .build

  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}