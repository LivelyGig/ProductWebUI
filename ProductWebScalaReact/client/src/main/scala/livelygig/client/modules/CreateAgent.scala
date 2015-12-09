//package livelygig.client.modules
//
//import japgolly.scalajs.react.{Callback, ReactComponentB}
//import livelygig.client.components.Bootstrap.Button
//import livelygig.client.components.Bootstrap.Modal
//import livelygig.client.components.Bootstrap.Modal.Backend
//import livelygig.client.components.GlobalStyles
//import livelygig.client.components.Icon
//import livelygig.client.logger._
//
///**
//  * Created by bhagyashree.b on 12/9/2015.
//  */
//object CreateAgent {
//  // shorthand for styles
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: ( Boolean) => Callback)
//
//  case class State(cancelled: Boolean = true)
//
////  class Backend(t: BackendScope[Props, State]) {
////    def submitForm(): Callback = {
////      // mark it as NOT cancelled (which is the default)
////      t.modState(s => s.copy(cancelled = false))
////    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      props.submitHandler( state.cancelled)
//    }
//
////    def updateDescription(e: ReactEventI) = {
////      // update TodoItem content
////      t.modState(s => s.copy(item = s.item.copy(content = e.target.value)))
////    }
//
////    def updatePriority(e: ReactEventI) = {
////      // update TodoItem priority
////      val newPri = e.currentTarget.value match {
////        case p if p == TodoHigh.toString => TodoHigh
////        case p if p == TodoNormal.toString => TodoNormal
////        case p if p == TodoLow.toString => TodoLow
////      }
////      t.modState(s => s.copy(item = s.item.copy(priority = newPri)))
////    }
//
//    def render(s: State, p: Props) = {
//    //  log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
//      val headerText = "Add new todo"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)),
//        // footer has the OK button that submits the form before hiding it
//        footer = hide => <.span("footer"),
//        // this is called after the modal has been hidden (animation is completed)
//        closed = () => formClosed(s, p)),
//        <.div(bss.formGroup,
//          <.label(^.`for` := "description", "Description"),
//          <.input(^.tpe := "text", bss.formControl, ^.id := "description", ^.value := s.item.content,
//            ^.placeholder := "write description" )),
//        <.div(bss.formGroup,
//          <.label(^.`for` := "priority", "Priority"),
//          // using defaultValue = "Normal" instead of option/selected due to React
//          <.select(bss.formControl, ^.id := "priority",
//            <.option("High"),
//            <.option( "Normal"),
//            <.option( "Low")
//          )
//        )
//      )
//    }
//
//  val component = ReactComponentB[Props]("CreateAgent")
//  //  .initialState_P(p => State(p.item.getOrElse(TodoItem("", 0, "", TodoNormal, false))))
//    //.renderBackend[Backend]
//    .build
//
//  def apply(props: Props) = component(props)
//
//}
