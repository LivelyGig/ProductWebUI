package client.css
import scalacss.Defaults._

object ProjectCSS {

  object Style extends StyleSheet.Inline {
    import dsl._

    val displayInitialbtn = style(
      display.initial,
      float.right,
      marginLeft(-30.px),
      marginRight(10.px)
    )
    val displayModalbtn = style(
      display.initial
    //      float.right,
    //      marginRight(10.px)
    )
    val textareaWidth = style(
      // width(100.%%),
      maxWidth(100.%%),
      minWidth(100.%%)
    )
    val projectdropdownbtn = style(
      fontSize(15.px),
      border(1.px, solid, gray),
      marginBottom(15.px),
      backgroundColor(transparent)
    )
  }
}
