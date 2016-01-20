package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css._
import livelygig.client.logger._
import livelygig.client.models.UserModel
import livelygig.client.services.CoreApi._
import livelygig.client.services._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object BiddingScreenModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc])

  case class State(showBiddingScreen: Boolean = false)


  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addBiddingScreenForm(): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addNewLoginForm(): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addBiddingScreen(userModel: UserModel, showBiddingScreen: Boolean = false): Callback = {
      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showBiddingScreen}")
      if (showBiddingScreen) {
        createUser(userModel).onComplete {
          case Success(s) =>
            log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiResponseMsg.CreateUserWaiting) {
              t.modState(s => s.copy(showBiddingScreen = true)).runNow()
            } else {
              log.debug(s"createUser msg : ${s.content}")
              t.modState(s => s.copy(/*showRegistrationFailed = true*/)).runNow()
            }
          case Failure(s) =>
            log.debug(s"createUserFailure: ${s}")
            t.modState(s => s.copy(/*showErrorModal = true*/)).runNow()
          // now you need to refresh the UI
        }
        t.modState(s => s.copy(showBiddingScreen = true))
      } else {
        t.modState(s => s.copy(showBiddingScreen = false))
      }
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn)(

        Button(Button.Props(B.addBiddingScreenForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)), "BiddingScreen"),
        if (S.showBiddingScreen) BiddingScreenModalForm(BiddingScreenModalForm.Props(B.addBiddingScreen))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object BiddingScreenModalForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (UserModel, Boolean) => Callback)

  case class State(userModel: UserModel, postProject: Boolean = false)


  case class Backend(t: BackendScope[Props, State]) /* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }

    def updateName(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
    }

    def updateEmail(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
    }

    def updatePassword(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
    }

    def toggleBTCWallet(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postProject = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postProject)
      props.submitHandler(state.userModel, state.postProject)
    }

    def render(s: State, p: Props) = {
      if (s.postProject) {
        jQuery(t.getDOMNode()).modal("hide")
      }
      val headerText = "Contract - ID: 25688  Title: Videographer Needed... "
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          //          <.div(^.className:="row")(
          //            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("BiddingScreen"))
          //          ),//main row
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                <.div()("Stage:")
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                // ToDo: the current status, e.g. Negotiating, should be in bold and come from the underlying model for the Contract.
                <.div()(<.a(^.fontWeight.bold)("Initiating "), " > ", <.a()("Ecrow "), " > ", <.a()("In Progress "), " > ", <.a()("Acceptance "), " > ", <.a()("Feedback "))
              )

            ),
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                <.div()("Project:")
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                <.div()(<.a()("25688"), " Videographer Needed ...")
              )
            ),
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                <.div()("Employer:")
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                <.div()(<.a()("Pam")), "snapshot"
              )
            ),
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                <.div()("Talent:")
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                <.div()(<.a()("Abed")), "Choose your profile:", "picklist... ", "snapshot"
              )
            ),
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                <.div()("Referred By:")
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                <.div()(<.a()("Britta"))
              )
            ),
            <.div(DashBoardCSS.Style.splitContainer)(
              <.div(^.className := "split")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ^.id := "dashboardResults2", BiddingScreenCSS.Style.BiddingScreenResults)(
                    <.div(^.id := "rsltScrollContainer")(
                      <.div(^.className := "container-fluid")(
                        //           <.div(^.className:="col-md-offset-5 col-sm-offset-5 col-xs-offset-5")(
                        //            <.div(BiddingScreenCSS.Style.biddingheader)("Agreement")
                        //
                        //          ),//row
                        <.div()(
                          <.div(^.className := "row", BiddingScreenCSS.Style.borderBottomHeader, BiddingScreenCSS.Style.marginLeftRight)(
                            <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("Term"),
                            <.div(^.className := "col-md-2 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Employer Agreement"),
                            <.div(^.className := "col-md-2 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Talent Agreement"),
                            <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("History")
                          ),
                          <.div(BiddingScreenCSS.Style.biddingScreenData)(
                            <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                <.div(/*DashBoardCSS.Style.slctHeaders*/)("Contract Template"),
                                <.div(^.className := "row")(
                                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", BiddingScreenCSS.Style.slctBiddingInputWidth)(
                                    <.div(^.className := "btn-group")(
                                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Nolo Service..-23")(
                                        <.span(^.className := "caret")
                                      ),
                                      <.ul(^.className := "dropdown-menu")(
                                        <.li()(<.a(^.href := "#")("Item 1")),
                                        <.li()(<.a(^.href := "#")("Item 2")),
                                        <.li()(<.a(^.href := "#")("Item 3"))
                                      )
                                    )
                                  ),
                                  <.div(BiddingScreenCSS.Style.slctBiddingInputLeftContainerMargin, DashBoardCSS.Style.marginTop10px)(
                                    //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                                    <.div()(<.a("view"))
                                  )
                                )
                              ),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                            ),

                            <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                <.div(/*DashBoardCSS.Style.slctHeaders*/)("Rate"),
                                <.div(^.className := "row")(
                                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                                    <.div()(<.input(^.className := "form-control", ^.placeholder := "25.30 USD"))
                                  )
                                )
                              ),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                            ),
                            <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                <.div()("Statement of Work"),
                                <.div(^.className := "row")(
                                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                                    <.div()(
                                      <.a()("View / Modify "),
                                      "Updated: 2016-01-12 SHA256:d14a sf"
                                    )
                                  )
                                )
                              ),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Last action: Abed updated 2016-01-12")
                            ),
                            <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(

                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                <.div(/*DashBoardCSS.Style.slctHeaders*/)("Moderator:"),
                                <.div(^.className := "row")(

                                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin, DashBoardCSS.Style.marginTop10px)(
                                    //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                                    <.div(^.className := "btn-group")(
                                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Aaron Wu")(
                                        <.span(^.className := "caret")
                                      ),
                                      <.ul(^.className := "dropdown-menu")(
                                        <.li()(<.a(^.href := "#")("Jim P. Blesho")),
                                        <.li()(<.a(^.href := "#")("Remi Fastaou")),
                                        <.li()(<.a(^.href := "#")("Jami Corporation"))
                                      )
                                    )
                                  )
                                )
                              ),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                            ),

                            <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                <.div(/*DashBoardCSS.Style.slctHeaders*/)("Completion Date"),
                                <.div(^.className := "row")(
                                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                                    <.div()(<.input(^.className := "form-control", ^.placeholder := "2016-08-15"))
                                  )
                                )
                              ),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                            )

                          )
                        ), //container

                        <.div()(
                          <.div(^.className := "row", BiddingScreenCSS.Style.borderBottomFooter, BiddingScreenCSS.Style.marginLeftRight)(
                            <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("All Terms"),
                            <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                            <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                            <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Last action:Statement of work: Abed updated..")
                          )
                        ),
                        <.div(BiddingScreenCSS.Style.marginLeftRight)(
                          <.div(^.className := "row")(
                            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                              <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(),
                              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(),
                              <.div(^.className := "col-md-8 col-sm-8 col-xs-8")(
                                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Apply")(),
                                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Message")(),
                                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Accept")(),
                                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Counter")(),
                                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Reject")(),
                                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Close")()
                              )
                            )
                          )
                        )
                      ) //gigConversation
                    )
                  )
                )
              )
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.footTextAlign)(
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State(new UserModel("", "", "", false)))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      val P = scope.props
      val S = scope.state
      val B = scope.backend

      def hideModal = Callback {
        if (S.postProject) {
          def hide = Callback {
            jQuery(scope.getDOMNode()).modal("hide")
          }
        }
      }
    })
    .componentDidUpdate(scope => Callback {

    })
    .build

  def apply(props: Props) = component(props)
}

