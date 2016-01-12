package livelygig.client.css
import scalacss.Defaults._
/**
  * Created by bhagyashree.b on 1/12/2016.
  */
object BiddingScreenCSS {
  object Style extends StyleSheet.Inline {

    import dsl._

    val biddingheader =style (
      padding(10.px)
    )
    val borderBottomHeader = style (
       borderBottom(2.px , solid, black)
    )
    val borderBottomFooter = style (
      borderTop(2.px , solid, black)
    )
    val marginLeftRight = style (
     marginLeft(15.px),
     marginRight(15.px),
     padding(15.px)
    )
    val biddingScreenData = style (
      height(400.px),
      overflowX.auto
    )
    val displayInlineText = style(
        color(c"#000"),
      media.maxWidth(1300.px)-
        fontSize(12.px)
    )

    val footBorder = style (
      borderStyle(none, none , none, solid)
    )

    val footDisplayInline = style(
      paddingLeft(4.%%),
      display.inline
    )

    val biddingPreset = style (
      padding(10.px),
      fontSize(1.1.em)
    )
  }
}
