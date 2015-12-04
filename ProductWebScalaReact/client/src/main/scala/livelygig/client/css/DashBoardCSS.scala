package livelygig.client.css
import scalacss.Defaults._
import livelygig.client.components.CanIUse.Agent._
import livelygig.client.components.CanIUse.Support._
/**
  * Created by bhagyashree.b on 12/1/2015.
  */
object DashBoardCSS {
  object Style extends StyleSheet.Inline {
    import dsl._
    val mainContainerDiv = style(
      backgroundColor(c"#EAEAEA"),
      marginTop(75.px),
      paddingLeft(7.%%),
      paddingRight(7.%%),
      overflowX.auto,
      height(800.px)
    )
    val splitContainer = style(
      position.relative,
      height(100.%%),
      overflow.hidden
    )
    val rsltContainer = style(
      display.inlineBlock
    )
    val slctContainer = style(
      display.inlineBlock
    )
    val gigActionsContainer = style(
      backgroundColor(c"#DFDFDF"),
      height(60.px)
    )
    val rsltGigActionsDropdown = style(
      display.inlineBlock
    )
    val gigMatchButton = style(
      backgroundColor(transparent),
      border(1.px, solid),
      fontSize(1.2.em)
    )
    val rsltCountHolderDiv = style(
      display.inlineBlock,
      fontSize(1.2.em),
      margin(2.%%)
    )
    val listIconPadding = style(
      padding(14.px, 0.px, 14.px, 0.px)
    )
    val gigConversation = style(
    )
    val profileNameHolder = style(
      height(40.px),
      padding(0.7.%%),
      fontSize(1.2.em)
    )
    val rsltProfileDetailsHolder = style(
      margin(1.%%),
      display.inlineBlock
    )
    val profileImg = style(
      height(120.px),
      width(120.px)
    )
    val btn = style(
      addClassName("btn"),
      border(2.px, solid, c"#005256"),
      marginRight(5.px)
    )
    val inputHeightWidth = style(
      height(25.px),
      marginBottom(12.px),
      display.inline,
      color(c"#333")
    )
    val slctInputWidth = style(
      width(20.px)
    )
    val slctInputLeftContainerMargin = style(
      marginLeft(45.px),
      marginRight(19.px)
    )
    val slctHeaders=style(
      paddingTop(15.px),
      paddingBottom(2.px),
      fontSize(1.em),
      fontWeight.bold
    )
    val checkboxLbl = style(
      &.before(
        fontWeight.normal,
        fontSize(8.px),
        color(green),
        backgroundColor(c"#FAFAFA"),
        border(1.px, solid, rgb(51, 51, 51)),
        borderRadius(0.px),
        display.inlineBlock,
        textAlign.center,
        verticalAlign.middle,
        height(13.px),
        lineHeight(13.px),
        minWidth(13.px),
        marginRight(11.px),
        marginTop(-3.px),
        marginLeft(-15.px)
      ))
  }
}