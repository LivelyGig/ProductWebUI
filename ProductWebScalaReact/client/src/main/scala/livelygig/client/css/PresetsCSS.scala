package livelygig.client.css

import scalacss.Defaults._

/**
  * Created by bhagyashree.b on 3/4/2016.
  */
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
