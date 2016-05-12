package client.css

import scalacss.Defaults._

object MessagesCSS {

  object Style extends StyleSheet.Inline {
    import dsl._

    val newProjectbtn = style(
      // float.left,
      // marginLeft(10.px),
      // marginTop(-4.px),
      display.inlineBlock,
      position.absolute
    )
    val marginLeftLeafs = style( //used in searches panel
      marginLeft(16.px)
    )
  }
}
