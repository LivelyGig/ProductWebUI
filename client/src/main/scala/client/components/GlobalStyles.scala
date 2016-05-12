package client.components

import scalacss.Defaults._

object GlobalStyles extends StyleSheet.Inline {
  import dsl._

  style(unsafeRoot("body")(
    paddingTop(0.px)
  //backgroundColor(c"#005256")
  ))
  // ToDo: temporary hack to hide visible gap underneath footer
  val bootstrapStyles = new BootstrapStyles
}
