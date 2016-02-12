package livelygig.client.css
import scalacss.Defaults._
/**
  * Created by bhagyashree.b on 1/11/2016.
  */
object TalentCSS {
  object Style extends StyleSheet.Inline {
    import dsl._
    val slctProjectInputWidth = style(
      width(100.px),
      fontSize(12.px)
    )
  }
}
