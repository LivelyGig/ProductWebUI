package synereo.client.css

import scalacss.Defaults._

/**
  * Created by mandar.k on 5/18/2016.
  */
object ConnectionsCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

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
    val connectionAvatar = style(
      width(70.px),
      height(70.px),
      maxWidth(70.px),
      maxHeight(70.px),
      margin(15.px)
    )
    val fullUserDescription = style(
      display.inlineBlock,
      marginRight(10.px),
      marginLeft(0.px),
      marginTop(0.px),
      marginBottom(6.px),
      media.minWidth(1500.px) -
        width(450.px),
      media.minWidth(1024.px).maxWidth(1499.px) -
        width(350.px),
      media.minWidth(769.px).maxWidth(1023.px) -
        width(314.px),
      media.minWidth(650.px).maxWidth(768.px) -
        width(252.px),
      media.maxWidth(650.px) -
        width(100.%%)
    )
    val fullDescUL = style(
      padding(0.%%, 7.%%),
      media.minWidth(1600.px).maxWidth(1930.px) -
        margin(10.px, 40.px)
    )
    val hidden = style(
      display.none
    )

    val connectionBody = style (
      verticalAlign.bottom
    )

  }

}
