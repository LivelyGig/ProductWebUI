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
    val signUpHeading = style(
      color(c"#FFFFFF"),
      marginBottom(20.px),
      marginTop(90.px)
    )
    val inputStyleSignUpForm = style(
      backgroundColor(c"#022639"),
      borderRadius(5.px),
      border(1.px, solid, c"#3C4346"),
      height(40.px),
      padding(4.%%),
      fontSize(18.px),
      color.white,
      width(70.%%),
      margin.auto.important
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
    val signUpBtn = style(
      margin(30.px)
    )
  }


}
