package livelygig.client.css
import scalacss.Defaults._

/**
  * Created by bhagyashree.b on 11/30/2015.
  */
object FooterCSS {

  object Style extends StyleSheet.Inline {
    import dsl._

    val footerContainer = style (
      borderTop(3.px, solid, c"#67EAF2"),
      backgroundColor(c"#005256"),
      minHeight(45.px),
      paddingLeft(7.%%),
      paddingRight(7.%%)
    )
    val footerNavA = style (
    /*  borderBottom(3.px, solid, transparent),*/
      color(c"#fff"),
      fontSize(1.em),
      letterSpacing(0.5.px),
      marginTop(-3.px),
      &.hover(
        height(42.px),
        backgroundColor(c"#005256"),
        color(c"#67EAF2"),
        outline(none)
      ),
      &.focus(
        height(42.px),
        backgroundColor(c"#005256"),
        color(c"#67EAF2"),
        outline(none)
      )
    )
    val footerNavLi = style (
      backgroundColor(c"#005256"),
      height(42.px),
      &.hover(
        height(42.px),
        backgroundColor(c"#005256"),
        color(c"#67EAF2"),
        outline(none)
      ),
      &.focus(
        height(42.px),
        backgroundColor(c"#005256"),
        color(c"#67EAF2"),
        outline(none)
      )
    )
    val footRight = style(
      float.right
    )
    val navbar=style(
      backgroundColor(c"#005256"),
      &.hover(
      backgroundColor(c"#005256")
      )
    )
    val displayInline=style(
      paddingLeft(4.%%),
      display.inline,
      fontSize(1.2.em),
      color(c"#fff")
    )
    val footGlyphContainer=style(
      paddingTop(8.px)
    )
    val displayInlineGlyph=style(
    paddingLeft(2.%%),
    fontSize(1.em),
    color(c"#fff")
    )
  }
}
