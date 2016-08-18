package synereo.client.css

import scalacss.Defaults._
import scala.language.postfixOps

/**
  * Created by Mandar  on 3/11/2016.
  */
object LoginCSS {
  val zero: Int = 0

  object Style extends StyleSheet.Inline {

    import dsl._

    val loginPageContainerMain = style(
      height(100.vh),
      backgroundImage := "url(\"./assets/synereo-images/Synereo_LogIn_AmpGame.jpg\")",
      marginTop(-53.px)
    )
    val loginContainer = style(
      height(620.px),
      width(600.px),
      paddingTop(135.px),
      backgroundImage := "url(\"./assets/synereo-images/LogInBox.png\")",
      backgroundSize := 100.%%
    )

    val loginScreenBgImage = style(
      margin.auto.important,
      maxHeight(600.px),
      minWidth(545.px),
      media.maxWidth(1400.px) -
        maxWidth(600.px)
    )
    val watchVideoBtn = style(
      border(1.px, solid)
    )
    val loginModalStyle = style(
      marginTop(90.px),
      media.maxWidth(1400.px) -
        marginTop(30.px)
    )
    val loginDilog = style(
      top.`0`,
      width(100.%%),
      minHeight(500.px),
      marginLeft(auto),
      marginRight(auto),
      position.absolute.important
    )
    val nodeDecorationImageBackground = style(
      backgroundImage := "url(\"./assets/synereo-images/login_nodeDecoration.png\")"
    )
    val formPadding = style(
      padding(4.%%),
      marginRight(28.px)
    )
    val SignUpBtn = style()
    val requestInviteModalStyle = style(
      //      backgroundColor(c"#96989B")
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
      width(32.%%),
      border(2.px, solid, c"#35B0E2"),
      position.relative,
      top(75.px),
      borderRadius(20.px),
      marginLeft.auto,
      marginRight.auto,
      marginBottom(150.px),
      marginTop.auto,
      backgroundColor(c"#012E3D"),
      media.maxWidth(991.px) -
        width(80.%%)
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
    val loginFormLabel = style(
      fontSize(18.px)
    )
    val inputFormLoginForm = style(
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
      bottom(10.%%),
      fontSize(20.px),
      display.inlineBlock,
      marginBottom(28.px),
      color(c"#fff"),
      backgroundColor.transparent.important,
      outline.none.important,
      &.hover(
        color(c"#fff"),
        outline.none.important,
        backgroundColor.transparent.important
      ),
      &.focus(
        color(c"#fff"),
        outline.none.important,
        backgroundColor.transparent.important
      ),
      border.none.important,
      fontFamily :=! "karla",
      opacity(0.4)
    )
    val loginModalFooter = style(
      textAlign.center,
      marginLeft(-46.px),
      marginRight(-46.px),
//      backgroundColor(rgb(0, 0, 0)),
      padding.`0`.important
    )
    val dontHaveAccountBtnLoginModal = style(
      //      position.absolute,
      //      right(43.%%),
      //      bottom(10.%%),
      display.block,
      marginLeft.auto,
      marginRight.auto,
      opacity(0.6),
      marginTop(38.px),
      color(c"#fff"),
      fontSize(20.px),
      //      display.inlineBlock,
      //      marginBottom(28.px),
      color(c"#fff"),
      backgroundColor.transparent.important,
      outline.none.important,

      &.hover(
        color(c"#fff"),
        outline.none.important,
        backgroundColor.transparent.important
      ),
      &.focus(
        color(c"#fff"),
        outline.none.important,
        backgroundColor.transparent.important
      ),
      border.none.important,
      fontFamily :=! "karla"
    )

    val requestInviteBtn = style(
      position.absolute,
      right(45.%%),
      bottom(7.%%),
      backgroundColor.transparent,
      color(c"#fff"),
      opacity(0.7),
      height(48.px),
      border(0.2.px, solid, c"#D2E1E3"),
      fontSize(20.px),
      padding(10.px, 15.px),
      &.hover(
        color(c"#D2E1E3"),
        backgroundColor.transparent.important
      ),
      &.focus(
        color(c"#D2E1E3"),
        backgroundColor.transparent.important
      )
    )
    val requestInviteBtnLoginModal = style(
      //      position.absolute,
      //      right(45.%%),
      //      bottom(7.%%),
      backgroundColor.transparent,
      color(c"#fff"),
      opacity(0.7),
      height(46.px),
      marginTop(28.px),
      border(0.2.px, solid, c"#D2E1E3"),
      fontSize(22.px),
      width(187.px),
      padding(10.px, 15.px),
      &.hover(
        color(c"#fff"),
        backgroundColor.transparent.important
      ),
      &.focus(
        color(c"#fff"),
        backgroundColor.transparent.important
      )
    )
    val message = style(
      fontSize(24.px),
      fontWeight.normal
    )
    val requestInviteModalText = style(
      fontSize(4.em),
      padding(40.px)
    )
    val requestInviteTextarea = style(
      marginTop(20.px),
      marginBottom(20.px),
      fontSize(25.px),
      height(75.px)

    )
    val checkBoxLoginModal = style(
      width(25.px),
      opacity(0.5),
      marginTop(-2.px),
      marginRight(2.px)
    )
    val loginModalTextStyle = style(
      fontFamily :=! "karla",
      color(c"#000000"),
      fontWeight.normal.important,
      fontSize(17.px)
    )
    val loginModalTextActionContainer = style(
      marginTop(16.px)
    )
    val modalLoginBtn = style(
      backgroundColor(c"#ff806c"),
      height(45.px),
      width(142.px),
      fontSize(30.px),
      padding(0.px),
      color(c"#fff"),
      fontFamily :=! "karla",
      float.right,
      marginTop(25.px),
      marginBottom(30.px)
    )
    val apiSubmitBtn = style(
      backgroundColor(c"#ff806c"),
      height(58.px),
      fontSize(20.px),
      fontFamily :=! "karla",
      marginTop(40.px),
      marginBottom(40.px)
    )
  }

}
