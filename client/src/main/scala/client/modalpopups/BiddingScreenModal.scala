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
import client.css._
import client.logger._
import client.models.UserModel
import client.services.CoreApi._
import client.services._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls

object BiddingScreenModal {
  val component = ReactComponentB[Props]("BiddingScreen")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayModalbtn)(
        Button(Button.Props(B.addBiddingScreenForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = "profile-action-buttons"), P.buttonName),
        // Button(Button.Props(B.addBiddingScreenForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn), className = "profile-action-buttons"),  Icon.music),
        if (S.showBiddingScreen) BiddingScreenModalForm(BiddingScreenModalForm.Props(B.addBiddingScreen))
        else if (S.showMessage) PostNewMessage(PostNewMessage.Props(B.hideMessage, "Message"))
        else if (S.showConfirmation) ConfirmationForm(ConfirmationForm.Props(B.hideConfirmation, "Confirmation"))
        else if (S.showAcceptDependencies) PayoutTransaction(PayoutTransaction.Props(B.hideAcceptDependencies, "Accept All Deliverables"))
        else if (S.showDispute) DisputeForm(DisputeForm.Props(B.hideDispute, "Dispute"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)

  @inline private def bss = GlobalStyles.bootstrapStyles

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String)

  case class State(showBiddingScreen: Boolean = false, showMessage: Boolean = false, showConfirmation: Boolean = false, showAcceptDependencies: Boolean = false, showDispute: Boolean = false)

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addBiddingScreenForm(): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addBiddingScreen(postBiddingScreen: Boolean = false, postMessage: Boolean = false, postConfirmation: Boolean = false, postAcceptDependencies: Boolean = false,
                         postDispute: Boolean = false): Callback = {
      log.debug(s"postMessage : ${postMessage} ,postBiddingScreen: ${postBiddingScreen}")
      if (postBiddingScreen) {
        t.modState(s => s.copy(showBiddingScreen = false))
      } else if (postMessage) {
        t.modState(s => s.copy(showBiddingScreen = false, showMessage = true))
      }
      else if (postConfirmation) {
        t.modState(s => s.copy(showBiddingScreen = false, showMessage = false, showConfirmation = true))
      }
      else if (postAcceptDependencies) {
        t.modState(s => s.copy(showBiddingScreen = false, showMessage = false, showConfirmation = false, showAcceptDependencies = true))
      }
      else if (postDispute) {
        t.modState(s => s.copy(showBiddingScreen = false, showMessage = false, showConfirmation = false, showAcceptDependencies = false, showDispute = true))
      }
      else {
        t.modState(s => s.copy(showBiddingScreen = false))
      }
    }

    def hideMessage(showMessage: Boolean = false): Callback = {
      t.modState(s => s.copy(showMessage = false, showConfirmation = false, showBiddingScreen = true))
    }

    def hideConfirmation(showConfirmation: Boolean = false): Callback = {
      t.modState(s => s.copy(showMessage = false, showConfirmation = false, showBiddingScreen = true))
    }

    def hideAcceptDependencies(showAcceptDependencies: Boolean = false): Callback = {
      t.modState(s => s.copy(showMessage = false, showConfirmation = false, showAcceptDependencies = false, showBiddingScreen = true))
    }

    def hideDispute(showDispute: Boolean = false): Callback = {
      t.modState(s => s.copy(showMessage = false, showConfirmation = false, showAcceptDependencies = false, showDispute = false, showBiddingScreen = true))
    }
  }

}

