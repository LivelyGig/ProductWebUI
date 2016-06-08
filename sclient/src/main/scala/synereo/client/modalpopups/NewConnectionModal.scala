package synereo.client.modalpopups

/**
  * Created by Mandar on 4/11/2016.
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

//
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.extra.OnUnmount
//import japgolly.scalajs.react.vdom.prefix_<^._
//import org.querki.jquery._
//import synereo.client.components.Bootstrap.Button
//import synereo.client.components.Bootstrap.CommonStyle
//import synereo.client.components.Bootstrap._
//import synereo.client.components.Icon.Icon
//import synereo.client.components.{GlobalStyles, Icon}
//import synereo.client.css.LoginCSS
//import scala.scalajs.js
//import scalacss.Defaults._
//import scalacss.ScalaCssReact._
//import scala.util.{Failure, Success}
//import scalacss.Defaults._
//import scalacss.ScalaCssReact._
//import scala.language.reflectiveCalls
//import org.querki.jquery._
//
//object NewConnectionModal {
//
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String = "Invite Friend")
//
//  case class State(showConnectionsForm: Boolean = false)
//
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//
//    def mounted(props: Props): Callback = {
//      t.modState(s => s.copy(showConnectionsForm = true))
//    }
//
//    def addNewRequestForm(): Callback = {
//      t.modState(s => s.copy(showConnectionsForm = true))
//    }
//
//    def addMessage(postMessage: Boolean = false): Callback = {
//      //log.debug(s"addNewAgent signUpModel : t{signUpModel} ,addNewAgent: t{showConnectionsForm}")
//      if (postMessage) {
//        t.modState(s => s.copy(showConnectionsForm = true))
//      } else {
//        t.modState(s => s.copy(showConnectionsForm = false))
//      }
//    }
//  }
//
//  val component = ReactComponentB[Props]("NewInvite")
//    .initialState(State())
//    .backend(new Backend(_))
//    .renderPS(($, P, S) => {
//      val B = $.backend
//      <.div(/*ProjectCSS.Style.displayInitialbtn*/
//        /*, ^.onMouseOver --> B.displayBtn*/)(
//        Button(Button.Props(B.addNewRequestForm(), CommonStyle.default, P.addStyles, "", P.title, className = ""), P.title),
//        if (S.showConnectionsForm) PostNewInvite(PostNewInvite.Props(B.addMessage))
//        else
//          Seq.empty[ReactElement]
//      )
//    })
//    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
//    .configure(OnUnmount.install)
//    .build
//
//  def apply(props: Props) = component(props)
//}
//
//object ConnectionsForm {
//  // shorthand for styles
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  val searchContainer: js.Object = "#searchContainer"
//
//  case class Props(submitHandler: (Boolean) => Callback, header: String)
//
//  case class State(postConnection: Boolean = false, selectizeInputId: String = "postNewConnectionSelectizeInput")
//
//  case class Backend(t: BackendScope[Props, State]) {
//    def hide = Callback {
//      $(t.getDOMNode()).modal("hide")
//    }
//
//    def hideModal = {
//      $(t.getDOMNode()).modal("hide")
//    }
//
//    def mounted(props: Props): Callback = Callback {
//
//    }
//
//    def submitForm(e: ReactEventI) = {
//      e.preventDefault()
//      t.modState(s => s.copy(postConnection = true))
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      println(state.postConnection)
//      props.submitHandler(state.postConnection)
//    }
//
//    def render(s: State, p: Props) = {
//
//      val headerText = p.header
//      Modal(
//        Modal.Props(
//          // header contains a cancel button (X)
//          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div("")),
//          // this is called after the modal has been hidden (animation is completed)
//          closed = () => formClosed(s, p)
//        ),
//        <.form(^.onSubmit ==> submitForm)(
//          <.div(^.className := "row", LoginCSS.Style.requestInviteModalStyle)(
//            <.div(^.className := "col-md-12")(
//              <.div(LoginCSS.Style.requestInviteModalText)(
//                "Invites aren't quite ready, however we're eager for you to join us on this journey!"
//              ),
//              <.div()(
//                <.input(^.`type` := "text", ^.className := "form-control", LoginCSS.Style.requestInviteTextarea, ^.placeholder := "you@email.com")
//              ),
//              <.div()(
//                <.button(^.tpe := "submit", ^.className := "btn btn-default", LoginCSS.Style.createConnectionBtn, ^.onClick --> hide, "Subscribe")
//              )
//            )
//          )
//
//        )
//      )
//    }
//  }
//
//  private val component = ReactComponentB[Props]("RequestNewInvite")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .componentDidMount(scope => scope.backend.mounted(scope.props))
//    .componentDidUpdate(scope => Callback {
//      if (scope.currentState.postMessage) {
//        scope.$.backend.hide
//      }
//    })
//    .build
//
//  def apply(props: Props) = component(props)
//}


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

  case class State(postConnection: Boolean = false, selectizeInputId: String = "postNewConnectionSelectizeInput")

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

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
              <.div()(<.input(^.`type` := "radio", ^.name := "userConnection"), " Introduce yourself to existing user(s)."), <.br(),
              <.div()(<.input(^.`type` := "radio", ^.name := "userConnection"), " Invite new user(s) to sign up and  connect with you."), <.br(),
              <.div()(<.input(^.`type` := "radio", ^.name := "userConnection"), " Invite existing connections to connect with each other." + <.br() +
                "Note, each pair of connections will be introduced with the message above."), <.br()
            ),
            <.div(<.h5("Recipients:")),
            <.div(^.id := s.selectizeInputId)(
              SYNEREOCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s.selectizeInputId)))
            ),
            <.div(<.h5("Introduction:")),
            <.div()(
              <.textarea(^.rows := 6, ^.placeholder := "Enter your message here:", ^.required := true)
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
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postConnection) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}


