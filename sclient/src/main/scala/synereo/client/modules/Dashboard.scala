package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.{MIcon, Icon}
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS, DashboardCSS}

import scala.scalajs.js
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import org.querki.jquery._
import scala.scalajs.js
import org.querki.jquery._
import scalacss.ScalaCssReact._

/**
  * Created by Mandar  on 3/17/2016.
  */
object Dashboard {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard").
    render_P { ctr =>
      <.div(^.className := "container-fluid", DashboardCSS.Style.dashboardContainerMain)(
        <.div(^.className := "row")(
          //Left Sidebar
          <.div(^.id := "searchContainer", ^.className := "col-md-2 col-sm-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ")(
            //            Footer(Footer.Props(c, r.page))
            Sidebar(Sidebar.Props())
          )),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
            <.div(^.className := "pull-right", "data-toggle".reactAttr := "popover","data-content".reactAttr:="Some content inside the popover", DashboardCSS.Style.profileActionContainer)(
              <.button(^.className := "btn", DashboardCSS.Style.profileActionButton)(
                <.span(^.color := "white")(MIcon.chatBubble),
                <.span(^.color := "white")(MIcon.share),
                <.span(^.color := "white")(MIcon.chevronRight)
              )
            )
          )
        ),
        <.div(^.className := "container")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
               <.div(^.className := "row")(
                <.div(^.className := "col-lg-6 col-md-6 col-sm-12 col-xs-12")(
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.input(DashboardCSS.Style.UserInput, ^.placeholder := "contribute your thoughts..."),
                    <.span()(Icon.camera)
                  ),
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.button(^.`type`:="button",^.className:="btn btn-default",DashboardCSS.Style.postActionButton)(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", DashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.button(^.`type`:="button",^.className:="btn btn-default",DashboardCSS.Style.postActionButton,SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(DashboardCSS.Style.postActions)(
                        <.div(^.className := "btn-group")(
                          <.button(^.`type` := "button", ^.className := "btn btn-default",DashboardCSS.Style.postActionButton)(
                            <.span(MIcon.chatBubble),
                           <.div("12", SynereoCommanStylesCSS.Style.inlineBlock)
                          ),
                          <.button(^.`type` := "button", ^.className := "btn btn-default",DashboardCSS.Style.postActionButton)(
                            <.span(MIcon.share),
                           <.div("123", SynereoCommanStylesCSS.Style.inlineBlock)
                          ),
                          <.button(^.`type` := "button", ^.className := "btn btn-default",DashboardCSS.Style.postActionButton)(
                            <.span(MIcon.add),
                           <.div("10", SynereoCommanStylesCSS.Style.inlineBlock)
                          )
                        ),
                        <.div(^.className := "btn-group pull-right")(
                          <.button(^.`type` := "button", ^.className := "btn btn-default",DashboardCSS.Style.postActionButton)(
                            <.span(Icon.minus)
                          ),
                          <.button(^.`type` := "button", ^.className := "btn btn-default",DashboardCSS.Style.postActionButton)(
                            <.span(Icon.heartO)
                          ),
                          <.button(^.`type` := "button", ^.className := "btn btn-default",DashboardCSS.Style.postActionButton)(
                            <.span(Icon.plus)
                          )
                        )
                      )
                    )
                  ),
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", DashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(DashboardCSS.Style.postActions)(
                        <.span(MIcon.chatBubble),
                        <.span(MIcon.share),
                        <.span(MIcon.add),

                        <.span(^.className := "pull-right")(
                          <.span(MIcon.chatBubble),
                          <.span(MIcon.share)
                        )
                      )
                    )
                  )
                ),
                <.div(^.className := "col-lg-6 col-md-6 col-sm-12 col-xs-12")(
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", DashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(DashboardCSS.Style.postActions)(
                        <.span(MIcon.chatBubble),
                        <.span(MIcon.share),
                        <.span(MIcon.add),

                        <.span(^.className := "pull-right")(
                          <.span(MIcon.chatBubble),
                          <.span(MIcon.share)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", DashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(DashboardCSS.Style.postActions)(
                        <.span(MIcon.chatBubble),
                        <.span(MIcon.share),
                        <.span(MIcon.add),

                        <.span(^.className := "pull-right")(
                          <.span(MIcon.chatBubble),
                          <.span(MIcon.share)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", DashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(DashboardCSS.Style.postActions)(
                        <.span(MIcon.chatBubble),
                        <.span(MIcon.share),
                        <.span(MIcon.add),

                        <.span(^.className := "pull-right")(
                          <.span(MIcon.chatBubble),
                          <.span(MIcon.share)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", DashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(DashboardCSS.Style.postActions)(
                        <.span(MIcon.chatBubble),
                        <.span(MIcon.share),
                        <.span(MIcon.add),

                        <.span(^.className := "pull-right")(
                          <.span(MIcon.chatBubble),
                          <.span(MIcon.share)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", DashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(DashboardCSS.Style.postActions)(
                        <.span(MIcon.chatBubble),
                        <.span(MIcon.share),
                        <.span(MIcon.add),

                        <.span(^.className := "pull-right")(
                          <.span(MIcon.chatBubble),
                          <.span(MIcon.share)
                        )

                      )

                    )
                  )
                )
              )


            )
          )
        )

      )

    }.build

  def apply(router: RouterCtl[Loc]) = component(router)

}
