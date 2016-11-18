package synereo.client.components

import japgolly.scalajs.react.{ReactElement, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import scala.language.reflectiveCalls

object FeedViewLeftAnimC {

  case class Props(/*messageCount: String = ""*/)

  case class State()

  class FeedViewLeftAnimCBKND(t: BackendScope[FeedViewLeftAnimC.Props, FeedViewLeftAnimC.State]) {

  }

  val component = ReactComponentB[Props]("FeedViewLeftAnimC")
    //    .initialState_P(p => State())
    //    .backend(new FeedViewLeftAnimCBKND(_))
    .render_P((props: Props) => {
    <.span()
  })
    .build

  def apply(props: Props) = component(props)

}