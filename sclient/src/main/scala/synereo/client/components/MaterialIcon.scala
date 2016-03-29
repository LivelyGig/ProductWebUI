package synereo.client.components

import japgolly.scalajs.react.ReactNode
import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Created by Mandar on 3/16/2016.
  */
object MIcon {
  type MIcon = ReactNode
  def apply(name: String): MIcon = <.i(^.className := "material-icons")(name)
  def  face = apply("face")

}
