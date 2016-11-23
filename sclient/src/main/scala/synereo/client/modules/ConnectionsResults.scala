package synereo.client.modules


import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._
import synereo.client.rootmodels.ConnectionsRootModel
import diode.react._
import japgolly.scalajs.react
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
      val topBtn: js.Object = s"#imageId${id}"
      if ($(topBtn).hasClass("ConnectionsCSS_Style-connectionAvatar")) {
        $(topBtn).addClass("ConnectionsCSS_Style-connectionAvatarSelected")
        $(topBtn).removeClass("ConnectionsCSS_Style-connectionAvatar")
      }
      else if($(topBtn).hasClass("ConnectionsCSS_Style-connectionAvatarSelected")){
        $(topBtn).removeClass("ConnectionsCSS_Style-connectionAvatarSelected")
        $(topBtn).addClass("ConnectionsCSS_Style-connectionAvatar")
      }
    }

    def displayUserSetting(id:String)=Callback{
      val usersettings : js.Object=s"#userSettings${id}"
      if($(usersettings).hasClass("ConnectionsCSS_Style-mediauserActionDropdownMenu")){
        $(usersettings).removeClass("ConnectionsCSS_Style-mediauserActionDropdownMenu")
      }
      else {
        $(usersettings).addClass("ConnectionsCSS_Style-mediauserActionDropdownMenu")
      }
    }

    def swappingEffect(id : String , direction : String): react.Callback = Callback {
      val swapedContent : js.Object = s"#swapedContent${id}"
      val defaultContent : js.Object= s"#defaultContent${id}"
      if(direction == "left") {
        $(defaultContent).addClass("ConnectionsCSS_Style-hideDiv")
        $(swapedContent).removeClass("ConnectionsCSS_Style-hideDiv")
      }
      else {
        $(defaultContent).removeClass("ConnectionsCSS_Style-hideDiv")
        $(swapedContent).addClass("ConnectionsCSS_Style-hideDiv")
      }
    }
  }

  val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionsList")
    .initialState_P(p => State())
    .backend(new Backend(_))
    .renderPS((b, p, s) => {
      def renderConnections(connection: ConnectionsModel) = {
        val postContainerId=connection.connection.target.substring(8).split("/")(0)
        <.li(^.className := " col-md-5 col-sm-5 col-xs-12 col-lg-5 post-container",ConnectionsCSS.Style.postContainer,

          <.span(^.id:={s"defaultContent${postContainerId}"},
            <.div(^.className := "row post-content", ConnectionsCSS.Style.fullUserDescription)(
              <.span( ^.className:="col-md-4 col-sm-4",ConnectionsCSS.Style.connectionAvatarContainer
              )(
                if (connection.imgSrc.isEmpty) {
                  <.img(^.className:="img-responsive img-circle",^.src := "./assets/images/default_avatar.jpg", ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                    + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label,
                    ^.id :={s"imageId${postContainerId}"} ,
                    ^.onClick --> b.backend.introduceUsers(s"${postContainerId}"))
                } else {
                  <.img(^.className:="img-responsive img-circle",^.src := connection.imgSrc, ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                    + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label ,
                    ^.id :={s"imageId${postContainerId}"} ,
                    ^.onClick --> b.backend.introduceUsers(s"${postContainerId}"))
                }
              ),
              <.span(^.className :="col-md-8 col-sm-8 row connection-post-shadow",ConnectionsCSS.Style.connectionNameContainer)
              (
                <.div(^.className := "col-md-10 col-sm-10 col-lg-10", ConnectionsCSS.Style.connectionNameHolder)(
                  if (connection.name.nonEmpty) {
                    <.h4(ConnectionsCSS.Style.connectionName,
                      connection.name,
                      <.div(ConnectionsCSS.Style.connectionInfoTooltip, ^.className := "infoTooltip",
                        <.span()(s"UID: "),
                        connection.connection.target.substring(8).split("/")(0)
                      )
                    )
                  } else {
                    <.span()
                  },
                  <.h5( ConnectionsCSS.Style.connectionNumbers)("7 connections")
                ),
                <.div(^.className := "col-md-2 col-sm-2  col-lg-2", ConnectionsCSS.Style.userActionsMenu)(
                  <.span(<.span(^.className := "fa fa-comment btn   ", ConnectionsCSS.Style.userActionIcons),
                    <.span(^.className := "privacy-option-dropdown")(<.button(^.className := "fa fa-star btn  dropdown  ", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", ConnectionsCSS.Style.userActionIcons),
                      <.ul(^.className := "dropdown-menu privacy-option-dropdown-menu", ConnectionsCSS.Style.userActionDropdownMenu)(
                        <.li(<.a("Work - Keep me Private")),
                        <.li(<.a("Only Direct Interactions")),
                        <.li(<.a("I am an Open Book")),
                        <.li(^.className := "divider"),
                        <.li(<.a("Create/ add to list")),
                        <.li(<.a("Introduce to a  friend")),
                        <.li(^.className := "divider"),
                        <.li(<.a("Unfriend"))
                      )
                    )),
                  <.span(
                    <.span(^.className := "privacy-option-dropdown")
                    (<.button(^.className := "fa fa-cog btn  dropdown  ", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", ConnectionsCSS.Style.mediaUserActionIcons,
                      ^.onClick --> b.backend.displayUserSetting(s"${postContainerId}")
                    )
                    ),
                    <.span(^.className := "fa fa-angle-double-left btn   ", ConnectionsCSS.Style.mediaUserActionIcons,^.onClick--> b.backend.swappingEffect(s"${postContainerId}","left"))
                  )
                )
              )
            ),
            <.div(^.className := "popular-tags", ConnectionsCSS.Style.userPopularTags)(
              <.button( ^.`type` := "button", "User Experts Advice", ConnectionsCSS.Style.userPopularTag),
              <.button( ^.`type` := "button", "User Experts Advice", ConnectionsCSS.Style.userPopularTag),
              <.button(^.`type` := "button", "User Experts Advice", ConnectionsCSS.Style.userPopularTag)
            ),
            <.ul(^.className := "dropdown-menu privacy-option-dropdown-menu",^.id:={s"userSettings${postContainerId}"})(
              <.li(<.a("Work - Keep me Private")),
              <.li(<.a("Only Direct Interactions")),
              <.li(<.a("I am an Open Book")),
              <.li(^.className := "divider"),
              <.li(<.a("Create/ add to list")),
              <.li(<.a("Introduce to a  friend")),
              <.li(^.className := "divider"),
              <.li(<.a("Unfriend"))
            )
          ),
          <.span(ConnectionsCSS.Style.hideDiv,^.id:={s"swapedContent${postContainerId}"},
            <.div(^.className := " post-content", ConnectionsCSS.Style.fullUserDescriptionOnLeftSwap)(
              <.span( ConnectionsCSS.Style.connectionAvatarContainerOnLeftSwap
              )(
                <.span(^.className := "fa fa-angle-double-right btn",ConnectionsCSS.Style.swapRightIconVisible
                  ,^.onClick--> b.backend.swappingEffect(s"$postContainerId","right")),
                if (connection.imgSrc.isEmpty) {
                  <.img(^.className:="img-responsive img-circle",^.src := "./assets/images/default_avatar.jpg", ConnectionsCSS.Style.connectionAvatarOnLeftSwap, ^.title := "Connection Source: "
                    + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label,
                    ^.onClick --> b.backend.introduceUsers(s"${postContainerId}"))
                } else {
                  <.img(^.className:="img-responsive img-circle",^.src := connection.imgSrc, ConnectionsCSS.Style.connectionAvatarOnLeftSwap, ^.title := "Connection Source: "
                    + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label ,
                    ^.onClick --> b.backend.introduceUsers(s"$postContainerId"))
                }
              ),
              <.div( ConnectionsCSS.Style.userPopularTagsOnLeftSwap)(
                <.button( ^.`type` := "button", "User Experts Advice", ConnectionsCSS.Style.userPopularTag),
                <.button( ^.`type` := "button", "User Experts Advice", ConnectionsCSS.Style.userPopularTag),
                <.button(^.`type` := "button", "User Experts Advice", ConnectionsCSS.Style.userPopularTag)
              )
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
