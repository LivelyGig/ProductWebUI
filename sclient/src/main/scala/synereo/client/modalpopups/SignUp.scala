package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.{SignupCSS, SynereoCommanStylesCSS}
import synereo.client.models.UserModel
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._


object NewUserForm {
  var addNewUserState: Boolean = false
  var userModelUpdate = new UserModel("", "", "", "", "", false, false, "")

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (UserModel, Boolean, Boolean) => Callback)

  case class State(userModel: UserModel, addNewUser: Boolean = false, showTermsOfServicesForm: Boolean = false, showLoginForm: Boolean = true)

  case class Backend(t: BackendScope[Props, State]) {
    def hideModal = Callback {
      // instruct Bootstrap to hide the modal
      addNewUserState = false
      userModelUpdate = new UserModel("", "", "", "", "", false, false, "")
      t.modState(s=>s.copy(showLoginForm = true))
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hidecomponent = {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateName(e: ReactEventI) = {
      val value = e.target.value
      println(value)
      t.modState(s => s.copy(userModel = s.userModel.copy(name = value)))
    }

    //    def updateLastName(e: ReactEventI) = {
    //      val value = e.target.value
    //      t.modState(s => s.copy(userModel = s.userModel.copy(lastName = value)))
    //    }

    def updateConfirmPassword(e: ReactEventI) = {
      val value = e.target.value
      println(value)
      t.modState(s => s.copy(userModel = s.userModel.copy(ConfirmPassword = value)))
    }

    def updateEmail(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def updatePassword(e: ReactEventI) = {
      val value = e.target.value
      println(value)
      t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
    }

    //    def toggleBTCWallet(e: ReactEventI) = {
    //      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
    //    }

    def showTermsOfServices(e: ReactEventI) = {
      addNewUserState = true
      t.modState(s => s.copy(showTermsOfServicesForm = true))
    }

    def changeCheckBox(e: ReactEventI) = {
      println(e.target)
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(addNewUser = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.addNewUser)
      userModelUpdate = state.userModel
      props.submitHandler(state.userModel, state.addNewUser, state.showTermsOfServicesForm)
    }

    def render(s: State, p: Props) = {
      val headerText = "Sign up"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span()(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
          <.div(SignupCSS.Style.signUpHeading)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p),
        addStyles = Seq(SignupCSS.Style.signUpModalStyle)
      ),
        <.form(^.onSubmit ==> submitForm)(
          <.div()(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "First name", ^.value := s.userModel.name,
              ^.onChange ==> updateName, ^.required := true, ^.placeholder := "Desired user name")
          ),
          //          <.div()(
          //            <.input(^.tpe := "text", bss.formControl, ^.id := "Last name", ^.value := s.userModel.lastName,
          //              ^.onChange ==> updateLastName)
          //          ),
          <.div()(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "email", bss.formControl, ^.id := "Email", ^.value := s.userModel.email,
              ^.onChange ==> updateEmail, ^.required := true, ^.placeholder := "Email address")
          ),
          <.div()(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Password", ^.value := s.userModel.password,
              ^.onChange ==> updatePassword, ^.required := true, ^.placeholder := "Password"),
            <.div(SignupCSS.Style.passwordTextInfo, ^.className := "col-md-12 text-center")("Must be 6 characters long and include one or more number or symbol")
          ),
          <.div()(
            <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Confirm Password", ^.value := s.userModel.ConfirmPassword,
              ^.onChange ==> updateConfirmPassword, ^.required := true, ^.placeholder := "Confirm password")
          ),
          <.div(^.className := "row")(
            //          <.div(^.className:="col-md-12")(
            //            <.div()(<.input(^.`type` := "checkbox"), " * I am cool with the",
            //              <.button(^.tpe := "button", ^.className := "btn btn-default", "Terms of Service ", ^.onClick ==> showTermsOfServices))
            //          )
            <.div(^.className := "col-md-12 text-left", SignupCSS.Style.termsAndServicesContainer)(
              <.input(^.`type` := "checkbox", ^.id := "IamCoolWithThe"), <.label(^.`for` := "IamCoolWithThe")("I'm cool with the"),
              // <.img(^.src := "./assets/synereo-images/CheckBox_Off.svg", SignupCSS.Style.checkBoxTermsAndCond /*, ^.onClick ==> changeCheckBox*/), <.span("I am cool with the"),
              <.button(^.tpe := "button", ^.className := "btn btn-default", SignupCSS.Style.termsAndCondBtn, ^.onClick ==> showTermsOfServices, "Terms of Service "))
          ),
          <.div()(
            <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, SynereoCommanStylesCSS.Style.paddingRightZero, SignupCSS.Style.howItWorks)(
              <.div(^.className := "pull-left", SignupCSS.Style.signUpuserNameContainer)(
                <.div(^.className := "text-left")("creating account on node: ", <.span(s.userModel.name)),
                <.a(^.href := "#", SignupCSS.Style.howAccountsWorkLink)("How do accounts works accross nodes?")
              ),
              <.div(^.className := "pull-right")(
                <.button(^.tpe := "submit", SignupCSS.Style.SignUpBtn, ^.className := "btn", ^.onClick --> hideModal, "Sign up")
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
        State(new UserModel(userModelUpdate.email, userModelUpdate.password, userModelUpdate.ConfirmPassword, userModelUpdate.name, userModelUpdate.lastName, false))
      else
        State(new UserModel("", "", "", "", "", false, false, ""))
    )
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.addNewUser || scope.currentState.showTermsOfServicesForm) {
        scope.$.backend.hidecomponent
      }
    })
    .build

  def apply(props: Props) = component(props)
}