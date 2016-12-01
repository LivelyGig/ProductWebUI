package synereo.client.css

import scala.language.postfixOps
import scalacss.Defaults._

/**
  * Created by mandar.k  on 3/11/2016.
  */
object LoginCSS {
  val zero: Int = 0

  object Style extends StyleSheet.Inline {

    import dsl._

    val loginPageContainerMain = style(
      height(100.vh),
      backgroundImage := "url(\"./assets/synereo-images/Synereo_LogIn_AmpGame.jpg\")",
      marginTop(-53.px),
      backgroundSize := "cover",
      media.maxWidth(768.px) -
        marginTop(-61.px)
    )
    val loginContainer = style(
      height(620.px),
      width(600.px),
      paddingTop(135.px),
      backgroundImage := "url(\"./assets/synereo-images/LogInBox.png\")",
      backgroundSize := 100.%%,
      media.width(800.px).height(400.px)(
        width(60.%%),
        marginLeft(20.%%),
        height(335.px),
        marginTop(-5.%%),
        borderBottomLeftRadius(10.px),
        borderBottomRightRadius(20.px)
      ),
      media.minWidth(581.px).maxWidth(767.px)(
        marginLeft(6.%%),
        backgroundImage := "url(\"./assets/synereo-images/LogInBox_Mobile.png\")",
        marginRight(6.%%),
        width.auto,
        backgroundRepeat := "no-repeat",
        backgroundSize := "cover",
        borderBottomRightRadius(5.px),
        borderBottomLeftRadius(5.px)
      ),
      media.maxWidth(580.px)(
        paddingTop.`0`.important,
        maxWidth(280 px),
        marginTop(3.%%),
        backgroundImage := "url(\"./assets/synereo-images/LogInBox_Mobile.png\")",
        backgroundRepeat := "no-repeat",
        marginLeft.auto.important,
        marginRight.auto.important,
        backgroundSize := 115.%%
      ),
      media.width(1024.px)(
        height(460.px),
        width(470.px),
        marginLeft(50.px),
        borderBottomLeftRadius(15.px),
        borderBottomRightRadius(26.px)
      ),
      media.width(1366.px)(
        height(460.px),
        width(470.px),
        marginLeft(50.px),
        borderBottomLeftRadius(15.px),
        borderBottomRightRadius(26.px)
      ),
      media.width(1152.px)(
        height(520.px),
        width(540.px),
        marginLeft(35.px),
        borderBottomLeftRadius(12.px),
        borderBottomRightRadius(20.px)
      ),
      media.width(1400.px)(
        height(560.px),
        borderBottomLeftRadius(15.px),
        borderBottomRightRadius(27.px)
      ),
      media.width(800.px).height(600.px)(
        height(500.px),
        borderBottomLeftRadius(13.px),
        borderBottomRightRadius(22.px),
        width(500.px),
        marginLeft(10.%%)
      ),
      media.width(1280.px).height(600.px)(
        height(500.px),
        borderBottomLeftRadius(13.px),
        borderBottomRightRadius(22.px),
        width(500.px),
        marginLeft(10.%%)
      ),
      media.width(640.px).height(480.px)(
        width(70.%%),
        marginLeft(15.%%),
        height(395.px),
        marginTop(3.%%)
      ),
      media.width(800.px).height(480.px)(
        width(70.%%),
        marginLeft(15.%%),
        height(395.px),
        marginTop(-3.%%)
      ), /*for 960x540 and 960x640*/
      media.width(960.px)(
        width(70.%%),
        marginLeft(15.%%),
        height(430.px)
      ),
      media.width(320.px).height(480.px)(
        marginTop(12.%%)
      ),
      media.width(1136.px).height(640.px)(
        height(508.px),
        borderBottomLeftRadius(13.px),
        borderBottomRightRadius(22.px)
      ),
      media.width(640.px).height(1136.px)(
        marginTop(15.%%)
      ),
      media.width(480.px).height(320.px)(
        height(244.px),
        width(70.%%),
        marginLeft(15.%%),
        marginTop(30.px),
        borderBottomLeftRadius(6.px),
        borderBottomRightRadius(6.px),
        borderTopRightRadius(8.px)
      ),
      media.width(480.px).height(360.px)(
        height(262.px),
        borderBottomLeftRadius(6.px),
        borderBottomRightRadius(6.px),
        borderTopRightRadius(6.px)
      ),
      media.width(240.px).height(320.px)(
        height(244.px),
        width(200.px),
        borderBottomLeftRadius(6.px),
        borderBottomRightRadius(12.px),
        borderTopRightRadius(4.px)
      ),
      media.width(720.px).height(1280.px)(
        borderBottomRightRadius(6.px),
        borderTopRightRadius(6.px)
      ),
      media.width(360.px).height(640.px)(
        borderBottomRightRadius(6.px),
        borderTopRightRadius(6.px)
      ),
      media.width(412.px).height(732.px)(
        borderBottomRightRadius(6.px),
        borderTopRightRadius(6.px)
      ),
      media.width(320.px).height(568.px)(
        borderBottomRightRadius(6.px),
        borderTopRightRadius(6.px)
      ),
      media.width(375.px).height(667.px)(
        borderBottomRightRadius(6.px),
        borderTopRightRadius(6.px)
      ),
      media.width(414.px).height(736.px)(
        borderBottomRightRadius(6.px),
        borderTopRightRadius(6.px)
      )
    )

