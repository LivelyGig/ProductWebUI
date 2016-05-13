package synereo.client.components

import synereo.client.css.DashboardCSS

import scalacss.Defaults._

object GlobalStyles extends StyleSheet.Inline {

  import dsl._

  style(unsafeRoot("body")(
    paddingTop(0.px),
    DashboardCSS.Style.bodyImg,
    fontFamily :=! "'karla', sans-serif",
    backgroundColor(c"#242D40"),
    height(100.vh),
    overflowY.hidden
  ))
  style(unsafeRoot(".modal-footer")(
    borderTop.`0`.important
  ))
  style(unsafeRoot(".modal-header")(
    borderBottom.none.important
  ))
  style(unsafeRoot("*:focus")(
    outline.none.important
  ))
  style(unsafeRoot("label")(
    fontWeight.normal
  ))

  // ToDo: temporary hack to hide visible gap underneath footer
  val bootstrapStyles = new BootstrapStyles
}
