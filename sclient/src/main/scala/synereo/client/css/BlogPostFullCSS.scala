package synereo.client.css

import scalacss.Defaults._
import scala.language.postfixOps


/**
  * Created by Mandar on 3/22/2016.
  */
object BlogPostFullCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val blogMainImage = style(
      marginLeft.auto,
      marginRight.auto,
      display.block
    )
    val postedActionbtn = style(
      backgroundColor.transparent,
      marginLeft(5.px),
      fontSize(1.1.em)
    )
    val postedImageContainerDiv = style(


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
      marginLeft(11.5.%%),
      marginRight(11.6.%%),
      backgroundColor.white
    )
    val postedUserAvatarDiv = style(

    )
    val postedUserActionDiv = style(
      textAlign.center,
      paddingTop(20.px)

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
        outline.none
      )
    )
  }

}
