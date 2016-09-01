package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Button
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.Bootstrap.Modal
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon._
import synereo.client.components._
import synereo.client.css._
import synereo.client.services.SYNEREOCircuit
import japgolly.scalajs.react
import synereo.client.components.Bootstrap._
import synereo.client.utils.ConnectionsUtils
import diode.AnyAction._

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos.{EstablishConnection, IntroConnections}
import shared.models.ConnectionsModel
import synereo.client.handlers.ContentHandler
import synereo.client.modalpopupbackends.ConnectionsFormBackend

import scala.scalajs.js

// scalastyle:off
object NewConnection {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String = "Introduce Connections")

  case class State(showConnectionsForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {

    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showConnectionsForm = true))
    }

    def addConnectionForm(): Callback = {
      t.modState(s => s.copy(showConnectionsForm = true))
    }

    def addConnections(): Callback = {
      t.modState(s => s.copy(showConnectionsForm = false))
    }
  }

  val component = ReactComponentB[Props]("Connections")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(/*ProjectCSS.Style.displayInitialbtn*/)(
        Button(Button.Props(B.addConnectionForm(), CommonStyle.default, P.addStyles, "", P.title, className = ""), P.title),
        if (S.showConnectionsForm) ConnectionsForm(ConnectionsForm.Props(B.addConnections, "Introduce Connections"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object ConnectionsForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, header: String)

  case class State(postConnection: Boolean = false, selectizeInputId: String = "pstNewCnxnSelParent",
                   introConnections: IntroConnections = IntroConnections(),
                   establishConnection: EstablishConnection = EstablishConnection(), introduceUsers: Boolean = false,
                   chkCnxnNewUser: Boolean = false, chkCnxnExstUser: Boolean = true, agentUid: String = "", userName: String = "")

  val connectionSelectize = SYNEREOCircuit.connect(_.connections)

  private val component = ReactComponentB[Props]("PostConnections")
    .initialState_P(p => State())
    .backend(new ConnectionsFormBackend(_))
      .renderPS((t,P,S)=>{

        val headerText = P.header
        Modal(
          Modal.Props(
            // header contains a cancel button (X)
            header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div()(headerText)),
            // this is called after the modal has been hidden (animation is completed)
            closed = () => t.backend.formClosed(S, P)
          ),
          <.form(^.onSubmit ==> t.backend.submitForm)(
            <.div(^.className := "row")(
              <.div()(
                <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.checked := S.chkCnxnExstUser, ^.onChange ==> t.backend.chkCnxnExstUser),
                  " Introduce yourself to existing user(s)."), <.br(),
                if (S.chkCnxnExstUser == true) {
                  <.div(^.marginLeft := "15px")(
                    <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "User ID, e.g. 2a6d5dcb40634e8dafa4ec0f562b8fda, 05d1ba8d0d7945359b717873b7e7f6bf",
                      ^.value := S.agentUid, ^.onChange ==> t.backend.updateAgentUid),
                    <.div(^.id := "agentFieldError", ^.className := "hidden")
                    ("User with this uid is already added as your connection")
                  )
                }
                else
                  <.div(),
                //  <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.onChange ==> chkCnxnNewUser),
                // " Invite new user(s) to sign up and  connect with you."), <.br(),
                <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.onChange ==> t.backend.chkCnxnExstandOther),
                  " Introduce your existing connections to each other."), <.br()
              ),

              //            else if (s.chkCnxnNewUser == true) {
              //              <.div()(
              //                <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter Email ID")
              //              )
              //            }
              //            else if (s.introduceTwoUsers == true) {
              <.div(^.marginLeft := "15px", (!S.introduceUsers == true) ?= ConnectionsCSS.Style.hidden)(
                <.div(<.h5("Connections:")),
                <.div(^.id := s"${S.selectizeInputId}")(
                  //  SYNEREOCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s"${s.selectizeInputId}")))
                  ConnectionsSelectize(ConnectionsSelectize.Props(s"${S.selectizeInputId}", t.backend.fromSelecize, Option(2)))
                ),
                <.div(^.id := "cnxnError", ^.className := "hidden text-danger")
                ("Please provide Only 2 Connections... !!!"),
                <.div((!S.introduceUsers) ?= ConnectionsCSS.Style.hidden,
                  <.div(<.h5("Introduction:")),
                  <.div()(
                    <.textarea(^.rows := 6,
                      ^.value := S.introConnections.aMessage, ^.onChange ==> t.backend.updateContent, ^.className := "form-control")
                    //                  <.div(s.introConnections.aMessage)
                  )

                )
              ),
              //            }
              <.div()(
                <.div(^.className := "text-right")(
                  <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, /* ^.onClick --> hide*/ "Send"),
                  <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> t.backend.hide, "Cancel")
                )
              )
            ),
            <.div(bss.modal.footer)
          ))

      })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postConnection) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}
