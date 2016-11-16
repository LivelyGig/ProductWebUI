package synereo.client.modules


import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._
import synereo.client.rootmodels.ConnectionsRootModel
import diode.react._
import synereo.client.css.{ConnectionsCSS, _}
import shared.models.ConnectionsModel
import org.querki.jquery._
import synereo.client.modalpopups.NewConnection
import scala.scalajs.js
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 5/18/2016.
  */

object ConnectionsResults {

  case class Props(proxy: ModelProxy[ConnectionsRootModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  class Backend(t: BackendScope[Props, State]) {

  }
  // create the React component for user's connections
  val component = ReactComponentB[Props]("ConnectionsResults")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, state) => {
      <.div(^.className := "container-fluid")(
        <.div(^.className := "row ", ConnectionsCSS.Style.connectionContainer)(
          <.div(^.className := "col-md-3 col-sm-3 col-xs-1 col-lg-3"),
          <.div(^.className := "col-md-6 col-sm-6 col-xs-11 col-lg-6", ConnectionsCSS.Style.connectionContainerMiddle,
            ConnectionsList(props.proxy().connectionsResponse)
          ),
          <.div(^.className := "col-md-3 col-sm-3 col-xs-0 col-lg-3"
          )
        ),
        <.div(<.span(ConnectionsCSS.Style.connectfriendsBtn,
          <.span(^.className := "fa fa-plus", ConnectionsCSS.Style.connectfriendsIcon),
          NewConnection(NewConnection.Props("", Seq(ConnectionsCSS.Style.connectfriendsIconText), "", "Connect Friend"))
        )
        )
      )
    })
    .build

  def apply(proxy: ModelProxy[ConnectionsRootModel]) = component(Props(proxy))
}

object ConnectionsList {

  case class ConnectionListProps(connections: Seq[ConnectionsModel])

  case class State(showAmplifyPostForm: Boolean = false, senderAddress: String = "")

  class Backend(t: BackendScope[ConnectionListProps, State]) {

    def amplifyPost(senderAddress: String): Callback = {
      t.modState(state => state.copy(showAmplifyPostForm = true, senderAddress = senderAddress ))
    }

    def introduceUsers(id: String) = Callback {
      val topBtn: js.Object = "#"+id
      if ($(topBtn).hasClass("ConnectionsCSS_Style-connectionAvatarDiv")) {
        $(topBtn).addClass("ConnectionsCSS_Style-onconnectionAvatarDivClick")
        $(topBtn).removeClass("ConnectionsCSS_Style-connectionAvatarDiv")
      }
      else {
        $(topBtn).removeClass("ConnectionsCSS_Style-onconnectionAvatarDivClick")
        $(topBtn).addClass("ConnectionsCSS_Style-connectionAvatarDiv")
      }
    }
  }

  val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionsList")
    .initialState_P(p => State())
    .backend(new Backend(_))
    .renderPS((b, p, s) => {
      def renderConnections(connection: ConnectionsModel) = {
        <.li(^.className := " col-md-5 col-sm-5 col-xs-12 col-lg-5 Posts",
          <.div(^.className := "", <.div(^.className := "row ", ConnectionsCSS.Style.fullUserDescription)(
            <.div(^.className := "col-md-3 col-sm-3 col-xs-3 col-lg-3", ConnectionsCSS.Style.connectionAvatarContainer, ^.id :={""+connection.connection.target.substring(8).split("/")(0)} , ^.onClick --> b.backend.introduceUsers(""+connection.connection.target.substring(8).split("/")(0))
            )(
              if (connection.imgSrc.isEmpty) {
                <.img(^.className:="img-responsive img-circle",^.src := "./assets/images/default_avatar.jpg", ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                  + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label)
              } else {
                <.img(^.className:="img-responsive img-circle",^.src := connection.imgSrc, ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                  + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label)
              }
            ),
            <.div(^.className :="col-md-9 col-sm-9 col-xs-9 col-lg-9 row ConnectionPostShadow",ConnectionsCSS.Style.connectionNameContainer)   (
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10 col-lg-10", ConnectionsCSS.Style.connectionNameHolder)(<.h4(  ConnectionsCSS.Style.connectionName)(
                if (connection.name.nonEmpty) {
                  <.div(ConnectionsCSS.Style.connectionName,
                    connection.name,
                    <.div(ConnectionsCSS.Style.connectionInfoTooltip, ^.className := "infoTooltip",
                      <.span()(s"UID: "),
                      connection.connection.target.substring(8).split("/")(0)
                    )
                  )
                } else {
                  <.span()
                }
              ),
                <.h5(^.className := "", ConnectionsCSS.Style.connectionNumbers)("7 connections")
              ),
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2 col-lg-2", ConnectionsCSS.Style.userActionsMenu)(
                <.span(<.span(^.className := "fa fa-comment btn   ", ConnectionsCSS.Style.userActionIcons),
                  <.span(^.className := "privacyOptionDropdown")(<.button(^.className := "fa fa-star btn  dropdown  ", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", ConnectionsCSS.Style.userActionIcons),
                    <.ul(^.className := "dropdown-menu privacyOptionDropdownMenu", ConnectionsCSS.Style.userActionDropdownMenu)(
                      <.li(<.a("Work - Keep me Private")),
                      <.li(<.a("Only Direct Interactions")),
                      <.li(<.a("I am an Open Book")),
                      <.li(^.className := "divider"),
                      <.li(<.a("Create/ add to list")),
                      <.li(<.a("Introduce to a  friend")),
                      <.li(^.className := "divider"),
                      <.li(<.a("Unfriend"))
                    )
                  ))

              ))
          ),
            <.div(^.className := "row popularTags", ConnectionsCSS.Style.userPopularTags)(
              <.button(^.className := " col-md-3 col-sm-3 col-xs-3 col-lg-3", ^.`type` := "button", "User Experts", ConnectionsCSS.Style.userPopularTag),
              <.button(^.className := "col-md-3 col-sm-3 col-xs-3 col-lg-3", ^.`type` := "button", "User Experts", ConnectionsCSS.Style.userPopularTag),
              <.button(^.className := " col-md-3 col-sm-3 col-xs-3 col-lg-3", ^.`type` := "button", "User Experts", ConnectionsCSS.Style.userPopularTag)
            )
          )
        )
      }
      <.ul(^.className := " row", ConnectionsCSS.Style.fullDescContainer)(p.connections map renderConnections)
    })
    .build

  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}
