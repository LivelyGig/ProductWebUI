package synereo.client.css

import scalacss.Defaults._

/**
  * Created by mandar.k on 5/18/2016.
  */
object ConnectionsCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val connectionsContainerMain = style(
      overflowY.scroll.important,
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
      display.block
    )

    val connectionAvatarContainer = style(
      padding(0.px),
      height(100.%%),
      borderStyle.solid,
      borderWidth(0.3.vw),
      borderColor.transparent,
      borderRadius(50.%%),
      &.hover(
        borderColor.pink,
        cursor.pointer
      )
    )

    val onconnectionAvatarDivClick = style(
      padding(0.px),
      height(100.%%),
      borderStyle.solid,
      borderWidth(0.3.vw),
      borderRadius(50.%%),
      borderColor.pink
    )


    val connectionAvatar = style(
      borderColor.transparent,
      height(5.vw),
      width(6.vw)
    )

    val fullUserDescription = style(
      padding(0.px),
      borderBottomLeftRadius(40.px),
      borderTopLeftRadius(40.px),
      boxShadow := " 10.px 0.px 10.px rgba(33,107,154)",
      marginRight(0.px).important,
      marginLeft(0.px).important,
      height(100.%%)
    )

    val fullDescContainer = style(
      listStyle.:=(value = none),
      padding(0.px)
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
      fontSize(1.vw),
      height(2.4.vw),
      marginTop(0.3.vw),
      marginRight(0.px),
      marginBottom(0.px),
      marginLeft(0.px),
      overflow.hidden,
      wordBreak.breakAll,
      &.hover(
        unsafeChild(".infoTooltip")(
          visibility.visible
        )
      )
    )

    val connectionNumbers = style(
      color(c"#afa798"),
      position.relative,
      fontSize(0.8.vw),
      marginRight(0.px),
      marginBottom(0.px),
      marginLeft(0.px)
    )

    val userActionIcons = style(
      backgroundColor.transparent.important,
      border.`0`.important,
      color(c"#ada3a3"),
      fontSize(1.vw),
      &.hover(
        color.white
      ),
      &.active(
        color.white
      ),
      &.focus(color.white)
    )

    val userActionDropdownMenu = style(
      borderRadius(7.px),
      width.auto,
      fontSize(1.vw)
    )

    val connectionNameHolder = style(
      paddingRight(0.px)
    )

    val connectfriendsBtn = style(
      position.absolute,
      backgroundColor(c"#f3816f"),
      borderTopLeftRadius(30.px),
      borderBottomLeftRadius(30.px),
      border(0.px),
      right(0.px),
      marginRight(17.px),
      media.maxWidth(766.px) -
        top(62.px) ,
      media.minWidth(766.px) -
        top(70.vh)
    )

    val connectfriendsIcon = style(
      padding(1.vw),
      borderRightWidth(2.px),
      borderRightColor.white,
      borderRightStyle.solid,
      color.white,
      fontSize(1.vw)
    )

    val connectfriendsIconText = style(
      color.white,
      fontSize(1.vw),
      backgroundColor.transparent,
      &.hover(
        color.white
      ),
      &.active(color.white,boxShadow:="none"),
      &.focus(color.white)
    )

    val userPopularTags = style(
      margin(0.px).important,
      fontSize(0.9.vw)
    )

    val userPopularTag = style(
      overflow.hidden,
      backgroundColor.transparent,
      borderWidth(1.px),
      borderStyle.solid,
      borderColor.white,
      color.white,
      borderRadius(1.vw),
      textAlign.left,
      height(1.5.vw),
      wordBreak.breakAll,
      margin(0.5.vw),
      paddingTop(0.1.vw),
      paddingBottom(0.1.vw),
      paddingRight(0.3.vw),
      paddingLeft(0.3.vw),
      whiteSpace.nowrap,
      textOverflow:="ellipsis",
      &.hover(
        borderColor(c"#f3816f"),
        color(c"#f3816f")
      )
    )

    val userActionsMenu = style(
      padding(0.px),
      float.right,
      media.maxWidth(766.px) -
        display.none
    )

    val connectionContainer = style(
      media.maxWidth(766.px) -
        marginTop(50.px)
    )

    val connectionContainerMiddle = style(
      padding(0.px)
    )

    val connectionNameContainer = style(
      height(4.5.vw),
      marginTop(0.5.vw),
      marginRight(0.px).important,
      marginBottom(0.px),
      marginLeft(-25.px).important,
      position.inherit,
      backgroundColor(c"#216b9a")
    )
  }
}
