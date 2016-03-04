package livelygig.client.css
import livelygig.client.components
import livelygig.client.css.MessagesCSS.Style._
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
      height(62.px),
      borderBottom(2.px, solid, c"#67EAF2"),
      paddingLeft(0.%%),
      paddingRight(0.%%),
      marginBottom(0.px),
      media.maxWidth(820.px)-
        paddingTop(4.px),
      media.minWidth(821.px)-
        paddingTop(8.px)

    )
    val headerNavA = style (
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
      backgroundColor(c"#005256"),
      borderBottom(3.px, solid, c"#67EAF2"),
      color(c"#67EAF2"),
      fontSize(1.2.em),
      letterSpacing(0.5.px),
      &.hover(
        color(c"#67EAF2")
      )
    )
    val imgLogo = style (
      borderRadius(50.%%),
      width(40.px),
      height(40.px),
      media.maxWidth(820.px)-
      marginTop(3.px)
    )
    val logoContainer = style(
    )
    /* css*/
    val middelNaviContainer =style(
      marginTop(0.px),
      height(44.px),
      // paddingLeft(0.px),
      // paddingRight(0.%%),
      backgroundColor(c"#00767C"),
      // top(-2.px),

      width(100.%%),
      paddingTop(4.px)
    )
   /* val presetPickContainer= style(
      //minWidth(250.px)
      float.left
    )*/
    val presetPickBtn= style(
      backgroundColor(rgba(0,0,0,0)),
      color(c"#13EEDD"),
      fontSize(1.2.em),

      /*mine*/
      textAlign.left,
     media.maxWidth(1306.px).minWidth(993.px)-
     fontSize(15.px),
     media.maxWidth(992.px).minWidth(975.px)-
     fontSize(14.px)
    )
    val recommendMatches = style (
      media.maxWidth(974.px)-
        fontSize(12.px),
      media.minWidth(865.px).maxWidth(973.px)-
        fontSize(11.px)
/*
        paddingTop(8.px),
      textAlign.left,
      paddingLeft(0.px)*/
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
      paddingTop(6.px),
      display.inlineBlock
    )


    val displayInline=style(
      display.inline,
      marginLeft(5.px),
      fontSize(1.1.em),
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
      marginRight(10.px),
        paddingRight(10.px),
          paddingLeft(10.px)
      //      float.right
    )
    val rsltContainerIconBtn = style(
      //      backgroundColor(c"#ffa500"),
//      color(c"#de9000"),
      fontSize(20.px),
      // marginTop(10.px),
      // marginBottom(8.px),
     // marginRight(10.px),
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
//      backgroundColor(c"#ffa500"),
//      color.rgba(51,51,51,1),
      fontSize(16.px),
      marginTop(10.px),
      marginBottom(8.px),
      marginRight(15.px),
        color(orange),
    backgroundColor.transparent,
    border.none
    )
    val  floatBtn = style (
      float.right
    )
    val loginbtn = style (
      backgroundColor.transparent,
      paddingLeft(0.px),
      paddingRight(0.px),

      &.hover (
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

    val rsltbtn = style (
      marginLeft( 30.px),
      marginTop( 10.px),
      marginRight(10.px),
      float.right,
      backgroundColor (c"#ffa500"),
      color.rgba(51,51,51,1),
      fontSize(16.px)
    )
    val userpreferences = style (
      border.none,
      marginRight(15.px),
      marginTop(-8.px)
    )
    val showDisputeBtn=style(
      backgroundColor(c"#FFA500"),
      color(rgba(51,51,51,1)),
      fontSize(16.px),
      marginLeft(15.px),
      marginTop(10.px),
      marginRight(15.px)
    )
  }
}