package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar  on 3/11/2016.
  */
object LoginCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val loginPageContainerMain = style(
      height(870.px),
      backgroundImage := "url(\"./assets/images/Login-bg.jpg\")"
    )
    val loginDilog = style(
      width(50.%%),
      minHeight(500.px),
      margin(auto)
    )
    val formPadding = style(
      padding(4.%%)
    )
    val requestInviteModalStyle = style(
      backgroundColor(c"#96989B")
    )
    val iconStylePasswordInputBox = style(
      color.white,
      marginLeft(-25.px),
      cursor.pointer
    )
    val loginDilogContainerDiv = style(
      width(100.%%),
      marginTop(2.%%),
      marginBottom(2.%%),
      position.relative
    )
    val loginFormImg = style(
      position.absolute,
      top.`0`,
      right.`0`,
      left.`0`,
      margin.auto
    )
    val loginFormContainerDiv = style(
      minHeight(340.px),
      width(65.%%),
      border(2.px, solid, c"#35B0E2"),
      position.relative,
      top(145.px),
      borderRadius(20.px),
      marginLeft.auto,
      marginRight.auto,
      marginBottom(150.px),
      marginTop.auto,
      backgroundColor(c"#012E3D")

    )
    val textWhite = style(
      color(c"#FFFFFF"),
      marginBottom(20.px)
    )
    val textBlue = style(
      color(c"#35B0E2")
    )
    val inputStyleLoginForm = style(
      backgroundColor(c"#022639"),
      width(70.%%),
      borderRadius(5.px),
      border(1.px, solid, c"#3C4346"),
      height(40.px),
      padding(4.%%),
      fontSize(18.px)
    )
    val inputFormLoginForm = style(
      textAlign.center,
      marginBottom(10.px),
      marginTop(10.px)

    )
    val loginFormFooter = style(
      padding(20.px),
      fontSize(16.px)
    )
    val keepMeLoggedIn = style(
      marginLeft(85.px),
      display.inlineBlock,
      color(c"#35b0e2")
    )
    val forgotMyPassLink = style(
      float.right,
      marginRight(65.px),
      color(c"#35b0e2"),
      &.hover(
        color(c"#35b0e2"),
        border.none
      )
    )
    val navLiAStyle = style(
      fontSize(1.1.em),
      & hover(
        color(c"#35b0e2"),
        backgroundColor.transparent.important
        )

    )
    val navLiAIcon = style(
      paddingRight(8.px)
    )
    val passwordGlyphStyle = style(
      marginLeft(-25.px),
      marginRight(10.px)
    )
    val dontHaveAccount = style(
      fontSize(20.px),
      display.inlineBlock,
      marginBottom(30.px),
      &.hover(
        color(c"#D2E1E3")
      )

    )
    val requestInviteBtn = style(
      backgroundColor.transparent,
      color(c"#D2E1E3"),
      border(0.2.px, solid, c"#D2E1E3"),
      fontSize(18.px),
      padding(10.px, 15.px),
      &.hover(
        color(c"#D2E1E3"),
        backgroundColor.transparent.important
      )
    )
    val message = style(
      fontSize(24.px),
      fontWeight.normal
    )
    val subscribeButton = style(
      backgroundColor(c"#F58634").important,
      fontSize(1.7.em),
      color.white.important,
      margin(40.px)
    )
    val modalText = style(
      fontSize(4.em),
      padding(40.px)
    )
    val textareaModal = style(
      marginTop(20.px),
      marginBottom(20.px),
      borderBottom(2.px, solid, black),
      fontSize(1.5.em),
      fontWeight._700,
      backgroundColor.transparent.important,
      padding(10.px, 20.px)
    )
  }

}
