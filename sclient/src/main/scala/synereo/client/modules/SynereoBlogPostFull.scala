package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.Icon
import synereo.client.css.{SynereoBlogPostFullCSS, SynereoCommanStylesCSS, SynereoDashboardCSS}

import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 3/22/2016.
  */
object SynereoBlogPostFull {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard").
    render_P { ctr =>
      <.div(^.className := "container-fluid")(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-1")(
                <.span(Icon.chevronLeft, SynereoBlogPostFullCSS.Style.navigationIcons)
              ),
              <.div(^.className := "col-md-10")(
                <.div(^.className := "row", SynereoBlogPostFullCSS.Style.postedImageContainerDiv)(
                  <.img(^.src := "./assets/images/blogpostimg.png", SynereoBlogPostFullCSS.Style.blogMainImage)
                ),
                <.div(^.className := "row", SynereoBlogPostFullCSS.Style.postedUserInfoContainerDiv)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div(^.className := " col-md-6 col-sm-12 col-xs-12")(
                      <.div(^.className := " col-md-12", SynereoBlogPostFullCSS.Style.postedUserAvatarDiv)(
                        <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                        <.div(SynereoDashboardCSS.Style.userNameDescription)(
                          <.span("Colby Brown"),
                          <.span(Icon.chevronRight),
                          <.span(^.color := "blue")("Epic Landscape Photography,Landscape love...(2)"), <.br(),
                          <.span("1 hour")
                        )
                      )
                    ),
                    <.div(^.className := " col-md-6 col-sm-12 col-xs-12")(
                      <.div(^.className := "col-md-12", SynereoBlogPostFullCSS.Style.postedUserActionDiv)(
                        <.div(^.className := "pull-right")(
                          <.button(^.className := "btn", SynereoBlogPostFullCSS.Style.postedActionbtn)(
                            <.span(Icon.archive),
                            <.span("6")
                          ),
                          <.button(^.className := "btn", SynereoBlogPostFullCSS.Style.postedActionbtn)(
                            <.span(Icon.dashboard),
                            <.span("36")
                          ),
                          <.button(^.className := "btn", SynereoBlogPostFullCSS.Style.postedActionbtn)(
                            <.span(Icon.share),
                            <.span("4")
                          ),
                          <.button(^.className := "btn", SynereoBlogPostFullCSS.Style.postedActionbtn)(
                            <.span(Icon.minus),
                            <.span(Icon.heart),
                            <.span(Icon.plus)
                          ),
                          <.button(^.className := "btn", SynereoBlogPostFullCSS.Style.postedActionbtn)(
                            <.span(Icon.ellipsisV)
                          )
                        )
                      )
                    )
                  )

                ),
                <.div(^.className := "row", SynereoBlogPostFullCSS.Style.postedUserInfoContainerDiv)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div(^.className := "col-md-offset-2 col-md-6 col-sm-offset-1 col-sm-8 col-xs-12")(
                      <.div(^.className := "row", SynereoBlogPostFullCSS.Style.postHeadlineContainerDiv)(
                        <.h1("\"The Beauty of Island\""),
                        <.h4(<.span(Icon.mapMarker)("xyz abc Island"))
                      ),
                      <.div(^.className := "row", SynereoBlogPostFullCSS.Style.postDescription)(
                        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                          "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat. " +
                          "Duis aute irure dolor in reprehenderit in voluptate velit esse\ncillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat " +
                          "non\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum." +
                          "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\\" +
                          "ntempor incididunt ut labore et dolore magna aliqua. \" +\n \"Ut enim ad minim veniam,\\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\\nconsequat. \" +\n" +
                          "\"Duis aute irure dolor in reprehenderit in voluptate velit esse\\ncillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat \"" +
                          " +\n\"non\\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum"
                      ),
                      <.div(^.className := "row")(
                        <.div(^.className := "col-md-12", SynereoBlogPostFullCSS.Style.tagsEditorsDiv)(
                          <.div("editable area for tags to add and remove tags ")
                        )
                      ),
                      <.div(^.className := "row")(
                        <.div(^.className := "col-md-12", SynereoBlogPostFullCSS.Style.tagsResponsesDiv)(
                          <.div(^.className := "col-md-12", SynereoBlogPostFullCSS.Style.tagsResponseHeadingSmall)(
                            "Responses"
                          ),
                          <.div(^.className := "col-md-12", SynereoDashboardCSS.Style.userPost)(
                            <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                            <.input(SynereoDashboardCSS.Style.UserInput, ^.placeholder := "Write a response")
                          ),
                          <.div(^.className := " col-md-12", SynereoBlogPostFullCSS.Style.postedUserAvatarDiv)(
                            <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SynereoDashboardCSS.Style.userAvatar),
                            <.div(SynereoDashboardCSS.Style.userNameDescription)(
                              <.span("James Everet"),
                              <.br(),
                              <.span("1 hour")
                            )
                          ),
                          <.div(^.className := " col-md-12")(
                            "Collapsible panel or accordion  for users posts"
                          )

                        )
                      ),
                      <.div(^.className := "row")(
                        <.div(^.className := "col-md-12", SynereoBlogPostFullCSS.Style.tagsResponseHeadingSmall, SynereoCommanStylesCSS.Style.bottomBorderOnePx)(
                          "Similar Content Loved by your network"
                        )
                      ),
                      <.div(^.className := "row")(
                        <.div(^.className := "col-md-12")(
                          <.ul(^.className := "media-list")(
                            <.li(^.className := "media")(
                              <.div(^.className := "media-left")(
                                <.a(^.href := "#synereofullblogpost")(
                                  <.img(^.className := "media-object", ^.src := "./assets/images/postSmallImage.png")
                                )
                              ),
                              <.div(^.className := "media-body")(
                                <.h3(^.className := "media-heading")(
                                  "Hotspots In iceland"
                                ),
                                ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                                  "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat.")

                              )
                            ),
                            <.li(^.className := "media")(
                              <.div(^.className := "media-left")(
                                <.a(^.href := "#synereofullblogpost")(
                                  <.img(^.className := "media-object", ^.src := "./assets/images/postSmallImage.png")
                                )
                              ),
                              <.div(^.className := "media-body")(
                                <.h3(^.className := "media-heading")(
                                  "Hotspots In iceland"
                                ),
                                ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                                  "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat.")

                              )
                            ),
                            <.li(^.className := "media")(
                              <.div(^.className := "media-left")(
                                <.a(^.href := "#synereofullblogpost")(
                                  <.img(^.className := "media-object", ^.src := "./assets/images/postSmallImage.png")
                                )
                              ),
                              <.div(^.className := "media-body")(
                                <.h3(^.className := "media-heading")(
                                  "Hotspots In iceland"
                                ),
                                ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                                  "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat.")

                              )
                            )

                          )


                        )
                      )

                    )
                  )
                )
              ),
              <.div(^.className := "col-md-1")(
                <.span(Icon.close, SynereoBlogPostFullCSS.Style.closeIcon),
                <.span(Icon.chevronRight, SynereoBlogPostFullCSS.Style.navigationIcons)
              )
            )

          )

        )
      )
    }.build

  def apply(router: RouterCtl[Loc]) = component(router)

}
