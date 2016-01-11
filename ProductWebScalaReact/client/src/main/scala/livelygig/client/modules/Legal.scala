package livelygig.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css.{CreateAgentCSS, DashBoardCSS, HeaderCSS}
import livelygig.client.models.AgentLoginModel
import livelygig.client.services.{ApiResponseMsg, CoreApi}
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object Legal {
  case class Props(router: RouterCtl[Loc])
  case class State(agentLoginModel: AgentLoginModel)

  val component = ReactComponentB[Unit]("Legal")
    .renderPS(($, P, S) => {
      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        (<.ul()(
          <.li()(<.a(^.href:="#")("Privacy Policy")),
          <.li()(<.a(^.href:="#")("End User Agreement")),
          <.li()(<.a(^.href:="#")("Terms of Service")),
          <.li()(<.a(^.href:="#")("Trademarks")),
          <.li()(<.a(^.href:="#")("Copyright"))
        ))
      ) //mainContainer
    })
    .build

  def apply(props: Props) = component(props)

}