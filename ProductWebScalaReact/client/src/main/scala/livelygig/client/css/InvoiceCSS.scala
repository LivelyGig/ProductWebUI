package livelygig.client.css
import scalacss.Defaults._

/**
  * Created by bhagyashree.b on 1/13/2016.
  */
object InvoiceCSS {
  object Style extends StyleSheet.Inline {
    import dsl._

    val invoiceText = style (
      textAlign.center,
      borderStyle(none,none,none,solid)
    )
    val invoiceInputHeightWidth = style (
      width.auto
    )
  }
}
