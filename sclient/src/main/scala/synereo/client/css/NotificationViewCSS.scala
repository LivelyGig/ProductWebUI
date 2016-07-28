package synereo.client.css
import scalacss.Defaults._

/**
  * Created by mandar.k on 7/27/2016.
  */
object NotificationViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val notificationViewContainerMain = style(
      height(97.vh),
      overflowY.scroll.important,
      overflowX.hidden
    )

  }

}
