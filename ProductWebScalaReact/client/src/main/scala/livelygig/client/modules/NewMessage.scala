//
//
//package livelygig.client.modules
//
//import livelygig.client.LGMain.Loc
//import livelygig.client.components.Bootstrap.Button
//import livelygig.client.components.Bootstrap.CommonStyle
//import livelygig.client.components.Bootstrap.Modal
//import livelygig.client.components.GlobalStyles
//import livelygig.client.components.Icon
//import livelygig.client.components._
//import livelygig.client.css.DashBoardCSS
//import livelygig.client.css.HeaderCSS
//import livelygig.client.css.ProjectCSS
//import livelygig.client.logger._
//import livelygig.client.models.UserModel
//import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
//import japgolly.scalajs.react.extra.router.RouterCtl
//import livelygig.client.LGMain.{Loc}
//import livelygig.client.services.ApiResponseMsg
//import livelygig.client.services.ApiResponseMsg
//import livelygig.client.services.CoreApi
//import livelygig.client.services.CoreApi._
//import livelygig.client.services.CoreApi._
//import livelygig.client.services.CoreApi._
//import org.scalajs.dom._
//import scala.scalajs.js
//import scala.util.{Failure, Success}
//import scalacss.ScalaCssReact._
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.extra.OnUnmount
//import japgolly.scalajs.react.vdom.prefix_<^._
//import livelygig.client.components.Bootstrap._
//import livelygig.client.components._
//import livelygig.client.logger._
//import livelygig.client.services._
//import livelygig.client.css.{HeaderCSS, DashBoardCSS,ProjectCSS}
//import scala.concurrent.ExecutionContext.Implicits.global
//
//object NewProject {
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(ctl: RouterCtl[Loc])
//
//  case class State(showPostProject: Boolean = false
//                  )
//
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//    def mounted(props: Props): Callback =  {
//      t.modState(s => s.copy(showPostProject = true))
//    }
//    def addLoginForm() : Callback = {
//      t.modState(s => s.copy(showPostProject = true))
//    }
//    def addNewLoginForm() : Callback = {
//      t.modState(s => s.copy(showPostProject = true))
//    }
//
//    def addNewAgent(userModel: UserModel, addNewAgent: Boolean = false): Callback = {
//      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${addNewAgent}")
//      if(addNewAgent){
//        createUser(userModel).onComplete {
//          case Success(s) =>
//            log.debug(s"createUser msg : ${s.msgType}")
//            if (s.msgType == ApiResponseMsg.CreateUserWaiting){
//              t.modState(s => s.copy(showPostProject = true)).runNow()
//            } else {
//              log.debug(s"createUser msg : ${s.content}")
//              t.modState(s => s.copy(/*showRegistrationFailed = true*/)).runNow()
//            }
//          case Failure(s) =>
//            log.debug(s"createUserFailure: ${s}")
//            t.modState(s => s.copy(/*showErrorModal = true*/)).runNow()
//          // now you need to refresh the UI
//        }
//        t.modState(s => s.copy(showPostProject = true))
//      } else {
//        t.modState(s => s.copy(showPostProject = true))
//      }
//    }
//  }
//
//  val component = ReactComponentB[Props]("AddNewAgent")
//    .initialState(State())
//    .backend(new Backend(_))
//    .renderPS(($, P, S) => {
//      val B = $.backend
//      <.div(ProjectCSS.Style.displayInitialbtn)(
//        Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"New Project"),
//        if (S.showPostProject) PostAProjectForm(PostAProjectForm.Props(B.addNewAgent))
//        else
//          Seq.empty[ReactElement]
//      )
//    })
//    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
//    .configure(OnUnmount.install)
//    .build
//  def apply(props: Props) = component(props)
//}
//
//object PostAProjectForm {
//  // shorthand for styles
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(submitHandler: (UserModel, Boolean) => Callback)
//  case class State(userModel: UserModel, postProject: Boolean = false)
//
//
//  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
//    def hide = Callback {
//      // instruct Bootstrap to hide the modal
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//    def mounted(props: Props): Callback = Callback {
//
//    }
//    def updateName(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
//    }
//    def updateEmail(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
//    }
//    def updatePassword(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
//    }
//    def toggleBTCWallet(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
//    }
//
//    def submitForm(e: ReactEventI) = {
//      e.preventDefault()
//      t.modState(s => s.copy(postProject = false))
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      println(state.postProject)
//      props.submitHandler(state.userModel, state.postProject)
//    }
//
//    def render(s: State, p: Props) = {
//      if (s.postProject){
//        jQuery(t.getDOMNode()).modal("hide")
//      }
//      val headerText = "Post A Project"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//        // this is called after the modal has been hidden (animation is completed)
//        closed = () => formClosed(s, p)),
//        <.form(^.onSubmit ==> submitForm)(
//          <.div(^.className:="row")(
//            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("Post A Project"))
//          ),
//          <.div(^.className:="row")(
//            <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
//              <.div(^.className:="row")(
//                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                  <.label(^.`for` := "Project Name", "Project Name")
//                ),
//                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Project Name",^.value:= s.userModel.name,
//                    ^.onChange==>updateName,^.required:=true)
//                )
//              ),
//              <.div(^.className:="row")(
//                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                  <.label(^.`for` := "Start Date", "Start Date")
//                ),
//                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,^.id := "Start Date")
//                )
//              ),
//              <.div(^.className:="row")(
//                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                  <.label(^.`for` := "Budget", "Budget")
//                ),
//                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                  <.input(^.tpe := "email", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Email", ^.value:= s.userModel.email,
//                    ^.onChange==>updateEmail,^.required:=true)
//                )
//              ),
//              <.div(^.className:="row")(
//                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                  <.label(^.`for` := "Currency", "Currency")
//                ),
//                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
//                  // <.input(^.`type` := "radio")(" client")
//                  <.div(^.className:="btn-group")(
//                    <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select One  ")(
//                      <.span(^.className:="caret"),
//                      <.ul(^.className:="dropdown-menu")(
//                        <.li()(<.a(^.href:="#")("Item 1")),
//                        <.li()(<.a(^.href:="#")("Item 2")),
//                        <.li()(<.a(^.href:="#")("Item 3"))
//                      )
//                    )
//                  )
//                )
//              )
//            ),//col-md-8
//            <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
//              <.div(^.className:="row")(
//                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                  <.label(^.`for` := "Contact Type", "Contact Type")
//                ),
//                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
//                  // <.input(^.`type` := "radio")(" client")
//                  <.div(^.className:="btn-group")(
//                    <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Template 1  ")(
//                      <.span(^.className:="caret")
//                    ),
//                    <.ul(^.className:="dropdown-menu")(
//                      <.li()(<.a(^.href:="#")("Item 1")),
//                      <.li()(<.a(^.href:="#")("Item 2")),
//                      <.li()(<.a(^.href:="#")("Item 3"))
//                    )
//                  ))
//              ),
//              <.div(^.className:="row")(
//                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                  <.label(^.`for` := "End Date", "End Date")
//                ),
//                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
//                  // <.input(^.`type` := "radio")(" client")
//                  <.div(^.className:="btn-group")(
//                    <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("mm/dd/yy  ")(
//                      <.span(^.className:="caret")
//                    ),
//                    <.ul(^.className:="dropdown-menu")(
//                      <.li()(<.a(^.href:="#")("Item 1")),
//                      <.li()(<.a(^.href:="#")("Item 2")),
//                      <.li()(<.a(^.href:="#")("Item 3"))
//                    )
//                  ))
//              ),
//              <.div(^.className:="row")(
//                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                  <.label(^.`for` := "Work Location", "Work Location")
//                ),
//                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                  //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
//                  // <.input(^.`type` := "radio")(" client")
//                  <.div(^.className:="btn-group")(
//                    <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select One")(
//                      <.span(^.className:="caret")
//                    ),
//                    <.ul(^.className:="dropdown-menu")(
//                      <.li()(<.a(^.href:="#")("Item 1")),
//                      <.li()(<.a(^.href:="#")("Item 2")),
//                      <.li()(<.a(^.href:="#")("Item 3"))
//                    )
//                  ))
//              )
//            )//col-md-4
//          ),//main row
//          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
//            <.div(DashBoardCSS.Style.marginTop10px)(
//            ),
//            <.div()(
//              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Describe the project:",^.lineHeight:= 4)
//            ),
//            <.div(DashBoardCSS.Style.marginTop10px)(
//              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Message for selected members:",^.lineHeight:= 4)
//            ),
//            <.div(DashBoardCSS.Style.marginTop10px)(
//              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Skill needed:",^.lineHeight:= 4)
//            ),
//            <.div(DashBoardCSS.Style.marginTop10px)(
//              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Recipients:",^.lineHeight:= 4)
//            )
//          ),
//          <.div()(
//            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign,DashBoardCSS.Style.marginTop10px)(
//              <.input(^.`type`:="checkbox")("Allow forwarding")
//            ),
//            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
//              //<.button(^.tpe := "submit",^.className:="btn btn-default","Submit"),
//              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Save as Draft"),
//              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
//              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
//            )
//          ),
//          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
//        )
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("PostAProjectForm")
//    .initialState_P(p => State(new UserModel("","","",false)))
//    .renderBackend[Backend]
//    .componentDidMount(scope => Callback {
//      val P = scope.props
//      val S=scope.state
//      val B=scope.backend
//
//      def hideModal = Callback {
//        if (S.postProject) {
//          def hide = Callback {
//            jQuery(scope.getDOMNode()).modal("hide")
//          }
//        }
//      }
//    })
//    .componentDidUpdate(scope=> Callback{
//
//    })
//    .build
//  def apply(props: Props) = component(props)
//}
