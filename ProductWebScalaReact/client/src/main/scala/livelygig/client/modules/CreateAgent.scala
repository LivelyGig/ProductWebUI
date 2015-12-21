package livelygig.client.modules
import japgolly.scalajs.react.{Callback, ReactComponentB}
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{EmailValidationLoc, TodoLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.CreateAgentCSS
import livelygig.client.models.UserModel
import org.scalajs.dom._
import scalacss.ScalaCssReact._
import livelygig.client.services.ApiService.createUser

object CreateAgent {
  // create the React component for CreateAgent
  def redirectToEmailValidate(userModel: UserModel) : Callback = Callback{
    println(userModel)
    createUser(userModel)
//    window.location.href = "#emailvalidation"
  }

  case class CreateAgentProps(userModel: UserModel, router: RouterCtl[Loc])
  val component = ReactComponentB[CreateAgentProps]("CreateAgent")
    .render_P(P => {
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
              <.div(CreateAgentCSS.Style.ModalBody, ^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(

               <.div(^.className:="row")(
                <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                  <.h4("Name")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth, ^.value:= P.userModel.name)
                )
               ),

              <.div(^.className:="row")(
                <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                  <.h4("Email")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth, ^.value:= P.userModel.email)
                )
              ),

              <.div(^.className:="row")(
                <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                  <.h4("Password")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth, ^.value:= P.userModel.password)
                )
              ),
                  <.div(^.className:="row")(

                    <.div(^.className:="col-md-8 col-sm-8 col-xs-8 col-md-offset-4 col-sm-offset-4 col-xs-offset-4")(
                      <.input(^.`type` := "checkbox", ^.checked:=P.userModel.createBTCWallet,
                        ^.onChange--> Callback(P.userModel.copy(createBTCWallet =  !P.userModel.createBTCWallet)) ),
                      <.h5(CreateAgentCSS.Style.displayInline)("creat BTC wallet")
                    )
                  )

              )
            ),
            <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(
              <.div(^.className:="col-md-4 col-sm-4 col-xs 4 col-md-offset-5 col-sm-offset-5 col-xs-offset-5")(
                //ctl.link(EmailValidationLoc)(^.className:="btn btn-default")("Create New Agent")
                <.button(^.className:="btn btn-default", ^.tpe := "button", ^.onClick --> redirectToEmailValidate(P.userModel))
                ("Create New Agent")
              ),
              <.div(^.className:="col-md-3 col-sm-3 col-xs 3")(
                <.button(CreateAgentCSS.Style.marginLeftbtn, ^.className:="btn btn-default", ^.tpe := "button")("Cancel")
              )
            )
          )
      ) //mainContainer
    })
    .build
  def apply(userModel: UserModel) = (router: RouterCtl[Loc]) => {
    component(CreateAgentProps(userModel, router))
  }
}