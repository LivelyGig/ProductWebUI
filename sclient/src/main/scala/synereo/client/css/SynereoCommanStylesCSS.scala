package synereo.client.css

import synereo.client.css.DashboardCSS.Style._

import scalacss.Defaults._

/**
  * Created by Mandar on 3/23/2016.
  */
object SynereoCommanStylesCSS {

  object Style extends StyleSheet.Inline {

    import dsl._


    val naviContainer = style(
      // backgroundColor(c"#00131D"),
      //minHeight(85.px),
      /*borderBottom(2.px, solid, c"#67EAF2"),*/
      paddingLeft(0.%%),
      paddingRight(0.%%),
      marginBottom(0.px),
      media.maxWidth(820.px) -
        paddingTop(4.px)
        //media.minWidth(821.px) -
       // paddingTop(8.px)
    )
    val searchFormInputBox = style(
      backgroundColor(rgba(115, 99, 99, 0.4)),
      borderTop.`0`,
      borderRight.`0`,
      borderRadius.`0`.important,
      borderLeft.`0`,
      borderBottom(1.px, solid, grey)
    )
    val userActionButton = style(
      fontSize(1.5.em),
      color(c"#69A5BF"),
      padding(8.px, 20.px),
      backgroundColor.transparent,
      border.`0`.important,
      outline.none.important,
      & focus(
        outline.none.important,
        backgroundColor.transparent.important
        ),
      & hover(
        outline.none.important,
        backgroundColor.transparent.important,
        color(c"#69A5BF")
        )
    )
    val userNameNavBar = style(
      fontSize(1.5.em),
      color(c"#69A5BF"),
      padding(5.px, 20.px)
    )
    val imgLogo = style(
      /*      borderRadius(50.%%),
            width(40.px),
            height(40.px),
            media.maxWidth(820.px)-*/
      marginTop(8.px)
    )
    val bottomBorderOnePx = style(
      borderBottom(1.px, solid, c"#B6BCCC")
    )
    val paddingRightZero = style(
      paddingRight(0.px).important
    )
    val searchFormNavbar = style(
      float.right,
      display.inlineBlock,
      marginRight(10.%%)
    )
    val paddingLeftZero = style(
      paddingLeft(0.px).important
    )
    val synereoBlueText = style(
      color(c"#2DBAF1")
    )
    val modalHeaderText = style(
      fontSize(1.3.em)
    )
    val MarginLeftchkproduct = style(
      marginLeft(15.px),
      marginRight(15.px)
    )

    val verticalAlignmentHelper = style(
      display.table,
      height(100.%%),
      width(100.%%)
    )
    val marginTop10px = style(
      marginTop(10.px)
    )
    val modalHeaderPadding = style(
      padding(10.px)
    )
    val modalHeaderMarginBottom = style(
      marginBottom(10.px)
    )
    val modalHeaderFont = style(
      fontSize(1.em),
      paddingBottom(15.px)
    )
    val marginLeftCloseBtn = style(
      marginLeft(20.px)
    )
    val footTextAlign = style(
      textAlign.center
    )
    val modalBorderRadius = style(
      borderRadius(20.px)
    )
    val modalBackgroundColor = style(
      backgroundColor(c"#96989B")
    )
    val marginLeftRight = style(
      marginRight(-15.px),
      marginLeft(-15.px),
      padding(22.px),
      backgroundColor(c"#00767c")
    )
    val modalBodyPadding = style(
      paddingLeft(15.px),
      paddingBottom(0.px),
      paddingTop(0.px),
      paddingRight(15.px)
    )

    val replyMarginTop = style(
      marginTop(20.px)
    )
    val inputBtnRadius = style(
      border.none,
      padding(0.2.em, 0.6.em, 0.1.em)
    )
    val loading = style(
      width(50.px),
      height(57.px),
      position.absolute,
      top(50.%%),
      left(50.%%),
      zIndex(10000)
    )
    val backgroundColorWhite = style(
      backgroundColor(c"#EEEEEE")
    )
    val userAvatar = style(
      width(30.px),
      height(30.px),
      borderRadius(50.%%),
      display.inlineBlock
    )
    val inlineBlock = style(
      display.inlineBlock
    )
  }

}
