package livelygig.client.css

import scalacss.Defaults._
/**
  * Created by bhagyashree.b on 1/11/2016.
  */
object MessagesCSS {

  object Style extends StyleSheet.Inline {
    import dsl._


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
      marginLeft(10.px),
      marginTop(-4.px),
      display.inlineBlock,
      position.absolute
    )
    val hideBtns = style(
      visibility.hidden,
      &.hover(
        visibility.visible
      )
    )
    val marginLeftLeafs = style(
      marginLeft(16.px)
    )
  }
}
