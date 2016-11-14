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
//scalastyle:off
object ConnectionsResults {

  case class Props(proxy: ModelProxy[ConnectionsRootModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  class Backend(t: BackendScope[Props, State]) {
    /*def mounted(props: Props): Callback =
      Callback.when(props.proxy().isEmpty)(props.proxy.dispatch(RefreshConnections()))*/
  }

  // create the React component for user's connections
  val component = ReactComponentB[Props]("ConnectionsResults")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, state) => {
      <.div(^.className := "container-fluid")(
        <.div(^.className := "row ", ConnectionsCSS.Style.connectionContentRow)(
          <.div(^.className := "col-md-3 col-sm-1 col-xs-1 col-lg-3"),
          <.div(^.className := "col-md-6 col-sm-11 col-xs-11 col-lg-6", ConnectionsCSS.Style.connectionContentMainContainer,
            ConnectionsList(props.proxy().connectionsResponse)
          ),

          <.div(^.className := "col-md-3 col-sm-0 col-xs-0 col-lg-3"

            //    NewConnection(NewConnection.Props("", Seq(ConnectionsCSS.Style.connectfriendsBtn), "", " + | Connect Friend"))

            //            <.div(<.span(ConnectionsCSS.Style.connectfriendsBtn,
            //              <.span(^.className := "fa fa-plus", ConnectionsCSS.Style.connectfriendsIcon),
            //              NewConnection(NewConnection.Props("", Seq(ConnectionsCSS.Style.connectfriendsIconText), "", "Connect Friend"))
            //            )
            //            )

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

  case class State(showAmplifyPostForm: Boolean = false, senderAddress: String = "", imageID : Int = 0)

  var OuterIlterator=0

  class Backend(t: BackendScope[ConnectionListProps, State]) {

    def amplifyPost(senderAddress: String): Callback = {
      t.modState(state => state.copy(showAmplifyPostForm = true, senderAddress = senderAddress,imageID = t.state.runNow().imageID+1 ))
    }

    def introduceUser(id: String) = Callback {
      val topBtn: js.Object = "#"+id
      if ($(topBtn).hasClass("ConnectionsCSS_Style-connectionAvatar")) {
        $(topBtn).addClass("ConnectionsCSS_Style-onconnectionAvatarClick")
        $(topBtn).removeClass("ConnectionsCSS_Style-connectionAvatar")
      }
      else {
        $(topBtn).removeClass("ConnectionsCSS_Style-onconnectionAvatarClick")
        $(topBtn).addClass("ConnectionsCSS_Style-connectionAvatar")
      }
    }

    def countPost():Callback= {

      t.modState(state => state.copy(imageID = t.state.runNow().imageID +1))

    }


    /*def mounted(props: Props): Callback =
      Callback.when(props.proxy().isEmpty)(props.proxy.dispatch(RefreshConnections()))*/
  }


  val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionsList")
    .initialState_P(p => State())
    .backend(new Backend(_))
    .renderPS((b, p, s) => {
      def renderConnections(connection: ConnectionsModel) = {
      //  b.backend.countPost
        val InnerIlterator ="img"+OuterIlterator
        OuterIlterator=OuterIlterator+1
        <.li(^.className := " col-md-5 col-sm-12 col-xs-12 col-lg-5 Posts", ConnectionsCSS.Style.connectionli,
          <.div(^.className := "ConnectionPostsOuter", <.div(^.className := "row ConnectionPosts", ConnectionsCSS.Style.fullUserDescription)(
            <.div(^.className := "col-md-3 col-sm-3 col-xs-3 col-lg-3", ConnectionsCSS.Style.connectionAvatarDiv, ^.onClick --> b.backend.introduceUser(""+InnerIlterator)
            )(
              if (connection.imgSrc.isEmpty) {
                <.img(^.src := "./assets/images/default_avatar.jpg", ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                  + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label, ^.id :={""+InnerIlterator})
              } else {
                <.img(^.src := connection.imgSrc, ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                  + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label, ^.id :={""+InnerIlterator})
              }
            ),

            <.div(^.className := "col-md-7 col-sm-7 col-xs-7 col-lg-7", ConnectionsCSS.Style.connectionNameDiv)(<.h5(^.className := "", ^.wordBreak := "break-word", ConnectionsCSS.Style.connectionName)(
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
              <.h6(^.className := "", ConnectionsCSS.Style.connectionNumbers)("7 connections")
            ),
            <.div(^.className := "col-md-2 col-sm-2 col-xs-2 col-lg-2", ConnectionsCSS.Style.userActionsMenuDiv)(
              <.span(^.className := "fa fa-comment btn   ", ConnectionsCSS.Style.userActionIcons),
              <.span(^.className := "privacyOptionDropdown")(<.button(^.className := "fa fa-star btn  dropdown  ", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", ConnectionsCSS.Style.userActionIcons),
                <.ul(^.className := "dropdown-menu privacyOptionDropdownMenu", ConnectionsCSS.Style.userActionsMenu)(
                  <.li(<.a("Work - Keep me Private")),
                  <.li(<.a("Only Direct Interactions")),
                  <.li(<.a("I am an Open Book")),
                  <.li(^.className := "divider"),
                  <.li(<.a("Create/ add to list")),
                  <.li(<.a("Introduce to a  friend")),
                  <.li(^.className := "divider"),
                  <.li(<.a("Unfriend"))
                )
              )
            )
          )),
          <.div(^.className := "row popularTags", ConnectionsCSS.Style.userPopularTagDiv)(
            <.button(^.className := " col-md-3 col-sm-3 col-xs-3 col-lg-3", ^.`type` := "button", "I love Privacy", ConnectionsCSS.Style.userPopularTags),
            <.button(^.className := "col-md-3 col-sm-3 col-xs-3 col-lg-3", ^.`type` := "button", "I am a snob", ConnectionsCSS.Style.userPopularTags),
            <.button(^.className := " col-md-3 col-sm-3 col-xs-3 col-lg-3", ^.`type` := "button", "User Experts", ConnectionsCSS.Style.userPopularTags)

          )
        )
      }
      <.ul(^.className := " row", ConnectionsCSS.Style.fullDescUL)(p.connections map renderConnections)
    })
    .build

  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}
