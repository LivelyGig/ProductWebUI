package client.css

import scalacss.Defaults._

object WorkContractCSS {
  object Style extends StyleSheet.Inline {
    import dsl._

    /*  val borderInitiating = style (
      borderBottom(2.px , solid, black)
    )*/
    val marginLeftRight = style(
      marginLeft(15.px),
      marginRight(15.px),
      padding(4.px)
    )
    val WorkContractData = style(
      height(250.px),
      overflowX.auto /*,
      marginRight(10.px)*/
    )
    /* val BiddingScreenResults = style (
      paddingLeft(15.px)
    )*/
    val slctWorkContractInputWidth = style(
      width(100.px)
    )
    val slctWorkContractInputLeftContainerMargin = style(
      marginLeft(165.px)
    )
    val createWorkContractBtn = style(
      backgroundColor(c"#FFA500"),
      color(rgba(51, 51, 51, 1)),
      marginLeft(10.px),
      marginTop(6.px)
    )
    val WorkContractImgWidth = style(
      width(700.px)
    )
    val marginHeader = style(
      margin(8.px)
    )
    val tableFont = style(
      media.minWidth(1701.px) -
        fontSize(16.px),
      media.maxWidth(1700.px).minWidth(1585.px) -
        fontSize(15.px),
      media.maxWidth(1584.px).minWidth(1430.px) -
        fontSize(13.px),
      media.maxWidth(1430.px).minWidth(1371.px) -
        fontSize(13.px),
      media.maxWidth(1370.px).minWidth(1236.px) -
        fontSize(11.px),
      media.maxWidth(1235.px).minWidth(1025.px) -
        fontSize(10.px),
      media.maxWidth(1024.px) -
        fontSize(9.px),
      media.maxWidth(1000.px) -
        fontSize(8.px)
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
    val WorkContractModalHeight = style(
      height(845.px)
    )

    val feedbackbgColor = style(
      backgroundColor(c"#d9edf7")
    )

    val capabilitiesItems = style(
      paddingTop(5.px)
    )

    val capabilities = style(
      paddingTop(12.%%),
      fontWeight.bold
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
