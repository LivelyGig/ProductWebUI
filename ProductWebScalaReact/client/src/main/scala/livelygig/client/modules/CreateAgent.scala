package livelygig.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components._
import livelygig.client.css.{CreateAgentCSS, DashBoardCSS}
import livelygig.client.models.UserModel
import livelygig.client.services.ApiResponseMsg
import livelygig.client.services.CoreApi.createUser
import org.scalajs.dom._
import boopickle.Default._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object CreateAgent {
  case class Props(router: RouterCtl[Loc])
  case class State(userModel: UserModel)
  class Backend(t : BackendScope[Unit, State]) {
    def updateName(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
    }
    def updateEmail(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
    }
    def updatePassword(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
    }
    def toggleBTCWallet(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
    }

  }
  // create the React component for CreateAgent
  def createAgent(userModel: UserModel) : Callback = Callback{
    println(userModel)
    createUser(userModel).onComplete {
      case Success(s) =>
        println(s.msgType)
        if (s.msgType == ApiResponseMsg.CreateUserWaiting){
          window.location.href = "#emailvalidation"
        } else {
          println("email already exists")
        }
      case Failure(s) =>
        println(s)

      // now you need to refresh the UI
    }

    //    window.location.href = "#emailvalidation"
  }


  val component = ReactComponentB[Unit]("CreateAgent")
    .initialState(State(new UserModel("","","",false))) // initial state from TodoStore
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend

      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div(^.className:="col-md-4 col-md-offset-4 col-sm-offset-3 col-xs-offset-4" , CreateAgentCSS.Style.modalContainer)(
          <.div(CreateAgentCSS.Style.ModalHeader, /*CreateAgentCSS.Style.paddinglefttitle ,*/ ^.className:="row")(
            <.div(^.className:="col-md-7 col-sm-7 col-xs-7 col-md-offset-3 col-sm-offset-3 col-xs-offset-3")(
              <.h3("Create New Agent")
            ),
            <.div(CreateAgentCSS.Style.marginTopClosebtn , ^.className:="col-md-1 col-sm-1 col-xs-1")(
              <.button(^.className:="btn btn-default",Seq(CreateAgentCSS.Style.closebtn),  ^.tpe := "button")(<.span(Icon.close))
            )
          ),
          <.form(^.onSubmit--> createAgent(S.userModel))(
            <.div(CreateAgentCSS.Style.ModalBody, ^.className:="row")(

              <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(

                <.div(^.className:="row")(
                  <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                    <.h4("Name")
                  ),
                  <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                    <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth, ^.value:= S.userModel.name,
                      ^.onChange==>B.updateName,^.required:=true)
                  )
                ),

                <.div(^.className:="row")(
                  <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                    <.h4("Email")
                  ),
                  <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                    <.input(^.className:="form-control",^.`type`:="email", CreateAgentCSS.Style.inputHeightWidth, ^.value:= S.userModel.email,
                      ^.onChange==>B.updateEmail,^.required:=true)
                  )
                ),

                <.div(^.className:="row")(
                  <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                    <.h4("Password")
                  ),
                  <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                    <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth, ^.value:= S.userModel.password,
                      ^.onChange==>B.updatePassword,^.`type`:="password",^.required:=true)
                  )
                ),
                <.div(^.className:="row")(

                  <.div(^.className:="col-md-8 col-sm-8 col-xs-8 col-md-offset-4 col-sm-offset-4 col-xs-offset-4")(
                    <.input(^.`type` := "checkbox", ^.checked:=S.userModel.createBTCWallet,
                      ^.onChange==>B.toggleBTCWallet ),
                    <.h5(CreateAgentCSS.Style.displayInline)("create BTC wallet")
                  )
                )
              )
            ),
            <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(
              <.div(^.className:="col-md-4 col-sm-4 col-xs 4 col-md-offset-5 col-sm-offset-5 col-xs-offset-5")(
                //ctl.link(EmailValidationLoc)(^.className:="btn btn-default")("Create New Agent")
                <.button(^.className:="btn btn-default", ^.tpe := "submit")
                ("Create New Agent")
              ),
              <.div(^.className:="col-md-3 col-sm-3 col-xs 3")(
                <.button(CreateAgentCSS.Style.marginLeftbtn, ^.className:="btn btn-default", ^.tpe := "button")("Cancel")
              )
            )
          )
        )
      ) //mainContainer
    })
    .build
  //  def apply() = (router: RouterCtl[Loc]) => {
  //    component(Props(router))
  //  }
}