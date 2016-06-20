package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components._
import client.css.{FooterCSS, DashBoardCSS}
import shared.models.{SignUpModel}
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._

object NewAgentForm {
  var addNewAgentState: Boolean = false
  var signUpModelUpdate = new SignUpModel()

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (SignUpModel, Boolean, Boolean) => Callback)

  case class State(signUpModel: SignUpModel, addNewAgent: Boolean = false, showTermsOfServicesForm: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {
    def hideModal = Callback {
      // instruct Bootstrap to hide the modal
      addNewAgentState = false
      signUpModelUpdate = new SignUpModel()
      $(t.getDOMNode()).modal("hide")
    }

    def hideComponent = {
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }

    def updateName(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(name = value)))
    }

    def updateLastName(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(lastName = value)))
    }

    def updateConfirmPassword(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(confirmPassword = value)))
    }

    def updateEmail(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(email = value)))
    }

    def updateCanReceiveEmailUpdates(e: ReactEventI) = {
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(canReceiveEmailUpdates = !s.signUpModel.canReceiveEmailUpdates)))

    }

    def updateIsFreelancer(e: ReactEventI) = {
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(isFreelancer = !s.signUpModel.isFreelancer)))
    }

    def updateIsClient(e: ReactEventI) = {
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(isClient = !s.signUpModel.isClient)))
    }

    def updateIsModerator(e: ReactEventI) = {
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(isModerator = !s.signUpModel.isModerator)))
    }

    def toggleBTCWallet(e: ReactEventI) = {
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(createBTCWallet = !s.signUpModel.createBTCWallet)))
    }

    def updatePassword(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(password = value)))
    }

    def showTermsOfServices(e: ReactEventI) = {
      addNewAgentState = true
      t.modState(s => s.copy(showTermsOfServicesForm = true))
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(addNewAgent = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //      println(state.addNewAgent)
      signUpModelUpdate = state.signUpModel
      props.submitHandler(state.signUpModel, state.addNewAgent, state.showTermsOfServicesForm)
    }

    def render(s: State, p: Props) = {
      val headerText = "Sign up with LivelyGig credentials"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hideModal, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-6 col-sm-6 col-xs-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "First name *", "First name *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "First name", ^.value := s.signUpModel.name,
                    ^.onChange ==> updateName, ^.required := true)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Last name *", "Last name *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Last name", ^.value := s.signUpModel.lastName,
                    ^.onChange ==> updateLastName)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Email *", "Email *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "email", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Email", ^.value := s.signUpModel.email,
                    ^.onChange ==> updateEmail, ^.required := true)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Password *", "Password *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "password", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Password", ^.value := s.signUpModel.password,
                    ^.onChange ==> updatePassword, ^.required := true)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Confirm Password *", "Confirm Password *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "password", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Confirm Password", ^.value := s.signUpModel.confirmPassword,
                    ^.onChange ==> updateConfirmPassword, ^.required := true)
                )
              )
            ), //col-md-8
            <.div(^.className := "col-md-6 col-sm-6 col-xs-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Account type *", "Account roles")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.div()(
                    <.label()(<.input(^.`type` := "checkbox", ^.id := "Freelancer", ^.checked := s.signUpModel.isFreelancer, ^.onChange ==> updateIsFreelancer), " Freelancer"), <.br(),
                    <.label()(<.input(^.`type` := "checkbox", ^.id := "Client", ^.checked := s.signUpModel.isClient, ^.onChange ==> updateIsClient), " Client"), <.br(),
                    <.label()(<.input(^.`type` := "checkbox", ^.id := "Moderator", ^.checked := s.signUpModel.isModerator, ^.onChange ==> updateIsModerator), " Moderator"), <.br()

                  ),
                  <.div()(
                    "These roles have distinct alias profiles and reputation. You can change your role options later. Obviously, one cannot moderate their own work contracts."
                  )
                )
              )
            )
          ), //main row
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(
              <.div()(
                <.input(^.`type` := "checkbox", ^.id := "createBTCWallet", ^.checked := s.signUpModel.createBTCWallet, ^.onChange ==> toggleBTCWallet),
                " * I understand and agree to the LivelyGig",
                <.button(^.tpe := "button", ^.className := "btn-link", DashBoardCSS.Style.btnDefault, FooterCSS.Style.legalModalBtn, "Terms of Service", ^.onClick ==> showTermsOfServices),
                " and ",
                <.button(^.tpe := "button", ^.className := "btn-link", DashBoardCSS.Style.btnDefault, FooterCSS.Style.legalModalBtn, "Privacy Policy", ^.onClick ==> showTermsOfServices),
                "."
              )


            ),
            <.div()(
              <.input(^.`type` := "checkbox", ^.id := "updateEmail", ^.checked := s.signUpModel.canReceiveEmailUpdates, ^.onChange ==> updateCanReceiveEmailUpdates), " Send me occasional email updates from LivelyGig"
            )
          ),
          <.div()(
            // <.div(DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.footTextAlign, DashBoardCSS.Style.marginTop10px)("You will receive a email of code confirming creation of your new account shortly after completing this form"),
            <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
              <.button(^.tpe := "submit", ^.className := "btn", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hideModal, "Submit"),
              <.button(^.tpe := "button", ^.className := "btn", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hideModal, "Cancel")
            )
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("NewAgentForm")
    .initialState_P(p =>
      if (addNewAgentState)
        State(new SignUpModel(signUpModelUpdate.email, signUpModelUpdate.password, signUpModelUpdate.confirmPassword, signUpModelUpdate.name, signUpModelUpdate.lastName, signUpModelUpdate.createBTCWallet, signUpModelUpdate.isModerator,
          signUpModelUpdate.isClient, signUpModelUpdate.isFreelancer, signUpModelUpdate.canReceiveEmailUpdates))
      else
        State(new SignUpModel("", "", "", "", "", false, false, false, false, false, false, "")))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.addNewAgent || scope.currentState.showTermsOfServicesForm) {
        scope.$.backend.hideComponent
      }
    })
    .build

  def apply(props: Props) = component(props)
}