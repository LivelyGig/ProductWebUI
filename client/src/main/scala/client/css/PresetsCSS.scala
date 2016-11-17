package client.css

import scalacss.Defaults._

object PresetsCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val modalBtn = style(
      float.right,
      marginTop(-4.px),
      marginRight(-8.px),
      height(0.px)
    )

    val overlay = style(
      display.flex,
      color(c"#ff7700"),
      fontSize.smaller,
      left(20.px),
      top(9.px),
      position.relative,
      pointerEvents := ("none")
    )

  }

}
