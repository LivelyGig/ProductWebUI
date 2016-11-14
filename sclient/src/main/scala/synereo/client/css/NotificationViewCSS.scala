package synereo.client.css

import scalacss.Defaults._

/**
  * Created by mandar.k on 7/27/2016.
  */
object NotificationViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    //    val notificationViewContainerMain = style(
    //      height(97.vh),
    //      overflowY.scroll.important,
    //      overflowX.hidden
    //    )
    val messageContent = style(
      whiteSpace.nowrap.important
    )
    val notificationCard = style(
      paddingTop(15.px),
      paddingBottom(15.px),
      maxWidth(1000.px),
      backgroundColor.white,
      borderRadius(25.px),
      fontSize(1.4.em),
      paddingLeft(6.%%)
    )
    val acceptBtn = style(
      margin(20.px)
    )
    val notificationCountHeading = style(
      color.white,
      textAlign.center,
      marginBottom(40.px)
    )
    val acceptRejectAllBtnContainer = style(
      maxWidth(1000.px),
      textAlign.right,
      marginBottom(40.px)
    )
    val acceptAllRejectAllBtns = style(
      marginLeft(10.px),
      marginRight(10.px)
    )
  }

}
