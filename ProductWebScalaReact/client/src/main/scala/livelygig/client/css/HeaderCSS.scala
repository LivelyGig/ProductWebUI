package livelygig.client.css
import livelygig.client.components
import scalacss.Defaults._
import scalacss.LengthUnit.px
/**
  * Created by shubham.k on 11/25/2015.
  */
object HeaderCSS {
  object Style extends StyleSheet.Inline {
    import dsl._

    val naviContainer = style (
      backgroundColor(c"#005256"),
      minHeight(63.px),
      borderBottom(2.px, solid, c"#67EAF2"),
      paddingTop(8.px),
      paddingLeft(7.%%),
      paddingRight(7.%%),
      marginBottom(0.px)
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
      ),
      /*media queries*/
      media.maxWidth(1130.px) -(
        fontSize(1.em),
        padding(10.px , 10.px)
        )
    )
    val nav = style(
      addClassName("nav > li > a"),
      padding(10.px , 10.px)
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
    )
    /* css*/
    val middelNaviContainer =style(
      marginTop(0.px),
      height(62.px),
      paddingLeft(7.%%),
      paddingRight(7.%%),
      backgroundColor(c"#00767C"),

      top(-2.px),

      width(100.%%),
      paddingTop(4.px)
    )
    val projectCreateBtn= style(
      backgroundColor(rgba(0,0,0,0.38)),
      color(c"#13EEDD"),
      fontSize(1.2.em),
      marginTop(6.px)
    )
    val dropdownMenuWidth=style(
      width(100.%%)
    )
    val createNewProjectBtn=style(
      backgroundColor(c"#FFA500"),
      color(rgba(51,51,51,1)),
      fontSize(16.px),
      marginLeft(30.px),
      marginTop(6.px)
    )
    val LoginInMenuItem=style(
      float.right,
      paddingTop(6.px)
    )
    val displayInline=style(
      paddingLeft(10.px),
      display.inline,
      fontSize(1.2.em),
      color(c"#fff"),
      media.maxWidth(1130 px) -
        fontSize(1.em)
    )
    val ContainerHeight=style(
      height(800.px),
      marginTop(75.px)
    )
    val SignUpBtn = style(
      color(white),
        backgroundColor(transparent),
      border.none,
      &.hover(
        backgroundColor(transparent),
        border.none,
        color(white)
      )
    )
    val rsltContainerBtn = style(
      backgroundColor(c"#ffa500"),
      color.rgba(51,51,51,1),
      fontSize(16.px),
    /*  marginLeft(8.px),*/
      marginTop(10.px),
      marginBottom(8.px),
      marginRight(10.px)
//      float.right
    )
    val  floatBtn = style (
      float.right
    )
    val rsltbtn = style (
       marginLeft( 30.px),
       marginTop( 10.px),
       marginRight(10.px),
       float.right,
      backgroundColor (c"#ffa500"),
    color.rgba(51,51,51,1),
    fontSize(16.px)
    )
  }
}