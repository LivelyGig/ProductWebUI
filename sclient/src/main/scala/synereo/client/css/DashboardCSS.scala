package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar on 3/17/2016.
  */

object DashboardCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val profileActionContainer = style(
      /*      minHeight(60.px),
            minWidth(100.px)*/
    )
    /*   val navBackgroundImage = style(
         width(100.%%),
         height(70.px)
       )*/
    val dashboardContainerMain = style(
      height(100.vh),
      overflowY.scroll.important,
      backgroundImage := "url(\"./assets/images/globalBg.jpg\")"
    )
    val profileActionButton = style(
      backgroundColor(c"#1F85B5"),
      position.fixed,
      right(20.px)
    )
    val glanceViewName = style(
      display.inlineBlock
    )
    val userPost = style(
      marginTop(5.%%),
      width(100.%%),
      backgroundColor.white,
      borderRadius(5.px),
      paddingTop(5.px),
      paddingBottom(5.px)
    )
    val userPostRight = style(
      marginTop(5.%%),
      marginLeft(15.px).important,
      width(100.%%),
      backgroundColor.white,
      borderRadius(5.px),
      paddingTop(5.px),
      paddingBottom(5.px)
    )
    val userAvatar = style(
      width(30.px),
      height(30.px),
      borderRadius(50.%%),
      display.inlineBlock
    )
    val UserInput = style(
      border.none.important,
      fontWeight._700,
      width(90.%%),
      height(40.px),
      paddingLeft(6.px),
      &.focus(
        outline.none.important
      )
    )
    val userNameDescription = style(
      width(86.%%),
      display.inlineBlock,
      padding(5.px, 15.px)
    )
    val postActions = style(
      marginLeft(-10.px)
    )
    val sidebarBtn = style(
      float.left,
      backgroundColor.transparent,
      border.none, fontSize(22.px),
      marginTop(9.px),
      color.white
    )
    val cardHeading = style(
      fontWeight._600
    )

    val sidebarNavStyle = style(
      fontSize(17.px)
    )
    val postActionButton = style(
      border.none.important,
      marginTop(-30.px)
    )

  }

}
