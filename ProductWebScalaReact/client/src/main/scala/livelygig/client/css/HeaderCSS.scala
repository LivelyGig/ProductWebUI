package livelygig.client.css
import scalacss.Defaults._
/**
  * Created by shubham.k on 11/25/2015.
  */
object HeaderCSS {

  object Style extends StyleSheet.Inline {
    import dsl._

    val naviContainer = style (
      backgroundColor(c"#005256"),
      borderBottom(4.px, solid, c"#67EAF2"),
      paddingTop(8.px)
    )
    val headerNavA = style (
      borderBottom(3.px, solid, transparent),
      color(c"#fff"),
      fontSize(1.2.em),
      letterSpacing(0.5.px),
      &.hover(
        borderBottom(3.px, solid, c"#67EAF2"),
        backgroundColor(inherit),
        color(c"#67EAF2"),
        outline(none)
      ),
      &.focus(
      borderBottom(3.px, solid, c"#67EAF2"),
      backgroundColor(inherit),
      color(c"#67EAF2"),
      outline(none)
    )
    )
    val headerNavLi = style (
      backgroundColor(c"#005256")
    )
    val imgLogo = style (
      borderRadius(50.%%),
      width(40.px),
      height(40.px)
    )
    val logoContainer = style(
      paddingRight(10.px)
    )
  }

}
