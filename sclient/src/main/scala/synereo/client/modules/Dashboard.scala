package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.{MIcon,Icon}
import synereo.client.css.{SynereoCommanStylesCSS, SDashboardCSS}

import scalacss.ScalaCssReact._

/**
  * Created by Mandar  on 3/17/2016.
  */
object Dashboard {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard").
    render_P { ctr =>
      <.div(^.className := "container-fluid",SDashboardCSS.Style.dashboardContainerMain)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
            <.div(^.className := "pull-right", SDashboardCSS.Style.profileActionContainer)(
              <.button(^.className := "btn", SDashboardCSS.Style.profileActionButton)(
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
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.input(SDashboardCSS.Style.UserInput, ^.placeholder := "contribute your thoughts..."),
                    <.span()(Icon.camera)
                  ),
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.div(SDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", SDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SDashboardCSS.Style.postActions)(
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
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.div(SDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", SDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SDashboardCSS.Style.postActions)(
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
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.div(SDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", SDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SDashboardCSS.Style.postActions)(
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
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.div(SDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", SDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SDashboardCSS.Style.postActions)(
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
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.div(SDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", SDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SDashboardCSS.Style.postActions)(
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
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.div(SDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", SDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SDashboardCSS.Style.postActions)(
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
                  <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                    <.div(SDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(MIcon.chevronRight),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(MIcon.moreVert),
                    <.div(^.className := "col-md-12")(
                      <.h3("Headed to  sxsw", SDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(SynereoCommanStylesCSS.Style.synereoBlueText)(MIcon.moreHoriz)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SDashboardCSS.Style.postActions)(
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
