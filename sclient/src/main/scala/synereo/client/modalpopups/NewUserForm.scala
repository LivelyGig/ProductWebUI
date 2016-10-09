package synereo.client.modalpopups

import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import synereo.client.components.Bootstrap.{Modal, _}
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS}

import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom._
import shared.models.SignUpModel
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.sessionitems.SessionItems

import scala.scalajs.js

//scalastyle:off
object NewUserForm {

  //  var addNewUserState: Boolean = false
  val editApiDetailBtn: js.Object = "#editApiDetailBtn"

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (SignUpModel, Boolean, Boolean, Boolean) => Callback)

  case class State(signUpModel: SignUpModel,
                   addNewUser: Boolean = false,
                   showTermsOfServicesForm: Boolean = false,
                   showLoginForm: Boolean = true,
                   apiURL: String = ""
                  )

  case class NewUserFormBackend(t: BackendScope[Props, State]) {

    var addNewUserState: Boolean = false
    var signUpModelUpdate = new SignUpModel("", "", "", "", "", false, false, false, false, false, false, "", false)

    def hideModal = {
      addNewUserState = false
      signUpModelUpdate = new SignUpModel("", "", "", "", "", false, false, false, false, false, false, "", false)
      t.modState(s => s.copy(showLoginForm = true))
    }

    def hidecomponent = {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateAPIURL(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(apiURL = value))
    }

    def closeAPITextbox(e: ReactEventI) = {
      $(editApiDetailBtn).show()
      if (window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL) != null)
        t.modState(s => s.copy(apiURL = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)))
      else
        t.modState(s => s.copy(apiURL = s"https://${dom.window.location.host}"))
    }

    def mounted(): Callback = {
      if (window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL) != null)
        t.modState(s => s.copy(apiURL = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)))
      else
        t.modState(s => s.copy(apiURL = s"https://${dom.window.location.host}"))
    }

    def updateName(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(name = value)))
    }

    def updateConfirmPassword(e: ReactEventI) = {
      val value = e.target.value
      //      println(value)
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(confirmPassword = value)))
    }

    def updateEmail(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(email = value)))
    }

    def updatePassword(e: ReactEventI) = {
      val value = e.target.value
      //      println(value)
      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(password = value)))
    }

    //    def toggleBTCWallet(e: ReactEventI) = {
    //      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(createBTCWallet = !s.signUpModel.createBTCWallet)))
    //    }

    def showTermsOfServices(): Callback = {
      addNewUserState = true
      t.modState(s => s.copy(showTermsOfServicesForm = true, addNewUser = false, showLoginForm = false))
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      window.sessionStorage.setItem(SessionItems.ApiDetails.API_URL, state.apiURL)
      val SignUp: js.Object = "#SignUp"
      if ($(SignUp).hasClass("disabled"))
        t.modState(s => s.copy(addNewUser = false))
      else
        t.modState(s => s.copy(addNewUser = true))
    }

    def formClosed(state: NewUserForm.State, props: NewUserForm.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //      println(state.addNewUser)
      signUpModelUpdate = state.signUpModel
      props.submitHandler(state.signUpModel, state.addNewUser, state.showLoginForm, state.showTermsOfServicesForm)
    }
  }

  private val component = ReactComponentB[Props]("NewUserForm")
    //    .initialState_P(p =>
    //      if (addNewUserState)
    //        State(new SignUpModel())
    //      else
    //        State(new SignUpModel()))
    .initialState_P(p => State(new SignUpModel()))
    .backend(new NewUserFormBackend(_))
    .renderPS((t, P, S) => {
      //val nodeName = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)
      val nodeName = dom.window.location.host
      val headerText = "Sign up"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span()(
            <.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div(SignupCSS.Style.signUpHeading)(headerText)
          ),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => t.backend.formClosed(S, P),
          addStyles = Seq(SignupCSS.Style.signUpModalStyle)
        ),
        <.form(^.id := "SignUpForm", "data-toggle".reactAttr := "validator", ^.role := "form", ^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "form-group")(
            <.div(LoginCSS.Style.apiDetailsContainer)(
              <.div(^.id := "addLabel", ^.className := "collapse")(
                <.div(^.className := "input-group")(
                  // <.label(LoginCSS.Style.loginFormLabel)("API Server"),
                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "API Server", ^.value := S.apiURL,
                    ^.className := "form-control", "data-error".reactAttr := "API is required", ^.onChange ==> t.backend.updateAPIURL,
                    ^.required := true, ^.placeholder := "API Server"),
                  <.div(^.className := "help-block with-errors"),
                  <.span(^.className := "input-group-addon", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel", ^.className := "btn", ^.onClick ==> t.backend.closeAPITextbox)(Icon.times),
                  <.span(^.className := "input-group-addon", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel", ^.className := "btn", ^.onClick --> Callback {
                    $(editApiDetailBtn).show()
                  })(Icon.check)
                )
              ),
              <.button(^.id := "editApiDetailBtn", ^.`type` := "button", LoginCSS.Style.editApiDetailBtn, ^.className := "btn btn-default", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel", ^.onClick --> Callback {
                $(editApiDetailBtn).hide()
              })("Edit API details")
            )
          ),
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "First name", ^.value := S.signUpModel.name, ^.className := "form-control", "data-error".reactAttr := "Username is required",
              ^.onChange ==> t.backend.updateName, ^.required := true, ^.placeholder := "Username"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "email", bss.formControl, ^.id := "Email", ^.value := S.signUpModel.email, ^.className := "form-control", "data-error".reactAttr := "Email is invalid",
              ^.onChange ==> t.backend.updateEmail, ^.required := true, ^.placeholder := "Email address"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Password", ^.value := S.signUpModel.password, ^.className := "form-control", /*"data-error".reactAttr:="Must be 6 characters long and include one or more number or symbol",*/
              ^.onChange ==> t.backend.updatePassword, ^.required := true, ^.placeholder := "Password", "data-minlength".reactAttr := "6"),
            <.div(/*SignupCSS.Style.passwordTextInfo, ^.className := "col-md-12 text-center",*/ ^.className := "help-block")("Must be 6 characters long and include at least one number or symbol")
          ),
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Confirm Password", "data-match".reactAttr := "#Password",
              ^.value := S.signUpModel.confirmPassword, ^.className := "form-control", "data-match-error".reactAttr := "Passwords do not match",
              ^.onChange ==> t.backend.updateConfirmPassword, ^.required := true, ^.placeholder := "Confirm password"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 text-left", SignupCSS.Style.termsAndServicesContainer)(
              <.input(^.`type` := "checkbox", ^.id := "termsOfServices", ^.required := true), <.label(^.`for` := "termsOfServices", ^.fontSize := "14.px")("I'm cool with the"),
              Button(Button.Props(t.backend.showTermsOfServices(), CommonStyle.default, Seq(SignupCSS.Style.termsAndCondBtn), "", ""), "Terms of Service ")
            )
          ),
          <.div()(
            <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, SynereoCommanStylesCSS.Style.paddingRightZero, SignupCSS.Style.howItWorks)(
              <.div(^.className := "pull-left", SignupCSS.Style.signUpuserNameContainer)(
                <.div(^.className := "text-left")("Creating account on node: ", <.span(nodeName)),
                <.span(SignupCSS.Style.howAccountsWorkLink)("How do accounts work across nodes?")
              ),
              <.div(^.className := "pull-right", ^.className := "form-group")(
                <.button(^.tpe := "submit", ^.id := "SignUp", SignupCSS.Style.signUpBtn, ^.className := "btn", ^.onClick --> t.backend.hideModal, "Sign up")
              )
            )
          ),
          <.div(bss.modal.footer)()
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.addNewUser || scope.currentState.showTermsOfServicesForm) {
        scope.$.backend.hidecomponent
      }
    })
    .build

  def apply(props: Props) = component(props)
}