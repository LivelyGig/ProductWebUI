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

  case class Props(ctl: RouterCtl[Loc], buttonName: String)

  case class State(showBiddingScreen: Boolean = false, showMessage: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addBiddingScreenForm(): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addBiddingScreen(postBiddingScreen: Boolean = false, postMessage: Boolean = false): Callback = {
      log.debug(s"postMessage : ${postMessage} ,postBiddingScreen: ${postBiddingScreen}")
      if (postBiddingScreen) {
        t.modState(s => s.copy(showBiddingScreen = false))
      } else if (postMessage) {
        t.modState(s => s.copy(showBiddingScreen = false, showMessage = true))
      }
      else {
        t.modState(s => s.copy(showBiddingScreen = false))
      }
    }

    def hideMessage(showMessage: Boolean = false): Callback = {
      t.modState(s => s.copy(showMessage = false, showBiddingScreen = true))
    }
  }

  val component = ReactComponentB[Props]("BiddingScreen")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn)(
        Button(Button.Props(B.addBiddingScreenForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)), P.buttonName),
        if (S.showBiddingScreen) BiddingScreenModalForm(BiddingScreenModalForm.Props(B.addBiddingScreen))
        else if (S.showMessage) PostNewMessage(PostNewMessage.Props(B.hideMessage, "Message"))
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

  case class Props(submitHandler: (Boolean, Boolean) => Callback)

  case class State(postBiddingScreen: Boolean = false, postMessage: Boolean = false)


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

    def messageForm(e: ReactEventI) = {
      t.modState(s => s.copy(postMessage = true))
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postBiddingScreen = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postBiddingScreen)
      props.submitHandler(state.postBiddingScreen, state.postMessage)
    }

    def render(s: State, p: Props) = {
      val headerText = "Contract - ID: 25688  Title: Videographer Needed... "
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(

          <.ul(^.className := "nav nav-tabs")(
            <.li(^.className := "active")(<.a(^.href := "#home", "data-toggle".reactAttr := "tab", "Initiating")),
            <.li()(<.a(^.href := "#menu1", "data-toggle".reactAttr := "tab", "Escrow")),
            <.li()(<.a(^.href := "#menu2", "data-toggle".reactAttr := "tab", "In Progress")),
            // 2016-01-25 -- Ed intentionally commenting out Acceptance.  We'll probably merge its contents with In Progress state.
            // <.li()(<.a(^.href := "#menu3", "data-toggle".reactAttr := "tab", "Acceptance")),
            <.li()(<.a(^.href := "#menu4", "data-toggle".reactAttr := "tab", "Feedback"))
          ),
          <.div(^.className := "tab-content")(
            <.div(^.id := "home", ^.className := "tab-pane fade in active")(
              // Initiating details
              <.div(^.id := "initiatingDetail" /*, ^.borderStyle.solid*/)(
                // <.span(^.fontWeight.bold)("Initiating"),
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
                    <.div()(<.a()("Pam"))
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                    <.div()("Talent:")
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

                                <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight, ^.backgroundColor := "lightcyan")(
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
                                <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight, ^.backgroundColor := "lightcyan")(

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
                                  <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Apply")(),

                                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Accept")(),
                                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Counter")(),
                                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Reject")(),
                                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                                    // NewMessage(NewMessage.Props(RouterCtl[Loc],"Message")),
                                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
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
              )
            ),
            <.div(^.id := "menu1", ^.className := "tab-pane fade")(
              // escrowDetail
              <.div(^.id := "escrowDetail" /*, ^.borderStyle.solid*/)(
                //   <.span(^.fontWeight.bold)("Escrow"),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div()(
                      "All parties in the contract as of 2016-07-30 have agreed to the terms. Now, funding into escrow is required:",
                      <.br(),
                      "From Employer, Pam:  1 XBT requested.  Not yet funded.",
                      <.br(),
                      "Deposit bitcoin in the following contract:",
                      <.br(),
                      <.img()(^.src := "./assets/images/example-bitcoin-qr-code.png"),
                      <.br(),
                      "342ftSRCvFHfCeFFBuz4xwbeqnDw6BGUey",
                      "From Talent, Abed:  0.02 XBT  requested.",
                      <.br(),
                      <.button(^.className := "btn dropdown-toggle", "data-target".reactAttr := "#demo", "data-toggle".reactAttr := "collapse")(" Escrow deposit, payment, and refund details..."),

                      <.div(^.id := "escrowDepositDetails", ^.id := "demo", ^.className := "collapse")(
                        <.span(^.fontWeight.bold)("Escrow Deposit and Payout Details"),
                        <.div(^.className := "row")(

                          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                            "The following amounts are expected deposit and payout amounts under various circumstances, based on current LivelyGig policies applicable in this situation. See ",
                            <.a(^.href := "#")("details"),
                            ".",
                            // ToDo: convert this to html.  Currency amounts should be unit-separator-aligned (decimal "." in US locale), so that not all amounts need to show lots of 000s after the decimal, but they can be visually added. See http://stackoverflow.com/questions/1363239/aligning-decimal-points-in-html
                            <.div()(<.img()(BiddingScreenCSS.Style.biddingscreenImgWidth, ^.src := "./assets/images/escrow_payout_example.png")
                            )
                          )
                        )
                      )
                    )
                  )
                ),
                <.div()(
                  <.div(DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.footTextAlign)(
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
                  )
                )
              )
            ),
            <.div(^.id := "menu2", ^.className := "tab-pane fade")(

              // inProgressDeatil
              <.div(^.id := "inProgressDetail" /*, ^.borderStyle.solid*/)(
                // <.span(^.fontWeight.bold)("In Progress"),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div()(
                      <.img()(^.src := "./assets/images/sampleContractMilestonesStatus.PNG"),
                      <.br(),
                      <.img()(^.src := "./assets/images/sampleContractMessages.png"),
                      <.br(),
                      <.img()(^.src := "./assets/images/sampleContractLinks.png")
                    )
                  )
                ),
                <.div()(
                  <.div(DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.footTextAlign)(
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Accept")(),
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Dispute")(),
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
                  )
                )
              )
            ),
            <.div(^.id := "menu3", ^.className := "tab-pane fade")(

              // acceptanceDetail
              <.div(^.id := "acceptanceDetail" /*, ^.borderStyle.solid*/)(
                //  <.span(^.fontWeight.bold)("Acceptance"),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div()(
                      "Final acceptance",
                      <.br(),
                      "Employer  --  accept, dispute (with confirmation)",
                      <.br(),
                      "Talent -- accept, dispute (with confirmation)",
                      <.br(),
                      "Final payout information and transaction."
                    )
                  )
                ),
                <.div()(
                  <.div(DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.footTextAlign)(
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
                  )
                )
              )
            ),
            <.div(^.id := "menu4", ^.className := "tab-pane fade")(

              // feedbackDetail
              <.div(^.id := "feedbackDetail" /*, ^.borderStyle.solid*/)(

                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div()(
                      <.span(^.fontWeight.bold)("Employer's Feedback to Talent:"),
                      <.br(),
                      <.img()(^.src := "./assets/images/sampleContractFeedback.png")
                    )
                  )
                )
              ),
              // shared actions bellow all of the workflow sections
              <.div(BiddingScreenCSS.Style.marginLeftRight)(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(),
                    <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(),
                    <.div(^.className := "col-md-8 col-sm-8 col-xs-8")(
                      <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn" )("Send Feedback")(),
                      <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                      <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
                    )
                  )
                )
              )
            )
          ) //tabcontent

        ), //submitform
        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()

      )
    }
  }

  private val component = ReactComponentB[Props]("BiddingScreenModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postBiddingScreen || scope.currentState.postMessage) {
        scope.$.backend.hidemodal
      }
    })
    .build

  def apply(props: Props) = component(props)
}