object BiddingScreenModalForm {
  private val component = ReactComponentB[Props]("BiddingScreenModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postBiddingScreen || scope.currentState.postMessage || scope.currentState.postConfirmation || scope.currentState.postAcceptDependencies || scope.currentState.postDispute) {
        scope.$.backend.hidemodal
      }
    })
    .build

  def apply(props: Props) = component(props)

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean, Boolean, Boolean, Boolean, Boolean) => Callback)

  case class State(postBiddingScreen: Boolean = false, postMessage: Boolean = false, postConfirmation: Boolean = false, postAcceptDependencies: Boolean = false, postDispute: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) /* extends RxObserver(t)*/ {
    def hidemodal = {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }

    def render(s: State, p: Props) = {
      val headerText = "Contract - ID: 25688  Title: Videographer Needed... "
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm, ^.className := "biddingscreenModalHeight")(
          <.ul(^.className := "nav nav-tabs")(
            <.li(^.className := "active")(<.a(^.href := "#home", "data-toggle".reactAttr := "tab", "Agreement")),
            <.li()(<.a(^.href := "#menu1", "data-toggle".reactAttr := "tab", "Escrow Setup")),
            <.li()(<.a(^.href := "#menu2", "data-toggle".reactAttr := "tab", "Execution")),
            <.li()(<.a(^.href := "#menu4", "data-toggle".reactAttr := "tab", "Feedback"))
          ),
          <.div(^.className := "tab-content")(
            <.div(^.id := "home", ^.className := "tab-pane fade in active")(
              // Initiating details
              <.div(^.id := "initiatingDetail", ^.className := "tab-container")(
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
                <.div(^.className := "row", ^.paddingTop := "10px")(
                  <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                    <.div()("Talent:")
                  ),
                  <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                    <.div()(<.a()("Abed"))
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
                <.div(^.className := "row", ^.paddingTop := "10px")(
                  <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                    <.div()("Status:")
                  ),
                  <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                    <.div()("Offer from talent to employer")
                  )
                ),
                <.div(DashBoardCSS.Style.splitContainer)(
                  <.div(^.className := "split")(
                    <.div(^.className := "row")(
                      <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ^.id := "dashboardResults2", BiddingScreenCSS.Style.BiddingScreenResults)(
                        <.div(^.id := "rsltScrollContainer")(
                          <.div(^.className := "container-fluid")(
                            <.div()(
                              <.div(^.className := "row", BiddingScreenCSS.Style.borderInitiating, BiddingScreenCSS.Style.marginLeftRight)(
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("Term ", <.span(^.paddingLeft := "20px")(), <.a()("add")),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Employer Agreement"),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Talent Agreement"),
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("History")
                                // ToDo: add actions column, e.g. for delete.
                              ),
                              <.div(BiddingScreenCSS.Style.biddingScreenData, ^.id := "workContractTermContainer")(
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
                                            <.li()(<.a()("Item 1")),
                                            <.li()(<.a()("Item 2")),
                                            <.li()(<.a()("Item 3"))
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

                                      <.div(DashBoardCSS.Style.marginTop10px)(
                                        //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                                        <.div(^.className := "btn-group")(
                                          <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Aaron Wu")(
                                            <.span(^.className := "caret")
                                          ),
                                          <.ul(^.className := "dropdown-menu")(
                                            <.li()(<.a()("Jim P. Blesho")),
                                            <.li()(<.a()("Remi Fastaou")),
                                            <.li()(<.a()("Jami Corporation"))
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
                            ),
                            <.div()(
                              <.div(^.className := "row", BiddingScreenCSS.Style.borderInitiating, BiddingScreenCSS.Style.marginLeftRight)(
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("All Terms"),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Last action:Statement of work: Abed updated..")
                              )
                            )
                          )
                        )
                      )
                    )
                  )
                )
              ),
              <.div(BiddingScreenCSS.Style.marginLeftRight)(
                <.div(^.className:="pull-right")(
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> confirmationForm)("Apply")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> confirmationForm)("Accept Offer")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Counter  Offer")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Reject  Offer")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
                )
              )
            ),
            <.div(^.id := "menu1", ^.className := "tab-pane fade")(
              // escrowDetail
              <.div(^.id := "escrowDetail", ^.className := "tab-container")(
                /*
                <.span(^.fontWeight.bold)("Status: "),
                <.div(^.className := "btn-group")(
                  <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Pending Funding: Join Shared Wallet ")(
                    <.span(^.className := "caret")
                  ),
                  <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                    <.li()(<.a()("Pending Funding - Join Shared Wallet")),
                    <.li()(<.a()("Pending Funding - Waiting on Funding Transaction")),
                    <.li()(<.a()("Funded")),
                    <.li()(<.a()("Pay Escrow Setup Commission"))
                  )
                ),
                */
                <.div(^.id := "Escrow1", ^.minHeight := "100%")(
                  <.span(^.fontWeight := "bold")("Status: Pending Funding -- Join Shared Wallet"), <.br(),
                  "All parties in the contract have agreed to the terms as of 2016-07-30 00:34 UTC-7 (PST). ",
                  "Those terms included Escrow. ",
                  // or "Those terms did not include Escrow. ",
                  <.br(),
                  "Please now set up Escrow:",
                  <.div(^.marginLeft := "15px")(
                    <.span(^.fontWeight := "bold")("1)"), " Prerequisite: Assure you have a CoPay wallet application installed, or ", <.a(^.href := "https://copay.io/", ^.target := "blank")("install it now"), ".", <.br(),
                    <.span(^.fontWeight := "bold")("2)"), " From the CoPay application:",
                    <.div(^.marginLeft := "15px")(
                      <.span(^.fontWeight := "bold")("a)"), " Add Wallet -> Join Shared Wallet.", <.br(),
                      <.span(^.fontWeight := "bold")("b)"), " After pressing the + button, scan the invitation QR below, or copy the code and paste it into CoPay", <.br(),
                      <.div(^.marginLeft := "15px")(
                        <.img(^.width := "263px")(^.src := "./assets/images/example-copay-invitation.png"), <.br(),
                        "92LtV5QQ6cMVEwlekjasfi9JKLH93277asllkjasdf98776ZHnL2LtV5QQ6cMVE ",
                        <.button()(
                          <.span(^.color := "red", ^.title := "Copy to clipboard")(Icon.copy)
                        )
                      ),
                      <.span(^.fontWeight := "bold")("c)"), " Follow prompts to complete your multi-sig wallet configuration. ", <.span(^.fontWeight.bold)("Important: back up and confirm your wallet seed."), <.br(),
                      <.span(^.fontWeight := "bold")("d)"), " Wait for the other party to do the same. Your CoPay application will indicate when completed."
                    )
                  )
                ),
                <.div(^.id := "Escrow2", ^.minHeight := "100%")(
                  <.span(^.fontWeight := "bold")("Status: Pending Funding -- Waiting on funding transaction"), <.br(),
                  "Both parties have set up their escrow wallet for this contract.", <.br(),
                  "Now, funding into escrow is required:", <.br(),
                  "From Employer, Pam:  1 XBT requested.  Not yet funded.", <.br(),
                  "Send bitcoin into the following address:", <.br(),
                  <.img()(^.src := "./assets/images/example-bitcoin-qr-code.png"),
                  <.br(),
                  "342ftSRCvFHfCeFFBzu4xwbeqnDw6BGUey",
                  "Copy to clipboard",
                  <.br(),
                  "From Talent, Abed:  0.02 XBT  requested.",
                  <.br(),
                  <.button(^.className := "btn dropdown-toggle", "data-target".reactAttr := "#demo", "data-toggle".reactAttr := "collapse")(" Escrow deposit, payment, and refund details..."),
                  <.div(^.id := "escrowDepositDetails", ^.id := "demo", ^.className := "collapse")(
                    <.span(^.fontWeight.bold)("Escrow Deposit and Payout Details"),
                    <.div(^.className := "row")(
                      <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                        "The following amounts are expected deposit and payout amounts under various circumstances, based on current LivelyGig policies applicable in this situation. See ",
                        <.a()("details"),
                        ".",
                        // ToDo: convert this to html.  Currency amounts should be unit-separator-aligned (decimal "." in US locale), so that not all amounts need to show lots of 000s after the decimal, but they can be visually added. See http://stackoverflow.com/questions/1363239/aligning-decimal-points-in-html
                        <.div()(<.img()(BiddingScreenCSS.Style.biddingscreenImgWidth, ^.src := "./assets/images/escrow_payout_example.png")
                        )
                      )
                    )
                  )
                ),
                <.div(^.id := "Escrow3", ^.minHeight := "100%")(
                  <.span(^.fontWeight := "bold")("Status: Funded"), <.br(),
                  "Funding received", <.br(),
                  "The following deposits were made into this contract:", <.br(),
                  <.a(^.href := "https://blockchain.info/tx/98640bd8a7b1db3d3ec3ce8b18fcd0c073001c6452a4d4277646870e455be81c", ^.target := "blank")("Tx 1"), <.br(),
                  <.a(^.href := "https://blockchain.info/tx/98640bd8a7b1db3d3ec3ce8b18fcd0c073001c6452a4d4277646870e455be81c", ^.target := "blank")("Tx 2"), <.br(),
                  "pending funding from buyer"
                ),
                <.div(^.id := "Escrow4", ^.minHeight := "100%")(
                  <.span(^.fontWeight := "bold")("Status: Pay Escrow Setup Commission"), <.br(),
                  "LivelyGig has initiated a payment request of XXX BTC (YYY USD). Your Contract workflow will continue once that payment is received."
                )
              ),
              <.div(DashBoardCSS.Style.modalHeaderPadding,^.className:="pull-right")(
                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Next >")(),
                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
              )
            ),
            <.div(^.id := "menu2", ^.className := "tab-pane fade")(
              // inProgressDeatil
              <.div(^.id := "inProgressDetail", ^.className := "tab-container")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div(^.className := "inProgress")(
                      <.div(BiddingScreenCSS.Style.marginHeader)("Milestones ", <.a("New")),
                      <.table(^.className := "table")(
                        <.thead(
                          <.tr(
                            <.th(BiddingScreenCSS.Style.indexWidth)("#"),
                            <.th(BiddingScreenCSS.Style.plannedFinishWidth)("Planned Finish"),
                            <.th(BiddingScreenCSS.Style.scheduledFinishWidth)("Scheduled Finish"),
                            <.th(BiddingScreenCSS.Style.scheduledFinishWidth)("Title"),
                            <.th(BiddingScreenCSS.Style.talentWidth)("Talent Complete"),
                            <.th(BiddingScreenCSS.Style.talentWidth)("Employer Complete"),
                            <.th(BiddingScreenCSS.Style.actionsWidth)("Actions")
                          )
                        ),
                        <.tbody(
                          <.tr(^.className := "info")(
                            <.td("1"),
                            <.td("1/22/16 11:22"),
                            <.td("1/22/16 11:22"),
                            <.td(BiddingScreenCSS.Style.titleTable)("Architecture"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.a("Link"), " ", <.a("Deliver"), " ", <.a("Pay"), " ", <.a("Delete"))
                          ),
                          <.tr(
                            <.td("2"),
                            <.td("1/22/16 11:22"),
                            <.td("1/22/16 11:22"),
                            <.td(BiddingScreenCSS.Style.titleTable)("Detailed Design"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.a("Link"), " ", <.a("Deliver"), " ", <.a("Pay"), " ", <.a("Delete"))
                          ),
                          <.tr(^.className := "info")(
                            <.td("3"),
                            <.td("1/22/16 11:22"),
                            <.td("1/22/16 11:22"),
                            <.td(BiddingScreenCSS.Style.titleTable)("Prototype"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.a("Link"), " ", <.a("Deliver"), " ", <.a("Pay"), " ", <.a("Delete"))
                          ),
                          <.tr(
                            <.td("4"),
                            <.td("1/22/16 11:22"),
                            <.td("1/22/16 11:22"),
                            <.td(BiddingScreenCSS.Style.titleTable)("Code Complete"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.input(^.`type` := "checkbox"), "1/22/16 11:22:00 AM"),
                            <.td(<.a("Link"), " ", <.a("Deliver"), " ", <.a("Pay"), " ", <.a("Delete"))
                          )
                        )
                      ),
                      <.div(BiddingScreenCSS.Style.marginHeader)("Escrowed Deliverables ", <.a("New")),
                      <.table(^.className := "table")(
                        <.thead(
                          <.tr(
                            <.th(^.className := "col-md-1")("#"),
                            <.th(^.className := "col-md-2")("Description"),
                            <.th(^.className := "col-md-2")("Filename"),
                            <.th(^.className := "col-md-2")("Uploaded"),
                            <.th(^.className := "col-md-2")("Escrow Status"),
                            <.th(^.className := "col-md-3")("Actions")
                          )
                        ),
                        <.tbody()(
                          <.tr(^.className := "info")(
                            <.td(^.className := "col-md-1")("1"),
                            <.td(^.className := "col-md-2")("Design Overview"),
                            <.td(^.className := "col-md-2")("design.vdx"),
                            <.td(^.className := "col-md-2")("2016-01-17"),
                            <.td(^.className := "col-md-2")("Released"),
                            <.td(^.className := "col-md-3")(<.a("Details"), " ", <.a("Pay/Release"))
                          ),
                          <.tr()(
                            <.td(^.className := "col-md-1")("2"),
                            <.td(^.className := "col-md-2")("Prototype code"),
                            <.td(^.className := "col-md-2")("Scala.zip"),
                            <.td(^.className := "col-md-2")("2016-02-14"),
                            <.td(^.className := "col-md-2")("Held pending payment"),
                            <.td(^.className := "col-md-3")(<.a("Details"), " ", <.a("Pay/Release"))
                          )
                        )
                      ),
                      <.div(BiddingScreenCSS.Style.marginHeader)("Messages ", <.a("New")),
                      <.table(^.className := "table")(
                        <.thead(
                          <.tr(
                            <.th(^.className := "col-md-1")("#"),
                            <.th(^.className := "col-md-2")("Sent"),
                            <.th(^.className := "col-md-2")("From"),
                            <.th(^.className := "col-md-2")("To"),
                            <.th(^.className := "col-md-2")("Subject"),
                            <.th(^.className := "col-md-3")("Actions")
                          )
                        ), //thead
                        <.tbody()(
                          <.tr(^.className := "info")(
                            <.td(^.className := "col-md-1")("1"),
                            <.td(^.className := "col-md-2")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-2")("Abed"),
                            <.td(^.className := "col-md-2")("SOW section 3.5"),
                            <.td(^.className := "col-md-3")(<.a(" Reply "), <.a("Forward "), <.a(" Favorite "), <.a(" Hide"))
                          ),
                          <.tr()(
                            <.td(^.className := "col-md-1")("2"),
                            <.td(^.className := "col-md-2")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-2")("Abed"),
                            <.td(^.className := "col-md-2")("SOW section 3.5"),
                            <.td(^.className := "col-md-3")(<.a(" Reply "), <.a("Forward "), <.a(" Favorite "), <.a(" Hide"))
                          ),
                          <.tr(^.className := "info")(
                            <.td(^.className := "col-md-1")("3"),
                            <.td(^.className := "col-md-2")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-2")("Abed"),
                            <.td(^.className := "col-md-2")("SOW section 3.5"),
                            <.td(^.className := "col-md-3")(<.a(" Reply "), <.a("Forward "), <.a(" Favorite "), <.a(" Hide"))
                          ),
                          <.tr(
                            <.td(^.className := "col-md-1")("4"),
                            <.td(^.className := "col-md-2")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-2")("Abed"),
                            <.td(^.className := "col-md-2")("SOW section 3.5"),
                            <.td(^.className := "col-md-3")(<.a(" Reply "), <.a("Forward "), <.a(" Favorite "), <.a(" Hide"))
                          )
                        )
                      ),
                      <.div(BiddingScreenCSS.Style.marginHeader)("Links ", <.a("New")),
                      <.table(^.className := "table")(
                        <.thead(
                          <.tr(
                            <.th(^.className := "col-md-1")("#"),
                            <.th(^.className := "col-md-3")("Added"),
                            <.th(^.className := "col-md-2")("By"),
                            <.th(^.className := "col-md-3")("Name"),
                            <.th(^.className := "col-md-2")("Actions")
                          )
                        ),
                        <.tbody(
                          <.tr(^.className := "info")(
                            <.td(^.className := "col-md-1")("1"),
                            <.td(^.className := "col-md-3")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-3")("SOW section 3.5"),
                            <.td(^.className := "col-md-2")(<.a("Favorite "), <.a("Hide"))
                          ),
                          <.tr(
                            <.td(^.className := "col-md-1")("2"),
                            <.td(^.className := "col-md-3")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-3")("SOW section 3.5"),
                            <.td(^.className := "col-md-2")(<.a("Favorite "), <.a("Hide"))
                          ),
                          <.tr(^.className := "info")(
                            <.td(^.className := "col-md-1")("3"),
                            <.td(^.className := "col-md-3")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-3")("SOW section 3.5"),
                            <.td(^.className := "col-md-2")(<.a("Favorite "), <.a("Hide"))
                          ),
                          <.tr(
                            <.td(^.className := "col-md-1")("4"),
                            <.td(^.className := "col-md-3")("1/22/16 11:22"),
                            <.td(^.className := "col-md-2")("Pam"),
                            <.td(^.className := "col-md-3")("SOW section 3.5"),
                            <.td(^.className := "col-md-2")(<.a("Favorite "), <.a("Hide"))
                          )
                        )
                      )
                    )
                  )
                )
              ),
                <.div(DashBoardCSS.Style.modalHeaderPadding,^.className:="pull-right")(
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> acceptDependencies)("Accept Deliverables")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> disputeForm)("Dispute")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Contract Message")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
              )
            ),
            <.div(^.id := "menu4", ^.className := "tab-pane fade")(
              // feedbackDetail
              <.div(^.id := "feedbackDetail", ^.className := "tab-container")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div()(
                      <.span(^.fontWeight.bold)("Employer's Feedback to Talent:"),
                      <.br(),
                      <.hr(),
                      <.span(^.fontWeight.bold)("General"),
                      // <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilities)("General"),
                      <.div()(
                        <.br(),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader, BiddingScreenCSS.Style.feedbackbgColor)(
                          <.div(^.className := "col-md-4 col-sm-4")("Communication"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Poor"),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "communication")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "communication")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "communication")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "communication")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "communication")),
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Excellent"),
                            <.div(^.className := "col-md-3 col-sm-3")()
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader)(
                          <.div(^.className := "col-md-4 col-sm-4")("Managed Expectations"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Poor"),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "managedExpectations")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "managedExpectations")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "managedExpectations")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "managedExpectations")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "managedExpectations")),
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Excellent"),
                            <.div(^.className := "col-md-3 col-sm-3")()
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader, BiddingScreenCSS.Style.feedbackbgColor)(
                          <.div(^.className := "col-md-4 col-sm-4")("Met Schedule"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Poor"),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "metSchedule")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "metSchedule")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "metSchedule")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "metSchedule")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "metSchedule")),
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Excellent"),
                            <.div(^.className := "col-md-3 col-sm-3")()
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader)(
                          <.div(^.className := "col-md-4 col-sm-4")("Delivered value for price"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Poor"),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "price")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "price")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "price")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "price")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "price")),
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Excellent"),
                            <.div(^.className := "col-md-3 col-sm-3")()
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader, BiddingScreenCSS.Style.feedbackbgColor)(
                          <.div(^.className := "col-md-4 col-sm-4")("Completeness of deliverables"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Poor"),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "deliverables")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "deliverables")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "deliverables")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "deliverables")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "deliverables")),
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Excellent"),
                            <.div(^.className := "col-md-3 col-sm-3")()
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader)(
                          <.div(^.className := "col-md-4 col-sm-4")("Likely to contract in future(if and when similar talent is needed)"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Unlikely"),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "contract")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "contract")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "contract")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "contract")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "contract")),
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Likely"),
                            <.div(^.className := "col-md-3 col-sm-3")()
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader, BiddingScreenCSS.Style.feedbackbgColor)(
                          <.div(^.className := "col-md-4 col-sm-4")("Likely to recommend to friend or colleague"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Unlikely"),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "recommend")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "recommend")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "recommend")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "recommend")),
                            <.div(^.className := "col-md-1 col-sm-1")(<.input(^.`type` := "radio", ^.name := "recommend")),
                            <.div(^.className := "col-md-2 col-sm-2", BiddingScreenCSS.Style.tableFont)("Likely"),
                            <.div(^.className := "col-md-3 col-sm-3")()
                          )
                        ),
                        <.hr(),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader /*BiddingScreenCSS.Style.capabilities*/)(
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilities)("Capabilities"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.table(^.className := "table table-bordered", BiddingScreenCSS.Style.tableFont)(
                              <.tbody(
                                <.tr(
                                  <.td(BiddingScreenCSS.Style.notApplicable)(^.rowSpan := 2)("Not applicable"),
                                  <.th(^.className := "text-center", ^.colSpan := 6)("Demonstrated")
                                ),
                                <.tr(
                                  <.td(BiddingScreenCSS.Style.noUnderstanding)("No Understanding"),
                                  <.td(BiddingScreenCSS.Style.awareness)("Awareness"),
                                  <.td(BiddingScreenCSS.Style.awareness)("Fundamental Understanding: Can work under supervision"),
                                  <.td(BiddingScreenCSS.Style.awareness)("Skilled Understanding: Can work alone and can delegate"),
                                  <.td(BiddingScreenCSS.Style.expertUnderstanding)("Expert Understanding")
                                )
                              )
                            )
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader, BiddingScreenCSS.Style.feedbackbgColor)(
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilitiesItems)("HTML, JavaScript, CSS"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "HJS")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "HJS")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "HJS")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "HJS")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "HJS")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "HJS"))
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader)(
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilitiesItems)("User Experience Design"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "userDesign")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "userDesign")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "userDesign")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "userDesign")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "userDesign")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "userDesign"))
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader, BiddingScreenCSS.Style.feedbackbgColor)(
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilitiesItems)("SQL Anywhere"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "sqlAnywhere")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "sqlAnywhere")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "sqlAnywhere")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "sqlAnywhere")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "sqlAnywhere")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "sqlAnywhere"))
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader)(
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilitiesItems)("Decentralized Architectures"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "architecture")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "architecture")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "architecture")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "architecture")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "architecture")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "architecture"))
                          )
                        ),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader, BiddingScreenCSS.Style.feedbackbgColor)(
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilitiesItems)("Financial Markets"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "markets")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "markets")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "markets")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "markets")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "markets")),
                            <.div(^.className := "col-md-2 col-sm-2 text-center")(<.input(^.`type` := "radio", ^.name := "markets"))
                          )
                        ),
                        <.hr(),
                        <.div(^.className := "row", BiddingScreenCSS.Style.marginHeader)(
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilities)("Testimonial"),
                          <.br(),
                          <.div(^.className := "col-md-4 col-sm-4", BiddingScreenCSS.Style.capabilitiesItems)("(will be made public if Talent agrees)"),
                          <.div(^.className := "col-md-8 col-sm-8")(
                            <.textarea(^.rows := 3, ProjectCSS.Style.textareaWidth)
                          )
                        )
                      )
                    )
                  )
                )
              ),
              <.div(BiddingScreenCSS.Style.marginLeftRight)(
                <.div(^.className:="pull-right")(
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Send Feedback")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick ==> messageForm)("Message")(),
                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn", ^.onClick --> hide)("Close")()
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
      )
    }

    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def messageForm(e: ReactEventI) = {
      t.modState(s => s.copy(postMessage = true))
    }

    def confirmationForm(e: ReactEventI) = {
      t.modState(s => s.copy(postConfirmation = true))
    }

    def disputeForm(e: ReactEventI) = {
      t.modState(s => s.copy(postDispute = true))
    }

    def acceptDependencies(e: ReactEventI) = {
      t.modState(s => s.copy(postAcceptDependencies = true))
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postBiddingScreen = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postBiddingScreen)
      props.submitHandler(state.postBiddingScreen, state.postMessage, state.postConfirmation, state.postAcceptDependencies, state.postDispute)
    }
  }

}

