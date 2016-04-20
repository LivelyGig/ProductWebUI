package synereo.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.{RouterCtl, Resolution}
import org.scalajs.dom.window
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos._
import synereo.client.Handlers.{CreateLabels, LoginUser}
import synereo.client.SYNEREOMain.Loc
import synereo.client.components.{GlobalStyles, MIcon, Icon}
import synereo.client.css.{SignupCSS, LoginCSS}
import synereo.client.modalpopups.{ServerErrorModal, ErrorModal, RequestInvite}
import synereo.client.models.UserModel
import synereo.client.services.{ApiResponseMsg, SYNEREOCircuit, CoreApi}
import scala.scalajs.js
import js.{Date, UndefOr}

//import scala.util.parsing.json.JSON
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.concurrent.ExecutionContext.Implicits.global
import org.querki.jquery._


/**
  * Created by Mandar on 4/15/2016.
  */
object Signup {/*
  var addNewAgentState: Boolean = false
  var userModelUpdate = new UserModel("", "", "", "", "", false, false, "")

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(/*submitHandler: (UserModel, Boolean, Boolean) => Callback*/)

  case class State(userModel: UserModel, addNewAgent: Boolean = false, showTermsOfServicesForm: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {

    def submitForm(e: ReactEventI) = {
      println("form submitted")
      e.preventDefault()
      t.modState(s => s.copy(addNewAgent = true))
    }

    def updateName(e: ReactEventI) = {
      val value = e.target.value
      println(s"name updated:$value")
      t.modState(s => s.copy(userModel = s.userModel.copy(name = value)))
    }

    def updateLastName(e: ReactEventI) = {
      val value = e.target.value
      println(s"lastname updated:$value")
      t.modState(s => s.copy(userModel = s.userModel.copy(lastName = value)))
    }

    def updateEmail(e: ReactEventI) = {
      val value = e.target.value
      println(s"emailupdated:$value")
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def updatePassword(e: ReactEventI) = {
      val value = e.target.value
      println(s"password updated:$value")
      t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
    }

    def updateConfirmPassword(e: ReactEventI) = {
      val value = e.target.value
      println(s"confirm pass updated:$value")
      t.modState(s => s.copy(userModel = s.userModel.copy(ConfirmPassword = value)))
    }

    //
    //    def addNewAgent(userModel: UserModel, addNewAgent: Boolean = false, showTermsOfServicesForm: Boolean = false): Unit = {
    //      //      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${addNewAgent}")
    //      if (addNewAgent) {
    //        CoreApi.createUser(userModel).onComplete {
    //          case Success(response) =>
    //            val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
    //            //            log.debug(s"createUser msg : ${s.msgType}")
    //            if (s.msgType == ApiResponseMsg.CreateUserWaiting) {
    //              //              t.modState(s => s.copy(showConfirmAccountCreation = true)).runNow()
    //            } else {
    //              //              log.debug(s"createUser msg : ${s.content}")
    //              //              t.modState(s => s.copy(showRegistrationFailed = true)).runNow()
    //            }
    //          case Failure(s) =>
    //            //            log.debug(s"createUserFailure: ${s}")
    //            //            t.modState(s => s.copy(showErrorModal = true)).runNow()
    //            Callback {
    //              println("failure")
    //            }
    //        }
    //        //        t.modState(s => s.copy(showNewAgentForm = false))
    //      } else if (showTermsOfServicesForm) {
    //        //        t.modState(s => s.copy(showNewAgentForm = false, showTermsOfServicesForm = true))
    //        Callback {
    //
    //        }
    //      } else {
    //        //        t.modState(s => s.copy(showNewAgentForm = false))
    //        Callback {
    //          println("failure")
    //        }
    //      }
    //    }


    def render(s: State, p: Props) = {
      <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div("")),
      <.div(^.className := "container-fluid", LoginCSS.Style.loginPageContainerMain)(
        <.div(^.className := "row")(
          <.h1(^.className := "text-center", SignupCSS.Style.signUpHeading)("Sign Up For Synereo")
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-4 col-md-offset-4 col-xs-12 col-sm-6", SignupCSS.Style.signUpFormContainerDiv)(
            <.form(^.id := "SignUpForm", ^.onSubmit ==> submitForm)(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.label(^.`for` := "First name *", "First name *")
                ),
                <.div()(
                  <.input(^.tpe := "text", bss.formControl, ^.id := "First name", ^.value := s.userModel.name, SignupCSS.Style.inputStyleSignUpForm,
                    ^.onChange ==> updateName, ^.required := true)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.label(^.`for` := "Last name *", "Last name *")
                ),
                <.div()(
                  <.input(^.tpe := "text", bss.formControl, ^.id := "Last name", ^.value := s.userModel.lastName, SignupCSS.Style.inputStyleSignUpForm,
                    ^.onChange ==> updateLastName)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.label(^.`for` := "Email *", "Email *")
                ),
                <.div()(
                  <.input(^.tpe := "email", bss.formControl, ^.id := "Email", ^.value := s.userModel.email, SignupCSS.Style.inputStyleSignUpForm,
                    ^.onChange ==> updateEmail, ^.required := true)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.label(^.`for` := "Password *", "Password *")
                ),
                <.div()(
                  <.input(^.tpe := "password", bss.formControl, ^.id := "Password", ^.value := s.userModel.password, SignupCSS.Style.inputStyleSignUpForm,
                    ^.onChange ==> updatePassword, ^.required := true)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.label(^.`for` := "Confirm Password *", "Confirm Password *")
                ),
                <.div()(
                  <.input(^.tpe := "password", bss.formControl, ^.id := "Confirm Password", ^.value := s.userModel.ConfirmPassword, SignupCSS.Style.inputStyleSignUpForm,
                    ^.onChange ==> updateConfirmPassword, ^.required := true)
                )
              ),
              <.div(^.className := "row")(
                <.div(SignupCSS.Style.SignupformFooter)(
                  <.div()(<.input(^.`type` := "checkbox"), " * I understand and agree to the Synereo Terms of Service ",
                    <.button(^.tpe := "button", SignupCSS.Style.termsAndServicesBtn, ^.className := "btn btn-default", "Terms of Service "))
                ),
                <.div(SignupCSS.Style.SignupformFooter)(
                  <.input(^.`type` := "checkbox"), " Send me occasional email updates from Synereo"
                )
              ),
              <.button(^.tpe := "submit", ^.className := "btn", SignupCSS.Style.signUpBtn)("Sign Up")

            )
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props]("SynereoSignup")
    .initialState_P(p => State(new UserModel("", "", "")))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)*/
}
