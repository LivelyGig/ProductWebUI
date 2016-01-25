package livelygig.client.modules

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import livelygig.client.components.Bootstrap.Panel
import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.models.ConnectionsModel
import livelygig.client.services.{ConnectionsRootModel, RefreshConnections}
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
      <.div(^.id := "mainContainer" /*, DashBoardCSS.Style.mainContainerDiv*/ , ^.className := "mainContainerDiv")(
        <.div()(
          ConnectionsPresets.component(Unit)
        ),
        // AddNewAgent(AddNewAgent.Props(ctl)),
        <.div(/*DashBoardCSS.Style.splitContainer*/ ^.className := "splitContainer")(
          <.div(^.className := "split")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                // todo: Need to parameterize the Search area depending on EntityType (e.g. Talent, Project) and preset
                ConnectionsSearch.component(Unit)
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10", ^.id := "resultsContainer", ^.id := "dashboardResults2", ^.className := "dashboardResults2")(
                <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
                  <.div(^.className := "container-fluid", ^.id := "resultsConnectionsContainer")(
                    <.div(^.id := "rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
                      // todo: Results will be parameterized depending on EntityType, preset
                      P.proxy().render(connectionsRootModel =>
                        ConnectionList(connectionsRootModel.connectionsResponse)
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
          )
        ) //row
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
        //        <.li(
        //          //         <.span( connection.content.jsonBlobModel.get.name)
        //          if (!connection.name.isEmpty){
        //            <.div(^.className:="col-md-12")(connection.name)
        //          }
        //          else {
        //            <.span()
        //          }
        //
        //        )

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
              <.a()(^.href:="", "title".reactAttr :="Videographer")("Videographer"),
              "| ",
              <.a()(^.href:="", "title".reactAttr :="Web Developer")("Web Developer"),
              "| ",
              <.a()(^.href:="", "title".reactAttr :="Janal, LLC")("Janal, LLC")
            )

          ),
          <.div(^.className := "media-left")(
            <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
          ), //media-left
          <.div(^.className := "media-body")(
            //*"lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",*
            <.div(^.className := "col-md-12 col-sm-12")(
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Hide")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Favorite")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Recommend")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Introduce")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Message")()
            )
          ) //media-body
        )


      }
      <.ul(^.className := "media-list")(p.connections map renderConnections)

    })
    .build

  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}