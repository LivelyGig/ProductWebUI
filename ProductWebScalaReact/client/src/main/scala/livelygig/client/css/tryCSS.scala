package livelygig.client.css
import scalacss.Defaults._
import livelygig.client.components
import livelygig.client.components.CanIUse.Agent._
import livelygig.client.components.CanIUse.Support._
/**
  * Created by bhagyashree.b on 12/2/2015.
  */
object tryCSS {
  object Style extends  StyleSheet.Standalone {
    import dsl._
    "nav>li>a" - (
      media.maxWidth(1130.px) -
        padding(10.px, 10.px)
      )
  }
}