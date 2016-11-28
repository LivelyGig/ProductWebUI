package synereo.client.css

import scalacss.Defaults._

/**
  * Created by mandar.k on 3/17/2016.
  */

object DashboardCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

//    val profileActionContainer = style(
//      //            minHeight(60.px),
//      //            minWidth(100.px)
//    )
//    val dashboardContainerMain = style(
//      height(97.vh),
//      overflowY.scroll.important,
//      overflowX.hidden
//      //      backgroundImage := "url(\"./assets/synereo-images/globalBg-Darker.jpg\")"
//    )
//    val fullViewModalDilog = style(
//      width(100.%%)
//    )
    val inputBtnRadius = style(
      border.none,
      display.block,
      float.right,
      width(25.px),
      height(25.px)
    )
//    val cardPostTagBtn = style(
//      margin(5.px),
//      fontFamily :=! "karla",
//      fontWeight.normal,
//      fontSize(12.px),
//      textTransform.capitalize,
//      backgroundColor.transparent.important,
//      height(38.px),
//      color(c"#000"),
//      opacity(0.8),
//      border(1.px, solid, c"#78D3F5"),
//      borderRadius(20.px),
//      minWidth(80.px),
//      padding.`0`.important,
//      &.hover(
//        color(c"#000"),
//        border(1.px, solid, c"#78D3F5"),
//        backgroundColor.transparent.important
//      )
//    )
    val createConnectionBtn = style(
      backgroundColor(c"#FF6F12 ").important,
      fontSize(22.px),
      color.white.important,
      margin(45.px, 0.px),
      media.maxWidth(580.px)(
        margin(15.px, 0.px)
      )
    )
    val confirmIntroReqBtn = style(
      backgroundColor.transparent,
      color.red,
      &.hover(
        color.red,
        backgroundColor.transparent.important
      ),
      marginTop.`0`.important
    )
    val bodyImg = style(
      borderImageRepeat.:=(none),
      overflow.hidden,
      height(100.vh),
      backgroundImage := "url(\"./assets/synereo-images/globalBg-Darker.jpg\")"
    )
    val homeFeedContainer = style(
      marginTop(30.px),
      marginBottom(90.px),
      minHeight(100.px)
    )
    val homeFeedMainContainer = style(
      //      width(762.px),
      //      padding(0.px)
    )
    val cardImage = style(
      //      paddingRight(65.px)s
      //      width(100.%%)
      maxHeight(500.px),
      margin.auto
    )
//    val userInputSubmitButton = style(
//      backgroundColor.transparent.important
//    )
    val cardDescriptionContainerDiv = style(
      cursor.pointer,
      media.minWidth(768.px)(
        paddingLeft(65.px),
        paddingRight(65.px)
      ),
      media.maxWidth(767.px)(
        paddingLeft(20.px),
        paddingRight(20.px)
      )
    )
    val CardHolderLiElement = style(
      //      margin(15.px, -30.px),
      overflowY.hidden.important,
      media.minWidth(768.px)(
        padding(15.px)
      ),
      media.maxWidth(767.px)(
        padding(0.px, 18.px, 10.px, 18.px)
      )
    )
    val glanceViewName = style(
      display.inlineBlock
    )
    val userPost = style(
      marginLeft.auto.important,
      marginRight.auto.important,
      maxWidth(762.px),
      backgroundColor(c"#FFFFFF"),
      borderRadius(5.px),
      paddingTop(5.px),
      paddingBottom(5.px)
    )
//    val userPostForm = style(
//      marginTop(5.%%),
//      backgroundColor(c"#FFFFFF"),
//      borderRadius(5.px),
//      paddingTop(5.px),
//      paddingBottom(5.px),
//      marginLeft.auto,
//      marginRight.auto,
//      maxWidth(762.px)
//    )
    val userPostRight = style(
      marginTop(5.%%),
      marginLeft(15.px).important,
      width(100.%%),
      backgroundColor.white,
      borderRadius(5.px),
      paddingTop(5.px),
      paddingBottom(5.px)
    )
    val userAvatarDashboardForm = style(
      width(30.px),
      height(30.px),
      borderRadius(50.%%),
      display.inlineBlock
    )
    val homeFeedUserAvatar = style(
      width(45.px),
      borderRadius(50.%%),
      padding(5.px),
      margin(5.px),
      display.inlineBlock.important
    )
    val UserInput = style(
      border.none.important,
      fontWeight._700,
      width(89.%%),
      height(40.px),
      display.inlineBlock,
      paddingLeft(6.px),
      &.focus(
        outline.none.important
      )
    )
