package synereo.client.modalpopups

import diode.ModelRO
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom._
import shared.models.SignUpModel
import synereo.client.components.Bootstrap.{Modal, _}
import synereo.client.components._
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS}
import synereo.client.services.SYNEREOCircuit
import synereo.client.sessionitems.SessionItems

import scala.language.reflectiveCalls
import scala.scalajs.js
import scalacss.ScalaCssReact._

//scalastyle:off
object SignUpForm {

  val editApiDetailBtn: js.Object = "#editApiDetailBtn"
  var addNewUserState: Boolean = false
  var signUpModelUpdate = new SignUpModel()

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (SignUpModel, Boolean, Boolean, Boolean) => Callback)

  case class State(signUpModel: SignUpModel,
                   addNewUser: Boolean = false,
                   showTermsOfServicesForm: Boolean = false,
                   showLoginForm: Boolean = false,
                   portNumber: String = if (dom.window.location.port == "443") {
                     ""
                   } else {
                     s":${dom.window.location.port}"
                   }, //"9876",
                   apiURL: String = "",
                   lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value
                  )

  case class NewUserFormBackend(t: BackendScope[Props, State]) {


    def hideModal = Callback {
      addNewUserState = false
      signUpModelUpdate = new SignUpModel()
      jQuery(t.getDOMNode()).modal("hide")
      //  t.modState(s => s.copy(showLoginForm = true,addNewUser=false))
    }

    def hidecomponent = {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateAPIURL(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(apiURL = value))
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def closeAPITextbox(e: ReactEventI) = {
      $(editApiDetailBtn).show()
      if (window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL) != null)
        t.modState(s => s.copy(apiURL = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)))
      else
        t.modState(s => s.copy(apiURL = s"https://${dom.window.location.host}${t.state.runNow().portNumber}"))
    }

    def mounted(): Callback = Callback {
      if (window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL) != null)
        t.modState(s => s.copy(apiURL = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL))).runNow()
      else
        t.modState(s => s.copy(apiURL = s"https://${dom.window.location.hostname}${t.state.runNow().portNumber}")).runNow()
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
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
      val signUpBtnId: js.Object = "#signUpBtnId"
      if ($(signUpBtnId).hasClass("disabled"))
        t.modState(s => s.copy(addNewUser = false))
      else
        t.modState(s => s.copy(addNewUser = true))
    }

    def formClosed(state: SignUpForm.State, props: SignUpForm.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //      println(state.addNewUser)
      if (state.showTermsOfServicesForm)
        signUpModelUpdate = state.signUpModel
      props.submitHandler(state.signUpModel, state.addNewUser, state.showLoginForm, state.showTermsOfServicesForm)
    }
  }

  private val component = ReactComponentB[Props]("NewUserForm")
    .initialState_P(p =>
      if (addNewUserState)
        State(new SignUpModel(signUpModelUpdate.email,
          "",
          "",
          signUpModelUpdate.name,
          signUpModelUpdate.lastName,
          signUpModelUpdate.createBTCWallet,
          signUpModelUpdate.isModerator,
          signUpModelUpdate.isClient,
          signUpModelUpdate.isFreelancer,
          signUpModelUpdate.canReceiveEmailUpdates))
      else
        State(new SignUpModel()))
    .backend(new NewUserFormBackend(_))
    .renderPS((t, props, state) => {
      //val nodeName = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)
      val nodeName = dom.window.location.host
      val headerText = state.lang.selectDynamic("SIGN_UP").toString
      Modal(
        Modal.Props(
          // header contains a cancel button (X)

          /*
          header = hide => <.span()(
            <.button(^.tpe := "button", bss.close, ^.onClick --> t.backend.hideModal, Icon.close),
   //         <.img(^.src := "./assets/synereo-images/synereologo.png", LoginCSS.Style.signUpImg),
            <.div(SignupCSS.Style.signUpHeading)(headerText)
          ),*/
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> t.backend.hideModal, Icon.close),
            <.div(SignupCSS.Style.signUpHeading)(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => t.backend.formClosed(state, props),
          addStyles = Seq(SignupCSS.Style.signUpModalStyle), keyboard = false, id = "signUpContainer"
        ),
        <.form(^.id := "SignUpForm", "data-toggle".reactAttr := "validator", ^.role := "form", ^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "form-group")(
            <.div(LoginCSS.Style.apiDetailsContainer)(
              <.div(^.id := "addLabel", ^.className := "collapse")(
                <.div(^.className := "input-group")(
                  // <.label(LoginCSS.Style.loginFormLabel)("API Server"),
                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "API Server", ^.value := state.apiURL,
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
          <.div(^.className := "form-group has-feedback")(
            <.input(SignupCSS.Style.inputStyleSignUpFormWidth, ^.tpe := "text", bss.formControl, ^.id := "First name", ^.value := state.signUpModel.name, ^.className := "form-control", "data-error".reactAttr := "Username is required",
              ^.onChange ==> t.backend.updateName, ^.required := true, ^.placeholder := state.lang.selectDynamic("USERNAME").toString),
            <.span(^.className := "glyphicon form-control-feedback", SignupCSS.Style.formControlMargin, "aria-hidden".reactAttr := "true"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "form-group has-feedback")(
            <.input(SignupCSS.Style.inputStyleSignUpFormWidth, ^.tpe := "email", bss.formControl, ^.id := "Email", ^.value := state.signUpModel.email, ^.className := "form-control", "data-error".reactAttr := "Email is invalid",
              ^.onChange ==> t.backend.updateEmail, ^.required := true, ^.placeholder := state.lang.selectDynamic("EMAIL_ADDRESS").toString),
            <.span(^.className := "glyphicon form-control-feedback", SignupCSS.Style.formControlMargin, "aria-hidden".reactAttr := "true"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "form-group has-feedback")(
            <.input(SignupCSS.Style.inputStyleSignUpFormWidth, ^.tpe := "password", bss.formControl, ^.id := "Password", ^.value := state.signUpModel.password, ^.className := "form-control", /*"data-error".reactAttr:="Must be 6 characters long and include one or more number or symbol",*/
              ^.onChange ==> t.backend.updatePassword, ^.required := true, ^.placeholder := state.lang.selectDynamic("PASSWORD").toString, "data-minlength".reactAttr := "6", "pattern".reactAttr := "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&.])[A-Za-z\\d$@$!%*#?&.]{6,}$"),
            <.span(^.className := "glyphicon form-control-feedback", SignupCSS.Style.formControlMargin, "aria-hidden".reactAttr := "true"),
            <.div(/*SignupCSS.Style.passwordTextInfo, ^.className := "col-md-12 text-center",*/ SignupCSS.Style.passwordTextInfo, ^.className := "help-block")(state.lang.selectDynamic("PASSWORD_CONDITION").toString)
          ),
          <.div(^.className := "form-group  has-feedback")(
            <.input(SignupCSS.Style.inputStyleSignUpFormWidth, ^.tpe := "password", bss.formControl, ^.id := "Confirm Password", "data-match".reactAttr := "#Password",
              ^.value := state.signUpModel.confirmPassword, ^.className := "form-control", "data-match-error".reactAttr := "Passwords do not match",
              ^.onChange ==> t.backend.updateConfirmPassword, ^.required := true, ^.placeholder := state.lang.selectDynamic("CONFIRM_PASSWORD").toString),
            <.span(^.className := "glyphicon form-control-feedback", SignupCSS.Style.formControlMargin, "aria-hidden".reactAttr := "true"),
            <.div(^.className := "help-block with-errors")
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 text-left", SignupCSS.Style.termsAndServicesContainer)(
              <.input(^.`type` := "checkbox", ^.id := "termsOfServices", ^.required := true),
              Button(Button.Props(t.backend.showTermsOfServices(), CommonStyle.default, Seq(SignupCSS.Style.termsAndCondBtn), "", "")
                , state.lang.selectDynamic("IM_COOL_WITH_THE_TERMS_OF_SERVICE").toString)
            )
          ),
          <.div()(
            <.div(^.className := "col-md-12", SynereoCommanStylesCSS.Style.paddingLeftZero, SynereoCommanStylesCSS.Style.paddingRightZero, SignupCSS.Style.howItWorks)(
              <.div(^.className := "pull-left", SignupCSS.Style.signUpuserNameContainer)(
                <.div(^.className := "text-left")(state.lang.selectDynamic("CREATING_ACCOUNT_ON_NODE").toString, <.span(s"   ${nodeName}")),
                <.span(SignupCSS.Style.howAccountsWorkLink)(state.lang.selectDynamic("HOW_ACCOUNTS_WORKS").toString)
              ),
              <.div(^.className := "pull-right", ^.className := "form-group")(
                <.button(^.tpe := "submit", ^.id := "signUpBtnId", SignupCSS.Style.signUpBtn, ^.className := "btn", state.lang.selectDynamic("SIGN_UP").toString)
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
