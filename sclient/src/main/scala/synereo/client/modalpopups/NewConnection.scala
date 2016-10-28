package synereo.client.modalpopups

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Button
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.Bootstrap.Modal
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon._
import synereo.client.css._
import diode.AnyAction._
import diode.ModelR

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import shared.dtos.{EstablishConnection, IntroConnections}
import japgolly.scalajs.react
import japgolly.scalajs.react.{Callback, _}
import org.querki.jquery._
import shared.models.ConnectionsModel
import synereo.client.components.{ConnectionsSelectize, _}
import synereo.client.services.{RootModel, SYNEREOCircuit}
import synereo.client.utils.{ConnectionsUtils, ContentUtils}
import synereo.client.components.Bootstrap._
import synereo.client.handlers.SetPreventNavigation

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

  val component = ReactComponentB[Props]("NewConnection")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(
        Button(Button.Props(B.addConnectionForm(), CommonStyle.default, P.addStyles, "", P.title, className = ""), P.title),
        if (S.showConnectionsForm) ConnectionsForm(ConnectionsForm.Props(B.addConnections, P.title))
        else
          Seq.empty[ReactElement]
      )
    })
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object ConnectionsForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback,
                   header: String)

  case class State(postConnection: Boolean = false, selectizeInputId: String = "pstNewCnxnSelParent",
                   introConnections: IntroConnections = IntroConnections(),
                   establishConnection: EstablishConnection = EstablishConnection(),
                   introduceUsers: Boolean = true,
                   chkCnxnNewUser: Boolean = true,
                   chkCnxnExstUser: Boolean = false,
                   agentUid: String = "",
                   userName: String = "",
                   lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  val connectionSelectize = SYNEREOCircuit.connect(_.connections)


  case class ConnectionsFormBackend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def fromSelecize(): Callback = {
      val cnxns = ConnectionsSelectize.getConnectionNames(t.state.runNow().selectizeInputId)
      val msg = if (cnxns.length > 1) {
        s"Hi ${cnxns(0)} and ${cnxns(1)}, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${t.state.runNow().userName}"
      } else {
        s"Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${t.state.runNow().userName}"
      }
      t.modState(s => s.copy(introConnections = s.introConnections.copy(aMessage = msg)))
    }

    def mounted(props: ConnectionsForm.Props): Callback = {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
      val usr = SYNEREOCircuit.zoom(_.user).value.name
      val msg = s"Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${usr}"
      t.modState(s => s.copy(userName = usr, introConnections = s.introConnections.copy(aMessage = msg)))
    }

    def hideModal(): Unit = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def chkCnxnExstUser(e: ReactEventI): react.Callback = {
      val state = t.state.runNow()
      t.modState(s => s.copy(chkCnxnExstUser = true, introduceUsers = false, chkCnxnNewUser = false))
    }

    def chkCnxnNewUser(e: ReactEventI): react.Callback = {
      val state = t.state.runNow()
      t.modState(s => s.copy(chkCnxnNewUser = true, introduceUsers = false, chkCnxnExstUser = false))
    }

    def chkCnxnExstandOther(e: ReactEventI): react.Callback = {
      val state = t.state.runNow()
      t.modState(s => s.copy(introduceUsers = true, chkCnxnNewUser = true, chkCnxnExstUser = false))
    }

    def getCnxn(uri: String): Option[ConnectionsModel] = {
      //      val test = SYNEREOCircuit.zoom(_.connections.get.connectionsResponse)
      //      println(test)
      val connectionsModel = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
      if (!connectionsModel.isEmpty)
        connectionsModel.find(e => e.connection.target.contains(uri))
      else
        None
    }

    def introduceTwoUsers() = {
      $("#cnxnError".asInstanceOf[js.Object]).addClass("hidden")
      val state = t.state.runNow()
      val msg = state.introConnections.aMessage.replaceAll("/", "//")
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.selectizeInputId)
      if (connections.length == 2) {
        val content = state.introConnections.copy(aConnection = connections(0), bConnection = connections(1),
          sessionURI = uri, alias = "alias", aMessage = msg, bMessage = msg)
        ContentUtils.postNewConnection(content)
        t.modState(s => s.copy(postConnection = true))
      } else {
        $("#cnxnError".asInstanceOf[js.Object]).removeClass("hidden")
        t.modState(s => s.copy(postConnection = false))
      }
    }

    def establishConnection(): react.Callback = {
      val state = t.state.runNow()
      $("#agentFieldError".asInstanceOf[js.Object]).addClass("hidden")
      getCnxn(state.agentUid) match {
        case Some(cnxn) =>
          $("#agentFieldError".asInstanceOf[js.Object]).removeClass("hidden")
          t.modState(s => s.copy(postConnection = false))
        case None =>
          val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
          val content = state.establishConnection.copy(sessionURI = uri,
            aURI = ConnectionsUtils.getSelfConnnection().source,
            bURI = s"agent://${state.agentUid}", label = "869b2062-d97b-42dc-af5d-df28332cdda1")
          ContentUtils.postNewConnection(content)
          t.modState(s => s.copy(postConnection = true))
      }
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val state = t.state.runNow()
      if (state.introduceUsers) {
        introduceTwoUsers()
      }
      else {
        establishConnection()
      }
    }

    def formClosed(state: ConnectionsForm.State, props: ConnectionsForm.Props): Callback = {
      props.submitHandler()
    }

    def updateContent(e: ReactEventI): react.Callback = {
      val value = e.target.value
      SYNEREOCircuit.dispatch(SetPreventNavigation())
      t.modState(s => s.copy(introConnections = s.introConnections.copy(bMessage = value, aMessage = value)))
    }

    def updateAgentUid(e: ReactEventI): react.Callback = {
      SYNEREOCircuit.dispatch(SetPreventNavigation())
      val value = e.target.value
      t.modState(s => s.copy(agentUid = value))
    }

    def updateLang(reader: ModelR[RootModel, js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

  }

  private val component = ReactComponentB[Props]("PostConnections")
    .initialState_P(p => State())
    .backend(new ConnectionsFormBackend(_))
    .renderPS((t, props, state) => {
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(props.header)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => t.backend.formClosed(state, props)
        ),
        <.form(^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "container-fluid")(
            <.div(^.className := "row")(
              <.div()(
                <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.checked := state.chkCnxnNewUser, ^.onChange ==> t.backend.chkCnxnExstandOther),
                  state.lang.selectDynamic("INTRODUCE_YOUR_EXISTING_CONNECTIONS_TO_EACH_OTHER").toString), <.br(),
                <.div(^.marginLeft := "15px", (!state.introduceUsers == true) ?= ConnectionsCSS.Style.hidden)(
                  <.div(<.h5("Connections:")),
                  <.div(^.id := s"${state.selectizeInputId}")(
                    ConnectionsSelectize(ConnectionsSelectize.Props(s"${state.selectizeInputId}", t.backend.fromSelecize, Option(2)))
                  ),
                  <.div(^.id := "cnxnError", ^.className := "hidden text-danger")
                  (state.lang.selectDynamic("PLEASE_PROVIDE_ONLY_TWO_CONNECTIONS").toString),
                  <.div((!state.introduceUsers) ?= ConnectionsCSS.Style.hidden,
                    <.div(<.h5("Introduction:")),
                    <.div()(
                      <.textarea(^.rows := 6,
                        ^.value := state.introConnections.aMessage, ^.onChange ==> t.backend.updateContent, ^.className := "form-control")
                    )

                  )
                ),
                //  <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", ^.onChange ==> chkCnxnNewUser),
                // " Invite new user(s) to sign up and  connect with you."), <.br(),

                <.div()(<.input(^.`type` := "radio", ^.name := "userConnection", /*^.checked := state.chkCnxnExstUser,*/ ^.onChange ==> t.backend.chkCnxnExstUser),
                  state.lang.selectDynamic("INTRODUCE_YOURSELF_TO_EXISTING_USER").toString), <.br()

              ),

              //            else if (s.chkCnxnNewUser == true) {
              //              <.div()(
              //                <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter Email ID")
              //              )
              //            }
              //            else if (s.introduceTwoUsers == true) {
              if (state.chkCnxnExstUser == true) {
                <.div(^.marginLeft := "15px")(
                  <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "User ID, e.g. 2a6d5dcb40634e8dafa4ec0f562b8fda, 05d1ba8d0d7945359b717873b7e7f6bf",
                    ^.value := state.agentUid, ^.onChange ==> t.backend.updateAgentUid),
                  <.div(^.id := "agentFieldError", ^.className := "hidden")
                  (state.lang.selectDynamic("USER_WITH_THIS_UID_IS_ALREADY_ADDED_AS_YOUR_CONNECTION").toString)
                )
              }
              else
                <.div(),


              //            }
              <.div()(
                <.div(^.className := "text-right")(
                  <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, /* ^.onClick --> hide*/ "Send"),
                  <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> t.backend.hide, "Cancel")
                )
              )
            ),
            <.div(bss.modal.footer)
          )
        )
      )

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
