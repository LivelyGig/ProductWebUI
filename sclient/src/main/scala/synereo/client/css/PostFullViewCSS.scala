package synereo.client.css

import scalacss.Defaults._
import scala.language.postfixOps


/**
  * Created by Mandar on 3/22/2016.
  */
object PostFullViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val fullPostViewContainer = style(
      backgroundImage := "url(\"./assets/synereo-images/globalBg-Darker.jpg\")",
      marginTop(-20.px),
      marginLeft(-15.px),
      overflowY.scroll
//      height(953.px)
    )
    val blogMainImage = style(
      marginLeft.auto,
      marginRight.auto,
      display.block,
      width(100.%%)
    )
    val postedActionbtn = style(
      backgroundColor.transparent,
      marginLeft(5.px),
      fontSize(1.1.em)
    )
    val postedImageContainerDiv = style(


    )
    val modalCloseButton = style(
      fontSize(60.px),
      color(c"#678892"),
      &.hover(
        color(c"#678892")
      ),
      margin(20.px)
    )
    val glanceView = style(
      paddingTop(5.px),
      paddingBottom(5.px)
    )
    val navigationIcons = style(
      fontSize(4.em),
      height(400.px),
      lineHeight(8.em),
      color.white
    )
    val closeIcon = style(
      fontSize(4.em),
      width(100 %%),
      color.white,
      display.block
    )
    val tagsResponseHeadingSmall = style(
      fontSize(14.px),
      fontWeight._600,
      paddingBottom(10.px),
      marginBottom(10.px)
    )
    val tagsResponsesDiv = style(
      border(1.px, solid, c"#F1F1F1")
    )
    val tagsEditorsDiv = style(
      marginTop(60.px),
      marginBottom(60.px),
      fontSize(24.px),
      fontWeight._100
    )
    val postDescription = style(
      fontSize(1.1.em)
    )
    val postedUserInfoContainerDiv = style(
      //      marginLeft(11.2.%%),
      //      marginRight(11.2.%%),
      paddingLeft.`0`.important,
      paddingRight.`0`.important,
      backgroundColor(c"#FFFFFF")
    )
    val postedUserInfoNavModal = style(
      //      marginLeft(11.2.%%),
      //      marginRight(11.2.%%),
      paddingLeft.`0`.important,
      paddingRight.`0`.important,
      backgroundColor(c"#FFFFFF"),
      margin(0.px, -15.px),
      zIndex(3)
    )
    val postedUserAvatarDiv = style(

    )
    val smallLiContainerUserActions = style(
      float.right.important
    )
    val postHeadlineContainerDiv = style(
      marginTop(60.px),
      marginBottom(30.px)
    )
    val collapsePostsButton = style(
      backgroundColor.transparent,
      borderRadius(50.%%),
      color(c"#000000"),
      &.focus(
        outline.none.important,
        backgroundColor.transparent.important,
        color(c"#000000").important,
        borderRadius(50.%%).important
      ),
      &.active(
        outline.none.important,
        backgroundColor.transparent.important,
        color(c"#000000").important,
        borderRadius(50.%%).important
      ),
      &.hover(
        outline.none.important,
        backgroundColor.transparent.important,
        color(c"#000000").important,
        borderRadius(50.%%).important
      )
    )
  }

}
