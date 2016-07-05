package synereo.client.css

import synereo.client.css.LoginCSS.Style._

import scalacss.Defaults._

/**
  * Created by Mandar on 3/17/2016.
  */

object DashboardCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val profileActionContainer = style(
      //            minHeight(60.px),
      //            minWidth(100.px)
    )
    val dashboardContainerMain = style(
      height(97.vh),
      overflowY.scroll.important,
      overflowX.hidden
      //      backgroundImage := "url(\"./assets/synereo-images/globalBg-Darker.jpg\")"
    )
    val fullViewModalDilog = style(
      width(100.%%)
    )

    val cardPostTagBtn = style(
      margin(5.px),
      fontFamily :=! "karla",
      fontWeight.normal,
      fontSize(12.px),
      textTransform.capitalize,
      backgroundColor.transparent.important,
      height(38.px),
      color(c"#000"),
      opacity(0.8),
      border(1.px, solid, c"#78D3F5"),
      borderRadius(20.px),
      minWidth(80.px),
      padding.`0`.important,
      &.hover(
        color(c"#000"),
        border(1.px, solid, c"#78D3F5"),
        backgroundColor.transparent.important
      )
    )
    val createConnectionBtn = style(
      backgroundColor(c"#FF6F12 ").important,
      fontSize(22.px),
      color.white.important,
      margin(45.px, 0.px)
    )
    val inviteFrndBtn = style(
      fontSize(24.px),
      backgroundColor(c"#FFFFFF"),
      color(c"#000"),
      width(250.px),
      height(60.px),
      marginBottom(30.px),
      marginLeft.auto,
      marginRight.auto,
      display.block
    )
    val confirmIntroReqBtn = style(
      position.absolute,
      top(69.px),
      right(37.px)
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
    )
    val userInputSubmitButton = style(
      backgroundColor.transparent.important
    )
    val ampsEarnedHeading = style(
      color(c"#FFFFFF"),
      paddingLeft(15.px),
      paddingTop(10.px)
    )
    val cardDescriptionContainerDiv = style(
      paddingLeft(65.px),
      paddingRight(65.px)
    )
    val CardHolderLiElement = style(
      margin(15.px, -30.px),
      padding(15.px)
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
    val userPostForm = style(
      marginTop(5.%%),
      backgroundColor(c"#FFFFFF"),
      borderRadius(5.px),
      paddingTop(5.px),
      paddingBottom(5.px),
      marginLeft.auto,
      marginRight.auto,
      maxWidth(762.px)
    )
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
    val newMessageFormBtn = style(
      width(100.%%),
      height(70.px),
      fontSize(30.px),
      borderRadius(0.px)
    )
    val userNameDescription = style(
      //      width(86.%%),
      display.inlineBlock,
      padding(0.px, 15.px),
      fontFamily :=! "karla",
      fontWeight.bold,
      lineHeight(1.5.em),
      color(c"#000"),
      opacity(0.65)
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
    val sidebarBtn = style(
      float.left,
      backgroundColor.transparent,
      border.none, fontSize(22.px),
      marginTop(9.px),
      color(c"#FFFFFF"),
      &.hover(
        backgroundColor(c"#1FB6F1")
      )
    )
    val cardHeading = style(
      fontWeight.bold,
      color(c"#000"),
      lineHeight(1.25.em),
      letterSpacing(0.5.px)
    )
    val sidebarNavStyle = style(
      fontSize(17.px)
    )
    val topBarStyle = style(
      fontSize(15.px)
      //      fontWeight.bold
    )
    val postActionButton = style(
      border.none.important,
      marginTop(-30.px)
    )
    val cardText = style(
      fontSize(21.px),
      fontFamily :=! "lora",
      fontWeight.normal,
      opacity(0.95),
      lineHeight(1.5.em)
    )

  }

}
