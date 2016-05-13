package client.css

import client.components
import client.css.MessagesCSS.Style._
import scalacss.Defaults._
import scalacss.LengthUnit.px
import scala.language.postfixOps


object HeaderCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val naviContainer = style(
      backgroundColor(c"#005256"),
      minHeight(52.px),
      borderBottom(2.px, solid, c"#67EAF2"),
      paddingLeft(0.%%),
      paddingRight(0.%%),
      marginBottom(0.px),
      media.maxWidth(820.px) -
        paddingTop(2.px),
      media.minWidth(821.px) -
        paddingTop(8.px)
    )

    val headerNavA = style(
      /*borderBottom(3.px, solid, transparent),*/
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
      ),
      /*media queries*/
      media.maxWidth(1130.px).minWidth(820.px) -(
        fontSize(1.2.em),
        padding(10.px, 10.px)
        ),
      media.maxWidth(819.px) -(
        fontSize(1.em),
        padding(10.px, 10.px)
        )
    )

    val nav = style(
      addClassName("nav > li > a"),
      padding(10.px, 10.px)
    )
    val headerNavLi = style(
      backgroundColor(c"#005256"),
      borderBottom(3.px, solid, c"#67EAF2"),
      color(c"#67EAF2"),
      letterSpacing(0.5.px),
      &.hover(
        color(c"#67EAF2")
      ),
      media.maxWidth(819.px) -(
        fontSize(1.em),
        padding(10.px, 10.px)
        ),
      media.minWidth(820.px) -(
        fontSize(1.2.em),
        padding(10.px, 10.px)
        )

    )
    val imgLogo = style(
      borderRadius(50.%%),
      width(40.px),
      height(40.px),
      media.minWidth(821.px) -
        marginTop(-3.px),
      media.maxWidth(820.px) -
        marginTop(0.px)
    )

    val logoImage = style (
      media.maxWidth(375.px) -
        display.none,
        media.minWidth(376.px) -
      display.initial
    )

    /* css*/
    val middelNaviContainer = style(
      //      marginTop(0.px),
      //      height(44.px),
      //      width(100.%%),
      //      paddingTop(4.px),
    )

    val connectionsmiddelNaviContainer = style(
      marginTop(0.px),
      height(44.px),
      width(100.%%),
      paddingTop(4.px),
      //      addClassName("middelNaviContainer"),
      backgroundColor(rgb(61, 56, 90))
    )

    val messagesmiddelNaviContainer = style(
      marginTop(0.px),
      height(44.px),
      width(100.%%),
      paddingTop(4.px),
      backgroundColor(rgb(90, 56, 83))
    )

    val jobsmiddelNaviContainer = style(
      marginTop(0.px),
      height(44.px),
      width(100.%%),
      paddingTop(4.px),
      backgroundColor(rgb(56, 56, 90))
    )
    val offeringsmiddelNaviContainer = style(
      marginTop(0.px),
      height(44.px),
      width(100.%%),
      paddingTop(4.px),
      backgroundColor(rgb(48, 94, 125))
    )
    val profilessmiddelNaviContainer = style(
      marginTop(0.px),
      height(44.px),
      width(100.%%),
      paddingTop(4.px),
      backgroundColor(rgb(49, 49, 49))
    )
    val contractssmiddelNaviContainer = style(
      marginTop(0.px),
      height(44.px),
      width(100.%%),
      paddingTop(4.px),
      backgroundColor(rgb(88, 48, 48))
    )

    val presetPickBtn = style(
      backgroundColor(rgba(0, 0, 0, 0)),
      color(c"#13EEDD"),
      fontSize(1.em),

      /*mine*/
      textAlign.left,
      media.maxWidth(1306.px).minWidth(993.px) -
        fontSize(15.px),
      media.maxWidth(992.px).minWidth(975.px) -
        fontSize(14.px)
    )
    val recommendMatches = style(
      media.maxWidth(974.px) -
        fontSize(12.px),
      media.minWidth(865.px).maxWidth(973.px) -
        fontSize(11.px)
    )
    val dropdownMenuWidth = style(
      width(100.%%)
    )
    val createNewProjectBtn = style(
      backgroundColor(c"#FFA500"),
      color(rgba(51, 51, 51, 1)),
      fontSize(16.px),
      marginLeft(30.px),
      marginTop(6.px)
    )
    val LoginInMenuItem = style(
      float.right,
      paddingTop(3.px),
      display.inlineBlock
    )

    val marginTopLoggedIn = style(
      media.minWidth(821.px) -
        marginTop(-6.px)
    )

    val displayInline = style(
      display.inline,
      marginLeft(5.px),
      fontSize(1.1.em),
      color(c"#fff"),
      media.maxWidth(1130 px) -
        fontSize(1.em)
    )

    val SignUpBtn = style(
      color(white),
      backgroundColor(transparent),
      border.none,
      &.hover(
        backgroundColor(transparent),
        border.none,
        color(white)
      ),
      media.maxWidth(443.px) -(
        fontSize(12.px),
      paddingTop(10.px)
        )

    )
    val rsltContainerBtn = style(
      backgroundColor(c"#ffa500"),
      color.rgba(51, 51, 51, 1),
      fontSize(16.px),
      marginTop(10.px),
      marginBottom(8.px),
      marginRight(10.px),
      paddingRight(10.px),
      paddingLeft(10.px)
    )

    val rsltContainerIconBtn = style(
      fontSize(20.px),
      color(orange),
      backgroundColor.transparent,
      border.none,
      float.right,
      &.hover(
        backgroundColor.transparent,
        color(orange),
        border.none
      )
    )
    val searchContainerBtn = style(
      fontSize(16.px),
      marginTop(10.px),
      marginBottom(8.px),
      marginRight(15.px),
      color(orange),
      backgroundColor.transparent,
      border.none
    )

    val loginbtn = style(
      backgroundColor.transparent,
      paddingLeft(0.px),
      paddingRight(0.px),
      media.minWidth(821.px) -
        paddingTop(4.px),
      media.maxWidth(820.px) -
        paddingTop(10.px),

      &.hover(
        backgroundColor.transparent,
        paddingLeft(0.px),
        paddingRight(0.px)
      ),
      &.active(
        backgroundColor.transparent,
        paddingLeft(0.px),
        paddingRight(0.px)
      )
    )

    val userpreferences = style(
      border.none,
      marginRight(15.px),
      marginTop(-8.px)
    )

    val navbarToggle = style(
      margin(0.px).important
    )
    val ModalNameDropDown = style {
      media.maxWidth(768.px) -
        display.block
      media.minWidth(769.px) -
        display.none
    }

    val ModalName = style {
      media.maxWidth(768.px) -
        display.none
      media.minWidth(769.px) -
        display.block
    }
  }

}