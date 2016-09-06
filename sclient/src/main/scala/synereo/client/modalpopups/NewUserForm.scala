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
import synereo.client.modalpopupbackends.NewUserFormBackend
import synereo.client.sessionitems.SessionItems

import scala.language.reflectiveCalls
import scala.scalajs.js
import scalacss.ScalaCssReact._

//scalastyle:off
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
                   apiURL: String = s"https://localhost:9876"
                  )

  private val component = ReactComponentB[Props]("NewUserForm")
    .initialState_P(p =>
      if (addNewUserState)
        State(new SignUpModel())
      else
        State(new SignUpModel()))
    .backend(new NewUserFormBackend(_))
      .renderPS((t,P,S)=>{
        //val nodeName = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)
        val nodeName = dom.window.location.hostname
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
              <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "API Server", ^.value := S.apiURL,
                ^.className := "form-control", "data-error".reactAttr := "Username is required", ^.onChange ==> t.backend.updateAPIURL,
                ^.required := true, ^.placeholder := "API Server"),
              <.div(^.className := "help-block with-errors")
            ),
            <.div(^.className := "form-group")(
              <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "First name", ^.value := S.signUpModel.name, ^.className := "form-control", "data-error".reactAttr := "Username is required",
                ^.onChange ==> t.backend.updateName, ^.required := true, ^.placeholder := "Desired user name"),
              <.div(^.className := "help-block with-errors")
            ),
            <.div(^.className := "form-group")(
              <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "email", bss.formControl, ^.id := "Email", ^.value := S.signUpModel.email, ^.className := "form-control", "data-error".reactAttr := "Email is Invalid",
                ^.onChange ==> t.backend.updateEmail, ^.required := true, ^.placeholder := "Email address"),
              <.div(^.className := "help-block with-errors")
            ),
            <.div(^.className := "form-group")(
              <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Password", ^.value := S.signUpModel.password, ^.className := "form-control", /*"data-error".reactAttr:="Must be 6 characters long and include one or more number or symbol",*/
                ^.onChange ==> t.backend.updatePassword, ^.required := true, ^.placeholder := "Password", "data-minlength".reactAttr := "6"),
              <.div(/*SignupCSS.Style.passwordTextInfo, ^.className := "col-md-12 text-center",*/ ^.className := "help-block")("Must be 6 characters long and include one or more number or symbol")
            ),
            <.div(^.className := "form-group")(
              //            data-match="#inputPassword" data-match-error="Whoops, these don't match"
              <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.id := "Confirm Password", "data-match".reactAttr := "#Password", ^.value := S.signUpModel.confirmPassword, ^.className := "form-control", "data-match-erro".reactAttr := "Whoops, these don't match",
                ^.onChange ==> t.backend.updateConfirmPassword, ^.required := true, ^.placeholder := "Confirm password"),
              <.div(^.className := "help-block with-errors")
            ),
            <.div(^.className := "row")(
              <.div(^.className := "col-md-12 text-left", SignupCSS.Style.termsAndServicesContainer)(
                <.input(^.`type` := "checkbox", ^.id := "IamCoolWithThe", ^.required := true), <.label(^.`for` := "IamCoolWithThe")("I'm cool with the"),
                // <.img(^.src := "./assets/synereo-images/CheckBox_Off.svg", SignupCSS.Style.checkBoxTermsAndCond /*, ^.onClick ==> changeCheckBox*/), <.span("I am cool with the"),
                <.button(^.tpe := "button", ^.className := "btn btn-default", SignupCSS.Style.termsAndCondBtn, ^.onClick ==> t.backend.showTermsOfServices, "Terms of Service ")
              )
            ),
            <.div()(
              <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, SynereoCommanStylesCSS.Style.paddingRightZero, SignupCSS.Style.howItWorks)(
                <.div(^.className := "pull-left", SignupCSS.Style.signUpuserNameContainer)(
                  <.div(^.className := "text-left")("creating account on node: ", <.span(nodeName)),
                  <.span(SignupCSS.Style.howAccountsWorkLink)("How do accounts works accross nodes?")
                ),
                <.div(^.className := "pull-right", ^.className := "form-group")(
                  <.button(^.tpe := "submit", ^.id := "SignUp", SignupCSS.Style.SignUpBtn, ^.className := "btn", ^.onClick --> t.backend.hideModal, "Sign up")
                )
                //            <.button(^.tpe := "button", ^.className := "btn", ^.onClick --> hideModal, "Cancel")
              )
            ),
            <.div(bss.modal.footer)()
          )
        )
      })
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