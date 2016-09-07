package synereo.client.modalpopups

import synereo.client.components.{GlobalStyles, Icon}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.{DashboardCSS, PostFullViewCSS, SignupCSS, SynereoCommanStylesCSS}

import scala.scalajs.js
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._

import scala.scalajs.js.dom
import org.querki.jquery._
import shared.models.MessagePost
import synereo.client.modalpopupbackends.FullPostViewBackend

/**
  * Created by Mandar on 5/3/2016.
  */
object FullPostViewModal {


  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, messages: MessagePost)

  case class State()


  private val component = ReactComponentB[Props]("FullPostViewModal")
    .initialState_P(p => State())
    .backend(new FullPostViewBackend(_))
    .renderPS((t, P, S) => {
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span() /*(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(SignupCSS.Style.signUpHeading))*/ ,
          closed = () => t.backend.modalClosed(S, P),
          CSSClass = "full-post-view-modal"
        ),
        <.div(
          <.div(^.id := "fullPostViewContainer", ^.className := "container-fluid", PostFullViewCSS.Style.fullPostViewContainer, ^.onScroll ==> t.backend.handleScroll)(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-offset-1 col-md-10")(
                  <.div(^.className := "row", PostFullViewCSS.Style.postedImageContainerDiv)(
                    <.img(^.id := "fullViewImage", ^.src := P.messages.postContent.imgSrc, PostFullViewCSS.Style.blogMainImage)
                  ),
                  <.ul(^.id := "fullViewModalNavBar", ^.className := "nav nav-tabs", PostFullViewCSS.Style.postedUserInfoNavModal)(
                    <.li(PostFullViewCSS.Style.postedUserAvatarDiv)(
                      <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                      <.div(DashboardCSS.Style.userNameDescription)(
                        <.span("Colby Brown"),
                        <.span(MIcon.chevronRight),
                        <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Epic Landscape Photography,Landscape love...(2)"), <.br(),
                        <.span("1 hour")
                      )
                    ),
                    <.li(PostFullViewCSS.Style.smallLiContainerUserActions)(
                      <.div(^.className := "pull-right")(
                        //                        <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                        //                          <.span(MIcon.chatBubble),
                        //                          <.span("6")
                        //                        ),
                        //                        <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                        //                          <.span(MIcon.share),
                        //                          <.span("36")
                        //                        ),
                        //                        <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                        //                          <.span(MIcon.share),
                        //                          <.span("4")
                        //                        ),
                        //                        <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                        //                          <.span(Icon.minus),
                        //                          <.span(Icon.heartO),
                        //                          <.span(Icon.plus)
                        //                        ),
                        <.button(^.className := "btn", PostFullViewCSS.Style.postedActionbtn)(
                          <.span(MIcon.moreVert)
                        )
                      )
                    )
                  ),
                  <.div(^.className := "row")(
                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12", PostFullViewCSS.Style.postedUserInfoContainerDiv)(
                      <.div(^.className := "col-md-offset-2 col-md-6 col-sm-offset-1 col-sm-8 col-xs-12")(
                        <.div(^.className := "row", PostFullViewCSS.Style.postHeadlineContainerDiv)(
                          <.h1(s"${P.messages.postContent.subject}"),
                          <.h4(<.span(Icon.mapMarker)("xyz abc Island"))
                        ),
                        <.div(^.className := "row", PostFullViewCSS.Style.postDescription)(
                          s"${P.messages.postContent.text}"
                        ),
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsEditorsDiv)(
                            <.div(
                              ^.className := "btn-group",
                              <.button(^.`type` := "button", PostFullViewCSS.Style.tagsButtons, ^.className := "btn btn-default text-uppercase", <.span("256+", PostFullViewCSS.Style.tagsCount), <.span("SXSW")),
                              <.button(^.`type` := "button", PostFullViewCSS.Style.tagsButtons, ^.className := "btn btn-default text-uppercase", <.span("50-", PostFullViewCSS.Style.tagsCount), <.span("TRAVEL")),
                              <.button(^.`type` := "button", PostFullViewCSS.Style.tagsButtons, ^.className := "btn btn-default text-uppercase", <.span("10+", PostFullViewCSS.Style.tagsCount), <.span("gena Rowlands")),
                              <.button(^.`type` := "button", PostFullViewCSS.Style.tagsButtons, ^.className := "btn btn-default text-uppercase", <.span("256", PostFullViewCSS.Style.tagsCount), <.span("Photography")),
                              <.button(^.`type` := "button", PostFullViewCSS.Style.tagsButtonsEdit, ^.className := "btn btn-default pull-right", (MIcon.modeEdit))
                            )
                          )
                        ),
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponsesDiv)(
                            <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponseHeadingSmall)(
                              "Responses"
                            ),
                            <.div(^.className := "", DashboardCSS.Style.userPost)(
                              <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                              <.input(DashboardCSS.Style.UserInput, ^.placeholder := "Write a response")
                            ),
                            <.div(^.className := "row")(
                              <.div(^.className := " col-md-12", PostFullViewCSS.Style.postedUserAvatarDiv)(
                                <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                                <.div(DashboardCSS.Style.userNameDescription)(
                                  <.span("James Everet"),
                                  <.br(),
                                  <.span("1 hour")
                                )
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
                                    <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post", PostFullViewCSS.Style.collapsePostsButton)(
                                      <.span(MIcon.moreHoriz)
                                    )
                                  )
                                ),
                                <.div(^.id := "collapse-post", ^.className := "collapse")("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
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
                                    <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post1", PostFullViewCSS.Style.collapsePostsButton)(
                                      <.span(MIcon.moreHoriz)
                                    )
                                  )
                                ),
                                <.div(^.id := "collapse-post1", ^.className := "collapse")("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
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
                  <.button(^.id := "modal-close-button", ^.tpe := "button", bss.close, PostFullViewCSS.Style.modalCloseButton, ^.onClick --> t.backend.hide, (MIcon.apply("close", "48")))
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer, ^.className := "hide")()
      )
    })
    .componentDidMount(scope => scope.backend.mounted)
    .build

  def apply(props: Props) = component(props)

}
