package synereo.client.modalpopups

/**
  * Created by mandar.k on 4/11/2016.
  */

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Button
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.Bootstrap.Modal
import synereo.client.components.Bootstrap._
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon
import synereo.client.components.Icon._
import synereo.client.components._
import synereo.client.services.SYNEREOCircuit
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import synereo.client.css.{DashboardCSS, NewMessageCSS}

//scalastyle:off
object NewConnectionModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String = "Invite Friend")

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

    def addConnections(postConnection: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      if (!postConnection) {
        t.modState(s => s.copy(showConnectionsForm = false))
      } else {
        t.modState(s => s.copy(showConnectionsForm = true))
      }
    }
  }

  val component = ReactComponentB[Props]("Connections")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      //      <.div(/*ProjectCSS.Style.displayInitialbtn*/)(
      //        Button(Button.Props(B.addConnectionForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title), P.buttonName), P.title,
      //        if (S.showConnectionsForm) ConnectionsForm(ConnectionsForm.Props(B.addConnections, "New Connection"))
      //        else
      //          Seq.empty[ReactElement]
      //      )
      <.div(/*ProjectCSS.Style.displayInitialbtn*/
        /*, ^.onMouseOver --> B.displayBtn*/)(
        Button(Button.Props(B.addConnectionForm(), CommonStyle.default, P.addStyles, "", P.title, className = ""), P.title),
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

  case class Props(submitHandler: (Boolean) => Callback, header: String)

  case class State(postConnection: Boolean = false, postNewConnectionSelectizeInput: String = "postNewConnectionSelectizeInput", inviteExistingConnections: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {
      val state = t.state.runNow()
      println(s"inviteExistingConnections= ${state.inviteExistingConnections}")
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postConnection = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postConnection)
      props.submitHandler(state.postConnection)
    }

    def introducingExistingUsr(e: ReactEvent): Callback = {
      val state = t.state.runNow()
      println(s"this is ${state.inviteExistingConnections}")
      t.modState(s => s.copy(inviteExistingConnections = true))
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div()(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            /*<.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("To"))
            ),*/
            /*val selectizeControl : js.Object =*/
            <.div()(
              <.div(^.className := "radio",
                <.input(^.`type` := "radio", ^.name := "userConnection", ^.value := "1"), " Introduce yourself to existing user(s)."),
              <.br(),
              <.div(^.className := "radio",
                <.input(^.`type` := "radio", ^.name := "userConnection", ^.value := "2"), " Invite new user(s) to sign up and  connect with you."),
              <.br(),
              <.div(^.className := "radio",
                <.input(^.`type` := "radio", ^.name := "userConnection", ^.value := "3", ^.onChange ==> introducingExistingUsr), " Invite existing connections to connect with each other."),
              <.br()
            ),
            <.div(<.h5("Recipients:")),
            <.div(^.id := s.postNewConnectionSelectizeInput, ^.display.none, s.inviteExistingConnections ?= (^.display.block))(
              SYNEREOCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s.postNewConnectionSelectizeInput)))
            ),
            <.div(^.display.block, s.inviteExistingConnections ?= (^.display.none))(
              <.input(^.tpe := "text", ^.className := "form-control")
            ),
            <.div(<.h5("Introduction:")),
            <.div()(
              <.textarea(^.rows := 6, ^.placeholder := "Enter your message here:", ^.required := true, ^.width := "100%", ^.className := "form-control")
            )
          ),
          <.div()(
            <.div(^.className := "text-right")(
              //              <.button(^.tpe := "submit", ^.className := "btn", /*^.onClick --> hide, */ "Send"),
              <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, ^.onClick --> hide, "Send"),
              //              <.button(^.tpe := "button", ^.className := "btn", ^.onClick --> hide, "Cancel")
              <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> hide, "Cancel")
            )
          ),
          <.div(bss.modal.footer)()
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("PostConnections")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postConnection) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}