//    val newMessageFormBtn = style(
//      width(100.%%),
//      height(70.px),
//      fontSize(30.px),
//      borderRadius(0.px)
//    )
    val userNameDescription = style(
      //      width(86.%%),
      display.inlineBlock,
      padding(0.px, 15.px),
      fontFamily :=! "karla",
      fontWeight.bold,
      lineHeight(1.5.em),
      color(c"#000"),
      opacity(0.65),
      media.minWidth(768.px)(
        fontSize(14.px)
      ),
      media.only.screen.maxDeviceWidth(767.px)(
        fontSize(11.px)
      )
    )
    val postDescription = style(
      display.inlineBlock,
      paddingTop(2.px),
      fontFamily :=! "karla",
      fontWeight.bold,
      lineHeight(1.5.em),
      color(c"#fff"),
      opacity(0.65),
      media.minWidth(768.px).maxDeviceWidth(991.px)(
        fontSize(12.px),
        width(12.px),
        paddingLeft(5.%%),
        paddingRight(5.%%)
      ),
      media.maxWidth(767.px)(
        fontSize(11.px),
        position.absolute,
        left(76.%%),
        width(12.px)
      )
    )
    val postActions = style(
      marginLeft(-10.px)
    )
    val homeFeedCardBtn = style(
      backgroundColor.transparent.important,
      border.`0`.important,
      fontSize(24.px),
      paddingLeft.`0`
    )
//    val sidebarBtn = style(
//      float.left,
//      backgroundColor.transparent,
//      border.none, fontSize(22.px),
//      marginTop(9.px),
//      color(c"#FFFFFF"),
//      &.hover(
//        backgroundColor(c"#1FB6F1")
//      )
//    )
    val cardHeading = style(
      fontWeight.bold,
      color(c"#000"),
      lineHeight(1.25.em),
      letterSpacing(0.5.px),
      media.minWidth(768.px)(
        fontSize(24.px)
      ),
      media.maxWidth(767.px)(
        fontSize(21.px)
      )
    )
    val sidebarNavStyle = style(
      fontSize(17.px)
    )
//    val postActionButton = style(
//      border.none.important,
//      marginTop(-30.px)
//    )
    val cardText = style(
      fontFamily :=! "lora",
      fontWeight.normal,
      whiteSpace.preWrap,
      wordWrap.breakWord,
      whiteSpace.preWrap,
      opacity(0.95),
      lineHeight(1.5.em),
      media.minWidth(768.px)(
        fontSize(21.px)
      ),
      media.maxWidth(767.px)(
        fontSize(17.px)
      )
    )
//    val imgBorder = style(
//      border(1.px, solid, gray),
//      borderRadius(6.px),
//      float.right,
//      marginBottom(10.px),
//      marginTop(-10.px)
//    )
//    val ampTokenBtn = style(
//      backgroundColor.transparent.important,
//      border.`0`.important,
//      marginTop(6.px),
//      paddingLeft(2.px),
//      &.hover(
//        backgroundColor.transparent.important,
//        border.`0`.important
//      ),
//      &.focus(
//        backgroundColor.transparent.important,
//        border.`0`.important
//      )
//    )
    val ampTokenImg = style(
      maxWidth(20.px)
    )
//    val ampTokenAnimImg = style(
//      maxWidth(25.px),
//      position.relative
//    )
    val animGlyphIcon = style(
      position.relative,
      top(6.px),
      left(7.px),
      media.maxWidth(991.px)(
        top(1.px),
        left(4.px),
        fontSize(12.px)
      )
    )
    val verticalAlignInherit = style (
      verticalAlign.inherit
    )
    val cardPaddingBottom = style(
      paddingBottom(15.px)
    )
    val paddingLRZero = style(
      media.maxWidth(767.px)(
        paddingLeft(0.px),
        paddingRight(0.px))
    )
    val cardPostImage = style(
      height(500.px),
      display.flex,
      backgroundColor(black)
    )
    val cardImageContainer = style(
      margin.auto,
      width(100.%%)
    )
    val ampbalancetext = style(
      whiteSpace.normal,
      width(120.px),
      float.right,
      marginRight(-20.px),
      textAlign.left,
      marginLeft(10.px)
    )
    val noMsg = style(
    color.white,
    textAlign.center,
    fontSize(2.em)
    )
  }
}
