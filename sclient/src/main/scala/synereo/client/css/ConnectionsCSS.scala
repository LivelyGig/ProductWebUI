package synereo.client.css

import scalacss.Defaults._

/**
  * Created by mandar.k on 5/18/2016.
  */
object ConnectionsCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val connectionli = style(
      marginRight(20.px),
      marginLeft(20.px),
      marginTop(15.px),
      marginBottom(15.px),
      media.maxWidth(991.px) -
        marginLeft(0.px)
    )
    val connectionsContainerMain = style(
      height(95.vh),
      overflowY.scroll.important,
      //      paddingTop(60.px),
      paddingBottom(60.px),
      overflowX.hidden
    )
    val inviteConnectionsBtn = style(
      fontSize(24.px),
      backgroundColor(c"#FFFFFF"),
      color(c"#000"),
      width(250.px),
      height(60.px),
      marginBottom(30.px),
      marginLeft.auto,
      marginRight.auto,
      display.block,
      media.maxWidth(786.px)(
        fontSize(14.px),
        width.auto.important,
        height.auto.important
      )
    )
    val onconnectionAvatarClick = style(
      height(94.px),
      width(94.px),
      marginTop(-7.px),
      borderWidth(5.px),
      borderStyle.solid,
      marginBottom(-5.px),
      borderRadius(47.px),
      borderColor.pink,
      cursor.pointer

    )
    val onconnectionAvatarHover = style(
      borderColor.pink,
      cursor.pointer
    )

    val connectionAvatarDiv = style(
      marginLeft(-5.px),
      padding(0.px)
    )
    val connectionAvatar = style(
      height(94.px),
      width(94.px),
      marginTop(-7.px),
      borderWidth(5.px),
      borderStyle.solid,
      marginBottom(-5.px),
      borderRadius(47.px),
      borderColor.transparent,
      &.hover(
        onconnectionAvatarHover
      )
    )
    val fullUserDescription = style(
      height(80.px),
      backgroundColor(c"#216b9a"),
      padding(0.px),
      borderBottomLeftRadius(40.px),
      borderTopLeftRadius(40.px),
      boxShadow := " 10.px 0.px 10.px rgba(33,107,154)",
      marginRight(0.px).important //   box-shadow: inset 10px 0px 10px rgba(33,107,154);
      //  box-shadow: 10px 0px 10px #216b9a;


    )
    val fullDescUL = style(
      // padding(0.%%, 7.%%),
      listStyle.:=(value = none),
      media.minWidth(1600.px).maxWidth(1930.px) -
        margin(10.px, 40.px)
    )
    val hidden = style(
      display.none
    )

    val connectionBody = style(
      verticalAlign.bottom
    )
    val connectionInfoTooltip = style(
      visibility.hidden,
      backgroundColor(rgba(0, 0, 0, 0.5)),
      color(c"#fff"),
      textAlign.left,
      padding(10.px),
      position.absolute,
      zIndex(1),
      margin(5.px, 0.px)
    )
    val connectionName = style(
      color.white,
      &.hover(
        unsafeChild(".infoTooltip")(
          visibility.visible
        )
      )
    )

    val connectionNumbers = style(
      color(c"#afa798"),
      position.relative,
      top(20.px)
    )
    val userActionIcons = style(
      backgroundColor.transparent.important,
      border.`0`.important,
      color(c"#ada3a3"),
      fontSize(20.px),
      &.hover(
        color.white
      ),
      &.active(
        color.white
      ),
      &.focus(color.white)
    )
    val userActionsMenu = style(
      borderRadius(7.px)
    )


    val connectionNameDiv = style(
      paddingLeft(35.px),
      paddingRight(0.px)

    )
    val connectfriendsBtn = style(
      //    position.fixed,
      position.absolute,
      backgroundColor(c"#f3816f"),
      // height(60.px),
      borderTopLeftRadius(30.px),
      borderBottomLeftRadius(30.px),
      border(0.px),
      right(0.px),
      marginTop(70.vh),
      marginRight(17.px)
      //    color.white,
      //    fontSize(20.px),
      //    padding(10.px),
      //    &.hover(
      //      color.white
      //    ),
      //    &.active(color.white),
      //    &.focus(color.white)
      , media.maxWidth(766.px) -
        marginTop(0.px)
    )
    val connectfriendsIcon = style(
      padding(15.px),
      borderRightWidth(2.px),
      borderRightColor.white,
      borderRightStyle.solid,
      color.white,
      fontSize.large
    )
    val connectfriendsIconText = style(
      color.white,
      fontSize.large,
      marginTop(-10.px),
      backgroundColor.transparent,
      &.hover(
        color.white
      ),
      &.active(color.white),
      &.focus(color.white)
    )

    val userPopularTagDiv = style(
      marginTop(10.px)
    )
    val userPopularTags = style(
      overflow.hidden,
      backgroundColor.transparent,
      borderWidth(1.px),
      borderStyle.solid,
      borderColor.white,
      color.white,
      borderRadius(20.px),
      textAlign.left,
      height(24.px),
      wordBreak.breakAll,
      margin(10.px)
      , &.hover(
        borderColor(c"#f3816f"),
        color(c"#f3816f")
      )

    )
    val userActionsMenuDiv = style(
      padding(0.px),
      margin(0.px, -10.px, 0.px, 0.px),
      float.right

    )
    val connectionContentRow = style(float.left)
    val connectionContentMainContainer = style(padding(0.px))
  }

}
