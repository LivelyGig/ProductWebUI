package client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Icon._
import client.css.DashBoardCSS
import org.scalajs.dom._

import scala.language.implicitConversions
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import org.scalajs.dom.ext.KeyCode
import scala.language.reflectiveCalls

/**
  * Common Bootstrap components for scalajs-react
  */
object Bootstrap {

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  @js.native
  trait BootstrapJQuery extends JQuery {
    def modal(action: String): BootstrapJQuery = js.native
    def modal(options: js.Any): BootstrapJQuery = js.native
  }

  implicit def jq2bootstrap(jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]

  // Common Bootstrap contextual styles
  object CommonStyle extends Enumeration {
    val default, primary, success, info, warning, danger = Value
  }

  object Button {

    case class Props(onClick: Callback, style: CommonStyle.Value = CommonStyle.default, addStyles: Seq[StyleA] = Seq(), addIcons : Icon, title: String, id: String=null, className: String = null)

    val component = ReactComponentB[Props]("Button")
      .renderPC { ($, P, C) =>
        <.button(^.className:="btn btn-default ".concat(P.className),P.addStyles,P.addIcons,^.title:= P.title, ^.id:= P.id,  ^.tpe := "button", ^.onClick --> P.onClick)(C)
      }.build

    def apply(props: Props, children: ReactNode*) = component(props, children: _*)
    def apply() = component
  }

  object Panel {

    case class Props(heading: String, style: CommonStyle.Value = CommonStyle.default)

    val component = ReactComponentB[Props]("Panel")
      .renderPC { ($, P, C) =>
        <.div(bss.panelOpt(P.style))(
          <.div(bss.panelHeading)(P.heading),
          <.div(bss.panelBody)(C)
        )
      }.build

    def apply(props: Props, children: ReactNode*) = component(props, children: _*)
    def apply() = component
  }

  object Modal {
    // header and footer are functions, so that they can get access to the the hide() function for their buttons
    case class Props(header: (Callback) => ReactNode, /*footer: (Callback) => ReactNode,*/ closed: () => Callback, backdrop: String = "static",
                     keyboard: Boolean = true)

    val OuterRef = Ref("o")

    class Backend(t: BackendScope[Props, Unit]) {
      def init: Callback =
        OuterRef(t).tryFocus

      def hide = Callback {
        // instruct Bootstrap to hide the modal
        jQuery(t.getDOMNode()).modal("hide")

      }
      // jQuery event handler to be fired when the modal has been hidden
      def hidden(e: JQueryEventObject): js.Any = {
        // inform the owner of the component that the modal was closed/hidden
        t.props.runNow().closed().runNow()
      }

   def modalClose(e:ReactKeyboardEvent) : Callback = {

     def plainKey: CallbackOption[Unit] =             // CallbackOption will stop if a key isn't matched
       CallbackOption.keyCodeSwitch(e) {
         case  KeyCode.Escape => hide
       //  case  KeyCode.Enter => hide
       }
         plainKey >>   e.preventDefaultCB
   }

      def render(P: Props, C: PropsChildren) = {
        val modalStyle = bss.modal
        <.div(modalStyle.modal, modalStyle.fade, ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1  ,
        <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
          <.div(modalStyle.dialog, DashBoardCSS.Style.verticalAlignCenter) (
            <.div(modalStyle.content,DashBoardCSS.Style.modalBorderRadius, ^.onKeyDown ==> modalClose ,^.ref:= OuterRef,
              <.div(^.className:= "modalheader" , modalStyle.header, DashBoardCSS.Style.modalHeaderPadding, P.header(hide)),
              <.div(modalStyle.body, DashBoardCSS.Style.modalBodyPadding, C)
//              <.div(modalStyle.footer, P.footer(hide))
            )
          )
         )
        )
      }
    }
    val component = ReactComponentB[Props]("Modal")
    /*  .stateless*/
      .renderBackend[Backend]
      .componentDidMount(_.backend.init)
      .componentDidMount(scope => Callback {
        val P = scope.props
        // instruct Bootstrap to show the modal data-backdrop="static" data-keyboard="false"
        jQuery(scope.getDOMNode()).modal(js.Dynamic.literal("backdrop" -> P.backdrop, "keyboard" -> P.keyboard, "show" -> true))
        // register event listener to be notified when the modal is closed
        jQuery(scope.getDOMNode()).on("hidden.bs.modal", null, null, scope.backend.hidden _)
      })
      .configure()
      .build

    def apply(props: Props, children: ReactElement*) = component(props, children: _*)
    def apply() = component
  }
}
