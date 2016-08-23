package client.css
import scalacss.Defaults._

object FooterCSS {
  object Style extends StyleSheet.Inline {
    import dsl._
    val footerContainer = style(
      // borderTop(2.px, solid, c"#67EAF2"),
      backgroundColor(transparent),
      minHeight(52.px),
      media.maxWidth(375.px)-
      display.none

    // paddingLeft(7.%%),
    // paddingRight(7.%%)
    )
    val footerNavA = style(
      color(c"#FFF"),
      fontSize(1.em),
      letterSpacing(0.5.px),
      marginTop(3.px),
      &.hover(
        height(42.px),
        backgroundColor(transparent),
        color(transparent),
        outline(none)
      ),
      &.focus(
        height(42.px),
        backgroundColor(transparent),
        color(transparent),
        outline(none)
      )
    )
    val footerNavLi = style(
      backgroundColor(transparent),
      height(42.px),
      &.hover(
        height(42.px),
        backgroundColor(transparent),
        //color(c"#67EAF2"),
        outline(none)
      ),
      &.focus(
        height(42.px),
        backgroundColor(transparent),
        // color(c"#67EAF2"),
        outline(none)
      )
    )
    val footRight = style(
      float.right
    )
    val navbar = style(
      backgroundColor(transparent),
      &.hover(
        backgroundColor(transparent)
      )
    )
    val displayInline = style(
      paddingRight(4.%%),
      display.inline,
      fontSize(1.2.em),
      color(c"#FFF")
    )
    val footPaddingLeft = style(
      paddingLeft(7.px)
    )
    val footGlyphContainer = style(
      paddingTop(14.px)
    )
    val displayInlineGlyph = style(
      paddingLeft(2.%%),
      fontSize(1.em),
      color(c"#FFF"),
      media.maxWidth(1300.px) -
        fontSize(12.px)
    )
    val legalModalBtn = style(
      border.none,
      &.hover(
        backgroundColor.transparent
      ),
      &.active(
        backgroundColor.transparent
      )
    )

  }
}