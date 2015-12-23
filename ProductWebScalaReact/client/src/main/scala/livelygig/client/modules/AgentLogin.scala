package livelygig.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css.{CreateAgentCSS, DashBoardCSS, HeaderCSS}
import livelygig.client.models.AgentLoginModel
import livelygig.client.services.CoreApi
import livelygig.client.services.CoreApi._
import org.scalajs.dom._
import scala.concurrent.ExecutionContext.Implicits.global
import scalacss.ScalaCssReact._

/**
  * Created by bhagyashree.b on 12/16/2015.
  */
object AgentLogin {


  case class Props(router: RouterCtl[Loc])
  case class State(agentLoginModel: AgentLoginModel)
  class Backend(t : BackendScope[Unit, State]) {

    def updateEmail(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(agentLoginModel = s.agentLoginModel.copy(email = e.target.value)))
    }
    def updatePassword(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(agentLoginModel = s.agentLoginModel.copy(password = e.target.value)))
    }


  }
  // create the React component for CreateAgent
  def agentLogin(agentLoginModel: AgentLoginModel) : Callback = Callback{
    println(agentLoginModel)
    CoreApi.agentLogin(agentLoginModel).onSuccess {
      case s =>
        println(s)
        //window.location.href = "/"
      // now you need to refresh the UI
    }
    //    window.location.href = "#emailvalidation"
  }

//  def redirectToDashboard() : Callback = Callback{
//    window.location.href = "/"
//  }
  // create the React component for AgentLogin

  val component = ReactComponentB[Unit]("AgentLogin")
    .initialState(State(new AgentLoginModel("","")))
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
      <.div(^.className:="row")(
        <.div(^.className:="col-md-4 col-md-offset-4 col-sm-offset-3 col-xs-offset-4",CreateAgentCSS.Style.modalContainer)(
          <.div(CreateAgentCSS.Style.ModalHeader, /*CreateAgentCSS.Style.paddinglefttitle ,*/ ^.className:="row")(

            <.div(^.className:="col-md-5 col-sm-5 col-xs-5 col-md-offset-5 col-sm-offset-5 col-xs-offset-5")(
              <.h3("Login")
            ),
            <.div(CreateAgentCSS.Style.marginTopClosebtn , ^.className:="col-md-1 col-sm-1 col-xs-1")(
              <.button(^.className:="btn btn-default",Seq(CreateAgentCSS.Style.closebtn),  ^.tpe := "button")(<.span(Icon.close))
            )
          ),
          <.div(CreateAgentCSS.Style.ModalBody, ^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(

              <.div(^.className:="row")(
                  <.div(^.className:="col-md-7 col-sm-7 col-xs-7 col-md-offset-5 col-sm-offset-5 col-xs-offset-5")(
                  <.div(HeaderCSS.Style.logoContainer,^.className := "navbar-header",<.img(CreateAgentCSS.Style.imgLogoLogin, ^.src := "./assets/images/logo-symbol.png"))
                )
              ),

              <.div(CreateAgentCSS.Style.marginTopClosebtn , ^.className:="row")(
                <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
                  <.h4("Email")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth,^.onChange==>B.updateEmail)
                )
              ),

              <.div(^.className:="row")(
                <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
                  <.h4("Password")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth, ^.onChange==>B.updatePassword)
                )
              )
            )
          ),
          <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(

            <.div(^.className:="col-md-2 col-sm-2 col-xs-2 col-md-offset-4 col-sm-offset-4 col-xs-offset-4 ")(
              <.button(CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default", ^.tpe := "button",
                ^.onClick --> agentLogin(S.agentLoginModel))("Login")
            ),
            <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
              <.button(CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default", ^.tpe := "button")("I'm new")
            ),
            <.div(^.className:="col-md-3 col-sm-3 col-xs 3")(
              <.button(CreateAgentCSS.Style.marginLeftbtn, ^.className:="btn btn-default", ^.tpe := "button")("Validate")
            )
          )
        )

      )//row
      ) //mainContainer
    })
    .build

}
