package livelygig.client.modules

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.{Ref, BackendScope, ReactComponentB}
import livelygig.client.LGMain.Loc
import org.scalajs.dom.html
import org.scalajs.dom.raw.HTMLInputElement
import japgolly.scalajs.react._

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.{DashBoardCSS, HeaderCSS, MessagesCSS, ProjectCSS}
import scala.scalajs.js
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

/**
  * Created by bhagyashree.b on 2/4/2016.
  */
object EventListnerBtn {

  case class Props(ctl: RouterCtl[Loc])

  val Main = ReactComponentB[Props]("EventListener Example")
    .initialState("Local mouseenter events + local/global click events will appear here.")
    .renderBackend[Backend]
    .configure(
      // Listen to mouseenter events within the component
      EventListener.install("mouseenter", _.backend.logMouseEnter)
     // Listen to click events
//      EventListener.install("click", _.backend.logLocalClick),
//      EventListener.install("click", _.backend.logWindowClick, _ => dom.window)
    )
    .build

  class Backend($: BackendScope[Props, String]) extends OnUnmount {
    def logEvent(desc: ReactTagOf[html.Button]) = $.modState(_ + "\n" +  desc)
  //  def logMouseEnter() = logEvent("hello")
    def logMouseEnter() = logEvent(<.button("hello"))

    def render(p:Props,state: String) = {
      <.div(
        ^.border  := "solid 1px black",
        ^.width   := "90ex",
        ^.height  := "20em",
        ^.padding := "2px 6px",
        ^.backgroundColor:= "white",
        state)

    }
  }
  def apply(props: Props) = Main(props)

//  val theInput = Ref[HTMLInputElement]("theInput")
//
//  class Backend($: BackendScope[Props, Unit]) {
//    def mounted(props: Props): Callback = Callback {
//
//    }
//
//    def handleChange(e: ReactEventI) =
//      $.setState(e.target.value)
//
//    def clearAndFocusInput() =
//      $.setState("", theInput($).tryFocus)
//
//
//    def render(props:Props) = {
//      <.div(
//        <.div(
//          ^.onMouseOver --> clearAndFocusInput,
//          <.button(
//            ^.ref       := theInput,
//            ^.onChange ==> handleChange,"button"))
////        <.input(
////          ^.ref       := theInput,
////         ^.onChange ==> handleChange),
//
//
//         )
//       }
//  }
//
//  private val App = ReactComponentB[Props]("App")
////    .initialState("")
//    .initialState_P(p => ())
//    .renderBackend[Backend]
//    .build
//
//  def apply(props: Props) = App(props)
}
