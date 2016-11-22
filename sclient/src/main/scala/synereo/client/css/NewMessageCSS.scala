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
      fontSize(18.px)
    )
    val newMessageActionsContainerDiv = style(
      //      marginTop(30.px),
      marginBottom(30.px)
    )
    val userImage = style(
      maxWidth(50.px),
      maxHeight(50.px)
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
      fontSize(20.px),
      fontWeight.bold,
      paddingLeft.`0`.important,
      &.hover(
        backgroundColor.transparent.important
      ),
      &.focus(
        backgroundColor.transparent.important
      ),
      media.maxWidth(1024.px)(
        fontSize(15.px)
      )
    )
    val PersonaContainerDiv = style(
      marginTop.`0`,
      marginBottom(20.px),
      media.maxWidth(580.px)(
        marginTop(-30.px)
      )
    )
    val newMessageCancelBtn = style(
      backgroundColor.transparent.important,
      color(c"#242D40"),
      border.none.important,
      fontSize(22.px),
      fontWeight._700,
      fontFamily :=! "karla",
      marginRight(10.px),
      marginLeft(10.px),
      &.hover(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important
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
        border.none.important
      ),
      &.focus(
        backgroundColor.transparent.important,
        color(c"#242D40"),
        border.none.important
      )
    )
    val userNameOnDilogue = style(
      fontSize(16 px),
      media.maxWidth(1024.px)(
        fontSize(10.px)
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


  }

}
