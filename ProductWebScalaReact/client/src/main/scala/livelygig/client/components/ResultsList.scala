package livelygig.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.models.{ModelType, AppModel, ConnectionsModel}
import livelygig.shared.dtos.{JobPostsResponse, ConnectionProfileResponse, ApiResponse}
import upickle.default._

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/12/2016.
  */

object ResultsList {

  case class Props(appModel: AppModel)

  private val ResultsList = ReactComponentB[Props]("ResultsList")
    .render_P(p => {
      /*def renderJobPosts(jobPosts: JobPostsResponse) = {
        <.li(^.className := "media  profile-description", DashBoardCSS.Style.rsltpaddingTop10p)(
          <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
          <.span(^.className := "checkbox-lbl"),
         /* if (!connection.name.isEmpty) {
            <.div(DashBoardCSS.Style.profileNameHolder)(connection.name)
          } else {
            <.span()
          },*/
          <.div(^.className := "col-md-12")(
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Software Developer"),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Pune, India"),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Connected since 2014-01-02"),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Profiles: ",
              <.a()(^.href := "", "title".reactAttr := "Videographer")("Videographer"),
              " | ",
              <.a()(^.href := "", "title".reactAttr := "Web Developer")("Web Developer"),
              " | ",
              <.a()(^.href := "", "title".reactAttr := "Janal, LLC")("Janal, LLC")
            ),
            <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("My Groups: ",
              <.a()(^.href := "", "title".reactAttr := "Film Industry")("Film Industry"),
              ", ",
              <.a()(^.href := "", "title".reactAttr := "Full Stack Developers")("Full Stack Developers"),
              ", ",
              <.a()(^.href := "", "title".reactAttr := "...")(",,,")
            )
          ),
          /*<.div(^.className := "media-left")(
            <.img(DashBoardCSS.Style.profileImg, ^.src := connection.imgSrc, ^.borderRadius := "25px", ^.alt := "Connection Source: " + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label)
          ),*/
          <.div(^.className := "media-body")(
            <.div(^.className := "col-md-12 col-sm-12 ")(
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Hide")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Favorite")(),
              <.button(HeaderCSS.Style.rsltContainerBtn,^.className := "btn profile-action-buttons")("Recommend")(),
              // ToDo: Above should use something like:
              // NewRecommendation(NewRecommendation.Props(ctl, "Recommend")),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Introduce")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn profile-action-buttons")("Message")()
              // ToDo: Above should use something like:
              // NewMessage(NewMessage.Props(ctl, "Message")
            )
          )
        )
      }*/
      <.div(^.id := "rsltSectionContainer" )(
        <.ul(^.className := "media-list")(/*p.appModel.jobPostsModel map renderJobPosts*/"WIP")
      )
    })
    .build

  def apply(props: Props) =
    ResultsList(props)
}