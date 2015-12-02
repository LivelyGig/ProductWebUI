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
      fontWeight.bold
    )
    val slctleftcontentdiv = style(
     border(1.px,solid ,transparent),
     backgroundColor(transparent),
     color(c"#222222"),

    &.hover -
      border(1.px ,solid ,c"#aaaaaa"),
      backgroundColor(c"#ffffff"),
      color(c"#222222")
    )

    val resizable= style(
      width(200.px),
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
