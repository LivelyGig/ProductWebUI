package l.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import l.client.LGMain.Loc
import l.client.components.Bootstrap._
import l.client.components.Icon
import l.client.components.Icon._
import l.client.components._
import l.client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object NewRecommendation {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(buttonName :String,addStyles: Seq[StyleA] = Seq() , addIcons : Icon,title: String)
  case class State(showNewRecommendationForm: Boolean = false, newRecommendationForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showNewRecommendationForm = true))
    }
    def addNewRecommendationForm() : Callback = {
      t.modState(s => s.copy(showNewRecommendationForm = true))
    }

    def addNewRecommendation(postNewRecommendation: Boolean = false): Callback = {
   // log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewRecommendationForm}")
      if(postNewRecommendation){
         t.modState(s => s.copy(showNewRecommendationForm = true))
      } else {
        t.modState(s => s.copy(showNewRecommendationForm = false))
      }
    }
  }

  val component = ReactComponentB[Props]("AddNewRecommendation")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayModalbtn)(
        Button(Button.Props(B.addNewRecommendationForm(), CommonStyle.default,P.addStyles,P.addIcons,P.title, className = "profile-action-buttons" ),P.buttonName),
        if (S.showNewRecommendationForm) NewRecommendationForm(NewRecommendationForm.Props(B.addNewRecommendation))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object NewRecommendationForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (Boolean) => Callback)
  case class State(postNewRecommendation: Boolean = false)


  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def hidemodal = {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postNewRecommendation = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postNewRecommendation)
      props.submitHandler(state.postNewRecommendation)
    }

    def render(s: State, p: Props) = {
      val headerText = "New Recommendation"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
//          <.div(^.className:="row")(
//            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("New Recommendation"))
//          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(
            ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Enter your message here:",ProjectCSS.Style.textareaWidth,DashBoardCSS.Style.replyMarginTop )
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("Recipients"))
            ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Enter your message here:",ProjectCSS.Style.textareaWidth,DashBoardCSS.Style.replyMarginTop )

            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("Reference to "))
            ),
            <.div()(
              <.input(^.`type` := "text",ProjectCSS.Style.textareaWidth,^.placeholder:="Job/Post:")
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              <.button(^.tpe := "submit",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostNewRecommendation")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if(scope.currentState.postNewRecommendation){
        scope.$.backend.hidemodal
      }
    })
    .build
  def apply(props: Props) = component(props)
}

