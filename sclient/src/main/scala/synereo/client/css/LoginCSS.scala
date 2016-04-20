package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar  on 3/11/2016.
  */
object LoginCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val loginPageContainerMain = style(
      height(100.vh),
      backgroundImage := "url(\"./assets/synereo-images/Login-bg.jpg\")",
      marginTop(-55.px)
    )
    val loginScreenBgImage = style(
      margin.auto.important,
      maxHeight(850.px),
      minWidth(600.px)
    )
    val loginModalStyle = style(
      marginTop(200.px)
    )
    val loginDilog = style(
      width(50.%%),
      minHeight(500.px),
      marginLeft(auto),
      marginRight(auto),
      marginTop(50.px)
    )
    val nodeDecorationImageBackground = style(
      backgroundImage := "url(\"./assets/synereo-images/login_nodeDecoration.png\")"
    )
    val formPadding = style(
      padding(4.%%)
    )
    val SignUpBtn = style(

    )
    val requestInviteModalStyle = style(
      backgroundColor(c"#96989B")
    )
    val signUpModalStyle = style(
      backgroundColor(c"#FFFFFF")
    )
    val iconStylePasswordInputBox = style(
      backgroundColor.transparent.important,
      marginLeft(-30.px),
      cursor.pointer,
      color.white,
      border.none.important,
      marginTop(10.px),
      outline.none.important
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
      backgroundColor(c"#012E3D"),
      media.maxWidth(1400.px) -
        width(100.%%)
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
      fontSize(18.px),
      color.white
    )
    val inputFormLoginForm = style(
      textAlign.center,
      marginBottom(10.px),
      marginTop(10.px)

    )
    val loginFormFooter = style(
      padding(5.%%, 15.%%),
      fontSize(16.px)
    )
    val keepMeLoggedIn = style(
      //      marginLeft(65.px),
      display.inlineBlock,
      color(c"#35b0e2")
    )
    val keepMeLoggedInText = style(
      paddingLeft(10.px)
    )
    val forgotMyPassLink = style(
      float.right,
      //      marginRight(65.px),
      color(c"#35b0e2"),
      &.hover(
        color(c"#35b0e2"),
        border.none
      )
    )
    val navLiAStyle = style(
      fontSize(1.1.em),
      color(c"#FFFFFF"),
      opacity(0.5),
      & hover(
        color(c"#FFFFFF"),
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
      position.absolute,
      right(43.%%),
      bottom(12.%%),
      fontSize(22.px),
      display.inlineBlock,
      marginBottom(30.px),
      color(c"#1282B2"),
      backgroundColor.transparent.important,
      outline.none.important,
      &.hover(
        color(c"#1282B2"),
        outline.none.important,
        backgroundColor.transparent.important
      ),
      &.focus(
        color(c"#1282B2"),
        outline.none.important,
        backgroundColor.transparent.important
      ),
      border.none.important,
      fontFamily :=! "karla"
    )
    val requestInviteBtn = style(
      position.absolute,
      right(46.%%),
      bottom(9.%%),
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
    val checkBoxLoginModal = style(
      width(25.px),
      opacity(0.5),
      marginTop(-2.px),
      marginRight(2.px)
    )
    val loginModalTextStyle = style(
      fontFamily :=! "karla",
      opacity(0.5),
      color(c"#000"),
      fontSize(20.px)
    )
    val loginModalTextActionContainer = style(
      marginTop(16.px)
    )
    val modalLoginBtn = style(
      backgroundColor(c"#ff806c"),
      height(58.px),
      fontSize(22.px),
      fontFamily :=! "karla",
      float.right,
      marginTop(74.px)
    )
  }

}
