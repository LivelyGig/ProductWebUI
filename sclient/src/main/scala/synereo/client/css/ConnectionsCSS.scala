package synereo.client.css

import scalacss.Defaults._

/**
  * Created by mandar.k on 5/18/2016.
  */
object ConnectionsCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val postContainer=style(
      margin(10.px),
      media.maxWidth(359.px)(
        marginLeft(30.px)
      ),
      media.width(360.px)(
        marginLeft(15.px)
      )
    )

    //   val connectionsContainerMain = style(
    //      overflowY.scroll.important,
    //      paddingBottom(60.px),
    //      overflowX.hidden
    //    )

    //    val inviteConnectionsBtn = style(
    //      fontSize(24.px),
    //      backgroundColor(c"#FFFFFF"),
    //      color(c"#000"),
    //      width(250.px),
    //      height(60.px),
    //      marginBottom(30.px),
    //      marginLeft.auto,
    //      marginRight.auto,
    //      display.block
    //    )

    val connectionAvatarContainer = style(
      padding(0.px),
      height(100.%%),
      marginTop(-5.px),
      marginRight(0.px),
      media.maxWidth(767.px)(
        float.left
      )
    )
    val connectionAvatarContainerOnLeftSwap=style(
      media.maxWidth(768.px)(
        margin(10.px)
      )
    )

    val connectionAvatarSelected = style(
      height(80.px),
      width(80.px),
      borderStyle.solid,
      borderWidth(5.px),
      borderColor.pink,
      borderRadius(50.%%),
      marginBottom(-5.px),
      marginLeft(-5.px),
      media.minWidth(769.px).maxWidth(1056.px)(
        height(70.px),
        width(70.px)
      ),
      &.hover(
        cursor.pointer
      )
    )

    val connectionAvatar = style(
      height(80.px),
      width(80.px),
      borderStyle.solid,
      borderWidth(5.px),
      borderColor.transparent,
      borderRadius(50.%%),
      marginBottom(-5.px),
      marginLeft(-5.px),
      media.minWidth(768.px)(
        &.hover(
          borderColor.pink,
          cursor.pointer
        )
      ),
      media.minWidth(769.px).maxWidth(1056.px)(
        height(70.px),
        width(70.px)
      )
    )

    val connectionAvatarOnLeftSwap=style(
      media.maxWidth(768.px)(
        height(70.px),
        width(70.px),
        marginTop(-10.px)
      )
    )

    val fullUserDescription = style(
      padding(0.px),
      borderBottomLeftRadius(40.px),
      borderTopLeftRadius(40.px),
      backgroundColor(c"#216b9a"),
      boxShadow := " 10.px 0.px 10.px rgba(33,107,154)",
      marginRight(0.px).important,
      marginLeft(0.px).important,
      height(100.%%),
      listStyle.:=("none"),
      width(80.%%),
      media.maxWidth(768.px)(
        width(100.%%),
        height(70.px),
        marginTop(15.px),
        marginBottom(15.px)
      )
    )

    val fullUserDescriptionOnLeftSwap=style(
      backgroundColor(c"#004469"),
      media.maxWidth(420.px)(
        marginLeft(-2.%%)
      ),
      media.minWidth(421.px).maxWidth(768.px)(
        height(90.px),
        marginLeft(-5.%%)
      ),
      media.width(375.px)(
        marginLeft(-10.px),
        marginRight(-5.px)
      )
    )

    val fullDescContainer = style(
      listStyle.:=(value = none),
      padding(0.px)
    )

    val hidden = style(
      display.none
    )

    //    val connectionBody = style(
    //      verticalAlign.bottom
    //    )

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
      fontSize(1.7.rem),
      fontFamily :=! "karla",
      /* maxHeight(3.5.rem),*/
      minHeight(2.5.rem),
      marginRight(0.px),
      marginBottom(0.px),
      marginLeft(0.px),
      overflow.hidden,
      wordBreak.breakAll,
      lineHeight(125.%%),
      textOverflow:="ellipsis",
      &.hover(
        unsafeChild(".infoTooltip")(
          visibility.visible
        )
      ),
      media.minWidth(768.px).maxWidth(1260.px)(
        fontSize(1.2.rem),
        maxHeight(3.rem)
      )
    )

    val connectionNumbers = style(
      color(c"#afa798"),
      position.relative,
      fontFamily :=! "karla",
      fontSize(1.2.rem),
      marginRight(0.px),
      marginBottom(0.px),
      marginLeft(0.px),
      marginTop(4.px),
      media.minWidth(881.px).maxWidth(1015.px)(
        fontSize(1.rem)
      ),
      media.minWidth(769.px).maxWidth(880.px)(
        fontSize(0.8.rem)
      )
    )

    val userActionIcons = style(
      backgroundColor.transparent.important,
      border.`0`.important,
      color(c"#ada3a3"),
      fontSize(1.7.rem),
      &.hover(
        color.white
      ),
      &.active(
        color.white
      ),
      &.focus(color.white),
      media.maxWidth(767.px)(
        display.none
      )
    )

    val mediaUserActionIcons=style(
      backgroundColor.transparent.important,
      border.`0`.important,
      color.white,
      fontSize(1.5.em),
      &.hover(
        color.white
      ),
      &.active(
        color.white
      ),
      &.focus(color.white),
      media.minWidth(768.px)(
        display.none
      )
    )

    val userActionDropdownMenu = style(
      borderRadius(7.px),
      width.auto
    )

    // val mediauserActionDropdownMenu=style(
    //      marginLeft(-8.%%),
    //      borderRadius(0.px),
    //      display.block,
    //      width(104.%%)
    //    )

    val connectionNameHolder = style(
      paddingRight(0.px),
      paddingLeft(0.px).important,
      width(80.%%),
      media.maxWidth(767.px)(
        width(40.%%),
        marginTop(-20.px),
        float.left
      )
    )

    val connectfriendsBtn = style(
      position.absolute,
      backgroundColor(c"#f3816f"),
      borderTopLeftRadius(30.px),
      borderBottomLeftRadius(30.px),
      border(0.px),
      right(0.px),
      marginRight(17.px),
      media.maxWidth(767.px) (
        top(62.px) ,
        borderRadius(0.px ),
        marginRight(0.px)
      ),
      media.minWidth(769.px) -
        top(70.%%)
    )

    val connectfriendsIcon = style(
      padding(15.px),
      borderRightWidth(2.px),
      borderRightColor.white,
      borderRightStyle.solid,
      color.white,
      fontSize(2.rem),
      media.maxWidth(767.px)(
        display.none
      )
    )

    val connectfriendsIconText = style(
      color.white,
      fontSize(2.rem),
      fontFamily :=! "karla",
      backgroundColor.transparent,
      &.hover(
        color.white
      ),
      &.active(color.white,boxShadow:="none"),
      &.focus(color.white),
      media.minWidth(768.px)(
        marginTop(-10.px)
      )
    )

    val userPopularTags = style(
      margin(0.px).important,
      fontSize(1.2.rem),
      fontFamily :=! "karla",
      paddingTop(12.px)
    )

    val userPopularTagsOnLeftSwap=style(
      media.maxWidth(768.px)(
        position.absolute,
        top(0.px),
        left(90.px),
        paddingLeft(25.px),
        paddingRight(15.px),
        paddingTop(15.px)
      ),
      media.width(375.px )(
        left(100.px)
      ),
      media.width(360.px)(
        paddingLeft(10.px),
        paddingRight(44.px)
      )
    )

    val userPopularTag = style(
      overflow.hidden,
      backgroundColor.transparent,
      borderWidth(1.px),
      borderStyle.solid,
      borderColor.white,
      color.white,
      borderRadius(15.px),
      textAlign.left,
      wordBreak.breakAll,
      margin(5.px),
      paddingTop(4.px),
      paddingBottom(4.px),
      whiteSpace.nowrap,
      textOverflow:="ellipsis",
      width(25.%%),
      &.hover(
        borderColor(c"#f3816f"),
        color(c"#f3816f")
      ),
      media.minWidth(331.px).maxWidth(767.px)(
        width(45.%%)
      ),
      media.maxWidth(330.px)(
        width(40.%%)
      ),
      media.width(360.px)(
        width(40.%%)
      )
    )

    val userActionsMenu = style(
      padding(0.px),
      float.right,
      media.minWidth(360.px).maxWidth(767.px)(
        paddingRight(50.px)
      ),
      media.maxWidth(359.px)(
        paddingRight(25.px)
      )
    )

    //    val mediaUserActionsMenu=style(
    //      padding(0.px),
    //      float.right
    //    )

    val connectionContainer = style(
      media.maxWidth(767.px) -
        marginTop(50.px)
    )

    val connectionContainerMiddle = style(
      padding(0.px)
    )

    val connectionNameContainer = style(
      height(70.px),
      marginLeft(-20.px),
      width(70.%%),
      media.maxWidth(767.px)(
        width(100.%%)
      ),
      media.minWidth(768.px).maxWidth(1056.px)(
        height(54.px)
      )
    )

    //    val connectionNameContainerOnSwap=style(
    //      display.none
    //    )

    val swapRightIconVisible=style(
      media.maxWidth(768.px)(
        display.block,
        backgroundColor.transparent,
        color.white,
        fontSize(1.5.em),
        float.left,
        marginTop(26.px)
      )
    )
    //    val swapRightIcon=style(
    //      display.none
    //    )
    val receipentsTextContainer = style(
      width(491.px),
      maxWidth(100.%%),
      minWidth(100.%%)
    )

    val hideDiv=style{
      display.none
    }

  }
}
