package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import livelygig.client.logger._
import livelygig.client.models.UserModel
import livelygig.client.services.CoreApi._
import livelygig.client.services._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object UserSkills {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc])
  case class State(showUserSkillsForm: Boolean = false )

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showUserSkillsForm = true))
    }
    def addUserSkillsForm() : Callback = {
      t.modState(s => s.copy(showUserSkillsForm = true))
    }
     def addUserSkills(postUserSkills: Boolean = false): Callback = {
    //  log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showUserSkillsForm}")
      if(postUserSkills){
        t.modState(s => s.copy(showUserSkillsForm = true))
      } else {
        t.modState(s => s.copy(showUserSkillsForm = false))
      }
    }
  }

  val component = ReactComponentB[Props]("UserSkills")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn)(
        Button(Button.Props(B.addUserSkillsForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"Update Profile"),
        if (S.showUserSkillsForm) UserSkillsForm(UserSkillsForm.Props(B.addUserSkills))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object UserSkillsForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: ( Boolean) => Callback)
  case class State(postUserSkills: Boolean = false)


  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def hidemodal ={
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postUserSkills = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postUserSkills)
      props.submitHandler(state.postUserSkills)
    }

    def render(s: State, p: Props) = {
      val headerText = "User Skills"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
//          <.div(^.className:="row")(
//            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("User Skills"))
//          ),
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Name *", "Name *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "First name",
               ^.required:=true)
            )
          ),
            <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Email *", "Email *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "email", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Email",
             ^.required:=true)
            )
          ),
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Password *", "Password *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "email", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Email",
              ^.required:=true)
            )
          ),
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              <.button(^.tpe := "submit",^.className:="btn btn-default",^.onClick --> hide, "Submit"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("UserSkillsModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if(scope.currentState.postUserSkills){
        scope.$.backend.hidemodal
      }
    })
    .build
  def apply(props: Props) = component(props)
}

