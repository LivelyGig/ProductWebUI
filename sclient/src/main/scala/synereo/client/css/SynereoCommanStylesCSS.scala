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
    val mainMenuNavbar = style(
      paddingTop(6.px)
    )
    val dropdownMenu = style(
      padding(40.px, 20.px)
    )
    val loginErrorHeading = style(
      marginTop.`0`.important
    )
    val dropdownLiMenuSeperator = style(
      backgroundColor(c"#F2F2F2"),
      fontSize(1.2.em),
      margin(20.px, 0.px),
      padding.`0`.important
    )
    val mainMenuUserActionDropdownBtn = style(
      marginTop(5.px),
      backgroundColor.transparent.important,
      color(c"#69a5bf"),
      &.hover(
        color(c"#69a5bf")
      )
    )
    val searchFormInputBox = style(
      backgroundColor.transparent,
      borderTop.`0`.important,
      borderRight.`0`.important,
      borderLeft.`0`.important,
      borderRight.`0`.important,
      borderRadius.`0`.important,
      borderBottom(1.px, solid),
      color(c"#95C9DE"),
      boxShadow.:=(none)
    )
    val userActionButton = style(
      fontSize(1.5.em),
      color(c"#8CBFD7 "),
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
        color(c"#8CBFD7 ")
        )
    )
    val dropDownLIHeading = style(
      fontSize(1.7.em),
      marginBottom(5.%%),
      display.inlineBlock
    )
    val userNameNavBar = style(
      fontSize(1.5.em),
      color(c"#8CBFD7 "),
      padding(5.px, 20.px)
    )
    val imgLogo = style(
      /*      borderRadius(50.%%),
            width(40.px),
            height(40.px),
            media.maxWidth(820.px)-*/
      marginTop(8.px),
      padding(5.px)
    )
    val bottomBorderOnePx = style(
      borderBottom(1.px, solid, c"#B6BCCC")
    )
    val marginRightZero = style(
      marginRight.`0`.important
    )
    val marginLeftZero = style(
      marginLeft.`0`.important
    )
    val marginLeftTwentyFive = style(
      marginLeft(22.px)
    )
    val marginLeftFifteen = style(
      marginLeft(8.px)
    )
    val paddingRightZero = style(
      paddingRight(0.px).important
    )
    val dropdownIcon = style(
      margin(0.px, 25.px)
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
      color(c"#1282B2"),
      &.hover(
        color(c"#2EAEE3")
      )
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
    val errorModalFooter = style(
      borderTop.`0`.important
    )
    val modalHeaderMarginBottom = style(
      marginBottom(10.px)
    )
    val modalHeaderBorder = style(
      borderRadius(20.px)
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

    val marginLeftRight = style(
      marginRight(-15.px),
      marginLeft(-15.px),
      padding(22.px),
      backgroundColor(c"#00767c")
    )
    val modalBodyPadding = style(
      paddingLeft(46.px),
      paddingRight(46.px),
      paddingBottom(0.px),
      paddingTop(0.px)
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
      zIndex(10000),
      color.grey
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
    val searchBtn = style(
      backgroundColor.transparent,
      border.none.important,
      borderRadius.`0`.important,
      paddingTop(3.px),
      paddingBottom(3.px),
      paddingLeft.`0`,
      paddingRight.`0`,
      color(c"#8CBFD7").important,
      &.hover(
        backgroundColor.transparent.important,
        border.none.important,
        color(c"#8CBFD7").important
      )
    )

    val emailVerifiedContainer = style (
//      marginTop(-10.%%),
//      backgroundColor(c"#9554B0"),
//      marginLeft(-46.px),
//      marginRight(-46.px),
//      borderTopLeftRadius(6.px),
//      borderTopRightRadius(6.px),
//      textAlign.left,
//      padding(10.px),
//      color(white),
//      marginBottom(5.%%),
//      //boxShadow(inset, 0.px , -7.px, -7.px, rgba(0,0,0,0.3))
//     boxShadow.:=(inset,0.px,-7.px,-7.px,black)
    )



  }

}
