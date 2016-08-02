package synereo.client.components

import japgolly.scalajs.react.ReactNode
import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Created by Mandar on 3/16/2016.
  */
object MIcon {
  type MIcon = ReactNode

  def apply(name: String): MIcon = <.i(^.className := "material-icons md-18")(name)

  def apply(name: String, size: String): MIcon = {
    <.i(^.className := s"material-icons md-$size")(name)
  }

  def face = apply("face")

  def playCircleOutline = apply("play_circle_outline")

  def pause = apply("pause")

  def insertComment = apply("insert_comment")

  def cancel = apply("cancel")

  def help = apply("help")

  def helpLive = apply("live_help")

  def helpOutline = apply("help_outline")

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

  def localParking = apply("local_parking")

  def speakerNotes = apply("speaker_notes")

  def people = apply("people")

  def settings = apply("settings")

  def casino = apply("casino")

  def starRate = apply("star_rate")

  def star = apply("star")

  def person = apply("person")

  def mailOutline = apply("mail outline")

  def accountCircle = apply("account circle")

  def accessTime = apply("access time")

  def search = apply("search")

  def sms = apply("sms")

  def done = apply("done")

}
