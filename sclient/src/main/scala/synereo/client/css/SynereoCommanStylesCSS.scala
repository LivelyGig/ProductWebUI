package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar on 3/23/2016.
  */
object SynereoCommanStylesCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val bottomBorderOnePx = style(
      borderBottom(1.px, solid, c"#B6BCCC")
    )
    val paddingRightZero = style(
      paddingRight(0.px).important
    )
    val paddingLeftZero = style(
      paddingLeft(0.px).important
    )
    val synereoBlueText = style(
      color(c"#2DBAF1")
    )
  }

}
