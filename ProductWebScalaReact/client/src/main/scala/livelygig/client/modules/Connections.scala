package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import livelygig.client.Handlers.RefreshConnections
import livelygig.client.LGMain.Loc
import livelygig.client.RootModels.ConnectionsRootModel
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

object Connections {

  case class Props(proxy: ModelProxy[Pot[ConnectionsRootModel]])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) =
      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections))
  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Connection")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div()(
          //ToDo:  something like...   Presets(Presets.Props(ctl, "connections"))
          ConnectionsPresets.component(Unit)
        ),
        <.div(DashBoardCSS.Style.splitContainer)(
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "split col-lg-10 col-md-12", ^.paddingRight := "0px")(
            //<.div(^.className := "row")(
            <.div(^.className := "col-xs-3", ^.padding := "0px", ^.overflow := "hidden")(
              ConnectionsSearch.component(Unit)
            ),
            <.div(^.className := "col-xs-9", ^.id := "dashboardResults2", DashBoardCSS.Style.dashboardResults2)(
              <.div()(
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
                      <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")(<.span(Icon.list)),
                      <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")(<.span(Icon.list)),
                      <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")(<.span(Icon.list))
                    )
                  )
                ), //col-12

                <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
                  <.div(^.className := "container-fluid", ^.id := "resultsConnectionsContainer")(
                    P.proxy().render(connectionsRootModel =>
                      ConnectionList(connectionsRootModel.connectionsResponse)
                    )
                  )
                  /*Panel(Panel.Props("Connections"), <.div(
                    P.proxy().renderFailed(ex => "Error loading"),
                    P.proxy().renderPending(_ > 500, _ => "Loading..."),
                    P.proxy().render(connectionsRootModel =>
                      ConnectionList(connectionsRootModel.connectionsResponse)
                    )
                  ))*/
                )
              )
            )
          )
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

  private val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionList")
    .render_P(p => {
      def renderConnections(connection: ConnectionsModel) = {
        <.li(^.className := "media", DashBoardCSS.Style.rsltContentBackground, DashBoardCSS.Style.rsltpaddingTop10p)(
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
            )
          ),
          <.div(^.className := "media-left")(
            <.img(DashBoardCSS.Style.profileImg, ^.src := connection.imgSrc, ^.borderRadius := "25px", ^.alt := "Connection Source: " + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label)
          ),
          <.div(^.className := "media-body")(
            <.div(^.className := "col-md-12 col-sm-12")(
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Hide")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Favorite")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Recommend")(),
              // ToDo: Above should use something like:
              // NewRecommendation(NewRecommendation.Props(ctl, "Recommend")),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Introduce")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Message")()
              // ToDo: Above should use something like:
              // NewMessage(NewMessage.Props(ctl, "Message")
            )
          )
        )
      }
      <.ul(^.className := "media-list")(p.connections map renderConnections)
    })
    .build

  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}