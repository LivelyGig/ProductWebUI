package client.modals

import client.components.Bootstrap.Modal
import client.components.GlobalStyles
import client.components.Icon
import client.LGMain.{ Loc }
import scala.util.{ Failure, Success }
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components._
import client.css.{ DashBoardCSS, MessagesCSS }
import scala.language.reflectiveCalls
import org.querki.jquery._

object TrademarksModal {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: () => Callback)
  case class State()

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler()
    }

    def render(s: State, p: Props) = {

      val headerText = "Trademarks and Credits"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12")(
            <.div(DashBoardCSS.Style.modalHeaderFont, DashBoardCSS.Style.paddingLeftModalHeaderbtn)("Trademarks")
          )
        ), //main row
        <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
          <.div(DashBoardCSS.Style.marginTop10px)(),
          <.div()(
            <.span(^.fontWeight := "bold")("Sofwtare Credits"),
            <.br(),
            "The development of this software was made possible using the following components:",
            <.br(),
            <.div(^.className := "col-sm-1")(),
            <.div()(^.className := "col-sm-11")(
              <.div()(
                <.hr(),
                <.a()(^.href := "https://github.com/synereo/special-k", ^.target := "_blank")("Special-K"),
                " by Biosimilarity, LLC.", " TBD",
                <.br(),
                "Licensed Under: ",
                <.a()(^.href := "https://www.tldrlegal.com/l/apache2", ^.target := "_blank")("Apache 2.0 TBD")
              ),
              <.div()(
                <.hr(),
                <.a()(^.href := "https://github.com/synereo/agent-service-ati-ia", ^.target := "_blank")("Agent Service ATI IA"),
                " by Biosimilarity, LLC.", " TBD",
                <.br(),
                "Licensed Under: ",
                <.a()(^.href := "https://www.tldrlegal.com/l/apache2", ^.target := "_blank")("Apache 2.0 TBD")
              ),
              <.div()(
                <.hr(),
                <.a()(^.href := "https://github.com/mongodb/mongo", ^.target := "_blank")("MongoDB"),
                //" by ???.",
                <.br(),
                "Licensed Under: ",
                <.a()(^.href := "https://www.tldrlegal.com/l/apache2", ^.target := "_blank")("Apache 2.0")
              ),
              <.div()(
                <.hr(),
                <.a()(^.href := "https://github.com/rabbitmq", ^.target := "_blank")("RabbitMQ"),
                " (c) Pivotal Software Inc., 2007-2016",
                <.br(),
                "Licensed Under: ",
                <.a()(^.href := "https://tldrlegal.com/license/mozilla-public-license-2.0-(mpl-2)", ^.target := "_blank")("MPL 2.0")
              )
            )
          )
        ),
        <.div()(
          <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")( //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
          //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
          )
        ),
        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }

  private val component = ReactComponentB[Props]("Trademarks")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}