    val loginImg = style(
      height(120.px),
      width(117.px),
      marginTop(-31.%%),
      marginLeft(36.9.%%),
      media.maxWidth(580.px)(
        width(47.px),
        height(50.px),
        marginLeft(39.8.%%)
      )
      ,
      media.width(800.px).height(400.px)(
        width(23.%%),
        height.auto,
        marginTop(-70.%%),
        marginLeft(37.%%)
      ),
      media.width(800.px).height(600.px)(
        height(104.px),
        width(104.px),
        marginTop(-39.%%),
        marginLeft(36.9.%%)
      ),
      media.width(1280.px).height(600.px)(
        height(104.px),
        width(104.px),
        marginTop(-45.%%),
        marginLeft(36.%%)
      ),
      media.deviceWidth(768.px)(
        marginTop(-27.5.%%),
        marginLeft(38.1.%%)
      ),
      media.width(1024.px)(
        height(92.px),
        width(97.px),
        marginTop(-47.%%),
        marginLeft(36.7.%%)
      ),
      media.width(1366.px)(
        height(92.px),
        width(97.px),
        marginTop(-51.2.%%),
        marginLeft(35.5.%%)
      ),
      media.width(1152.px)(
        height(111.px),
        width(109.px),
        marginTop(-35.%%)
      ),
      media.width(640.px).height(480.px)(
        width(20.%%),
        height.auto,
        marginTop(-60.%%),
        marginLeft(40.%%)
      ),
      media.width(800.px).height(480.px)(
        width(23.%%),
        height.auto,
        marginTop(-54.%%),
        marginLeft(37.%%)
      ), /*for 960x540 and 960x640*/
      media.width(960.px)(
        width(26.%%),
        height.auto,
        marginTop(-63.%%),
        marginLeft(35.%%)
      ),
      media.width(480.px).height(320.px)(
        marginTop(-6.%%)
      ),
      media.width(480.px).height(360.px)(
        marginTop(-6.%%)
      ),
      media.width(240.px).height(320.px)(
        marginTop(-9.%%)
      ),
      media.width(800.px).height(1280.px)(
        marginTop(-27.%%),
        marginLeft(38.%%)
      ),
      media.width(400.px).height(800.px)(
        marginTop(-6.%%)
      ),
      media.width(480.px).height(800.px)(
        marginTop(-6.%%)
      ),
      media.width(540.px).height(960.px)(
        marginTop(-6.%%)
      ),
      media.width(320.px).height(480.px)(
        marginTop(-6.%%)
      ),
      media.width(360.px).height(640.px)(
        marginTop(-6.%%)
      ),
      media.width(412.px).height(732.px)(
        marginTop(-6.%%)
      ),
      media.width(320.px).height(568.px)(
        marginTop(-6.%%)
      ),
      media.width(375.px).height(667.px)(
        marginTop(-6.%%)
      ),
      media.width(414.px).height(736.px)(
        marginTop(-6.%%)
      )

    )
    val signUpImg = style(
      height(120.px),
      width(117.px),
      marginTop(-23.%%),
      marginLeft(38.4.%%)
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
      //            backgroundColor(c"#96989B")
    )
    val loginHeading = style(
      color(c"#fff"),
      fontSize(52.px),
      fontFamily :=! "karla",
      textAlign.center,
      marginBottom(30.px),
      media.maxWidth(580.px)(
        fontSize(3.rem),
        marginBottom(10.px)
      ),
      media.width(1024.px)(
        fontSize(36.px),
        marginBottom(16.px),
        marginTop(-51.px)
      ),
      media.width(1366.px)(
        fontSize(36.px),
        marginBottom(16.px),
        marginTop(-51.px)
      ),
      media.width(1152.px)(
        fontSize(40.px),
        marginBottom(22.px),
        marginTop(-22.px)
      ),
      media.width(1400.px)(
        fontSize(42.px),
        marginBottom(24.px),
        marginTop(-10.px)
      ),
      media.width(800.px).height(600.px)(
        fontSize(40.px),
        marginTop(-20.px),
        marginBottom(20.px)
      ),
      media.width(1280.px).height(600.px)(
        fontSize(40.px),
        marginTop(-20.px),
        marginBottom(20.px)
      ),
      media.width(640.px).height(480.px)(
        fontSize(3.rem),
        marginTop(-20.%%),
        marginBottom(3 %%)
      ),
      media.width(800.px).height(480.px)(
        fontSize(3.rem),
        marginTop(-18.%%),
        marginBottom(3 %%)
      ),
      media.width(800.px).height(400.px)(
        fontSize(3.rem),
        marginTop(-27.%%),
        marginBottom(3 %%)
      ), /*for 960x540 and 960x640*/
      media.width(960.px)(
        fontSize(3.rem),
        marginTop(-18.%%),
        marginBottom(3 %%)
      ),
      media.width(1136.px).height(640.px)(
        fontSize(40.px),
        marginTop(-20.px),
        marginBottom(11.px)
      ),
      media.width(480.px).height(320.px)(
        fontSize(3.rem),
        marginTop(0.%%),
        marginBottom(3 %%)
      ),
      media.width(480.px).height(360.px)(
        fontSize(2.5.rem).important,
        marginTop(0.%%),
        marginBottom(3 %%)
      ),
      media.width(240.px).height(320.px)(
        fontSize(3.rem),
        marginTop(0.%%),
        marginBottom(3 %%)
      )
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
      marginLeft(65.px),
      display.inlineBlock,
      color(c"#35b0e2")
    )
    val keepMeLoggedInText = style(
      paddingLeft(10.px)
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
      color(c"#FFFFFF"),
      opacity(0.5),
      & hover(
        color(c"#FFFFFF"),
        backgroundColor.transparent.important
        )
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
      textAlign.center
    )
    val dontHaveAccountBtnLoginModal = style(
      display.block,
      float.left,
      opacity(0.6),
      marginTop(38.px),
      color(c"#fff"),
      fontSize(20.px),
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
      media.maxWidth(580.px)(
        textAlign.center,
        float.none,
        fontSize(1.7.rem)
      ),
      media.width(1024.px)(
        fontSize(16.px),
        marginTop(20.px)
      ),
      media.width(1366.px)(
        fontSize(16.px),
        marginTop(20.px)
      ),
      media.width(320.px).height(480.px)(
        marginTop(0.px),
        fontSize(16.px)
      ),
      media.width(800.px).height(600.px)(
        marginTop(-6.px)
      ),
      media.width(1280.px).height(600.px)(
        marginTop(-6.px),
        fontSize(1.7.rem)
      ),
      media.width(640.px).height(480.px)(
        fontSize(1.7.rem),
        marginTop(0.px)
      ),
      media.width(800.px).height(480.px)(
        fontSize(1.5.rem),
        marginTop(0.px)
      ),
      media.width(800.px).height(400.px)(
        fontSize(1.5.rem),
        marginTop(-5.px),
        marginLeft(-37.px)
      ), /*for 960x540 and 960x640*/
      media.width(960.px)(
        fontSize(1.5.rem),
        marginTop(10.px),
        marginLeft(-11.%%)
      ),
      media.width(480.px).height(320.px)(
        fontSize(1.2.rem),
        marginTop(-18.px)
      ), media.width(768.px).height(1024.px)(
        marginTop(30.px)
      ),
      media.width(480.px).height(360.px)(
        fontSize(1.5.rem).important,
        marginTop(-10.px)
      ),
      media.width(240.px).height(320.px)(
        fontSize(1.2.rem),
        marginTop(-14.px)
      ),
      media.width(1152.px).height(648.px)(
        marginTop(15.px)
      ),
      media.width(1136.px).height(640.px)(
        marginTop(10.px)
      ),
      media.width(360.px).height(640.px)(
        marginTop(0.px),
        fontSize(1.5.rem)
      )
      ,
      media.width(412.px).height(732.px)(
        marginTop(0.px), fontSize(1.7.rem)
      ),
      media.width(320.px).height(568.px)(
        marginTop(0.px), fontSize(2.rem)
      ),
      media.width(375.px).height(667.px)(
        marginTop(0.px), fontSize(2.rem)
      ),
      media.width(414.px).height(736.px)(
        marginTop(0.px), fontSize(2.rem)
      ),
      media.width(480.px).height(800.px)(
        marginTop(2.px),
        fontSize(1.7.rem)
      ),
      media.width(540.px).height(960.px)(
        marginTop(10.px)
      )
    )
    val verifyUserBtnLoginModal = style(
      display.block,
      opacity(0.6),
      float.right,
      marginTop(38.px),
      color(c"#fff"),
      fontSize(20.px),
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
      media.maxWidth(580.px)(
        textAlign.center,
        float.none,
        fontSize(1.7.rem)
      ),
      media.width(1024.px)(
        fontSize(16.px),
        marginTop(20.px)
      ),
      media.width(1366.px)(
        fontSize(16.px),
        marginTop(20.px)
      ),
      media.width(320.px).height(480.px)(
        marginTop(0.px),
        fontSize(16.px)
      ),
      media.width(800.px).height(600.px)(
        marginTop(-6.px)
      ),
      media.width(1280.px).height(600.px)(
        marginTop(-6.px),
        fontSize(1.7.rem)
      ),
      media.width(640.px).height(480.px)(
        fontSize(1.7.rem),
        marginTop(0.px)
      ),
      media.width(800.px).height(480.px)(
        fontSize(1.5.rem),
        marginTop(0.px)
      ),
      media.width(800.px).height(400.px)(
        fontSize(1.5.rem),
        marginTop(-5.px)
      ), /*for 960x540 and 960x640*/
      media.width(960.px)(
        fontSize(1.5.rem),
        marginTop(10.px)
      ),
      media.width(480.px).height(320.px)(
        fontSize(1.2.rem),
        marginTop(-15.px)
      ),
      media.width(480.px).height(360.px)(
        fontSize(1.5.rem).important,
        marginTop(-15.px)
      ), media.width(768.px).height(1024.px)(
        marginTop(30.px)
      ),
      media.width(240.px).height(320.px)(
        fontSize(1.2.rem),
        marginTop(-15.px)
      ),
      media.width(1152.px).height(648.px)(
        marginTop(15.px)
      ),
      media.width(400.px).height(800.px)(
        marginTop(0.px)
      ),
      media.width(480.px).height(800.px)(
        marginTop(0.px)
      ),
      media.width(540.px).height(960.px)(
        marginTop(0.px)
      ),
      media.width(1136.px).height(640.px)(
        marginTop(10.px)
      ),
      media.width(360.px).height(640.px)(
        marginTop(0.px), fontSize(1.5.rem)
      )
      ,
      media.width(412.px).height(732.px)(
        marginTop(0.px), fontSize(1.7.rem)
      ),
      media.width(320.px).height(568.px)(
        marginTop(0.px), fontSize(1.7.rem)
      ),
      media.width(375.px).height(667.px)(
        marginTop(0.px), fontSize(1.7.rem)
      ),
      media.width(414.px).height(736.px)(
        marginTop(0.px), fontSize(1.7.rem)
      ),
      media.width(480.px).height(800.px)(
        fontSize(1.7.rem)
      )
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
      fontSize(1.7.rem),
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
      textAlign.center,
      &.hover(
        color(c"#cc3300")
      ),
      backgroundColor(c"#ff806c"),
      height(46.px),
      width(142.px),
      fontSize(30.px),
      padding(0.px, 0.px, 3.px, 0.px),
      border.none,
      lineHeight(40.px),
      color(c"#fff"),
      fontFamily :=! "karla",
      float.right,
      marginTop(25.px),
      marginRight(5.%%),
//      media.minWidth(240.px).maxWidth(580.px)(
//        textAlign.center,
//        marginTop(8.px),
//        &.hover(
//          color(c"#cc3300")
//        ),
//        backgroundColor(c"#ff806c"),
//        color(c"#fff"),
//        fontFamily :=! "karla",
//        float.right
//      ),  media.maxWidth(240.px)(
//        textAlign.center,
//        &.hover(
//          color(c"#cc3300")
//        ),
//        backgroundColor(c"#ff806c"),
//        color(c"#fff"),
//        fontFamily :=! "karla",
//        float.right
//      ),
//      media.minWidth(768.px).maxWidth(920.px)(
//        marginRight(15.px)
//      ),
      media.width(1024.px)(
        width(120.px),
        fontSize(25.px),
        marginTop(3.px)
      ),
      media.width(1366.px)(
        width(120.px),
        fontSize(25.px),
        marginTop(3.px)
      ),
      media.width(1152.px).height(648.px)(
        //    height(41.px),
        //  width(142.px),
        //  fontSize(30.px),
        marginTop(6.px)
      ),
      media.width(800.px).height(600.px)(
        marginTop(0.px)
      ),
      media.width(1280.px).height(600.px)(
        marginTop(0.px)
      ),
      media.width(640.px).height(480.px)(
        fontSize(2.rem),
        width.auto,
        marginTop(-12.px),
        paddingLeft(15.px),
        paddingRight(15.px)
      ),
      media.width(800.px).height(480.px)(
        fontSize(2.rem),
        width.auto,
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(-16.px),
        height(40.px)
      ),
      media.width(800.px).height(400.px)(
        fontSize(1.6.rem),
        width.auto,
        paddingLeft(10.px),
        paddingRight(10.px),
        marginTop(-12.px),
        height(40.px)
      ),/*for 960x540 and 960x640*/
      media.width(960.px)(
        fontSize(2.rem),
        width.auto,
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(-12.px)
      ),
      media.width(480.px).height(320.px)(
        fontSize(1.6.rem),
        width.auto,
        paddingLeft(10.px),
        paddingRight(10.px),
        marginTop(-16.px),
        height(34.px),
        lineHeight(19.px)
      ),
      media.width(480.px).height(360.px)(
        fontSize(1.6.rem),
        width.auto,
        paddingLeft(10.px),
        paddingRight(10.px),
        marginTop(-17.px),
        height(40.px)
      ),
      media.width(240.px).height(320.px)(
        fontSize(1.6.rem),
        width.auto,
        paddingLeft(10.px),
        paddingRight(10.px),
        marginTop(-10.px),
        height(28.px),
        lineHeight(32.px)
      ),
      media.width(320.px).height(480.px)(
        width.auto,
        fontSize(2.rem),
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(4.px)
      ),
      media.width(1136.px).height(640.px)(
        width.auto,
        fontSize(2.rem),
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(5.px)
      ),
      media.width(360.px).height(640.px)(
        width.auto,
        fontSize(1.7.rem),
        paddingLeft(10.px),
        paddingRight(10.px),
        marginTop(10.px)
      )
      ,
      media.width(412.px).height(732.px)(
        width.auto,
        fontSize(2.rem),
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(8.px)
      ),
      media.width(320.px).height(568.px)(
        width.auto,
        fontSize(2.rem),
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(8.px),
        height(41.px)
      ),
      media.width(375.px).height(667.px)(
        width.auto,
        fontSize(2.rem),
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(8.px),
        height(41.px)
      ),
      media.width(414.px).height(736.px)(
        width.auto,
        fontSize(2.rem),
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(8.px),
        height(41.px)
      ),
      media.width(480.px).height(800.px)(
        marginTop(10.px),
        fontSize(2.rem),
        paddingLeft(15.px),
        paddingRight(15.px),
        width.auto,
        height(40.px)
      ),
      media.width(1280.px).height(720.px)(
        marginTop(10.px)
      ),
      media.width(540.px).height(960.px)(
        fontSize(2.rem),
        height(40.px),
        width.auto,
        paddingLeft(15.px),
        paddingRight(15.px),
        marginTop(0.px)
      )
    )
    val modalTryAgainBtn = style(
      textAlign.center,
      &.hover(
        color(c"#cc3300")
      ),
      backgroundColor(c"#ff806c"),
      height(45.px),
      width(142.px),
      fontSize(30.px),
      padding(0.px),
      color(c"#fff"),
      fontFamily :=! "karla",
      float.right,
      marginTop(25.px),
      marginBottom(20.px)
    )
    val apiSubmitBtn = style(
      backgroundColor(c"#ff806c"),
      height(58.px),
      fontSize(20.px),
      fontFamily :=! "karla",
      marginTop(40.px),
      marginBottom(40.px)
    )
    val loginFormInputText = style(
      marginBottom(22.px),
      media.maxWidth(580.px)(
        marginBottom(15.px)
      ),
      media.width(1024.px)(
        marginBottom(14.px)
      ),
      media.width(1366.px)(
        marginBottom(14.px)
      ),
      media.width(800.px).height(600.px)(
        marginBottom(8.px)
      ),
      media.width(1280.px).height(600.px)(
        marginBottom(8.px)
      ),
      media.width(800.px).height(480.px)(
        marginBottom(8.px)
      ),
      media.width(800.px).height(400.px)(
        marginBottom(8.px)
      ), /*for 960x540 and 960x640*/
      media.width(960.px)(
        marginBottom(8.px)
      ),
      media.width(480.px).height(320.px)(
        marginBottom(8.px)
      ),
      media.width(480.px).height(360.px)(
        marginBottom(8.px)
      ),
      media.width(640.px).height(480.px)(
        marginBottom(16.px)
      ),
      media.width(240.px).height(320.px)(
        marginBottom(8.px)
      )
    )
    val apiDetailsContainer = style(
      height(50.px),
      width(95.%%), marginBottom(15.px),
      media.maxWidth(580.px)(
        height(30.px)
      ),
      media.width(1024.px)(
        height(42.px)
      ),
      media.width(1366.px)(
        height(42.px)
      ),
      media.width(1152.px)(
        height(48.px)
      ),
      media.width(800.px).height(400.px)(
        marginBottom(-13.px)
      ),
      media.width(480.px).height(320.px)(
        marginBottom(-9.px)
      ),
      media.width(480.px).height(360.px)(
        marginBottom(-9.px)
      ),
      media.width(240.px).height(320.px)(
        marginBottom(-9.px)
      )
    )
    val editApiDetailBtn = style(
      media.maxWidth(580.px)(
        fontSize(10.px)
      )
    )
    val HelpBlockWithErrorsContainer = style(
      height(10.px),
      fontSize(1.5.rem),
      media.maxWidth(580.px)(
        fontSize(1.rem),
        height(7.px)

      ),
      media.width(800.px).height(400.px)(
        fontSize(1.rem),
        height(2.px)
      ),
      media.width(480.px).height(320.px)(
        fontSize(1.rem),
        height(4.px),
        marginBottom(0.px).important,
        marginTop(0.px).important
      ),
      media.width(480.px).height(360.px)(
        fontSize(1.rem),
        height(4.px),
        marginBottom(0.px).important,
        marginTop(0.px).important
      ),
      media.width(240.px).height(320.px)(
        fontSize(1.rem),
        height(0.px),
        marginBottom(0.px).important,
        marginTop(0.px).important
      ),
      media.width(720.px).height(1280.px)(
        height(12.px),
        fontSize(1.7.rem)
      )
    )
  }

}
