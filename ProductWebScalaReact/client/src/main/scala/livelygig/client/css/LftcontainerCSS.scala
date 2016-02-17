package livelygig.client.css
import scalacss.Defaults._
/**
  * Created by Devendu Deodhar
  */
object LftcontainerCSS {
  object Style extends StyleSheet.Inline {
    import dsl._
    val fontsize12em = style (
      fontSize(1.2.em),
      fontWeight.bold
    )
    val slctsearchpanelabelposition = style(
      paddingTop(15.px),
      paddingBottom(2.px),
      fontWeight.bold,
      overflowY.scroll,  //EE tmp
      overflowX.hidden,
      media.maxWidth(1250.px) -(
        width(200.px))
    )
    val slctsearchpaneheader = style(
      paddingTop(15.px),
      paddingBottom(2.px),
      fontSize(1.em),
      fontWeight.normal
    )
    val checkboxlabel = style(
      paddingLeft(0.px),
      fontSize(1.em),
      fontWeight.normal
    )
    val slctleftcontentdiv = style(
      border(1.px,solid ,transparent),
      backgroundColor(transparent),
      color(c"#222222"),
      &.hover -
        border(1.px ,solid ,c"#aaaaaa"),
      color(c"#222222")
    )
    val resizable= style(
      width(100.%%),
      margin(0.%%),
      minHeight(100.px)
    )
    val main_container_div= style(
      backgroundColor(c"#EAEAEA"),
      marginTop(122.px),
      paddingLeft(7.%%),
      paddingRight(7.%%),
      overflowY.auto
    )
    val split_container= style(
      position.relative,
      height(100.%%),
      overflow.hidden
    )
  }
}