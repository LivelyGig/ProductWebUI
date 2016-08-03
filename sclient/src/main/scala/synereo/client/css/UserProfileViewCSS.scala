package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar on 3/28/2016.
  */
object UserProfileViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val userProfileHeadingContainerDiv = style(
      minHeight(500.px),
      display.block,
      marginLeft.auto,
      marginRight.auto
    )
    val heading = style(
      color.white,
      textAlign.center,
      marginTop(10.%%),
      fontSize(3.em)
    )
    val userImage = style(
      minWidth(200.px),
      maxWidth(200.px)
    )
    val newImageBtn = style(
      backgroundColor.transparent.important,
      paddingLeft.`0`.important,
      &.hover(
        backgroundColor.transparent.important
      ),
      &.focus(
        backgroundColor.transparent.important
      ),
      border.`0`,
      borderRadius.`0`,
      maxWidth(45.px),
      maxHeight(45.px),
      marginRight(45.px)
    )


    val newImageSubmitBtnContainer = style(
      marginTop(30.px),
      marginBottom(30.px)
    )

    val editSaveButton = style(
      ToStyleAV(marginLeft(1.%%)),
      marginTop(1.px)
    )



    val sectionButtons = style(
      marginLeft(2.%%)
    )

    val buttonDiv = style(
      position.absolute,
      top(0.px),
      left(300.px),
      width( 100.%%)
    )

    val label = style(
      width(350.px)
    )

    val agentUID = style(
      marginLeft(15.%%),
      padding(10.px),
      fontSize(20.px),
      color.white,
      position.relative
    )

    val inputText = style(
      width(200.px),
      color.black
    )

  }

}
