package synereo.client.css

import scala.language.postfixOps
import scalacss.Defaults._

/**
 * Created by mandar.k on 3/28/2016.
 */
object UserTimelineViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val coverHolderDiv = style(
      marginTop(3.%%),
      marginBottom(2.%%)
    )
    val coverBackgroundImg = style(
      borderRadius(15.px),
      maxWidth(100 %%)
    )
    val followBtn = style(
      fontSize(1.2.em),
      borderRadius.`0`.important,
      backgroundColor(rgba(0, 0, 0, 0.57)),
      color.white,
      marginTop(50.px)
    )
    val coverTitle = style(
      color.white
    )
    val userName = style(
      color(c"#C2C2D8"),
      fontSize(1.2 em),
      marginTop(20.px)
    )
    val coverDescriptionContainer = style(
      width(100 %%),
      height(150.px),
      backgroundColor(c"#191919"),
      opacity(0.5),
      marginTop(-150.px)
    )
    val userAvatar = style(
      maxWidth(50.%%),
      marginTop(-60.px),
      display.block,
      marginLeft.auto,
      marginRight.auto

    )
  }

}

