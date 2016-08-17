package client.modalpopups

/**
  * Created by bhagyashree.b on 2016-06-21.
  */
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap.{Button, CommonStyle, Modal, _}
import client.components.{ConnectionsSelectize, GlobalStyles, Icon}
import client.components.Icon._
import client.css.{DashBoardCSS, ProjectCSS, WorkContractCSS, _}
import client.services.{CoreApi, LGCircuit, _}
import japgolly.scalajs.react

import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import shared.dtos.{EstablishConnection, IntroConnections}

// scalastyle:off
object Account {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String,dropDownModalDialog : Option[Boolean] = None)

  case class State(showAccountForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showAccountForm = true))
    }

    def addAccountForm(): Callback = {
      t.modState(s => s.copy(showAccountForm = true))
    }

    def addAccount(): Callback = {
      t.modState(s => s.copy(showAccountForm = false))
    }
  }

  val component = ReactComponentB[Props]("Connections")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(

        P.dropDownModalDialog match{
          case Some(true) => AccountForm(AccountForm.Props(B.addAccount, "Account"))
          case None => {
            Button(Button.Props(B.addAccountForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title), P.buttonName)
            if (S.showAccountForm) AccountForm(AccountForm.Props(B.addAccount, "Account"))
            else
              Seq.empty[ReactElement]
          }
        }

      )
    })
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object AccountForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, header: String)

  case class State(postConnection: Boolean = false, agentUid : String = "")

  case class Backend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }

    def hideModal(): Unit = {
      $(t.getDOMNode()).modal("hide")
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      t.modState(s => s.copy())
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler()
    }

    def updateAgentUid (e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(agentUid = value))
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(),
                     <.div(
                <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter USER ID",^.value := s.agentUid, ^.onChange ==> updateAgentUid)
//                       ,
//                <.div(^.id := "agentFieldError", ^.className := "hidden")
//                ("User with this uid is already added as your connection")
              ),

            <.div()(
              <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
                <.button(^.tpe := "submit", ^.className := "btn", WorkContractCSS.Style.createWorkContractBtn, "Send"),
                <.button(^.tpe := "button", ^.className := "btn", WorkContractCSS.Style.createWorkContractBtn, ^.marginLeft:="20.px", ^.onClick --> hide, "Cancel")
              )
            )
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        ))
    }
  }

  private val component = ReactComponentB[Props]("PostConnections")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postConnection) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}


