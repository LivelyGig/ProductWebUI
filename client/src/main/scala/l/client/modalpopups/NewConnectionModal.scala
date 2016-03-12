package l.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import l.client.LGMain.Loc
import l.client.components.Bootstrap.Button
import l.client.components.Bootstrap.CommonStyle
import l.client.components.Bootstrap.Modal
import l.client.components.Bootstrap._
import l.client.components.GlobalStyles
import l.client.components.Icon
import l.client.components.Icon
import l.client.components.Icon._
import l.client.components._
import l.client.components._
import l.client.css.DashBoardCSS
import l.client.css.HeaderCSS
import l.client.css.MessagesCSS
import l.client.css.ProjectCSS
import l.client.css.{DashBoardCSS, HeaderCSS, MessagesCSS, ProjectCSS}
import l.client.modals.Confirmation.State
import l.client.modals.ConfirmationForm.State
import scala.scalajs.js
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object NewConnectionModal {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(buttonName: String,addStyles: Seq[StyleA] = Seq() , addIcons : Icon,title: String)
  case class State(showConnectionsForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {

    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showConnectionsForm = true))
    }
    def addConnectionForm() : Callback = {
      t.modState(s => s.copy(showConnectionsForm = true))
    }
    def addConnections(postConnection: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      if(!postConnection){
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
      <.div(/*ProjectCSS.Style.displayInitialbtn*/)(
        Button(Button.Props(B.addConnectionForm(), CommonStyle.default,P.addStyles,P.addIcons,P.title),P.buttonName),
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
  case class State(postConnection: Boolean = false)

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
      t.modState(s => s.copy(postConnection = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postConnection)
      props.submitHandler(state.postConnection)
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(

          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostConnections")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope=> Callback{
      if(scope.currentState.postConnection){
        scope.$.backend.hideModal
      }
    })
    .build
  def apply(props: Props) = component(props)
}

