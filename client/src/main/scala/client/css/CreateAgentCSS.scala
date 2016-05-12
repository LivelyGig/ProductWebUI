package client.css

import scalacss.Defaults._

object CreateAgentCSS {
  object Style extends StyleSheet.Inline {
    import dsl._
    val modalContainer = style(
      height(100.%%),
      width(500.px),
      //  marginLeft(38.%%),
      marginTop(13.%%)
    )
    val ModalHeader = style(
      backgroundColor(c"#00767c"),
      height(60.px),
      borderTopRightRadius(2.px),
      borderTopLeftRadius(2.px),
      color(white)
    )
    val ModalBody = style(
      height(225.px),
      backgroundColor(white),
      paddingLeft(60.px),
      paddingRight(60.px),
      paddingTop(20.px)
    )
    val inputHeightWidth = style(
      height(30.px),
      marginBottom(12.px),
      display.inline,
      color(c"#333")
    )

    val closebtn = style(
      backgroundColor(transparent),
      border.none,
      fontSize(18.px),
      &.hover(
        backgroundColor(transparent),
        border.none
      )
    )
    val displayInline = style(
      display.inline,
      marginLeft(5.px)
    )

    val imgLogoLogin = style(
      height(65.px),
      width(65.px)
    )
    /*val footTextAlign = style(
      textAlign.center
    )*/

    val loginBtn = style(
      width(100.%%),
      backgroundColor(c"#ccccff"),
      margin(3.px)
    )
  }
}
