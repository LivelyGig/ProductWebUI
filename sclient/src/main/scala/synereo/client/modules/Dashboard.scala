package synereo.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import synereo.client.components.{MIcon, Icon}
import synereo.client.css.{SynereoCommanStylesCSS, DashboardCSS}
import scala.scalajs.js
import scalacss.ScalaCssReact._
import org.querki.jquery._
import scala.scalajs.js
import org.querki.jquery._
import org.scalajs.dom.window
import js.{Date, UndefOr}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.timers._

/**
  * Created by Mandar on 3/11/2016.
  */
object Dashboard {

  case class Props()

  case class State()

  class Backend(t: BackendScope[Props, State]) {
    val homeFeedList: js.Object = "#homeFeedMediaList"
    val window: js.Object = window

    def toggleTopbar = Callback {
      val topBtn: js.Object = "#TopbarContainer"
      $(topBtn).toggleClass("topbar-left topbar-lg-show")
    }

    def modifyCardSize(e: ReactEvent): Callback = {
      var clickedElement = e.target
      println(clickedElement)
      CallbackTo.pure(0)
      //      t.modState(s => s.copy(dummyFlag = true)).runNow()
    }

    def handleMouseEnterEvent(e: ReactEvent): Callback = {
      val Li = e.target
      setTimeout(1500) {
        println("completed 1500ms")
        $(Li).find(".glance-view-button").trigger("click")
      }
      CallbackTo.pure(0)
    }

    def handleMouseLeaveEvent(e: ReactEvent): Callback = {
      val Li = e.target
      setTimeout(1500) {
        println("completed 1500ms")
        $(Li).find(".glance-view-button").trigger("click")
      }
      CallbackTo.pure(0)
    }

    def render(s: State, p: Props) = {
      <.div(^.className := "container-fluid", DashboardCSS.Style.dashboardContainerMain)(
        <.div(^.className := "row")(
          //Left Sidebar
          <.div(^.id := "searchContainer", ^.className := "col-md-2 col-sm-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ")(
            //            Footer(Footer.Props(c, r.page))
            Sidebar(Sidebar.Props())
          )),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
            <.div(^.className := "pull-right", DashboardCSS.Style.profileActionContainer)(
              <.div(^.id := "TopbarContainer", ^.className := "col-md-2 col-sm-2 topbar topbar-animate")(
                TopMenuBar(TopMenuBar.Props()),
                <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", DashboardCSS.Style.profileActionButton, ^.onClick --> toggleTopbar)(
                  <.img(^.src := "./assets/synereo-images/ampsIcon.PNG"), <.span("543")
                )
              )
            )
          )
        ),
        <.div(^.className := "container", DashboardCSS.Style.homeFeedMainContainer)(
          <.div(^.className := "row")(
            <.div(^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "col-md-12 card-shadow", DashboardCSS.Style.userPostForm)(
                <.form(/*^.onSubmit ==> submitForm*/)(
                  <.img(^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.userAvatar),
                  <.input(^.tpe := "text", DashboardCSS.Style.UserInput, ^.className := "form-control", ^.placeholder := "contribute your thoughts..."),
                  //                  <.button(^.tpe := "submit")(<.span()(Icon.camera))
                  <.button(^.tpe := "submit", ^.className := "btn pull-right", DashboardCSS.Style.userInputSubmitButton)(Icon.camera)
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-sm-12 col-md-12 col-lg-12")(
                  <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer, ^.onClick ==> modifyCardSize)(
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost, ^.onMouseEnter ==> handleMouseEnterEvent, ^.onMouseLeave ==> handleMouseLeaveEvent)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn,
                                "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#collapse-post", ^.className := "glance-view-button")(
                                (MIcon.moreHoriz)
                              ),
                              <.div(^.id := "collapse-post", ^.className := "collapse",DashboardCSS.Style.cardText)(
                                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod" +
                                  "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                                  "\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\nconsequat." +
                                  " Duis aute irure dolor in reprehenderit in voluptate velit esse\ncillum dolore eu fugiat nulla pariatur. " +
                                  "Excepteur sint occaecat cupidatat non\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                              )
                            )
                          )
                        )
                      )
                    )
                    ,
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.img(^.src := "./assets/synereo-images/blogpostimg.png", ^.className := "img-responsive", DashboardCSS.Style.cardImage),
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.img(^.src := "./assets/synereo-images/blogpostimg.png", ^.className := "img-responsive", DashboardCSS.Style.cardImage),
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
                            )
                          )
                        )
                      )
                    ),
                    <.li(^.className := "media card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "")(
                        <.div(^.className := "col-md-1")(
                          <.img(^.className := "media-object", ^.src := "./assets/synereo-images/default_avatar.jpg", ^.alt := "user avatar", DashboardCSS.Style.homeFeedUserAvatar)
                        ),
                        <.div(^.className := "col-md-11", SynereoCommanStylesCSS.Style.paddingLeftZero)(
                          <.div(DashboardCSS.Style.userNameDescription)(
                            <.span("James Gosling"),
                            <.span(MIcon.chevronRight),
                            <.span(SynereoCommanStylesCSS.Style.synereoBlueText)("Ux love,party at new york"), <.br(),
                            <.span("just now")
                          ),
                          <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreVert)
                        )
                      ),
                      <.div(^.className := "")(
                        <.div(^.className := "row")(
                          <.div(^.className := "col-md-12")(
                            <.div(DashboardCSS.Style.descriptionHolderForImageCard)(
                              <.h3("The Beautiful Iceland", DashboardCSS.Style.cardHeading),
                              <.div(DashboardCSS.Style.cardText)("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do " +
                                "eiusmod\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\nquis nostrud exercitation ullamco laboris nisi ut aliquip "),
                              <.button(SynereoCommanStylesCSS.Style.synereoBlueText, DashboardCSS.Style.homeFeedCardBtn)(MIcon.moreHoriz)
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
        )
      )
    }
  }

  val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State())
    .renderBackend[Backend]

    .build

  def apply(props: Props) = component(props)
}
