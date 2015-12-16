package livelygig.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css.{HeaderCSS, CreateAgentCSS, DashBoardCSS}
import scalacss.ScalaCssReact._

/**
  * Created by bhagyashree.b on 12/16/2015.
  */
object AgentLogin {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("AgentLogin")
    .render_P(ctl => {
      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div(CreateAgentCSS.Style.modalContainer)(
          <.div(CreateAgentCSS.Style.ModalHeader, /*CreateAgentCSS.Style.paddinglefttitle ,*/ ^.className:="row")(
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(

            ),
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
              <.h3("Login")
            ),
            <.div(CreateAgentCSS.Style.marginTopClosebtn , ^.className:="col-md-1 col-sm-1 col-xs-1")(
              <.button(^.className:="btn btn-default",Seq(CreateAgentCSS.Style.closebtn),  ^.tpe := "button")(<.span(Icon.close))
            )
          ),
          <.div(CreateAgentCSS.Style.ModalBody, ^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(

              <.div(^.className:="row")(
                <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(

                ),
                <.div(^.className:="col-md-7 col-sm-7 col-xs-7")(
                  <.div(HeaderCSS.Style.logoContainer,^.className := "navbar-header",<.img(CreateAgentCSS.Style.imgLogoLogin, ^.src := "./assets/images/logo-symbol.png"))
                )
              ),

              <.div(CreateAgentCSS.Style.marginTopClosebtn , ^.className:="row")(
                <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
                  <.h4("Email")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth)
                )
              ),

              <.div(^.className:="row")(
                <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
                  <.h4("Password")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth)
                )
              )
            )
          ),
          <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(
            <.div(^.className:="col-md-4 col-sm-4 col-xs-4 ")(

            ),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2 ")(
              <.button(CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default", ^.tpe := "button")("Login")
            ),
            <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
              <.button(CreateAgentCSS.Style.marginLeftCloseBtn, ^.className:="btn btn-default", ^.tpe := "button")("I'm new")
            ),
            <.div(^.className:="col-md-3 col-sm-3 col-xs 3")(
              <.button(CreateAgentCSS.Style.marginLeftbtn, ^.className:="btn btn-default", ^.tpe := "button")("Validate")
            )
          )
        )
      ) //mainContainer
    })
    .build

}
