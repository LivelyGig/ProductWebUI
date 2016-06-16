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
import client.css._
import client.services.{ApiTypes, CoreApi, LGCircuit}
import japgolly.scalajs.react
import client.components.Bootstrap._
import com.oracle.webservices.internal.api.message.ContentType

import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom._
import shared.dtos.{EstablishConnection, IntroConnections}
import shared.sessionitems.SessionItems

// scalastyle:off
object NewConnectionModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String)

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
        Button(Button.Props(B.addConnectionForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title), P.buttonName),
        if (S.showConnectionsForm) ConnectionsForm(ConnectionsForm.Props(B.addConnections, "New Connection"))
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
                   introConnections: IntroConnections = IntroConnections(aMessage = "Hi , \n Here's an introduction for the two of you to connect. \n \n Best regards, \n <name>"), establishConnection: EstablishConnection = EstablishConnection(), chkCnxnExstandOther: Boolean = false, chkCnxnNewUser: Boolean = false, chkCnxnExstUser: Boolean = true)

  case class Backend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }

    def hideModal(): Unit = {
      $(t.getDOMNode()).modal("hide")
    }

    def chkCnxnExstUser(e: ReactEventI): react.Callback = {
      val state = t.state.runNow()
      t.modState(s => s.copy(chkCnxnExstUser = true, chkCnxnExstandOther = false, chkCnxnNewUser = false))
    }

    def chkCnxnNewUser(e: ReactEventI): react.Callback = {
      val state = t.state.runNow()
      t.modState(s => s.copy(chkCnxnNewUser = true, chkCnxnExstandOther = false, chkCnxnExstUser = false))
    }

    def chkCnxnExstandOther(e: ReactEventI): react.Callback = {
      val state = t.state.runNow()
      t.modState(s => s.copy(chkCnxnExstandOther = true, chkCnxnNewUser = false, chkCnxnExstUser = false))
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val state = t.state.runNow()
      val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.selectizeInputId)
      val msg = state.introConnections.aMessage.replaceAll("/", "//")
      if (state.chkCnxnExstandOther) {
        val content = state.introConnections.copy(aConnection = connections(0), bConnection = connections(1),
          sessionURI = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI), alias = "alias", aMessage = msg, bMessage = msg)
        CoreApi.postIntroduction(content)
      }
      if (state.chkCnxnExstUser) {
        val content = state.establishConnection.copy(sessionURI = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI),
          aURI = connections(0).target, bURI = connections(1).target, label = connections(0).label)
        CoreApi.postIntroduction(content)
      }
      t.modState(s => s.copy(postConnection = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postConnection)
      props.submitHandler()
    }

    def updateContent(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(introConnections = s.introConnections.copy(bMessage = value, aMessage = value)))
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
            <.div()(
              <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.checked := s.chkCnxnExstUser, ^.onChange ==> chkCnxnExstUser), " Introduce yourself to existing user(s)."), <.br(),
            //  <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.onChange ==> chkCnxnNewUser), " Invite new user(s) to sign up and  connect with you."), <.br(),
              <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.onChange ==> chkCnxnExstandOther), " Invite existing connections to connect with each other.\n Note, each pair of connections will be introduced with the message above."), <.br()
            ),
            if (s.chkCnxnExstUser == true)
              <.div(
                <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter USER ID")
              )
//            else if (s.chkCnxnNewUser == true) {
//              <.div()(
//                <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter Email ID")
//              )
//            }
            else if (s.chkCnxnExstandOther == true) {
              <.div(
                <.div(<.h5("Recipients:")),
                <.div(^.id := s"${s.selectizeInputId}")(
                  LGCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s"${s.selectizeInputId}")))
                ),
                <.div((!s.chkCnxnExstandOther) ?= DashBoardCSS.Style.hidden,
                  <.div(<.h5("Introduction:")),
                  <.div()(
                    <.textarea(^.rows := 6, ^.placeholder := "Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n <name>", ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.value := s.introConnections.aMessage, ^.onChange ==> updateContent /*, ^.required := true*/)
                  )
                )
              )
            }
            else
              <.div(),
            <.div()(
              <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
                <.button(^.tpe := "submit", ^.className := "btn", WorkContractCSS.Style.createWorkContractBtn, /*^.onClick --> hide, */ "Send"),
                <.button(^.tpe := "button", ^.className := "btn", WorkContractCSS.Style.createWorkContractBtn, ^.onClick --> hide, "Cancel")
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
      //println(s"states:chkCnxnExstUser = ${scope.currentState.chkCnxnExstUser}, chkCnxnNewUser = ${scope.currentState.chkCnxnNewUser}, chkCnxnExstandOther = ${scope.currentState.chkCnxnExstandOther}")
      if (scope.currentState.postConnection) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}

