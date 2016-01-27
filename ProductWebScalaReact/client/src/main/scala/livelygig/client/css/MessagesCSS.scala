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
    val paddingLeftModalHeaderbtn = style(
      paddingLeft(15.px),
      color(c"#000")
    )

    val newProjectbtn = style (
      float.left,
     display.initial,
    marginLeft(10.px),
    marginTop(-4.px),
    display.inlineBlock,
    position.absolute
    )
  }
}
