package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap.Button
import client.components.Bootstrap.CommonStyle
import client.components.Bootstrap.Modal
import client.components.GlobalStyles
import client.components.Icon
import client.components.Icon._
import client.components._
import client.css._
import client.services.{ApiTypes, CoreApi, LGCircuit, RootModel}
import japgolly.scalajs.react
import client.components.Bootstrap._
import client.utils.ConnectionsUtils
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos.{EstablishConnection, IntroConnections}
import shared.models.ConnectionsModel
import scala.scalajs.js

// scalastyle:off
object NewConnection {
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
  val usr = window.sessionStorage.getItem("userName")
  case class Props(submitHandler: () => Callback, header: String)

  case class State(postConnection: Boolean = false, selectizeInputId: String = "pstNewCnxnSelParent",
                   introConnections: IntroConnections = IntroConnections(),
                   establishConnection: EstablishConnection = EstablishConnection(), introduceUsers: Boolean = false,
                   chkCnxnNewUser: Boolean = false, chkCnxnExstUser: Boolean = true, agentUid : String = "")

  case class Backend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback =  {
      val msg = s"Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${usr}"
     // println("In mounted  = " + msg)
      t.modState(s => s.copy(introConnections =s.introConnections.copy(aMessage = msg)))
    }

    def hideModal(): Unit = {
      $(t.getDOMNode()).modal("hide")
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
      t.modState(s => s.copy(introduceUsers = true, chkCnxnNewUser = false, chkCnxnExstUser = false))
    }
    def getCnxn(uri: String): Option[ConnectionsModel] = {
      val connectionsModel = LGCircuit.zoom(_.connections.connectionsResponse).value
      if (!connectionsModel.isEmpty)
        connectionsModel.find(e => e.connection.target.contains(uri))
      else
        None
    }

//    def introduceTwoUsers() = {
//      $("#cnxnError".asInstanceOf[js.Object]).addClass("hidden")
//      val state = t.state.runNow()
//      //      val msg = state.introConnections.aMessage.replaceAll("/", "//")
//      val uri = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)
//      val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.selectizeInputId)
//      //      val content = state.introConnections.copy(aConnection = connections(0), bConnection = connections(1),
//      //        sessionURI = uri, alias = "alias", aMessage = msg, bMessage = msg)
//      //      println(connections.length)
//      if (connections.length == 2) {
//        val content = state.establishConnection.copy(sessionURI = uri,
//          aURI = connections(0).target,
//          bURI = connections(1).target, label = connections(0).label)
//        CoreApi.postIntroduction(content)
//        t.modState(s => s.copy(postConnection = true))
//      } else {
//        $("#cnxnError".asInstanceOf[js.Object]).removeClass("hidden")
//        t.modState(s => s.copy(postConnection = false))
//      }
//    } /*window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)*/

    def introduceTwoUsers() = {
      $("#cnxnError".asInstanceOf[js.Object]).addClass("hidden")
      val state = t.state.runNow()
      val msg = state.introConnections.aMessage.replaceAll("/", "//")
     // println(s"SessionURI in introduceTwoUsers ${LGCircuit.zoom(_.session.messagesSessionUri).value}")
      val uri = LGCircuit.zoom(_.session.messagesSessionUri).value
      val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.selectizeInputId)

      if (connections.length == 2) {
        val content = state.introConnections.copy(aConnection = connections(0), bConnection = connections(1),
          sessionURI = uri, alias = "alias", aMessage = msg, bMessage = msg)
        //        val content = state.establishConnection.copy(sessionURI = uri,
        //          aURI = connections(0).target,
        //          bURI = connections(1).target, label = connections(0).label)
        CoreApi.postIntroduction(content)
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
         // println(s"SessionURI in establishConnection ${LGCircuit.zoom(_.session.messagesSessionUri).value}")
          val uri = LGCircuit.zoom(_.session.messagesSessionUri).value /*window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)*/
          val content = state.establishConnection.copy(sessionURI = uri,
            aURI = ConnectionsUtils.getSelfConnnection(uri).source,
            bURI = s"agent://${state.agentUid}", label = "869b2062-d97b-42dc-af5d-df28332cdda1")
          CoreApi.postIntroduction(content)
          t.modState(s => s.copy(postConnection = true))
      }
    }


    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val state = t.state.runNow()
      if (state.introduceUsers) {
        introduceTwoUsers
      }
      else {
        establishConnection
      }
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println(state.postConnection)
      props.submitHandler()
    }

    def fromSelecize(): Callback = {
      val cnxns = ConnectionsSelectize.getConnectionNames(t.state.runNow().selectizeInputId)
      val msg =  if (cnxns.length > 1) {
        s"Hi ${cnxns(0)} and ${cnxns(1)}, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${usr}"
      } else {
        s"Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${usr}"
      }
     // println("msg " + msg)
      t.modState(s => s.copy(introConnections =s.introConnections.copy(aMessage = msg)))
    }

    def updateContent(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(introConnections = s.introConnections.copy(bMessage = value, aMessage = value)))
    }

    def updateAgentUid (e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(agentUid = value))
    }

    def render(s: State, p: Props) = {
      val connectionsProxy = LGCircuit.connect(_.connections)
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
            if (s.chkCnxnExstUser == true){
              <.div(
                <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter USER ID",^.value := s.agentUid, ^.onChange ==> updateAgentUid),
                <.div(^.id := "agentFieldError", ^.className := "hidden")
                ("User with this uid is already added as your connection")
              )
            }else
              <.div(),

            //            else if (s.chkCnxnNewUser == true) {
            //              <.div()(
            //                <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter Email ID")
            //              )
            //            }
            //   else if (s.chkCnxnExstandOther == true) {
            <.div((!s.introduceUsers == true) ?= DashBoardCSS.Style.hidden)(
              <.div(
                <.div(<.h5("Recipients:")),
                <.div(^.id := s"${s.selectizeInputId}")(
                  connectionsProxy(connectionsProxy => ConnectionsSelectize(ConnectionsSelectize.Props(connectionsProxy, s"${s.selectizeInputId}",fromSelecize)))
                ),
                <.div(^.id := "cnxnError", ^.className := "hidden text-danger")
                ("Please provide two connections"),
                <.div((!s.introduceUsers) ?= DashBoardCSS.Style.hidden,
                  <.div(<.h5("Introduction:")),
                  <.div()(
                    <.textarea(^.rows := 6, ^.value:=s.introConnections.aMessage, ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.value := s.introConnections.aMessage, ^.onChange ==> updateContent /*, ^.required := true*/)
                  )
                )
              )
            ),
            //  }
            //            else
            //              <.div(),
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
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentDidUpdate(scope => Callback {
      //println(s"states:chkCnxnExstUser = ${scope.currentState.chkCnxnExstUser}, chkCnxnNewUser = ${scope.currentState.chkCnxnNewUser}, chkCnxnExstandOther = ${scope.currentState.chkCnxnExstandOther}")
      if (scope.currentState.postConnection) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}

