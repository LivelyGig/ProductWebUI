package livelygig.client.css

import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry
/**
  * Created by shubham.k on 11/24/2015.
  */
object AppCSS {
  def load () {
    GlobalRegistry.register(
      HeaderCSS.Style
    )
//    GlobalRegistry.onRegistration(_.addToDocument()(s))

  }
}
