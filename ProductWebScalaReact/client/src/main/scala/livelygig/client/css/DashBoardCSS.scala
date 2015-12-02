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

     val mainContainerDiv=style(
       backgroundColor(c"#EAEAEA") ,
       marginTop(75.px),
       paddingLeft(7.%%),
       paddingRight(7.%%),
       overflowX.auto,
       height(800.px)
     )
    val splitContainer = style (
      position.relative,
      height(100.%%),
      overflow.hidden
    )
    val rsltContainer=style(
  /*    marginLeft(220.px),*/
      display.inlineBlock
     /* width(85.%%)*/
    )
  val slctContainer=style(
 /*   width(215.px),*/
    display.inlineBlock
  )

    val gigActionsContainer =style(
    backgroundColor(c"#DFDFDF"),
    height(60.px)
    )
   val rsltGigActionsDropdown = style(
    display.inlineBlock
   )

    val gigMatchButton=style(

      backgroundColor(transparent),
      border( 1.px,solid),
      fontSize(1.2.em)
    )
    val rsltCountHolderDiv=style(
      display.inlineBlock,
      fontSize(1.2.em),
      margin( 2.%%)
    )
    val listIconPadding = style(
      padding(14.px,0.px,14.px,0.px)
    )
    val gigConversation=style(

    )
    val profileNameHolder=style(
      height(40.px),
      padding(0.7.%%),
      fontSize(1.2.em)
    )
    val rsltProfileDetailsHolder=style(
      margin(1.%%),
      display.inlineBlock
    )
    val profileImg=style(
    height(100.px),
    width(100.px)
    )

   val btn=style(
       addClassName("btn"),
       border(2.px , solid , c"#005256"),
      marginRight(5.px)
   )
  }

}
