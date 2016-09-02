package synereo.client.modules


import shared.models.UserModel
import synereo.client.css.{ConnectionsCSS,  UserProfileViewCSS}
import diode.react._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.{ConnectionsModel}
import synereo.client.utils.ConnectionsUtils
import scalacss.ScalaCssReact._


/**
  * Created by bhagyashree.b on 2016-07-01.
  */


object Info {

  case class Props(proxy: ModelProxy[UserModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  val agentUID = ConnectionsUtils.getSelfConnnection().source
  val newAgentUID = agentUID.substring(8)
  val output = newAgentUID.split("\"")


  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props): Callback = Callback {
    }
  }

  // create the React component for user's connections
  val component = ReactComponentB[Props]("ConnectionsResults")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      <.div(^.id := "connectionsContainerMain", ConnectionsCSS.Style.connectionsContainerMain, UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
        <.div(UserProfileViewCSS.Style.agentUID)(s"Agent UID : ${output.head}"),
        <.div(UserProfileViewCSS.Style.agentUID)("Build Number : ")
      ) //connectionsContainerMain
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[UserModel]) = component(Props(proxy))
}


