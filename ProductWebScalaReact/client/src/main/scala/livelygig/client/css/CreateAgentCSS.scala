package livelygig.client.css
import scalacss.Defaults._
object CreateAgentCSS {
  object Style extends StyleSheet.Inline {
    import dsl._
    val modalContainer = style (
      height(100.%%),
      width(500.px),
      //  marginLeft(38.%%),
      marginTop(13.%%)
    )
    val ModalHeader = style (
      backgroundColor(c"#00767c"),
      height(60.px),
      borderTopRightRadius(2.px),
      borderTopLeftRadius(2.px),
      color(white)
    )
    val ModalBody = style (
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
    val ModalFoot = style (
      height(60.px),
      backgroundColor(white),
      borderTop(1.px , solid , gray),
      paddingTop(10.px)
    )
    val marginLeftbtn = style (
      marginLeft(-15.px)
    )
    val paddinglefttitle = style (
      paddingLeft(107.px)
    )
    val marginTopClosebtn = style (
      marginTop(13.px)
    )
    val closebtn = style (
      backgroundColor(transparent),
      border.none,
      fontSize(18.px),
      &.hover (
        backgroundColor(transparent),
        border.none
      )
    )
    val displayInline = style (
      display.inline,
      marginLeft(5.px)
    )
    val marginLeftCloseBtn = style (
      marginLeft(20.px)
    )
    val imgLogoLogin = style(
      height(65.px),
      width(65.px)
    )
    val footTextAlign = style(
      textAlign.center
    )
  }
}
