package livelygig.client.css

import scalacss.Defaults._
/**
  * Created by bhagyashree.b on 1/11/2016.
  */
object MessagesCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val slctMessagesInputLeftContainerMargin = style(
      marginLeft(100.px),
      marginRight(19.px)
    )

    val slctMessagesInputWidth = style(
      width(100.px),
      fontSize(12.px)
    )

    val displayInline=style(
      paddingLeft(4.%%),
      display.inline,
      fontSize(1.2.em),
      color(c"#fff"),
      float.right
    )

  }
}
