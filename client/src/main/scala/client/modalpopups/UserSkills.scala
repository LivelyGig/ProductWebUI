package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components.Icon.Icon
import client.components._
import client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scalacss.StyleA
import scala.language.reflectiveCalls

object UserSkills {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String,addStyles: Seq[StyleA] = Seq() , addIcons : Icon,title: String)

  case class State(showUserSkillsForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showUserSkillsForm = true))
    }

    def addUserSkillsForm(): Callback = {
      t.modState(s => s.copy(showUserSkillsForm = true))
    }

    def addUserSkills(postUserSkills: Boolean = false): Callback = {
      //  log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showUserSkillsForm}")
      if (postUserSkills) {
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
      <.div(/*ProjectCSS.Style.displayInitialbtn*/)(
        Button(Button.Props(B.addUserSkillsForm(), CommonStyle.default, P.addStyles,P.addIcons,P.title), P.buttonName),
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

  case class Props(submitHandler: (Boolean) => Callback)

  case class State(postUserSkills: Boolean = false)


  case class Backend(t: BackendScope[Props, State]) /* extends RxObserver(t)*/ {
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
      t.modState(s => s.copy(postUserSkills = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postUserSkills)
      props.submitHandler(state.postUserSkills)
    }

    def render(s: State, p: Props) = {
      val headerText = "Talent Profile  |  Employer Profile   |   Moderator Profile"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          //          <.div(^.className:="row")(
          //            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("User Skills"))
          //          ),
          "// For Talent:",
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Name", "Name *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name",
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Title", "Title *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Title",
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Capabilities", "Capabilities *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Capabilities",
                ^.required := true, ^.placeholder := "<Select top 10, ranked>")
            )
          ),
          "// For Employer:",
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "EmployerName", "Employer Name *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "EmployerName",
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Website", "Website")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Website",
                ^.required := false)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Tagline", "Tagline")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Tagline",
                ^.required := false, ^.placeholder := "Briefly describe your company")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Video", "Video")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Video",
                ^.required := false, ^.placeholder := "A link to a video about your company")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Twitter", "Twitter username")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Twitter",
                ^.required := false, ^.placeholder := "@yourcompany")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Logo", "Logo")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Logo",
                ^.required := false, ^.placeholder := "<Choose File>")
            )
          ),
          "// For Moderator:",
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Name", "Name *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name",
                ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Capabilities", "Capabilities *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Capabilities",
                ^.required := true, ^.placeholder := "<Select top 10, ranked>")
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
              <.label(^.`for` := "Commission", "Commission *")
            ),
            <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Commission",
                ^.required := true, ^.placeholder := "Enter commission rate, 0% - 2%")
            )
          ),
          <.div(DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.footTextAlign)(
            <.button(^.tpe := "submit", ^.className := "btn btn-default", ^.onClick --> hide, "Submit"),
            <.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide, "Cancel")
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("UserSkillsModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postUserSkills) {
        scope.$.backend.hidemodal
      }
    })
    .build

  def apply(props: Props) = component(props)
}

