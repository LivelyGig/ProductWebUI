package synereo.client.css

import scalacss.Defaults._
import scala.language.postfixOps

/**
  * Created by Mandar on 3/23/2016.
  */
object SynereoCommanStylesCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val naviContainer = style(
      paddingLeft(0.%%),
      paddingRight(0.%%),
      marginBottom(0.px),
      position.initial,
      zIndex(0),
      //      backgroundColor(rgba(48, 134, 161, 0.6)),
      //      backgroundColor(rgb(39, 116, 144)),
      media.maxWidth(820.px) -
        paddingTop(4.px)
    )
    val labelSelectizeContainer = style(
      //      marginTop(30.px)
    )
    val searchBoxContainer = style(
      marginTop(12.px),
      display.inlineBlock,
      width(67.%%),
      media.maxWidth(617.px) -
        width(50.%%)
    )
    val labelSelectizeNavbar = style(
      width(480.px),
      display.inlineBlock,
      margin(14.px, 0.px, 0.px, 0.px),
      borderBottom(1.px, solid, c"#FFFFFF")
    )
    val selectizeSearchComponent = style(
      display.inlineBlock,
      margin(14.px, 0.px, 0.px, 0.px),
      media.maxWidth(1130.px).minWidth(920.px) -
        width(180.px),
      media.maxWidth(919.px).minWidth(820.px) -
        width(132.px),
      media.maxWidth(819.px).minWidth(768.px) -
        width(100.px),
      media.maxWidth(767.px) -
        width(230.px),
      media.minWidth(1131.px) -
        width(230.px)
    )
    val loadingScreen = style(
      height(100.vh),
      width(100.vw),
      backgroundColor(rgba(0, 0, 0, 0.5)),
      position.absolute,
      top.`0`,
      left.`0`,
      zIndex(100)
    )

    val userNameTopMenubar = style(
      opacity(0.6),
      fontSize(12.px),
      display.inlineBlock
    )
    val createPostButton = style(
      //      backgroundColor(c"#2283AE"),
      backgroundColor(transparent),
      fontSize(22.px),

      //      marginTop(8.px),
      marginTop(12.%%),
      marginRight(15.px),
      //      marginRight(-4.px),
      //      height(50.px),
      textAlign.left,
      // width(20.px),
      color(white),
      overflow.hidden,
      display.block,
      &.hover(
        backgroundColor(transparent),
        color(white)
      ),
      &.focus(
        backgroundColor(transparent),
        color(white)
      )
      //      &.hover(
      //        width(135.px),
      //        backgroundColor(c"#2283AE"),
      //        color(c"#FFFFFF").important
      //      ),
      //      &.focus(
      //        width(135.px),
      //        backgroundColor(c"#2283AE"),
      //        color(c"#FFFFFF").important
      //      )
    )
    val ampsCount = style(
      fontWeight._600,
      display.inlineBlock
    )
    val mainMenuNavbar = style(
      //      position.absolute,
      //      paddingTop(6.px),
      //      paddingRight(20.px),
      right.`0`,
      //  media.maxWidth(767.px) -
      // marginTop(65.px)
      //      zIndex(100)
      //      backgroundColor(c"#277490")
      media.minWidth(546.px).maxWidth(766.px) -
        width(233.px),
      float.right,

      media.maxWidth(545.px) -(
        marginTop(65.px),
        width(100.%%)
        )
    )
    val renderFailedMessage = style(
      marginTop(40.px),
      fontSize(20.px),
      textAlign.center,
      color(c"#FFFFFF")
    )
    val nonLoggedInMenu = style(
      paddingRight(15.px)
      //      paddingTop(28.px)
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
      color(c"#FFFFFF"),
      &.hover(
        color(c"#FFFFFF")
      ),
      &.focus(
        color(c"#FFFFFF")
      )

    )
    val userActionButton = style(
      fontSize(1.5.em),
      color(c"#FFFFFF"),
      padding(8.px, 20.px),
      backgroundColor.transparent,
      border.`0`.important,
      outline.none.important,
      & focus(
        color(c"#FFFFFF"),
        outline.none.important,
        backgroundColor.transparent.important
        ),
      & hover(
        outline.none.important,
        backgroundColor.transparent.important,
        color(c"#FFFFFF")
        )
    )
    val dropDownLIHeading = style(
      fontSize(1.7.em),
      marginBottom(5.%%),
      display.inlineBlock
    )
    val userNameNavBar = style(
      color(c"#FFFFFF "),
      padding(5.px, 5.px),
      media.maxWidth(1250.px) -
        fontSize(16.px),
      media.minWidth(1251.px) -
        fontSize(20.px),
      media.maxWidth(1050.px).minWidth(546.px) -
        width(130.px),
      media.maxWidth(545.px) -
        width.auto
    )
    val ampsDropdownToggleBtn = style(
      //      width(110.px),
      //      border(1.px, solid, transparent),
      //      borderBottomLeftRadius(9.em),
      backgroundColor.transparent.important,
      //      position.absolute,
      //      right.`0`,
      fontSize(15.px),
      padding.`0`.important,
      color(c"#FFFFFF"),
      &.hover(
        backgroundColor.transparent.important,
        color(c"#FFFFFF")
      ),
      &.focus(
        backgroundColor.transparent.important,
        color(c"#FFFFFF")
      ),
      &.visited(
        backgroundColor.transparent.important,
        color(c"#FFFFFF")
      )
    )
    val userActionsMenu = style(
      width.auto.important,
      minHeight.auto.important,
      right.`0`.important
    )
    val imgLogo = style(
      marginTop(5.px),
      padding(5.px),
      maxHeight(48.px)
    )
    val imgLogoOtherLoc = style(
      marginTop(4.px),
      padding(5.px),
      maxHeight(54.px)
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
      margin(0 px, 25 px)
    )
    val paddingLeftZero = style(
      paddingLeft(0.px).important
    )
    val synereoBlueText = style(
      color(c"#2EAEE3")
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
    val modalBodyPadding = style(
      paddingLeft(46.px),
      paddingRight(46.px),
      paddingBottom(0.px),
      paddingTop(0.px)
    )

    val loading = style(
      width(50.px),
      height(57.px),
      position.absolute,
      top(50.%%),
      left(50.%%),
      //      zIndex(00),
      color.grey
    )
    val messagesLoadingWaitCursor = style(
      width(50.px),
      height(57.px),
      position.fixed,
      top(29.%%),
      left(50.%%),
      zIndex(100),
      color.grey
    )
    val backgroundColorWhite = style(
      backgroundColor(c"#EEEEEE")
    )
    val userAvatar = style(
      width(45.px),
      height(45.px),
      borderRadius(50.%%),
      display.inlineBlock
    )
    val userAvatarTopBar = style(
      width(30.px),
      height(30.px),
      borderRadius(50.%%),
      display.inlineBlock,
      float.left
    )
    val userAvatarAnchor = style(
      backgroundColor.transparent.important,
      paddingLeft.`0`.important,
      &.hover(
        backgroundColor.transparent.important
      ),
      &.focus(
        backgroundColor.transparent.important
      )
    )
    val inlineBlock = style(
      display.inlineBlock
    )
    val searchBtn = style(
      backgroundColor.transparent,
      border.none.important,
      borderRadius.`0`.important,
      marginTop(-30.px),
      marginLeft(10.px),
      paddingTop(3.px),
      paddingBottom(3.px),
      paddingLeft.`0`,
      paddingRight(6.px),
      color(c"#FFFFFF").important,
      &.hover(
        backgroundColor.transparent.important,
        border.none.important,
        color(c"#FFFFFF").important
      )
    )

    val emailVerifiedContainer = style(//      marginTop(-10.%%),
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

    val marginRight15px = style(
      marginRight(15.px)
    )
    val displayInline = style(
      display.inlineBlock
    )
    //
    //    val verticalAlignmentHelper = style(
    //      display.table,
    //      height(100.%%),
    //      width(100.%%)
    //    )
    val verticalAlignCenter = style(
      /* To center vertically */
      display.tableCell,
      verticalAlign.middle,
      paddingLeft(30.%%),
      paddingRight(30.%%)

    )

    val featureHide = style(
      display.none.important
    )

    val userNameOverflow = style(
      whiteSpace.nowrap,
      overflow.hidden,
      textOverflow.:=("ellipsis")
    )

    val paddingLeft15p = style(
      paddingLeft(17.%%).important
    )

    val lftHeightPost = style (
//      height(50.vh),
      display.flex,
      alignItems.center
    )

    val marginTop20px = style (
      marginTop(20.px)
    )
  }
}
