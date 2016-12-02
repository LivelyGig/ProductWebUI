package synereo.client.css

import scala.language.postfixOps
import scalacss.Defaults._

/**
  * Created by a4tech on 6/3/2016.
  */
object NewMessageCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val textAreaNewMessage = style(
      width(100.%%),
      marginTop(16.px),
      marginBottom(10.px),
      border.none.important,
      resize.none.important,
      fontSize(1.7.rem)
    )
    val newMessageActionsContainerDiv = style(
      //      marginTop(30.px),
      marginBottom(30.px)
    )
    val userImage = style(
      width(100.%%),
      marginTop(17.px)
    )
    val userImgDiv= style(

      media.minWidth(921.px)(
        marginLeft(6.px),
        width(80.px)
      ),
      media.minWidth(581.px).maxWidth(921.px)(
        marginLeft(-16.px),
        width(70.px)
      ),
      media.maxWidth(581.px)(
        width(70.px),
        marginLeft(-28.px)
      )
    )
    val createPostBtn = style(
      backgroundColor(c"#ff806c"),
      color(c"#FFFFFF"),
      fontSize(22.px),
      fontFamily :=! "karla",
      //      marginRight(10.px),
      &.hover(
        backgroundColor(c"#ff806c").important,
        color(c"#FFFFFF")
      ),
      &.focus(
        backgroundColor(c"#ff806c").important,
        color(c"#FFFFFF")
      )
    )
    val changePersonaBtn = style(
      backgroundColor.transparent.important,
      fontSize(2.rem),
      fontWeight.normal,
      paddingLeft.`0`.important,
      &.hover(
        backgroundColor.transparent.important
      ),
      &.active(
        boxShadow:="none"
      ),
      &.focus(
        backgroundColor.transparent.important
      ),
      media.maxWidth(1024.px)(
        fontSize(1.5.rem)
      )
      //      ,
      //      media.maxWidth(1024.px)(
      //        fontSize(15.px)
      //      )
    )
    val PersonaContainerDiv = style(
      marginTop(20.px),
      marginBottom(20.px),
      marginLeft(35.px).important,
      marginRight(0.px).important,
      media.maxWidth(580.px)(
        marginTop(-30.px)
      )
    )
    val newMessageCancelBtn = style(
      backgroundColor.transparent.important,
      color(c"#242D40"),
      border.none.important,
      fontSize(22.px),
      fontWeight.normal,
      fontFamily :=! "karla",
      marginRight(10.px),
      marginLeft(-27.px),
      &.hover(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important,
        unsafeChild(".infoTooltip")(
          visibility.visible
        )
      ),
      &.focus(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important
      )
    )
    val newMessageAttachBtn = style(
      backgroundColor.transparent.important,
      color(c"#242D40"),
      border.none.important,
      fontSize(22.px),
      fontWeight.normal,
      fontFamily :=! "karla",
      marginRight(10.px),
      marginLeft(0.px),
      &.hover(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important,
        unsafeChild(".infoTooltip")(
          visibility.visible
        )
      ),
      &.focus(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important
      )
    )
    val postingShortHandBtn = style(
      backgroundColor.transparent.important,
      color(c"#929497"),
      border.none.important,
      fontSize(14.px),
      fontFamily :=! "karla",
      marginRight(10.px),
      marginLeft(10.px),
      &.hover(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important,
        unsafeChild(".infoTooltip")(
          visibility.visible
        )
      ),
      &.focus(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important
      )
    )
    val userNameOnDilogue = style(
      fontSize(1.7.rem),
      media.maxWidth(1024.px)(
        fontSize(1.2.rem)
      )
    )
    val createPostTagBtn = style(
      margin(5.px),
      fontFamily :=! "karla",
      fontWeight.normal,
      fontSize(12.px),
      //      textTransform.capitalize,
      backgroundColor.transparent.important,
      height(38.px),
      color(c"#000"),
      opacity(0.8),
      border(1.px, solid, c"#78D3F5"),
      borderRadius(20.px),
      minWidth(80.px),
      //      padding.`0`.important,
      &.hover(
        color(c"#000"),
        border(1.px, solid, c"#78D3F5"),
        backgroundColor.transparent.important
      )
    )
    //spinner css//
    val spinner = style(
      width(100.%%)
    )
    val spinnerinput = style(
      textAlign.right
    )
    val inputgroupbtnVertical = style(
      position.relative,
      whiteSpace.nowrap,
      width(1 %%),
      verticalAlign.middle,
      display.tableCell
    )
    val spinnerBtn1 = style(
      display.block,
      float.none,
      width(100 %%),
      maxWidth(100 %%),
      padding(8.px),
      marginLeft(-1.px),
      position.relative,
      borderTopRightRadius(4.px),
      borderBottomRightRadius(4.px),
      borderBottomLeftRadius(0.px),
      borderTopLeftRadius(0.px)
    )
    val spinnerBtn2 = style(
      display.block,
      float.none,
      width(100 %%),
      maxWidth(100 %%),
      padding(8.px),
      marginLeft(-1.px),
      position.relative,
      borderBottomRightRadius(4.px),
      borderTopRightRadius(0.px),
      borderBottomLeftRadius(0.px),
      borderTopLeftRadius(0.px),
      marginTop(-2.px)
    )
    val spinnerCaretIcon = style(
      position.absolute,
      top(0.px),
      left(4.px)
    )
    val modalFooterBtnMargin=style(
      marginRight(0.%%)
    )
    val newMessageTooltip=style(
      visibility.hidden,
      backgroundColor(rgba(0, 0, 0, 0.5)),
      color(c"#fff"),
      textAlign.left,
      paddingRight(2.px),
      paddingLeft(2.px),
      paddingTop(5.px),
      paddingBottom(5.px),
      position.absolute,
      zIndex(1),
      fontSize(1.7.rem),
      media.maxWidth(920.px)(
        fontSize(1.4.rem)
      )
    )
  }
}
