package livelygig.client.css

import scalacss.Defaults._

/**
  * Created by Devendu Deodhar
  */
object LftcontainerCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val fontsize12em = style(
      // fontSize(1.2.em),
      // fontWeight.bold
    )
    val marginBottomSearchmodelNode = style(
      marginBottom(15.px)
    )
    val slctMessagesInputLeftContainerMargin = style(
      marginLeft(100.px),
      marginRight(19.px)
    )
    val slctContainer = style(
      display.inlineBlock,
      minWidth(200.px),
      width(100.%%)
    )
    val textareaWidth = style(
      maxWidth(100.%%),
      minWidth(100.%%),
      fontWeight.normal,
      backgroundColor.transparent,
      borderColor.transparent,
      &.hover(
        backgroundColor.white,
        borderColor.gray
      )
    )
    val slctInputWidth = style(
      width(100.px),
      paddingBottom(7.px)
    )
    val textArea = style(
      minWidth(100 %%),
      maxWidth(100 %%),
      fontSize(0.8.em),
      fontWeight.normal
    )
    val slctDate = style(
      border.none,
      backgroundColor.transparent,
      paddingLeft(0.px),
      fontWeight.normal,
      paddingTop(3.px),
      paddingBottom(0.px),
      height.auto,
      lineHeight.normal,
      boxShadow := "none",
      marginLeft(0.px),
      &.hover(
        fontWeight.bold
      )
    )
    val slctsearchpanelabelposition = style(
      paddingTop(0.px),
      paddingBottom(2.px),
      paddingRight(10.px),
      fontWeight.bold,
      overflowY.auto,
      overflowX.auto,
      width(100.%%),
      lineHeight.normal
      // media.maxWidth(1250.px) -(
      // width(200.px))
    )
    val slctsearchpaneheader = style(
      paddingTop(15.px),
      paddingBottom(2.px),
      fontSize(1.em),
      fontWeight.normal
    )
    val inputHeightWidth = style(
      height(25.px),
      marginBottom(12.px),
      display.inline,
      color(c"#333"),
      width(100.%%)
    )
    /*val checkboxlabeltree= style(
      checkboxlabel,
      marginLeft(0.px)

    )*/
    val checkboxlabel = style(
      paddingLeft(0.px),
      fontSize(1.em),
      fontWeight.normal,
    //  marginLeft(18.px),
      &.hover(
        fontWeight.bold
      )
    )
    val subcheckboxlabel = style(
      fontSize(1.em),
      fontWeight.normal,
      marginLeft(18.px)
    )
    val slctleftcontentdiv = style(
      border(1.px, solid, transparent),
      backgroundColor(transparent),
      color(c"#222222"),
      &.hover -
        border(1.px, solid, c"#aaaaaa"),
      color(c"#222222")
    )
    val resizable = style(
      width(100.%%),
      margin(0.%%),
      minHeight(100.px)
    )
    val main_container_div = style(
      backgroundColor(c"#EAEAEA"),
      marginTop(122.px),
      paddingLeft(7.%%),
      paddingRight(7.%%),
      overflowY.auto
    )
    val split_container = style(
      position.relative,
      height(100.%%),
      overflow.hidden
    )
    val row = style(
      marginTop(10.px)
    )

  }

}