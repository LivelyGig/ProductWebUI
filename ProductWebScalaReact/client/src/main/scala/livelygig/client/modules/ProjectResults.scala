package livelygig.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, BackendScope, ReactComponentB}
import livelygig.client.Handlers.RefreshProjects
import livelygig.client.RootModels.ProjectsRootModel
import livelygig.client.components._
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.modals.{NewRecommendation, NewMessage, BiddingScreenModal}
import livelygig.client.models.{ProjectsModel, AppModel, ModelType}
import livelygig.shared.dtos.{ApiResponse, ProjectsResponse}
import scala.scalajs.js.Date
import scalacss.ScalaCssReact._

object ProjectResults {

  case class Props (proxy : ModelProxy[Pot[ProjectsRootModel]])
  case class State(selectedItem: Option[ProjectsResponse] = None)
  class Backend($: BackendScope[Props, _]) {
    def mounted(props: Props) =
      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshProjects()))
  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Projects")
    .backend(new Backend(_))
    .renderPS((B,P,S) =>
      <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "row")(
          <.div(^.className := "col-md-4 col-sm-4 col-xs-4", ^.paddingRight := "0px", ^.paddingTop := "12px")(
            <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle, ^.verticalAlign := "middle"),
            //                      <.span(DashBoardCSS.Style.MarginLeftchkproduct, ^.className:="checkbox-lbl"),
            <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown", ^.verticalAlign := "middle")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className := "dropdown-menu")(
                <.li()(<.a(^.href := "#")("Hide")),
                <.li()(<.a(^.href := "#")("Favorite")),
                <.li()(<.a(^.href := "#")("Unhide")),
                <.li()(<.a(^.href := "#")("Unfavorite"))
              )
            ) //dropdown class
          ),
          <.div(^.className := "col-md-8 col-sm-8 col-xs-8", ^.paddingLeft := "0px")(
            <.div(DashBoardCSS.Style.rsltCountHolderDiv, ^.margin := "0px", ^.paddingTop := "19px")("2,352 Results"),
            <.div(^.display := "inline-block")(
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.borderWidth := "0px", ^.paddingTop := "0px", ^.paddingBottom := "2px")("By Date ")(
                  <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
                ),
                <.ul(^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#")("By Date")),
                  <.li()(<.a(^.href := "#")("By Experience")),
                  <.li()(<.a(^.href := "#")("By Reputation")),
                  <.li()(<.a(^.href := "#")("By Rate")),
                  <.li()(<.a(^.href := "#")("By Projects Completed"))
                )
              ),
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.padding := "0px", ^.paddingBottom := "2px", ^.border := "0px")("Newest ")(
                  <.span(Icon.longArrowDown))
              )
            ),
            <.div(^.className := "pull-right", ^.paddingTop := "10px")(
              // todo: icon buttons should be different.  Earlier mockup on s3 had <span class="icon-List1">  2  3  ?
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")( <.span(^.className:="icon-List1")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")( <.span(^.className:="icon-List2")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")( <.span(^.className:="icon-List3"))
            )
          )
        ), //col-12
        <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.className:="rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
            P.proxy().render(jobPostsRootModel =>
              ProjectsList(jobPostsRootModel.projectsModelList)
            ),
            P.proxy().renderFailed(ex =>  <.div( <.span(Icon.warning)," Error loading")),
            if (P.proxy().isEmpty) {
              if (!P.proxy().isFailed) {
                <.div("Loading")
                <.img(^.src:="./assets/images/processing.gif")
              } else {
                <.div()
              }
            } else {
              <.div()
            }
          )
        ) //gigConversation
      )
    )
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build
  def apply(proxy: ModelProxy[Pot[ProjectsRootModel]]) = component(Props(proxy))
}

object ProjectsList {

  case class Props(projects: Seq[ProjectsModel])

  private val ProjectsList = ReactComponentB[Props]("ProjectList")
    .render_P(p => {
      def renderJobPosts(project: ProjectsModel) = {
        <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p)(
          <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
          <.span(^.className := "checkbox-lbl"),
          <.div(DashBoardCSS.Style.profileNameHolder)("Project: "+project.pageOfPosts.summary+" Posted: "+new Date(project.pageOfPosts.postedDate).toString),

          <.div(^.className := "media-body")(
            project.pageOfPosts.description,
            <.div(/*^.className := "col-md-4 col-sm-4",*/ DashBoardCSS.Style.marginTop10px)(
              <.div(DashBoardCSS.Style.profileNameHolder)("Recommended By: Tom")
            ),
            <.div(/*^.onMouseOver ==> displayBtn*/ /*^.onMouseOver --> displayBtn*/ /*^.className:="profile-action-buttons"*/)(
              <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Hide")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Favorite")(),
              NewRecommendation(NewRecommendation.Props("Recommend")),
              <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Find Matching Talent")(),
              BiddingScreenModal(BiddingScreenModal.Props("Apply")),
              NewMessage(NewMessage.Props("Message")
              )
            )
          ) //media-body
        ) //li
      }
      <.div( ^.className:="rsltSectionContainer"  )(
        <.ul(^.className := "media-list")(p.projects map renderJobPosts)
      )
    })
    .build

  def apply(jobPosts: Seq[ProjectsModel]) =
    ProjectsList(Props(jobPosts))
}