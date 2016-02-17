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
      height(250.px),
      overflowX.auto/*,
      marginRight(10.px)*/
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
    val BiddingScreenResults = style (
      paddingLeft(15.px)
    )
    val rsltBiddingContainer = style(
      display.inlineBlock,
      width(100.%%),
      fontSize(1.2.em),
      marginLeft(55.px)
    )
    val slctBiddingInputWidth = style (
      width(100.px)
    )
    val slctBiddingInputLeftContainerMargin = style (
      marginLeft(165.px)
    )
    val createBiddingBtn=style(
      backgroundColor(c"#FFA500"),
      color(rgba(51,51,51,1)),
      marginLeft(10.px),
      marginTop(6.px)
    )
    val biddingscreenImgWidth = style (
      width(700.px)
    )
    val inProgerssTable = style (
      height(150.px),
//      margin(15.px),
      overflow.auto
    )
    val marginHeader = style (
      margin(8.px)
    )

    val marginTableItems = style (
      marginLeft(5.px),
      marginRight(5.px)
    )
    val tableFont = style(
      media.minWidth(1701.px)   - (
        fontSize(16.px)
        ),
      media.maxWidth(1700.px).minWidth(1585.px)   - (
        fontSize(15.px)
        ),
      media.maxWidth(1584.px).minWidth(1430.px)   - (
        fontSize(13.px)
        ),
      media.maxWidth(1430.px).minWidth(1371.px)   - (
        fontSize(13.px)
        ),
      media.maxWidth(1370.px).minWidth(1236.px)   - (
        fontSize(11.px)
        ),
      media.maxWidth(1235.px).minWidth(1025.px)   - (
        fontSize(10.px)
        ),
      media.maxWidth(1024.px)   - (
        fontSize(9.px)
        ),
      media.maxWidth(1000.px)   - (
        fontSize(8.px)
        )
    )

    val titleTable = style(
      width(13.%%)
    )
    val indexWidth = style(
      width(3.%%)
    )
    val plannedFinishWidth = style(
      width(12.%%)
    )
    val scheduledFinishWidth = style(
      width(13.%%)
    )
    val talentWidth = style(
      width(20.%%)
    )
    val actionsWidth = style(
      width(19.%%)
    )
    val biddingscreenModalHeight = style (
      height(845.px)
    )

    val feedbackbgColor = style(
      backgroundColor(c"#d9edf7")
    )

    val capabilitiesItems = style (
      paddingTop(30.px)
    )

     val capabilities = style(
       paddingTop(12.%%)
     )
    val notApplicable = style(
      width(15.%%)
    )
    val noUnderstanding = style(
      width(14.%%)
    )
    val awareness = style(
      width(16.%%)
    )
    val expertUnderstanding = style(
      width(10.%%)
    )

  }
}
