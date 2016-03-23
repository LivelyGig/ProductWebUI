package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap.Button
import client.components.Bootstrap.CommonStyle
import client.components.Bootstrap.Modal
import client.components.Bootstrap._
import client.components.GlobalStyles
import client.components.Icon
import client.components.Icon._
import client.components._
import client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls

object Accept {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(buttonName: String,addStyles: Seq[StyleA] = Seq() , addIcons : Icon,title: String)

  case class State(showPayoutTransactionForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showPayoutTransactionForm = true))
    }
    def addPayoutTransactionForm() : Callback = {
      t.modState(s => s.copy(showPayoutTransactionForm = true))
    }
    def addPayoutTransaction(postPayoutTransaction: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      if(postPayoutTransaction){
        t.modState(s => s.copy(showPayoutTransactionForm = true))
      } else {
        t.modState(s => s.copy(showPayoutTransactionForm = false))
      }
    }
  }
  val component = ReactComponentB[Props]("PayoutTransaction")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn)(
        Button(Button.Props(B.addPayoutTransactionForm(), CommonStyle.default, P.addStyles,"",""),P.buttonName),
        if (S.showPayoutTransactionForm) PayoutTransaction(PayoutTransaction.Props(B.addPayoutTransaction, "Accept All Deliverables"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object PayoutTransaction {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (Boolean) => Callback, header: String)
  case class State(postPayoutTransaction: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }
    def hideModal =  {
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postPayoutTransaction = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postPayoutTransaction)
      props.submitHandler(state.postPayoutTransaction)
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div()("Accept All Deliverables?", " ... details of payout transaction"),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,^.className:="text-right")(
              <.button(^.tpe := "submit",^.className:="btn", DashBoardCSS.Style.marginLeftCloseBtn, "Accept"),
              <.button(^.tpe := "button",^.className:="btn", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)("")
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostpostPayoutTransaction")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope=> Callback{
      if(scope.currentState.postPayoutTransaction){
        scope.$.backend.hideModal
      }
    })
    .build
  def apply(props: Props) = component(props)
}

