package synereo.client.components

import japgolly.scalajs.react.{ReactElement, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.MessagePost
import synereo.client.css.{DashboardCSS, SynereoCommanStylesCSS}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls

/**
  * Created by mandar.k on 11/17/2016.
  */
//scalastyle:off
object FeedViewRightAnimC {

  case class Props(message: MessagePost,
                   feedViewRightStatusAnimDiv: (String, String) => Callback,
                   replyPostCB: (MessagePost) => Callback,
                   forwardPostCB: (MessagePost) => Callback,
                   amplifyPostCB: (String) => Callback)

  case class State()

  class FeedViewRightAnimCBKND(t: BackendScope[FeedViewRightAnimC.Props, FeedViewRightAnimC.State]) {
    //methods if need be can be added here in future
  }

  /*  val feedViewRightAnimSubIconComponent = ReactComponentB[String]("feedViewRightAnimSubIconComponent")
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
        <.ul(^.className:="list-inline",
          <.li(
            DashboardCSS.Style.postDescription, messageCount),
          <.li()(
            <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg",
              SynereoCommanStylesCSS.Style.animSubIconFirst)),
          <.li()(
            <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg",
              SynereoCommanStylesCSS.Style.animSubIconSecond)),
          <.li()(
            <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg",
              SynereoCommanStylesCSS.Style.animSubIconThird)),
          <.li(SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
            <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg",
              SynereoCommanStylesCSS.Style.animSubIconFourth))
        )
      )
      .build*/

  val component = ReactComponentB[Props]("FeedViewRightAnimC")
    .initialState_P(p => State())
    .backend(new FeedViewRightAnimCBKND(_))
    .renderPS((t, props, state) => {
      <.div(^.className := "row ",
        SynereoCommanStylesCSS.Style.feedViewRightAnimDivStatusIconRow,
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop12px)(
          <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon,
            <.img(^.src := "./assets/synereo-images/Love.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
              SynereoCommanStylesCSS.Style.animLove, ^.onClick --> props.feedViewRightStatusAnimDiv(s"LoveIcon${props.message.uid}", s"subIcon${props.message.uid}"))
          ),
          <.div(^.id := s"LoveIcon${props.message.uid}", ^.className := s"subIcon${props.message.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
              DashboardCSS.Style.postDescription, "9"),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Love.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconFirst)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Love.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconSecond)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Love.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconThird)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Love.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconFourth))
          )
        ),
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop12px)(
          <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon)
          (<.img(^.src := "./assets/synereo-images/Comment.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animComment,
            ^.onClick --> props.feedViewRightStatusAnimDiv(s"CommentIcon${props.message.uid}", s"subIcon${props.message.uid}"))),

          <.div(^.id := s"CommentIcon${props.message.uid}", ^.className := s"subIcon${props.message.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
              DashboardCSS.Style.postDescription, "8"),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Comment.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconFirst)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Comment.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconSecond)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Comment.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconThird)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/Comment.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconFourth))
          )
        ),
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop12px)(
          <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon)
          (<.img(^.src := "./assets/synereo-images/Amp_circle.gif", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animAmp_Circle,
            ^.onClick --> props.feedViewRightStatusAnimDiv(s"AmpCircleIcon${props.message.uid}", s"subIcon${props.message.uid}"))),

          <.div(^.id := s"AmpCircleIcon${props.message.uid}", ^.className := s"subIcon${props.message.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
              DashboardCSS.Style.postDescription, "12"),
            if (props.message.sender.name.equals("me")) {
              <.span()
            } else {
              <.div(^.className := " col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
                "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Amplify Post", "data-placement".reactAttr := "right",
                ^.onClick --> props.amplifyPostCB(props.message.sender.connection.target.split("/")(2)))(
                <.img(^.src := "./assets/synereo-images/amptoken.png", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon, SynereoCommanStylesCSS.Style.animSubIconFirst)
              )
            },
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/amptoken.png", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconSecond)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/amptoken.png", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconThird)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/amptoken.png", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconFourth))
          )
        ),
        <.div(^.className := "row", SynereoCommanStylesCSS.Style.marginTop12px)(
          <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivMainStatusIcon)
          (<.img(^.src := "./assets/synereo-images/Share.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
            SynereoCommanStylesCSS.Style.animShare,
            ^.onClick --> props.feedViewRightStatusAnimDiv(s"ShareIcon${props.message.uid}", s"subIcon${props.message.uid}"))),

          <.div(^.id := s"ShareIcon${props.message.uid}", ^.className := s"subIcon${props.message.uid}", SynereoCommanStylesCSS.Style.feedViewLftAnimDivDisplayNone,
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
              DashboardCSS.Style.postDescription, "12"),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
              "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Reply Post", "data-placement".reactAttr := "right",
              ^.onClick --> props.replyPostCB(props.message))(
              <.div(<.span(Icon.mailReply, DashboardCSS.Style.animGlyphIcon), SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon, SynereoCommanStylesCSS.Style.animSubIconFirst)),

            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol,
              "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Forward Post", "data-placement".reactAttr := "right",
              ^.onClick --> props.forwardPostCB(props.message))(
              <.div(<.span(Icon.mailForward, DashboardCSS.Style.animGlyphIcon), SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconSecond))/*,
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconThird)),
            <.div(^.className := "col-lg-1 col-md-1 col-sm-1 col-xs-1", SynereoCommanStylesCSS.Style.feedViewRightAnimDivSubIconCol)(
              <.img(^.src := "./assets/synereo-images/AMP_FullColor+circle.svg", SynereoCommanStylesCSS.Style.feedViewRightPostDivSubIcon,
                SynereoCommanStylesCSS.Style.animSubIconFourth))*/
          )
        )
      )
    }
    )
    .build

  def apply(props: Props) = component(props)

}