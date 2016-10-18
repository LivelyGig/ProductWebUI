package synereo.client.css

import scalacss.Defaults._
import scala.language.postfixOps

/**
  * Created by mandar.k on 3/22/2016.
  */
object PostFullViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val fullPostViewContainer = style(
      backgroundImage := "url(\"./assets/synereo-images/globalBg-Darker.jpg\")",
      marginTop(-20.px),
      marginLeft(-15.px),
      overflowY.hidden,
      backgroundSize := "cover"
    )

    val fullPostViewLeftRightContainer = style(
      height(100.vh),
      cursor.pointer,
      position.relative
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
    val postedImageContainerDiv = style()
    val modalCloseButton = style(
      //      color(c"#678892"),
      color(white),
      opacity(1),
      &.hover(
        color(c"#678892")
      ),
      media.maxWidth(767.px) -
        fontSize(48.px),
      media.minWidth(768.px) -
        fontSize(30.px),
      verticalAlign.textTop,
      position.absolute,
      left(0.px),
      right(0.px),
      width(100.%%)
      /*,
   margin(20.px)*/
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
      marginBottom(60.px)
      //      fontSize(24.px)
      //      fontWeight._100
      //      textAlign.center
    )
    val postDescription = style(
      fontSize(1.1.em),
      paddingBottom(30.px),
      whiteSpace.preWrap
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
    val postedUserAvatarDiv = style()
    val smallLiContainerUserActions = style(
      float.right.important
    )
    val tagsButtons = style(
      marginLeft(5.px),
      marginRight(5.px),
      borderRadius(20.px).important,
      borderColor(c"#2EAEE3"),
      fontSize(18.px),
      &.hover(
        backgroundColor.transparent.important
      ),
      &.focus(
        backgroundColor.transparent.important
      )
    )
    val tagsCount = style(
      color(c"#2EAEE3"),
      marginLeft(2.px),
      marginRight(2.px)
    )
    val tagsButtonsEdit = style(
      border.`0`.important,
      &.hover(backgroundColor.transparent.important)
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

    val marginLeft15PX = style(
      marginLeft(-15.px)
    )

    val closeSIcon = style(
      media.maxWidth(767.px) -
        display.initial,
      media.minWidth(768.px) -
        display.none
    )
    val closeLIcon = style(
      media.maxWidth(767.px) -
        display.none,
      media.minWidth(768.px) -
        display.initial
    )
  }

}
