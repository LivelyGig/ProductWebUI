package livelygig.client.modules
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components._
import livelygig.client.css.{CreateAgentCSS, DashBoardCSS}
import livelygig.client.models.{UserModel, EmailValidationModel}
import livelygig.client.modules.CreateAgent.State
import livelygig.client.services.CoreApi._
import org.scalajs.dom._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
import scalacss.ScalaCssReact._


/**
  * Created by bhagyashree.b on 12/16/2015.
  */
object EmailValidation {

  case class Props(router: RouterCtl[Loc])
  case class State(emailValidationModel: EmailValidationModel)
  class Backend(t : BackendScope[Unit, State]) {
    def updateToken(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(emailValidationModel = s.emailValidationModel.copy(token = e.target.value)))
    }
  }

  def confirmEmail(emailValidationModel: EmailValidationModel) : Callback = Callback{
    println(emailValidationModel)
    emailUserValidation(emailValidationModel).onSuccess {
      case s =>
        println(s)
        window.location.href = "#agentlogin"
      // now you need to refresh the UI
    }
    //    window.location.href = "#emailvalidation"
  }

  def redirectToLogin() : Callback = Callback{
    window.location.href = "#agentlogin"
  }


  // create the React component for Email Validation
  val component = ReactComponentB[Unit]("EmailValidation")
    .initialState(State(new EmailValidationModel(""))) // initial state from TodoStore
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div(^.className:="row")(
        <.div(^.className:="col-md-4 col-md-offset-4 col-sm-offset-3 col-xs-offset-4",CreateAgentCSS.Style.modalContainer)(
          <.div(CreateAgentCSS.Style.ModalHeader, /*CreateAgentCSS.Style.paddinglefttitle ,*/ ^.className:="row")(

            <.div(^.className:="col-md-7 col-sm-7 col-xs-7 col-md-offset-3 col-sm-offset-3 col-xs-offset-3")(
              <.h3("Email Validation")
            ),
            <.div(CreateAgentCSS.Style.marginTopClosebtn , ^.className:="col-md-1 col-sm-1 col-xs-1")(
              <.button(^.className:="btn btn-default",Seq(CreateAgentCSS.Style.closebtn),  ^.tpe := "button")(<.span(Icon.close))
            )
          ),
          <.div(CreateAgentCSS.Style.ModalBody, ^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(

              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
                 <.h4("Your request for a Splicious Agent has been submitted. Upon reciving your confirmation email, you may click the link it containes or paste the token below to valdate your email address.")
                )
              ),
              <.div(^.className:="row")(

                <.div(^.className:="col-md-8 col-sm-8 col-xs-8 col-md-offset-4 col-sm-offset-4 col-xs-offset-4")(
                  <.h4("Your Token")
                )
              ),
              <.div(^.className:="row")(

                <.div(^.className:="col-md-8 col-sm-8 col-xs-8 col-md-offset-2 col-sm-offset-2 col-xs-offset-4")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth, S.emailValidationModel.token,
                    ^.onChange==>B.updateToken)
                )
              )
            )
          ),
          <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(

            <.div(^.className:="col-md-3 col-sm-3 col-xs-3 col-md-offset-6 col-sm-offset-4 col-xs-offset-4")(
                   //ctl.link(AgentLoginLoc) (CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default")("Validate")
                <.button(CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default", ^.onClick --> confirmEmail(S.emailValidationModel))("Validate")
            ),
            <.div(^.className:="col-md-3 col-sm-3 col-xs 3")(
              <.button(CreateAgentCSS.Style.marginLeftbtn, ^.className:="btn btn-default", ^.tpe := "button")("Cancel")
            )
          )
        )
      )//row
      ) //mainContainer
    })
    .build
}
