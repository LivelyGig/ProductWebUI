package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar on 4/15/2016.
  */
object SignupCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val signUpFormContainerDiv = style(
      backgroundColor(c"#012e3d"),
      top(20.px)
    )
    val termsAndServicesContainer = style(
      opacity(0.65)
    )
    val howItWorks = style(
      margin(57.px, 0.px),
      color(c"#000"),
      fontSize(12.px)
    )
    val verificationMessageText = style(
      fontSize(12.px),
      marginTop(-15.px),
      marginBottom(25.px)
    )
    val accountValidationSuccessText = style(
      fontSize(2.4.em)
    )
    val verificationMessageContainer = style(
      marginBottom(30.px)
    )
    val signUpuserNameContainer = style(
      opacity(0.5)
    )
    val howAccountsWorkLink = style(
      color(c"#35b0e2")
    )
    val termsAndCondBtn = style(
      color(c"#000"),
      backgroundColor.transparent.important,
      border.none.important,
      paddingLeft(4.px),
      textDecoration := "underline",
      //      textDecorationLine.underline,
      fontSize(14.px),
      fontWeight.lighter,
      marginTop(-1.px),
      &.hover(
        textDecoration := "underline"
      )

    )
    val signUpBtn = style(
      backgroundColor(c"#FF806C"),
      height(58.px),
      fontSize(22.px),
      fontFamily :=! "karla",
      media.maxWidth(580.px)(
        textAlign.center,
        &.hover(
          color(c"#cc3300")
        ),
        backgroundColor(c"#ff806c"),
        height.auto.important,
        width.auto.important,
        fontSize(16.px),
        padding(5.px, 10.px),
        color(c"#fff"),
        fontFamily :=! "karla",
        float.right,
        marginTop(5.px)
      )
    )
    val verifyBtn = style(
      backgroundColor(c"#FF806C"),
      height(48.px),
      width(110.px),
      fontSize(22.px),
      fontFamily :=! "karla"
    )
    val signUpHeading = style(
      color(c"#000"),
      fontSize(52.px),
      fontFamily :=! "karla",
      opacity(0.65),
      textAlign.center,
      marginBottom(30.px),
      marginTop(20.px),
      media.maxWidth(580.px)(
        fontSize(20.px),
        marginBottom(10.px)
      )

    )
    val checkBoxTermsAndCond = style(
      width(20.px),
      opacity(0.5),
      margin(2.px)
    )
    val passwordTextInfo = style(
      marginTop(-15.px),
      marginLeft(-33.px),
      marginBottom(5.px),
      fontFamily :=! "karla",
      opacity(0.5),
      padding.`0`.important,
      fontSize(0.9.em)
    )
    val signUpModalStyle = style(
      marginTop(50.px),
      media.maxWidth(1367.px) -
        marginTop(1.px)
    )
    val inputStyleSignUpForm = style(
      fontSize(20.px),
      fontFamily :=! "karla",
      height(50.px),
      color(c"#000"),
      media.maxWidth(580.px)(
        fontSize(10.px),
        height.auto.important
      )
    )
    val SignupformFooter = style(
      color(c"#35b0e2"),
      //      textAlign.center,
      fontSize(1.1.em),
      marginLeft(45.px),
      marginTop(10.px)
    )
    val termsAndServicesBtn = style(
      backgroundColor.transparent,
      border.none.important,
      color(c"#35b0e2"),
      paddingLeft.`0`,
      fontSize(16.px),
      textDecorationLine.underline,
      &.hover(
        backgroundColor.transparent,
        color(c"#35b0e2")
      )
    )
  }

}
