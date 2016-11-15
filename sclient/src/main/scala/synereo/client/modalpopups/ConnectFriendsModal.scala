//package synereo.client.modalpopups
//
//
//import scala.language.reflectiveCalls
//import synereo.client.components.Bootstrap._
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
//import synereo.client.components.Icon
//import synereo.client.components._
//
//
///**
//  * Created by shubham.j on 11/11/2016.
//  */
//object ConnectFriendsModal {
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props()
//  case class State()
//  class ConnectFriendsModal(t: BackendScope[Props, State]) {
//
//    def modalClosed(state: State, props: Props): Callback = {
//
//    }
//
//
//  }
//  private val component = ReactComponentB[Props]("ConnectFriendsModal")
//    .initialState_P(p => State())
//    .backend(new ConnectFriendsModal(_))
//    .renderPS((t, props, state) => {
//      val headerText = "NodeSettings"
//      Modal(
//        Modal.Props(
//          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)),
//          closed = () => t.backend.modalClosed(state, props)
//        )
//      )
//
//    }
//
////  def apply(props: Props) = component(props)
//
//
//}
