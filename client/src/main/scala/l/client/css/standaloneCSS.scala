package l.client.css

import scalacss.Defaults._

/**
  * Created by bhagyashree.b on 3/15/2016.
  */
object standaloneCSS {
object Style extends StyleSheet.Standalone{
  import dsl._

  "div.ul.li" -(
    backgroundColor(red)
    )


}
}
