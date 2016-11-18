package synereo.client.components

import japgolly.scalajs.react.{ReactElement, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.css.{DashboardCSS, SynereoCommanStylesCSS}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls

/**
  * Created by mandar.k on 11/17/2016.
  */
object FeedViewRightAnimC {

  case class Props(uid: String = "", feedViewRightStatusAnimDiv: (String, String) => Callback)

  case class State()

  class FeedViewRightAnimCBKND(t: BackendScope[FeedViewRightAnimC.Props, FeedViewRightAnimC.State]) {
    //methods if need be can be added here in future
  }

  val feedViewRightAnimSubIconComponent = ReactComponentB[String]("feedViewRightAnimSubIconComponent")
    .render_P(messageCount =>
      <.div(
        <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
          DashboardCSS.Style.postDescription, messageCount),
        <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
          <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animSubIconFirst)),
        <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
          <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animSubIconSecond)),
        <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
          <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animSubIconThird)),
        <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
          <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animSubIconFourth))
      )
    )
    .build

  val component = ReactComponentB[Props]("FeedViewRightAnimC")
    .initialState_P(p => State())
    .backend(new FeedViewRightAnimCBKND(_))
    .renderPS((t, props, state) => {
      <.div(^.className := "row ",
        SynereoCommanStylesCSS.Style.feedViewRightAnimDivStatusIconRow,
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop20px)(
          <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon,
            <.img(^.src := "./assets/synereo-images/Love.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
              SynereoCommanStylesCSS.Style.animLove,
              ^.onClick --> props.feedViewRightStatusAnimDiv(s"LoveIcon${props.uid}", s"subIcon${props.uid}"))),

          <.div(^.id := s"LoveIcon${props.uid}", ^.className := s"subIcon${props.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            feedViewRightAnimSubIconComponent("12")
          )
        ),
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop20px)(
          <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon)
          (<.img(^.src := "./assets/synereo-images/Comment.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animComment,
            ^.onClick --> props.feedViewRightStatusAnimDiv(s"CommentIcon${props.uid}", s"subIcon${props.uid}"))),

          <.div(^.id := s"CommentIcon${props.uid}", ^.className := s"subIcon${props.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            feedViewRightAnimSubIconComponent("42")
          )
        ),
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop20px)(
          <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon)
          (<.img(^.src := "./assets/synereo-images/Amp_circle.gif", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animAmp_Circle,
            ^.onClick --> props.feedViewRightStatusAnimDiv(s"AmpCircleIcon${props.uid}", s"subIcon${props.uid}"))),

          <.div(^.id := s"AmpCircleIcon${props.uid}", ^.className := s"subIcon${props.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            feedViewRightAnimSubIconComponent("72")
          )
        ),
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop20px)(
          <.div(^.className := "col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon)
          (<.img(^.src := "./assets/synereo-images/Share.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animShare,
            ^.onClick --> props.feedViewRightStatusAnimDiv(s"ShareIcon${props.uid}", s"subIcon${props.uid}"))),

          <.div(^.id := s"ShareIcon${props.uid}", ^.className := s"subIcon${props.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            feedViewRightAnimSubIconComponent("22")
          )
        )
      )

    })
    .build

  def apply(props: Props) = component(props)

}