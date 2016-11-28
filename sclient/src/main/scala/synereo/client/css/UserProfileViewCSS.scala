package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar on 3/28/2016.
  */
object UserProfileViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val comingSoonImgPreview = style(
      minHeight(60.px),
      margin(4.px)
    )
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
      minWidth(150.px),
      maxWidth(150.px),
      maxHeight(150.px)
    )
//    val newImageBtn = style(
//      backgroundColor.transparent.important,
//      paddingLeft.`0`.important,
//      &.hover(
//        backgroundColor.transparent.important
//      ),
//      &.focus(
//        backgroundColor.transparent.important
//      ),
//      border.`0`,
//      borderRadius.`0`,
//      maxWidth(45.px),
//      maxHeight(45.px),
//      marginRight(45.px)
//    )
    val newImageSubmitBtnContainer = style(
      marginTop(30.px),
      marginBottom(30.px)
    )
//    val editSaveButton = style(
//      ToStyleAV(marginLeft(1.%%)),
//      marginTop(1.px)
//    )
    val sectionButtons = style(
      marginRight(10.px)
    )
//    val buttonsContainerDiv = style(
//      position.absolute,
//      top(0.px),
//      left(300.px),
//      width(100.%%)
//    )
    val label = style(
      width(350.px)
    )
//    val agentUID = style(
//      marginLeft(15.%%),
//      padding(10.px),
//      fontSize(20.px),
//      color.white,
//      position.relative
//    )
    val nodeSettingSection = style(
      fontSize(20.px),
      color.black,
      wordWrap.breakWord,
      media.maxWidth(580.px)(
        fontSize(12.px)
      )
    )
    val aboutInfoSectionContainer = style(
      marginBottom(15.px)
    )
//    val inputText = style(
//      width(200.px),
//      color.black,
//      marginBottom(1.%%)
//    )
    val nodeSettingsFormControlContainer = style(
      marginBottom(10.px)
    )
//    val deleteButton = style(
//      position.relative,
//      top(-47.px),
//      left(270.px)
//    )
    val nodeSettingsSelectButtons = style(
      marginTop(5.px),
      marginRight(10.px)
    )
    val sectionButtonsContainer = style(
      width(100.%%),
      marginBottom(5.px)
    )
    val modalImgUploadTab = style(
      width(50.%%)
    )
    val modalImgUploadTabUl = style(
      marginBottom(10.px),
      width(118.%%),
      marginLeft(-9.%%),
      media.minWidth(582.px).maxWidth(922.px)(
        width(106.%%),
        marginLeft(-3.%%)
      ),
      media.maxWidth(581.px)(
        width(102.%%),
        marginLeft(-1.%%)
      )
    )
    val modalImgUploadTabAnchorTag = style(
      marginLeft(20.%%).important,
      width(106.px),
      border.none.important,
      paddingLeft(0.px).important,

      color(c"#7a7a7a"),
      &.hover(
        backgroundColor.initial.important,
        color.initial,
        fontWeight.initial
      )
    )
    val modalImgUploadHeader = style(
      marginLeft(9.%%),
      color(c"#5c5c5c"),
      fontWeight._500,
      media.maxWidth(921.px)(
        marginLeft(4.%%)
      )
    )
    val modalImgUploadImgDiv = style(
      height(212.px)

    )
    val aboutInfoSectionHeader= style(
      textAlign.center,
      fontSize(24.px),
      fontWeight.bolder
    )
    val aboutInfoSectionTitle=style(
      fontWeight.bold,
      fontSize(22.px)
    )

  }

}
