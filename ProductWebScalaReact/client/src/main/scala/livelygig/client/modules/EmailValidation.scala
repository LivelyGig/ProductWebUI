package livelygig.client.modules
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{AgentLoginLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.{CreateAgentCSS, DashBoardCSS}
import livelygig.client.css.DashBoardCSS
import scala.scalajs.js
import scalacss.ScalaCssReact._
import scala.language.implicitConversions
import org.scalajs.dom._


/**
  * Created by bhagyashree.b on 12/16/2015.
  */
object EmailValidation {

  def redirectToLogin() : Callback = Callback{
    window.location.href = "#agentlogin"
  }


  // create the React component for Email Validation
  val component = ReactComponentB[RouterCtl[Loc]]("EmailValidation")
    .render_P(ctl => {
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
                 <.h4("Your request for a Splicious Agent has been submitted. Upon reciving your conformation email, you may click the link it containes or paste the token below to valdate your email address.")
                )
              ),
              <.div(^.className:="row")(

                <.div(^.className:="col-md-8 col-sm-8 col-xs-8 col-md-offset-4 col-sm-offset-4 col-xs-offset-4")(
                  <.h4("Your Token")
                )
              ),
              <.div(^.className:="row")(

                <.div(^.className:="col-md-8 col-sm-8 col-xs-8 col-md-offset-2 col-sm-offset-2 col-xs-offset-4")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth)
                )
              )
            )
          ),
          <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(

            <.div(^.className:="col-md-3 col-sm-3 col-xs-3 col-md-offset-6 col-sm-offset-4 col-xs-offset-4")(
                   //ctl.link(AgentLoginLoc) (CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default")("Validate")
                <.button(CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default", ^.onClick --> redirectToLogin())("Validate")
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
