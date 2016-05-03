package synereo.client.modalpopups


import synereo.client.components.{Icon, GlobalStyles}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.{PostFullViewCSS, DashboardCSS, SynereoCommanStylesCSS, SignupCSS}
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._
import scala.scalajs.js.dom

/**
  * Created by Mandar on 5/3/2016.
  */
object FullPostViewModal {


  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State()

  class Backend(t: BackendScope[Props, State]) {
    def mounted() = Callback {

    }

    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler()
    }

    def render(s: State, p: Props) = {

      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span() /*(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(SignupCSS.Style.signUpHeading))*/ ,
        closed = () => modalClosed(s, p),
        CSSClass = "full-post-view-modal"),
        <.div(
          <.div(^.className := "container-fluid", PostFullViewCSS.Style.fullPostViewContainer)(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-offset-1 col-md-10")(
                  <.div(^.className := "row", PostFullViewCSS.Style.postedImageContainerDiv)(
                    <.img(^.src := "./assets/synereo-images/blogpostimg.png", PostFullViewCSS.Style.blogMainImage)
                  ),
                  <.div(^.className := "row", PostFullViewCSS.Style.postedUserInfoContainerDiv)(
                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                      <.div(^.className := " col-md-6 col-sm-12 col-xs-12")(
                        <.div(^.className := " col-md-12", PostFullViewCSS.Style.postedUserAvatarDiv)(
                          <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("Colby Brown"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Epic Landscape Photography,Landscape love...(2)"), <.br(),
                            <.span("1 hour")
                          )
                        )
                      ),
                      <.div(^.className := " col-md-6 col-sm-12 col-xs-12")(
                        <.div(^.className := "col-md-12", PostFullViewCSS.Style.postedUserActionDiv)(
                          <.div(^.className := "pull-right")(
                            <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                              <.span(MIcon.chatBubble),
                              <.span("6")
                            ),
                            <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                              <.span(MIcon.share),
                              <.span("36")
                            ),
                            <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                              <.span(MIcon.share),
                              <.span("4")
                            ),
                            <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                              <.span(Icon.minus),
                              <.span(Icon.heartO),
                              <.span(Icon.plus)
                            ),
                            <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                              <.span(MIcon.moreVert)
                            )
                          )
                        )
                      )
                    )

                  ),
                  <.div(^.className := "row", PostFullViewCSS.Style.postedUserInfoContainerDiv)(
                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                      <.div(^.className := "col-md-offset-2 col-md-6 col-sm-offset-1 col-sm-8 col-xs-12")(
                        <.div(^.className := "row", PostFullViewCSS.Style.postHeadlineContainerDiv)(
                          <.h1("\"The Beauty of Island\""),
                          <.h4(<.span(Icon.mapMarker)("xyz abc Island"))
                        ),
                        <.div(^.className := "row", PostFullViewCSS.Style.postDescription)(
                          "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                            "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat. " +
                            "Duis aute irure dolor"
                        ),
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsEditorsDiv)(
                            <.div("editable area for tags to add and remove tags "),
                            <.span(MIcon.modeEdit)
                          )
                        ),
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponsesDiv)(
                            <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponseHeadingSmall)(
                              "Responses"
                            ),
                            <.div(^.className := "col-md-12", DashboardCSS.Style.userPost)(
                              <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                              <.input(DashboardCSS.Style.UserInput, ^.placeholder := "Write a response")
                            ),
                            <.div(^.className := " col-md-12", PostFullViewCSS.Style.postedUserAvatarDiv)(
                              <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                              <.div(DashboardCSS.Style.userNameDescription)(
                                <.span("James Everet"),
                                <.br(),
                                <.span("1 hour")
                              )
                            ),
                            /*collapsible component*/
                            <.div(^.className := "row")(

                              <.div(^.className := " col-md-12")(
                                <.div(^.className := "row", PostFullViewCSS.Style.glanceView)(
                                  <.div(^.className := "col-md-1")(
                                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm)
                                  ),
                                  <.div(^.className := "col-md-9", DashboardCSS.Style.glanceViewName)(
                                    <.span("James Gosling", ^.fontWeight.bold), <.br(),
                                    <.span("19 Mins Ago")
                                  ),
                                  <.div(^.className := " col-md-2 text-center")(
                                    <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post"
                                      , PostFullViewCSS.Style.collapsePostsButton)(
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
                              <.div(^.className := " col-md-12")(
                                <.div(^.className := "row", PostFullViewCSS.Style.glanceView)(
                                  <.div(^.className := "col-md-1")(
                                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm)
                                  ),
                                  <.div(^.className := "col-md-9", DashboardCSS.Style.glanceViewName)(
                                    <.span("Evan Moore", ^.fontWeight.bold), <.br(),
                                    <.span("19 Mins Ago")
                                  ),
                                  <.div(^.className := " col-md-2 text-center")(
                                    <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post1"
                                      , PostFullViewCSS.Style.collapsePostsButton)(
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
                          <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponseHeadingSmall, SynereoCommanStylesCSS.Style.bottomBorderOnePx)(
                            "Similar Content Loved by your network"
                          )
                        ),
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.ul(^.className := "media-list")(
                              <.li(^.className := "media")(
                                <.div(^.className := "media-left")(
                                  <.a(^.href := "#synereofullblogpost")(
                                    <.img(^.className := "media-object", ^.src := "./assets/synereo-images/postSmallImage.png")
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
                                    <.img(^.className := "media-object", ^.src := "./assets/synereo-images/postSmallImage.png")
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
                                    <.img(^.className := "media-object", ^.src := "./assets/synereo-images/postSmallImage.png")
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
                  //                  <.span(MIcon.close, PostFullViewCSS.Style.closeIcon)
                  <.button(^.tpe := "button", bss.close,PostFullViewCSS.Style.modalCloseButton, ^.onClick --> hide, (MIcon.apply("close","48")))
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer, ^.className := "hide")()
      )
    }
  }

  private val component = ReactComponentB[Props]("FullPostViewModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted)
    .build

  def apply(props: Props) = component(props)

}
