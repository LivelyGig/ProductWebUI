package livelygig.client.css

import scalacss.Defaults._
import livelygig.client.components
import livelygig.client.components.CanIUse.Agent._
import livelygig.client.components.CanIUse.Support._


/**
  * Created by bhagyashree.b on 12/2/2015.
  */
object tryCSS extends  StyleSheet.Standalone {
  import dsl._

//   "try"-(
//       height : components.CanIUse.calc (100 vh - 170)
//     )

  "nav.li.a" - (
   /* margin(12 px, auto),
    textAlign.left,
    cursor.pointer,*/

/*
    &.hover -
      cursor.zoomIn,
*/

    media.maxWidth(1130 px) -
      padding(10 px , 10 px)
/*

    &("span") -
      color.red
    )

  "h1".firstChild -
    fontWeight.bold

  for (i <- 0 to 3)
    s".indent-$i" -
      paddingLeft(i * 2.ex)
*/
    )
}
