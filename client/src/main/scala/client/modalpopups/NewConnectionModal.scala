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
import client.services.{CoreApi, LGCircuit}
import japgolly.scalajs.react

import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos.IntroductionModel
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
      <.div( /*ProjectCSS.Style.displayInitialbtn*/ )(
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
                   introductionModel: IntroductionModel = IntroductionModel())

  case class Backend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }
    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val state = t.state.runNow()
      val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.selectizeInputId)
      val introduction = state.introductionModel.copy(aConnection = connections(0), bConnection = connections(1),
        sessionURI = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI), alias = "alias")
      CoreApi.postIntroduction(introduction)
      t.modState(s => s.copy(postConnection = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postConnection)
      props.submitHandler()
    }

    def updateContent(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(introductionModel = s.introductionModel.copy(bMessage = value, aMessage = value)))
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
            /*<.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("To"))
            ),*/
            /*val selectizeControl : js.Object =*/
            <.div()(
              <.div()(<.input(^.`type` := "radio",^.name:="userConnection"/*, ^.checked := s.userModel.isFreelancer, ^.onChange ==> updateIsFreelancer*/), " Introduce yourself to existing user(s)."), <.br(),
              <.div()(<.input(^.`type` := "radio" ,^.name:="userConnection"/*, ^.checked := s.userModel.isClient, ^.onChange ==> updateIsClient*/), " Invite new user(s) to sign up and  connect with you."), <.br(),
              <.div()(<.input(^.`type` := "radio" ,^.name:="userConnection",^.checked:="true" /*, ^.checked := s.userModel.isModerator, ^.onChange ==> updateIsModerator*/), " Invite existing connections to connect with each other." + <.br() +
        "Note, each pair of connections will be introduced with the message above."), <.br()
            ),
            <.div(<.h5("Recipients:")),
            <.div(^.id := s.selectizeInputId)(
              //              val to = "{\"source\":\"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias\", \"label\":\"34dceeb1-65d3-4fe8-98db-114ad16c1b31\",\"target\":\"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias\"}"
              LGCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s.selectizeInputId)))
            ),
            <.div(<.h5("Introduction:")),
            <.div()(
              <.textarea(^.rows := 6, ^.placeholder := "Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n <name>", ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.value := s.introductionModel.aMessage, ^.onChange ==> updateContent, ^.required := true)
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
              <.button(^.tpe := "submit", ^.className := "btn",WorkContractCSS.Style.createWorkContractBtn, /*^.onClick --> hide, */ "Send"),
              <.button(^.tpe := "button", ^.className := "btn",WorkContractCSS.Style.createWorkContractBtn, ^.onClick --> hide, "Cancel")
            )
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostConnections")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postConnection) {
        scope.$.backend.hide.runNow()
      }
    })
    .build
  def apply(props: Props) = component(props)
}

