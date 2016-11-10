package synereo.client.modalpopups

import diode.{ModelR, ModelRO}
import synereo.client.components.GlobalStyles
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{DashboardCSS, PostFullViewCSS, SynereoCommanStylesCSS}

import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import shared.models.MessagePost
import japgolly.scalajs.react._
import org.querki.jquery._
import org.widok.moment.Moment
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.services.{RootModel, SYNEREOCircuit}

import scala.scalajs.js

/**
  * Created by mandar.k on 5/3/2016.
  */
//scalastyle:off
object FullPostViewModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, messages: MessagePost, fromSender: String = "", toReceiver: String = "")

  case class State(lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class FullPostViewBackend(t: BackendScope[Props, State]) {
    val fullPostViewContainer: js.Object = "#fullPostViewContainer"
    val fullViewModalNavBar: js.Object = "#fullViewModalNavBar"
    val fullViewImage: js.Object = "#fullViewImage"
    val modalCloseButton: js.Object = "#modal-close-button"
    var scrollY: Int = 0

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def mounted() = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
      //      println(s"Dashboard height ${$(dashboardContainerMain).height()}")
      //      $(fullPostViewContainer).height($(dashboardContainerMain).height() + 25)

    }

    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def modalClosed(state: FullPostViewModal.State, props: FullPostViewModal.Props): Callback = {
      props.submitHandler()
    }

    def handleScroll(e: ReactEventI): Callback = {
      val fullViewImageHeight = $(fullViewImage).height()
      scrollY = $(fullPostViewContainer).scrollTop()
      //      println(scrollY)
      if (scrollY > fullViewImageHeight) {
        $(fullViewModalNavBar).addClass("postedUserInfoNavModalScroll").css("top", fullViewImageHeight.toInt + (scrollY - fullViewImageHeight.toInt))
        $(modalCloseButton).css("position", "absolute")
        $(modalCloseButton).css("top", fullViewImageHeight.toInt + (scrollY - fullViewImageHeight.toInt - 20))
        $(modalCloseButton).css("right", "20px")
      } else if (scrollY < fullViewImageHeight - 160) {
        $(fullViewModalNavBar).removeClass("postedUserInfoNavModalScroll").css("top", "0")
        $(modalCloseButton).addClass("").css("position", "initial")
      }
      Callback.empty
    }

  }


  private val component = ReactComponentB[Props]("FullPostViewModal")
    .initialState_P(p => State())
    .backend(new FullPostViewBackend(_))
    .renderPS((t, props, state) => {
      Modal(
        Modal.Props(
          header = hide => <.span() /*(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(SignupCSS.Style.signUpHeading))*/ ,
          closed = () => t.backend.modalClosed(state, props),
          CSSClass = "full-post-view-modal"
        ),
        <.div(
          <.div(^.id := "fullPostViewContainer", PostFullViewCSS.Style.fullPostViewContainer /*, ^.onScroll ==> t.backend.handleScroll*/)(
            <.div()(
              <.div(^.className := "col-md-1 col-sm-1 col-xs-2", PostFullViewCSS.Style.fullPostViewLeftRightContainer, ^.onClick --> t.backend.hide)(" "),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-8")(
                <.div(^.className := "row", PostFullViewCSS.Style.postedImageContainerDiv)(
                  if (props.messages.postContent.imgSrc != "" && props.messages.postContent.imgSrc.size > 80659) {
                    // getMessage = message
                    <.img(^.id := "fullViewImage", ^.src := props.messages.postContent.imgSrc, PostFullViewCSS.Style.blogMainImage)
                  } else {
                    // getMessage = null
                    <.span("")
                  }

                ),
                <.ul(^.id := "fullViewModalNavBar", ^.className := "nav nav-tabs", PostFullViewCSS.Style.postedUserInfoNavModal)(
                  <.li(PostFullViewCSS.Style.postedUserAvatarDiv)(
                    <.img(^.src := props.messages.sender.imgSrc, ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm, DashboardCSS.Style.verticalAlignInherit),
                    <.div(DashboardCSS.Style.userNameDescription)(
                      <.span(s"From : ${props.fromSender}"),
                      <.span(SynereoCommanStylesCSS.Style.marginLeftTwentyFive)(s"To : ${props.toReceiver}"), <.br(),
                      //                        <.span(MIcon.chevronRight),
                      //                        <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Epic Landscape Photography,Landscape love...(2)"), <.br(),
                      <.span("data-toggle".reactAttr := "tooltip", "title".reactAttr := props.messages.created, "data-placement".reactAttr := "right")(Moment(props.messages.created).format("LLL").toLocaleString)
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
                    <.div(^.className := "col-md-offset-1 col-md-9 col-sm-offset-1 col-sm-8 col-xs-offset-1 col-xs-11")(
                      <.div(^.className := "row", PostFullViewCSS.Style.postHeadlineContainerDiv)(
                        <.h1(s"${props.messages.postContent.subject}") /*,
                          <.h4(<.span(Icon.mapMarker)("xyz abc Island"))*/
                      ),
                      <.div(^.className := "row", PostFullViewCSS.Style.postDescription)(
                        if (props.messages.postContent.imgSrc != "" && props.messages.postContent.imgSrc.size < 80659) {
                          <.div(
                            <.div(^.className := "col-md-8 col-sm-8 col-xs-8")(
                              s"${props.messages.postContent.text}"
                            ),
                            <.div(^.className := "col-md-4 col-sm-4 col-xs-4")(
                              <.img(^.src := props.messages.postContent.imgSrc, ^.height := "100.px", ^.width := "100.px", DashboardCSS.Style.imgBorder))
                          )
                        } else {
                          s"${props.messages.postContent.text}"
                        }
                      ) /*,
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
                        )*/
                      //                       , <.div(^.className := "row")(
                      //                          <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponsesDiv)(
                      //                            <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponseHeadingSmall)(
                      //                              "Responses"
                      //                            ),
                      //                            <.div(^.className := "", DashboardCSS.Style.userPost)(
                      //                              <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                      //                              <.input(DashboardCSS.Style.UserInput, ^.placeholder := "Write a response")
                      //                            ),
                      //                            <.div(^.className := "row")(
                      //                              <.div(^.className := " col-md-12", PostFullViewCSS.Style.postedUserAvatarDiv)(
                      //                                <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm),
                      //                                <.div(DashboardCSS.Style.userNameDescription)(
                      //                                  <.span("James Everet"),
                      //                                  <.br(),
                      //                                  <.span("1 hour")
                      //                                )
                      //                              )
                      //                            ),
                      //                            /*collapsible component*/
                      //                            <.div(^.className := "row")(
                      //                              <.div(^.className := " col-md-12")(
                      //                                <.div(^.className := "row", PostFullViewCSS.Style.glanceView)(
                      //                                  <.div(^.className := "col-md-1")(
                      //                                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm)
                      //                                  ),
                      //                                  <.div(^.className := "col-md-9", DashboardCSS.Style.glanceViewName)(
                      //                                    <.span("James Gosling", ^.fontWeight.bold), <.br(),
                      //                                    <.span("19 Mins Ago")
                      //                                  ),
                      //                                  <.div(^.className := " col-md-2 text-center")(
                      //                                    <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post", PostFullViewCSS.Style.collapsePostsButton)(
                      //                                      <.span(MIcon.moreHoriz)
                      //                                    )
                      //                                  )
                      //                                ),
                      //                                <.div(^.id := "collapse-post", ^.className := "collapse")("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                      //                                  "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                      //                                  "\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat." +
                      //                                  " Duis aute irure dolor in reprehenderit in voluptate velit esse\ncillum dolore eu fugiat nulla pariatur. " +
                      //                                  "Excepteur sint occaecat cupidatat non\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                      //                              )
                      //                            ),
                      //                            <.div(^.className := "row")(
                      //                              <.div(^.className := " col-md-12")(
                      //                                <.div(^.className := "row", PostFullViewCSS.Style.glanceView)(
                      //                                  <.div(^.className := "col-md-1")(
                      //                                    <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatarDashboardForm)
                      //                                  ),
                      //                                  <.div(^.className := "col-md-9", DashboardCSS.Style.glanceViewName)(
                      //                                    <.span("Evan Moore", ^.fontWeight.bold), <.br(),
                      //                                    <.span("19 Mins Ago")
                      //                                  ),
                      //                                  <.div(^.className := " col-md-2 text-center")(
                      //                                    <.button(^.className := "btn btn-primary", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post1", PostFullViewCSS.Style.collapsePostsButton)(
                      //                                      <.span(MIcon.moreHoriz)
                      //                                    )
                      //                                  )
                      //                                ),
                      //                                <.div(^.id := "collapse-post1", ^.className := "collapse")("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                      //                                  "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                      //                                  "\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat." +
                      //                                  " Duis aute irure dolor in reprehenderit in voluptate velit esse\ncillum dolore eu fugiat nulla pariatur. " +
                      //                                  "Excepteur sint occaecat cupidatat non\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                      //                              )
                      //                            )
                      //                          )
                      //                        ),
                      //                        <.div(^.className := "row")(
                      //                          <.div(^.className := "col-md-12", PostFullViewCSS.Style.tagsResponseHeadingSmall, SynereoCommanStylesCSS.Style.bottomBorderOnePx)(
                      //                            "Similar Content Loved by your network"
                      //                          )
                      //                        ),
                      //                        <.div(^.className := "row")(
                      //                          <.div(^.className := "col-md-12")(
                      //                            <.ul(^.className := "media-list")(
                      //                              <.li(^.className := "media")(
                      //                                <.div(^.className := "media-left")(
                      //                                  <.a(^.href := "#synereofullblogpost")(
                      //                                    <.img(^.className := "media-object", ^.src := "./assets/synereo-images/postSmallImage.png")
                      //                                  )
                      //                                ),
                      //                                <.div(^.className := "media-body")(
                      //                                  <.h3(^.className := "media-heading")(
                      //                                    "Hotspots In iceland"
                      //                                  ),
                      //                                  ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                      //                                    "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat.")
                      //                                )
                      //                              ),
                      //                              <.li(^.className := "media")(
                      //                                <.div(^.className := "media-left")(
                      //                                  <.a(^.href := "#synereofullblogpost")(
                      //                                    <.img(^.className := "media-object", ^.src := "./assets/synereo-images/postSmallImage.png")
                      //                                  )
                      //                                ),
                      //                                <.div(^.className := "media-body")(
                      //                                  <.h3(^.className := "media-heading")(
                      //                                    "Hotspots In iceland"
                      //                                  ),
                      //                                  ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                      //                                    "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat.")
                      //                                )
                      //                              ),
                      //                              <.li(^.className := "media")(
                      //                                <.div(^.className := "media-left")(
                      //                                  <.a(^.href := "#synereofullblogpost")(
                      //                                    <.img(^.className := "media-object", ^.src := "./assets/synereo-images/postSmallImage.png")
                      //                                  )
                      //                                ),
                      //                                <.div(^.className := "media-body")(
                      //                                  <.h3(^.className := "media-heading")(
                      //                                    "Hotspots In iceland"
                      //                                  ),
                      //                                  ("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\ntempor incididunt ut labore et dolore magna aliqua. " +
                      //                                    "Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat.")
                      //                                )
                      //                              )
                      //                            )
                      //                          )
                      //                        )
                    )
                  )
                )
              ),
              <.div(^.className := "col-md-1 col-sm-1 col-xs-2", PostFullViewCSS.Style.fullPostViewLeftRightContainer, ^.onClick --> t.backend.hide)(
                //                  <.span(MIcon.close, PostFullViewCSS.Style.closeIcon)
                //  if (props.messages.postContent.imgSrc == "") {
                // getMessage = message
                <.button(^.id := "modal-close-button", ^.tpe := "button", bss.close, PostFullViewCSS.Style.modalCloseButton, ^.onClick --> t.backend.hide,
                  <.span(PostFullViewCSS.Style.closeSIcon)(MIcon.apply("close", "24")), <.span(PostFullViewCSS.Style.closeLIcon)(MIcon.apply("close", "48"))
                )
                //  }else{<.span()}
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
