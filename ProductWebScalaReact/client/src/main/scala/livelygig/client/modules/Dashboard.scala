package livelygig.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{TodoLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.DashBoardCSS
import scalacss.ScalaCssReact._


object Dashboard {

  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl => {

      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div(DashBoardCSS.Style.splitContainer)(
          <.div(^.className:="split")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(
            <.div(^.id:="slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em,LftcontainerCSS.Style.slctsearchpanelabelposition)(
                <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
                  <.select (^.id:="tokenize")(

                  )
                )
              )
            )),
          <.div(^.className:="col-md-10 col-sm-10 col-xs-10")(
            <.div(^.id:="rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
               <.div(DashBoardCSS.Style.gigActionsContainer , ^.className:="col-md-12 col-sm-12 col-xs-12")(
                 <.div(^.className:="col-md-8 col-sm-7 col-xs-8")(
                   <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
                    <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("LivelyGig Match ")(
                      <.span(^.className:="caret")
                    ),
                    <.ul(^.className:="dropdown-menu")(
                      <.li()(<.a(^.href:="#")("By Bid Amount")),
                      <.li()(<.a(^.href:="#")("(More Sorting)")),
                      <.li()(<.a(^.href:="#")("profile3"))
                    )
                  ),//dropdown class
                     <.div (DashBoardCSS.Style.rsltCountHolderDiv)("34,321 results")
                 ),
                 <.div(DashBoardCSS.Style.listIconPadding , ^.className:="col-md-4 col-sm-5 col-xs-4")(

                 <.div(/*DashBoardCSS.Style.gigConvActionBtnContainer ,*/^.className:="pull-right" )(
                   <.button(^.className:="btn","data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Summery")(<.span(Icon.list)),
                   <.button(^.className:="btn","data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Summery")(<.span(Icon.list)),
                   <.button(^.className:="btn","data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Summery")(<.span(Icon.list))
                 )

                 )

               ),//col-12


            <.div(DashBoardCSS.Style.gigConversation, ^.className:="container-fluid" )(
                <.div (^.id:="rsltSectionContainer", ^.className:="col-md-12 col-sm-12 col-xs-12")(
                  <.ul(^.className:="media-list")(

                    <.li(^.className:="media")(
                       <.div (DashBoardCSS.Style.profileNameHolder , ^.className:="col-md-12")("Name : job-title"),
                       <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                       <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                       <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable"),
                       <.div(^.className:="media-left")(
                         <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                      ), //media-left
                    <.div(^.className:="media-body")(
                     "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."

                    )//media-body
                    ),//li


                    <.li(^.className:="media")(
                    <.div (DashBoardCSS.Style.profileNameHolder , ^.className:="col-md-12")("Name : job-title"),
                    <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                    <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                    <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable"),
                    <.div(^.className:="media-left")(
                      <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                    ), //media-left
                    <.div(^.className:="media-body")(
                      "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."

                    )//media-body
                  ),//li
                    <.li(^.className:="media")(
                      <.div (DashBoardCSS.Style.profileNameHolder , ^.className:="col-md-12")("Name : job-title"),
                      <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                      <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                      <.div (DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable"),
                      <.div(^.className:="media-left")(
                        <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img.png")
                      ), //media-left
                      <.div(^.className:="media-body")(
                        "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."

                      )//media-body
                    )


                  )//ul
                )
            )//gigConversation

            )
          )
          )
          )//split class
        )
      ) //mainContainer
    }).build
}
