package livelygig.client.css

import scalacss.Defaults._

/**
  * Created by bhagyashree.b on 12/8/2015.
  */
object CreateNewAgentCSS {
  object Style extends StyleSheet.Inline {
    import dsl._

    val borderColorStyle = style (
       height(400.px),
       width(600.px),
       border(1.px , solid, c"#43E5B5")
    )
    val mainContainerDiv1 = style(
      backgroundColor(red),
      marginTop(75.px),
      paddingLeft(7.%%),
      paddingRight(7.%%),
      overflowX.auto,
      height(200.px)
    )
  }
}
