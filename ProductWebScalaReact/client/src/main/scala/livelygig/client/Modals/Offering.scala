package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.Button
import livelygig.client.components.Bootstrap.CommonStyle
import livelygig.client.components.Bootstrap.Modal
import livelygig.client.components.Bootstrap._
import livelygig.client.components.GlobalStyles
import livelygig.client.components.Icon
import livelygig.client.components._
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css.ProjectCSS
import livelygig.client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import livelygig.client.modals.NewMessage.State
import livelygig.client.modals.PostNewMessage.State
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object Offering {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(buttonName: String)
  case class State(showNewOfferingForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {


    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showNewOfferingForm = true))
    }
    def addNewOfferingForm() : Callback = {
      t.modState(s => s.copy(showNewOfferingForm = true))
    }
    def addOffer(postOffer: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      if(postOffer){
        t.modState(s => s.copy(showNewOfferingForm = false))
      } else {
        t.modState(s => s.copy(showNewOfferingForm = true))
      }
    }
  }
  val component = ReactComponentB[Props]("NewOffering")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn/*, ^.onMouseOver --> B.displayBtn*/)(
        Button(Button.Props(B.addNewOfferingForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn),"","",className = "profile-action-buttons"),P.buttonName),
        if (S.showNewOfferingForm) OfferingForm(OfferingForm.Props(B.addOffer, "New Offering"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object OfferingForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (Boolean) => Callback, header: String)
  case class State(postOffer: Boolean = false)
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
      t.modState(s => s.copy(postOffer = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postOffer)
      props.submitHandler(state.postOffer)
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(

          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              //<.button(^.tpe := "submit",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, "Send"),
              //<.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope=> Callback{
      if(scope.currentState.postOffer){
        scope.$.backend.hideModal
      }
    })
    .build
  def apply(props: Props) = component(props)
}

