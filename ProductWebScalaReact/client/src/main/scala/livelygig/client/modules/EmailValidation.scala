package livelygig.client.modules
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{AgentLoginLoc, Loc}
import livelygig.client.components.Icon
import livelygig.client.css.{CreateAgentCSS, DashBoardCSS}
import livelygig.client.css.DashBoardCSS
import scalacss.ScalaCssReact._
import org.querki.jquery._

/**
  * Created by bhagyashree.b on 12/16/2015.
  */
object EmailValidation {
  // create the React component for Email Validation
  val component = ReactComponentB[RouterCtl[Loc]]("EmailValidation")
    .render_P(ctl => {
      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div(CreateAgentCSS.Style.modalContainer)(
          <.div(CreateAgentCSS.Style.ModalHeader, /*CreateAgentCSS.Style.paddinglefttitle ,*/ ^.className:="row")(
            <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(

            ),
            <.div(^.className:="col-md-7 col-sm-7 col-xs-7")(
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
                <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(

                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.h4("Your Token")
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(

                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth)
                )
              )
            )
          ),
          <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(
            <.div(^.className:="col-md-6 col-sm-6 col-xs-6 ")(

            ),
            <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
                   ctl.link(AgentLoginLoc) (CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default")("Validate")
            ),
            <.div(^.className:="col-md-3 col-sm-3 col-xs 3")(
              <.button(CreateAgentCSS.Style.marginLeftbtn, ^.className:="btn btn-default", ^.tpe := "button")("Cancel")
            )
          )
        )
      ) //mainContainer
    })
    .build

}
