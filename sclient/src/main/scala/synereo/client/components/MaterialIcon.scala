package synereo.client.components

import japgolly.scalajs.react.ReactNode
import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Created by Mandar on 3/16/2016.
  */
object MIcon {
  type MIcon = ReactNode

  def apply(name: String): MIcon = <.i(^.className := "material-icons")(name)

  def face = apply("face")

  def playCircleOutline = apply("play_circle_outline")

  def pause = apply("pause")

  def help = apply("live_help")

  def moreVert = apply("more_vert")

  def moreHoriz = apply("more_horiz")

  def chevronRight = apply("chevron_right")

  def chevronLeft = apply("chevron_left")

  def share = apply("share")

  def chatBubble = apply("chat_bubble_outline")

  def timeline = apply("timeline")

  def add = apply("add")

  def close = apply("close")

  def modeEdit = apply("mode_edit")

  def keyboardArrowDown = apply("keyboard_arrow_down")

  def helpOutline = apply("help_outline")
}
