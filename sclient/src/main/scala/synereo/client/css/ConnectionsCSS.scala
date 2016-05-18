package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar on 5/18/2016.
  */
object ConnectionsCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val connectionsContainerMain = style(
      height(98.vh),
      overflowY.scroll.important,
      paddingTop(60.px),
      paddingBottom(60.px)
    )
    val connectionAvatar = style(
      width(80.px),
      margin(15.px)
    )
    val fullUserDescription = style(
      width(450.px),
      margin(10.px, 40.px),
      display.inlineBlock
    )
    val fullDescUL = style (
      padding(0.%%, 7.%%)
    )


  }

}
