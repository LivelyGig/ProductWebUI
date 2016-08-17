package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom._
import shared.models.SignUpModel
import synereo.client.components.Bootstrap.{Modal, _}
import synereo.client.components._
import synereo.client.css.{SignupCSS, SynereoCommanStylesCSS}
import synereo.client.sessionitems.SessionItems

import scala.language.reflectiveCalls
import scala.scalajs.js
import scalacss.ScalaCssReact._

object NewUserForm {
  var addNewUserState: Boolean = false
  var signUpModelUpdate = new SignUpModel("", "", "", "", "", false, false, false, false, false, false, "", false)

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (SignUpModel, Boolean, Boolean) => Callback)

  case class State(signUpModel: SignUpModel,
                   addNewUser: Boolean = false,
                   showTermsOfServicesForm: Boolean = false,
                   showLoginForm: Boolean = true,
                   apiURL: String = s"http://" + dom.window.location.hostname +":9876"
                  )

  case class Backend(t: BackendScope[Props, State]) {
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

    def showTermsOfServices(e: ReactEventI) = {
      addNewUserState = true
      t.modState(s => s.copy(showTermsOfServicesForm = true))
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

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //      println(state.addNewUser)
      signUpModelUpdate = state.signUpModel
      props.submitHandler(state.signUpModel, state.addNewUser, state.showLoginForm)
    }

    def render(s: State, p: Props) = {
      //val nodeName = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)
      val nodeName = s.apiURL
      val headerText = "Sign up"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span()(
            <.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div(SignupCSS.Style.signUpHeading)(headerText)
          ),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p),
          addStyles = Seq(SignupCSS.Style.signUpModalStyle)
        ),
        <.form(^.id := "SignUpForm", "data-toggle".reactAttr := "validator", ^.role := "form", ^.onSubmit ==> submitForm)(
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "API Server", ^.value := s.apiURL,
              ^.className := "form-control", "data-error".reactAttr := "Username is required",^.onChange ==> updateAPIURL,
              ^.required := true, ^.placeholder := "API Server"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "First name", ^.value := s.signUpModel.name, ^.className := "form-control", "data-error".reactAttr := "Username is required",
              ^.onChange ==> updateName, ^.required := true, ^.placeholder := "Desired user name"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "email", bss.formControl, ^.id := "Email", ^.value := s.signUpModel.email, ^.className := "form-control", "data-error".reactAttr := "Email is Invalid",
              ^.onChange ==> updateEmail, ^.required := true, ^.placeholder := "Email address"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "form-group")(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Password", ^.value := s.signUpModel.password, ^.className := "form-control", /*"data-error".reactAttr:="Must be 6 characters long and include one or more number or symbol",*/
              ^.onChange ==> updatePassword, ^.required := true, ^.placeholder := "Password", "data-minlength".reactAttr := "6"),
            <.div(/*SignupCSS.Style.passwordTextInfo, ^.className := "col-md-12 text-center",*/ ^.className := "help-block")("Must be 6 characters long and include one or more number or symbol")
          ),
          <.div(^.className := "form-group")(
            //            data-match="#inputPassword" data-match-error="Whoops, these don't match"
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Confirm Password", "data-match".reactAttr := "#Password", ^.value := s.signUpModel.confirmPassword, ^.className := "form-control", "data-match-erro".reactAttr := "Whoops, these don't match",
              ^.onChange ==> updateConfirmPassword, ^.required := true, ^.placeholder := "Confirm password"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 text-left", SignupCSS.Style.termsAndServicesContainer)(
              <.input(^.`type` := "checkbox", ^.id := "IamCoolWithThe"), <.label(^.`for` := "IamCoolWithThe")("I'm cool with the"),
              // <.img(^.src := "./assets/synereo-images/CheckBox_Off.svg", SignupCSS.Style.checkBoxTermsAndCond /*, ^.onClick ==> changeCheckBox*/), <.span("I am cool with the"),
              <.button(^.tpe := "button", ^.className := "btn btn-default", SignupCSS.Style.termsAndCondBtn, ^.onClick ==> showTermsOfServices, "Terms of Service ")
            )
          ),
          <.div()(
            <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, SynereoCommanStylesCSS.Style.paddingRightZero, SignupCSS.Style.howItWorks)(
              <.div(^.className := "pull-left", SignupCSS.Style.signUpuserNameContainer)(
                <.div(^.className := "text-left")("creating account on node: ", <.span(nodeName)),
                <.a(^.href := "#", SignupCSS.Style.howAccountsWorkLink)("How do accounts works accross nodes?")
              ),
              <.div(^.className := "pull-right", ^.className := "form-group")(
                <.button(^.tpe := "submit", ^.id := "SignUp", SignupCSS.Style.SignUpBtn, ^.className := "btn", ^.onClick --> hideModal, "Sign up")
              )
              //            <.button(^.tpe := "button", ^.className := "btn", ^.onClick --> hideModal, "Cancel")
            )
          ),
          <.div(bss.modal.footer)()
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("NewUserForm")
    .initialState_P(p =>
      if (addNewUserState)
        State(new SignUpModel())
      else
        State(new SignUpModel()))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {

    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.addNewUser || scope.currentState.showTermsOfServicesForm) {
        scope.$.backend.hidecomponent
      }
    })
    .build

  def apply(props: Props) = component(props)
}