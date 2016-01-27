package livelygig.client.css
import scalacss.Defaults._

object ProjectCSS {

  object Style extends StyleSheet.Inline {

    import dsl._
    val slctProjectInputWidth = style(
      width(100.px),
      fontSize(12.px)
    )

    val displayInitialbtn = style (
     display.initial,
      float.right,
      marginTop(4.px),
      marginLeft(-30.px),
      marginRight(10.px)
   )


    val textareaWidth = style (
     width(100.%%)
    )
    val projectdropdownbtn = style (
    fontSize(15.px),
    border(1.px, solid, gray),
    marginBottom(15.px),
    backgroundColor(transparent)
    )
  }
}
