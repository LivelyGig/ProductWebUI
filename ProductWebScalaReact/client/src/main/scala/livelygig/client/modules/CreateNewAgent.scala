//package livelygig.client.modules
//
//import japgolly.scalajs.react.extra.router.RouterCtl
//import japgolly.scalajs.react.vdom.prefix_<^._
//import japgolly.scalajs.react.{Callback, ReactComponentB}
//import livelygig.client.LGMain.Loc
//import livelygig.client.components._
//import livelygig.client.css.CreateNewAgentCSS
//import  livelygig.client.css.DashBoardCSS
//import scalacss.ScalaCssReact._
//
//object CreateNewAgent {
//  // create the React component for Dashboard
//  val component = ReactComponentB[RouterCtl[Loc]]("CreateNewAgent")
//    .render_P(ctl => {
//      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
//       <.div(DashBoardCSS.Style.borderColorStyle)(
//            <.div(^.className:="row")(
//              <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
//                <.button(^.id :="headerbtn" , DashBoardCSS.Style.btnStyle)(
//                  <.div(DashBoardCSS.Style.headerBtnFont)(
//                      "Create New Agent"
//                  ),
//                 <.div(DashBoardCSS.Style.btnClose)(
//                    <.span(DashBoardCSS.Style.closeBtnFont)("x")
//                 )
//                )
//            )
//         ),
//        <.div()(
//         <.div(^.className:="row")(
//           <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
//                <.div()("Name")
//           ),
//           <.div(^.className:="col-md-7 col-sm-7 col-xs-7")(
//             <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
//           )
//         )
//       )
//       ) //mainContainer
//     )
//   })
//    .build
//}