package livelygig.client.modules
import japgolly.scalajs.react.{Callback, ReactComponentB}
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{EmailValidationLoc, TodoLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.CreateAgentCSS
import scalacss.ScalaCssReact._
object CreateAgent {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("CreateAgent")
    .render_P(ctl => {
      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
          <.div(CreateAgentCSS.Style.modalContainer)(
             <.div(CreateAgentCSS.Style.ModalHeader, /*CreateAgentCSS.Style.paddinglefttitle ,*/ ^.className:="row")(
               <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(

               ),
                <.div(^.className:="col-md-7 col-sm-7 col-xs-7")(
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
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth)
                )
               ),

              <.div(^.className:="row")(
                <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                  <.h4("Email")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth)
                )
              ),

              <.div(^.className:="row")(
                <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
                  <.h4("Password")
                ),
                <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                  <.input(^.className:="form-control", CreateAgentCSS.Style.inputHeightWidth)
                )
              ),
                  <.div(^.className:="row")(
                    <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(

                    ),
                    <.div(^.className:="col-md-8 col-sm-8 col-xs-8")(
                      <.input(^.`type` := "checkbox"),
                      <.h5(CreateAgentCSS.Style.displayInline)("creat BTC wallet")
                    )
                  )

              )
            ),
            <.div(CreateAgentCSS.Style.ModalFoot , ^.className:="row")(
              <.div(^.className:="col-md-5 col-sm-5 col-xs ")(


              ),
              <.div(^.className:="col-md-4 col-sm-4 col-xs 4")(
                ctl.link(EmailValidationLoc)(^.className:="btn btn-default")("Create New Agent")
                //<.button(^.className:="btn btn-default", ^.tpe := "button")("Create New Agent")
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