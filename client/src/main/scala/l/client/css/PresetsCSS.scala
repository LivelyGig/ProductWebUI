package l.client.css

import scalacss.Defaults._

object PresetsCSS {
  object Style extends StyleSheet.Inline {
    import dsl._

    val modalBtn = style (
      float.right,
      marginTop(-4.px),
      marginRight(-8.px)
    )

  }

}
