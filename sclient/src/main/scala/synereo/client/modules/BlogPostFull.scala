package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.{MIcon, Icon}
import synereo.client.css.{BlogPostFullCSS, SynereoCommanStylesCSS, SDashboardCSS}

import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 3/22/2016.
  */
object BlogPostFull {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard").
    render_P { ctr =>
      <.div(^.className := "container-fluid",BlogPostFullCSS.Style.blogPostFullContainer)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-1")(
                <.span(MIcon.chevronLeft, BlogPostFullCSS.Style.navigationIcons)
              ),
              <.div(^.className := "col-md-10")(
                <.div(^.className := "row", BlogPostFullCSS.Style.postedImageContainerDiv)(
                  <.img(^.src := "./assets/images/blogpostimg.png", BlogPostFullCSS.Style.blogMainImage)
                ),
                <.div(^.className := "row", BlogPostFullCSS.Style.postedUserInfoContainerDiv)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div(^.className := " col-md-6 col-sm-12 col-xs-12")(
                      <.div(^.className := " col-md-12", BlogPostFullCSS.Style.postedUserAvatarDiv)(
                        <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                        <.div(SDashboardCSS.Style.userNameDescription)(
                          <.span("Colby Brown"),
                          <.span(MIcon.chevronRight),
                          <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Epic Landscape Photography,Landscape love...(2)"), <.br(),
                          <.span("1 hour")
                        )
                      )
                    ),
                    <.div(^.className := " col-md-6 col-sm-12 col-xs-12")(
                      <.div(^.className := "col-md-12", BlogPostFullCSS.Style.postedUserActionDiv)(
                        <.div(^.className := "pull-right")(
                          <.button(^.className := "btn", BlogPostFullCSS.Style.postedActionbtn)(
                            <.span(MIcon.chatBubble),
                            <.span("6")
                          ),
                          <.button(^.className := "btn", BlogPostFullCSS.Style.postedActionbtn)(
                            <.span(MIcon.share),
                            <.span("36")
                          ),
                          <.button(^.className := "btn", BlogPostFullCSS.Style.postedActionbtn)(
                            <.span(MIcon.share),
                            <.span("4")
                          ),
                          <.button(^.className := "btn", BlogPostFullCSS.Style.postedActionbtn)(
                            <.span(Icon.minus),
                            <.span(Icon.heartO),
                            <.span(Icon.plus)
                          ),
                          <.button(^.className := "btn", BlogPostFullCSS.Style.postedActionbtn)(
                            <.span(MIcon.moreVert)
                          )
                        )
                      )
                    )
                  )

                ),
                <.div(^.className := "row", BlogPostFullCSS.Style.postedUserInfoContainerDiv)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    <.div(^.className := "col-md-offset-2 col-md-6 col-sm-offset-1 col-sm-8 col-xs-12")(
                      <.div(^.className := "row", BlogPostFullCSS.Style.postHeadlineContainerDiv)(
                        <.h1("\"The Beauty of Island\""),
                        <.h4(<.span(Icon.mapMarker)("xyz abc Island"))
                      ),
                      <.div(^.className := "row", BlogPostFullCSS.Style.postDescription)(
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
                        <.div(^.className := "col-md-12", BlogPostFullCSS.Style.tagsEditorsDiv)(
                          <.div("editable area for tags to add and remove tags "),
                          <.span(MIcon.modeEdit)
                        )
                      ),
                      <.div(^.className := "row")(
                        <.div(^.className := "col-md-12", BlogPostFullCSS.Style.tagsResponsesDiv)(
                          <.div(^.className := "col-md-12", BlogPostFullCSS.Style.tagsResponseHeadingSmall)(
                            "Responses"
                          ),
                          <.div(^.className := "col-md-12", SDashboardCSS.Style.userPost)(
                            <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                            <.input(SDashboardCSS.Style.UserInput, ^.placeholder := "Write a response")
                          ),
                          <.div(^.className := " col-md-12", BlogPostFullCSS.Style.postedUserAvatarDiv)(
                            <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar),
                            <.div(SDashboardCSS.Style.userNameDescription)(
                              <.span("James Everet"),
                              <.br(),
                              <.span("1 hour")
                            )
                          ),
                          /*collapsible component*/
                          <.div(^.className := "row")(
                            /* <.div(^.className := " col-md-12 text-center")(
                               <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post"
                                 , BlogPostFullCSS.Style.collapsePostsButton)(
                                 <.span(MIcon.keyboardArrowDown)
                               )
                             ),*/
                            <.div(^.className := " col-md-12")(
                              <.div(^.className := "row", BlogPostFullCSS.Style.glanceView)(
                                <.div(^.className := "col-md-1")(
                                  <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar)
                                ),
                                <.div(^.className := "col-md-9", SDashboardCSS.Style.glanceViewName)(
                                  <.span("James Gosling", ^.fontWeight.bold), <.br(),
                                  <.span("19 Mins Ago")
                                ),
                                <.div(^.className := " col-md-2 text-center")(
                                  <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post"
                                    , BlogPostFullCSS.Style.collapsePostsButton)(
                                    <.span(MIcon.moreHoriz)
                                  )
                                )
                              ),
                              <.div(^.id := "collapse-post", ^.className := "collapse")
                              ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                                "\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat." +
                                " Duis aute irure dolor in reprehenderit in voluptate velit esse\ncillum dolore eu fugiat nulla pariatur. " +
                                "Excepteur sint occaecat cupidatat non\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                            )
                          ),
                          <.div(^.className := "row")(
                            /*     <.div(^.className := " col-md-12 text-center")(
                                   <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post1"
                                     , BlogPostFullCSS.Style.collapsePostsButton)(
                                     <.span(MIcon.keyboardArrowDown)
                                   )
                                 ),*/
                            <.div(^.className := " col-md-12")(
                              <.div(^.className := "row", BlogPostFullCSS.Style.glanceView)(
                                <.div(^.className := "col-md-1")(
                                  <.img(^.src := "./assets/images/default_avatar.jpg", ^.alt := "user avatar", SDashboardCSS.Style.userAvatar)
                                ),
                                <.div(^.className := "col-md-9", SDashboardCSS.Style.glanceViewName)(
                                  <.span("Evan Moore", ^.fontWeight.bold), <.br(),
                                  <.span("19 Mins Ago")
                                ),
                                <.div(^.className := " col-md-2 text-center")(
                                  <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post1"
                                    , BlogPostFullCSS.Style.collapsePostsButton)(
                                    <.span(MIcon.moreHoriz)
                                  )
                                )
                              ),
                              <.div(^.id := "collapse-post1", ^.className := "collapse")
                              ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                                "\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat." +
                                " Duis aute irure dolor in reprehenderit in voluptate velit esse\ncillum dolore eu fugiat nulla pariatur. " +
                                "Excepteur sint occaecat cupidatat non\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                            )
                          )
                        )
                      ),
                      <.div(^.className := "row")(
                        <.div(^.className := "col-md-12", BlogPostFullCSS.Style.tagsResponseHeadingSmall, SynereoCommanStylesCSS.Style.bottomBorderOnePx)(
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
                <.span(MIcon.close, BlogPostFullCSS.Style.closeIcon),
                <.span(MIcon.chevronRight, BlogPostFullCSS.Style.navigationIcons)
              )
            )

          )

        )
      )
    }.build

  def apply(router: RouterCtl[Loc]) = component(router)

}
