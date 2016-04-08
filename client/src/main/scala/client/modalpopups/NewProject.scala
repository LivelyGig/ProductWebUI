package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import client.LGMain.Loc
import client.components.Bootstrap._
import client.components.Icon
import client.components.Icon._
import client.components._
import client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._

object NewProject {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(buttonName: String,addStyles: Seq[StyleA] = Seq() , addIcons : Icon,title: String)
  case class State(showNewProjectForm: Boolean = false)
  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showNewProjectForm = true))
    }
    def addProjectForm() : Callback = {
      t.modState(s => s.copy(showNewProjectForm = true))
    }
     def addNewProject( postProject: Boolean = false): Callback = {
     // log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewProjectForm}")
      if(postProject){
        t.modState(s => s.copy(showNewProjectForm = true))
      } else {
        t.modState(s => s.copy(showNewProjectForm = false))
      }
    }
      }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(/*ProjectCSS.Style.displayInitialbtn*/)(
        Button(Button.Props(B.addProjectForm(), CommonStyle.default, P.addStyles,P.addIcons,P.title,className = "profile-action-buttons"),P.buttonName),
        if (S.showNewProjectForm) PostAProjectForm(PostAProjectForm.Props(B.addNewProject))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object PostAProjectForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: ( Boolean) => Callback)
  case class State(postProject: Boolean = false)


  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }
    def hidemodal ={
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postProject = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postProject)
      props.submitHandler(state.postProject)
    }

    def render(s: State, p: Props) = {
           val headerText = "New Project"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
            <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Project Name", "Project Name")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Project Name",
                   ^.required:=true)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Start Date", "Start Date")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,^.id := "Start Date")
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Budget", "Budget")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "email", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Email",
                   ^.required:=true)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Currency", "Currency")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
                  // <.input(^.`type` := "radio")(" client")
                  <.div(^.className:="btn-group")(
                  <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select One  ")(
                    <.span(^.className:="caret"),
                    <.ul(^.className:="dropdown-menu")(
                      <.li()(<.a()("Item 1")),
                      <.li()(<.a()("Item 2")),
                      <.li()(<.a()("Item 3"))
                    )
                  )
                  )
                )
              )
            ),//col-md-8
            <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Contact Type", "Contact Type")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
                 // <.input(^.`type` := "radio")(" client")
                  <.div(^.className:="btn-group")(
                  <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Template 1  ")(
                    <.span(^.className:="caret")
                  ),
                  <.ul(^.className:="dropdown-menu")(
                    <.li()(<.a()("Item 1")),
                    <.li()(<.a()("Item 2")),
                    <.li()(<.a()("Item 3"))
                  )
                ))
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "End Date", "End Date")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
                  // <.input(^.`type` := "radio")(" client")
                  <.div(^.className:="btn-group")(
                  <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("mm/dd/yy  ")(
                    <.span(^.className:="caret")
                  ),
                  <.ul(^.className:="dropdown-menu")(
                    <.li()(<.a()("Item 1")),
                    <.li()(<.a()("Item 2")),
                    <.li()(<.a()("Item 3"))
                  )
                ))
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Work Location", "Work Location")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
                  // <.input(^.`type` := "radio")(" client")
                  <.div(^.className:="btn-group")(
                  <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select One")(
                    <.span(^.className:="caret")
                  ),
                  <.ul(^.className:="dropdown-menu")(
                    <.li()(<.a()("Item 1")),
                    <.li()(<.a()("Item 2")),
                    <.li()(<.a()("Item 3"))
                  )
                ))
              )
            )//col-md-4
          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(
            ),
            <.div()(
              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Describe the project:",^.lineHeight:= 4)
            ),
            <.div(DashBoardCSS.Style.marginTop10px)(
              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Message for selected members:",^.lineHeight:= 4)
            ),
            <.div(DashBoardCSS.Style.marginTop10px)(
              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Skill needed:",^.lineHeight:= 4)
            ),
            <.div(DashBoardCSS.Style.marginTop10px)(
              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Recipients:",^.lineHeight:= 4)
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign,DashBoardCSS.Style.marginTop10px)(
              <.div(
                <.input(^.`type`:="checkbox"),
                <.span(^.fontWeight:="bold")(" Allow forwarding")
              )

              ),
            <.div(DashBoardCSS.Style.modalHeaderPadding,^.className:="text-right")(
              //<.button(^.tpe := "submit",^.className:="btn btn-default","Submit"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Save as Draft"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Submit"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostAProjectForm")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if(scope.currentState.postProject){
        scope.$.backend.hidemodal
      }
    })
    .build
  def apply(props: Props) = component(props)
}
