package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.Icon
import synereo.client.css.SynereoDashboardCSS

import scalacss.ScalaCssReact._

/**
  * Created by Mandar  on 3/17/2016.
  */
object SynereoDashboard {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard").
    render_P { ctr =>
      <.div(^.className := "container-fluid")(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
            <.div(^.className := "pull-right", SynereoDashboardCSS.Style.profileActionContainer)(
              <.button(^.className := "btn", SynereoDashboardCSS.Style.profileActionButton)(
                <.span(^.color := "white")(Icon.thList),
                <.span(^.color := "white")(Icon.adjust),
                <.span(^.color := "white")(Icon.anchor),
                <.span(^.color := "white")(Icon.apple)
              )
            )
          )
        ),
        <.div(^.className := "container")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-lg-6 col-md-6 col-sm-12 col-xs-12")(
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.input(SynereoDashboardCSS.Style.UserInput, ^.placeholder := "contribute your thoughts..."),
                    <.span()(Icon.camera)
                  ),
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.div(SynereoDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(Icon.chevronRight),
                      <.span(^.color := "blue")("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(Icon.ellipsisV),
                    <.div(^.className := "col-md-12")(
                     <.h3("Headed to  sxsw", SynereoDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(^.color := "blue")(Icon.ellipsisH)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SynereoDashboardCSS.Style.postActions)(
                        <.span(Icon.thList),
                        <.span(Icon.adjust),
                        <.span(Icon.anchor),
                        <.span(Icon.apple),
                        <.span(^.className := "pull-right")(
                          <.span(Icon.thList),
                          <.span(Icon.adjust)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.div(SynereoDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(Icon.chevronRight),
                      <.span(^.color := "blue")("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(Icon.ellipsisV),
                    <.div(^.className := "col-md-12")(
                     <.h3("Headed to  sxsw", SynereoDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(^.color := "blue")(Icon.ellipsisH)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SynereoDashboardCSS.Style.postActions)(
                        <.span(Icon.thList),
                        <.span(Icon.adjust),
                        <.span(Icon.anchor),
                        <.span(Icon.apple),
                        <.span(^.className := "pull-right")(
                          <.span(Icon.thList),
                          <.span(Icon.adjust)
                        )

                      )

                    )
                  )
                ),
                <.div(^.className := "col-lg-6 col-md-6 col-sm-12 col-xs-12")(
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.div(SynereoDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(Icon.chevronRight),
                      <.span(^.color := "blue")("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(Icon.ellipsisV),
                    <.div(^.className := "col-md-12")(
                     <.h3("Headed to  sxsw", SynereoDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(^.color := "blue")(Icon.ellipsisH)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SynereoDashboardCSS.Style.postActions)(
                        <.span(Icon.thList),
                        <.span(Icon.adjust),
                        <.span(Icon.anchor),
                        <.span(Icon.apple),
                        <.span(^.className := "pull-right")(
                          <.span(Icon.thList),
                          <.span(Icon.adjust)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.div(SynereoDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(Icon.chevronRight),
                      <.span(^.color := "blue")("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(Icon.ellipsisV),
                    <.div(^.className := "col-md-12")(
                     <.h3("Headed to  sxsw", SynereoDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(^.color := "blue")(Icon.ellipsisH)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SynereoDashboardCSS.Style.postActions)(
                        <.span(Icon.thList),
                        <.span(Icon.adjust),
                        <.span(Icon.anchor),
                        <.span(Icon.apple),
                        <.span(^.className := "pull-right")(
                          <.span(Icon.thList),
                          <.span(Icon.adjust)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.div(SynereoDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(Icon.chevronRight),
                      <.span(^.color := "blue")("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(Icon.ellipsisV),
                    <.div(^.className := "col-md-12")(
                     <.h3("Headed to  sxsw", SynereoDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(^.color := "blue")(Icon.ellipsisH)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SynereoDashboardCSS.Style.postActions)(
                        <.span(Icon.thList),
                        <.span(Icon.adjust),
                        <.span(Icon.anchor),
                        <.span(Icon.apple),
                        <.span(^.className := "pull-right")(
                          <.span(Icon.thList),
                          <.span(Icon.adjust)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.div(SynereoDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(Icon.chevronRight),
                      <.span(^.color := "blue")("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(Icon.ellipsisV),
                    <.div(^.className := "col-md-12")(
                     <.h3("Headed to  sxsw", SynereoDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(^.color := "blue")(Icon.ellipsisH)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SynereoDashboardCSS.Style.postActions)(
                        <.span(Icon.thList),
                        <.span(Icon.adjust),
                        <.span(Icon.anchor),
                        <.span(Icon.apple),
                        <.span(^.className := "pull-right")(
                          <.span(Icon.thList),
                          <.span(Icon.adjust)
                        )

                      )

                    )
                  ),
                  <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                    <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                    <.div(SynereoDashboardCSS.Style.userNameDescription)(
                      <.span("James Gosling"),
                      <.span(Icon.chevronRight),
                      <.span(^.color := "blue")("Ux love,party at new york"), <.br(),
                      <.span("just now")
                    ),
                    <.span()(Icon.ellipsisV),
                    <.div(^.className := "col-md-12")(
                     <.h3("Headed to  sxsw", SynereoDashboardCSS.Style.cardHeading),
                      <.div("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                        "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                      <.br(),
                      <.span(^.color := "blue")(Icon.ellipsisH)
                    ),
                    <.div(^.className := "col-md-12")(
                      <.div(SynereoDashboardCSS.Style.postActions)(
                        <.span(Icon.thList),
                        <.span(Icon.adjust),
                        <.span(Icon.anchor),
                        <.span(Icon.apple),
                        <.span(^.className := "pull-right")(
                          <.span(Icon.thList),
                          <.span(Icon.adjust)
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
