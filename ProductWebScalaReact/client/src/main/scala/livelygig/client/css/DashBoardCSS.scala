package livelygig.client.css

import scalacss.Defaults._

object DashBoardCSS {

  object Style extends StyleSheet.Inline {
    import dsl._

    val mainContainerDiv = style(
      backgroundColor(c"#EAEAEA"),
      paddingLeft(0.%%),
      paddingRight(0.%%),
      overflowX.auto,
      height(800.px)
    )
    val splitContainer = style(
      position.relative,
      height(100.%%),
      overflow.hidden
    )
    val rsltContainer = style(
      display.inlineBlock,
      width(100.%%),
      fontSize(1.2.em)
    )

    val dashboardResults2 = style(
      borderLeft(2.px, solid, c"#005256"),
      paddingLeft(0.px),
      paddingRight(0.px)
    )
    val gigActionsContainer = style(
      backgroundColor(c"#CCCCFF"),
      height(55.px),
      marginTop(0.px),
      marginLeft(0.px),
      marginRight(0.px),
      fontSize(1.1.em),
      paddingTop(0.px),
      marginBottom(0.px),
      verticalAlign.middle,
      width(100.%%)
    )
    val rsltCheckboxStyle = style(
      height(22.px),
      width(22.px),
      verticalAlign.middle,
      marginTop(-3.px),
      media.maxWidth(1365.px) -(
        height(18.px),
        width(18.px)
        )
    )
    val rsltContentBackground = style(
      backgroundColor(c"#D3E7E7"),
      paddingLeft(15.px),
      paddingRight(15.px)
    )
    val rsltGigActionsDropdown = style(
      display.inlineBlock
    )
    val rsltCaretStyle = style(
      width(10.px),
      height(15.px),
      borderTop(9.px, dashed),
      borderRight(6.px, solid, transparent),
      borderLeft(6.px, solid, transparent),
      marginTop(3.px),

      media.maxWidth(1367.px) -(
        width(9.px),
        height(9.px),
        borderTop(5.px, dashed)
        )
    )
    val gigMatchButton = style(
      backgroundColor(transparent),
      fontWeight.bold,
      media.maxWidth(1130.px) - (
        fontSize(11.px)),
      media.minWidth(1131.px) - (
        fontSize(15.px)
        ),
      media.minWidth(1367.px) - (
        fontSize(1.1.em)
        )
    )
    val rsltCountHolderDiv = style(
      display.inlineBlock,
      fontSize(1.2.em),
      margin(3.%%),
      fontWeight.bold,
      media.maxWidth(1130.px) - (
        fontSize(11.px)),
      media.minWidth(1131.px) -(
        fontSize(15.px)
        // margin(5.%%)
        ),
      media.minWidth(1367.px) -(
        fontSize(18.px)
        // margin(5.%%)
        )
    )
    val listIconPadding = style(
      padding(14.px, 0.px, 14.px, 0.px)
    )
    val profileNameHolder = style(
      height(40.px),
      padding(0.7.%%),
      fontSize(1.2.em),
      display.inline
    )
    val rsltProfileDetailsHolder = style(
      margin(1.%%),
      display.inlineBlock
    )
    val profileImg = style(
      height(120.px),
      width(120.px)
    )
    val btn = style(
      addClassName("btn"),
      marginRight(5.px)
    )
    val inputHeightWidth = style(
      height(25.px),
      marginBottom(12.px),
      display.inline,
      color(c"#333"),
      width(100.%%)
    )
    val slctInputWidth = style(
      width(20.px)
    )
    val slctInputWidthLabel = style(
      width(170.px),
      paddingLeft(5.%%),
      media.maxWidth(1130.px) -(
        fontSize(1.em),
        width(135.px),
        paddingLeft(9.%%)
        )
    )
    val slctInputWidthValidateLabel = style(
      width(179.px),
      marginLeft(36.%%)
    )
    val slctInputLeftContainerMargin = style(
      marginLeft(60.px),
      marginRight(19.px)
    )
    val scltInputModalLeftContainerMargin = style(
      marginLeft(180.px),
      marginRight(60.px),
      media.maxWidth(1130.px) -(
        marginLeft(135.px),
        marginRight(38.px)
        )
    )
    val scltInputModalContainerMargin = style(
      marginLeft(150.px),
      marginRight(150.px)
    )
    val slctHeaders = style(
      paddingTop(15.px),
      paddingBottom(2.px),
      fontSize(1.em),
      fontWeight.normal
    )
    val slctSubCheckboxesDiv = style(
      paddingLeft(20.px),
      fontSize(1.em),
      fontWeight.normal
    )

    /*CreateNewAgent Css Styles*/
    val modalContainer = style(
      height(500.px),
      width(400.px),
      backgroundColor(yellow)
    )
    val borderColorStyle = style(
      height(400.px),
      width(600.px),
      border(1.px, solid, c"#4AB8E6"),
      padding(3.px)
    )
    val btnStyle = style(
      width(100.%%),
      height(55.px),
      backgroundColor(c"#4AB8E6"),
      borderRadius(12.px)
    )
    val btnContainerDiv = style(
      margin(3.px)
    )
    val headerBtnFont = style(
      fontSize(22.px),
      color(c"#fff"),
      fontWeight.bold,
      display.inlineBlock
    )
    val closeBtnFont = style(
      color(c"#4AB8E6"),
      fontSize(20.px),
      paddingLeft(6.px),
      paddingRight(6.px),
      fontWeight.bold
    )
    val btnClose = style(
      display.inlineBlock,
      backgroundColor(c"#D6EEF8"),
      float.right,
      borderRadius(5.px),
      marginTop(3.px)
    )
    val inputContainer = style(
      marginTop(3.%%),
      marginLeft(10.%%),
      marginRight(10.%%)
    )
    val inputStyle = style(
      height(37.px),
      color(c"#F38430"),
      fontWeight.bold,
      fontSize(1.3.em)
    )
    val backgroundTransperant = style(
      backgroundColor(transparent),
      border.none,
      &.hover(
        backgroundColor(transparent),
        border.none
      )
    )
    val modalHeaderText = style(
      fontSize(1.3.em)
    )
    val modalBodyText = style(
      fontSize(2.5.em),
      textAlign.center,
      verticalAlign.middle
    )
    val inputModalMargin = style(
      marginBottom(2.%%)
    )
    val marginLeftchk = style(
      marginLeft(2.%%)
    )
    val MarginLeftchkproduct = style(
      marginLeft(15.px),
      marginRight(15.px)
    )
    val headerbtnstyle = style(
      height(0.px),
      width(0.px),
      padding(0.px)
    )
    val verticalAlignmentHelper = style(
      display.table,
      height(100.%%),
      width(100.%%)
    )
    val verticalAlignCenter = style(
      /* To center vertically */
      display.tableCell,
      verticalAlign.middle,
      paddingRight(17.%%),
      paddingLeft(17.%%)
    )
    val modalContent = style(
      width.inherit,
      height.inherit,
      margin(0.px, auto)

    )
    val marginTop10px = style(
      marginTop(10.px)
    )
    val modalContentFont = style(
      fontSize(15.px),
      marginTop(15.px)

    )
    val modalHeaderPadding = style(
      padding(10.px)
    )
    val modalHeaderFont = style(
      fontSize(1.em),
      paddingBottom(15.px)
    )
    val marginLeftCloseBtn = style(
      marginLeft(20.px)
    )
    val imgLogoLogin = style(
      height(65.px),
      width(65.px)
    )
    val footTextAlign = style(
      textAlign.center
    )
    val modalBorderRadius = style(
      borderRadius(0.px)
    )
    val marginLeftRight = style(
      marginRight(-15.px),
      marginLeft(-15.px),
      padding(22.px),
      backgroundColor(c"#00767c")
    )
    val btnWidth = style(
      width(100.%%)
    )
    val marginTop5p = style(
      marginTop(5.%%)
    )
    val modalBodyPadding = style(
      paddingLeft(15.px),
      paddingBottom(0.px),
     // paddingTop(15.px),
      paddingRight(15.px)
    )
    val rsltpaddingTop10p = style(
      paddingTop(10.px),
      paddingLeft(15.px),
      paddingRight(15.px)
    )
    val paddingLeftLoginbtn = style(
      paddingLeft(0.px)
    )
    val paddingRightLoginbtn = style(
      paddingRight(0.px)
    )
    val marginTop20px = style(
      marginTop(20.px)
    )
    val floatRightbtn = style(
      float.right
    )
    val footLegalStyle = style(
      color(c"#fff"),
      backgroundColor(transparent),
      paddingTop(17.px),
      fontSize(15.px),
      borderColor(transparent),
      &.active(
        color(c"#fff"),
        backgroundColor(transparent),
        paddingTop(17.px),
        fontSize(15.px),
        borderColor(transparent)
      ),
      &.hover(
        color(c"#fff"),
        backgroundColor(transparent),
        fontSize(15.px),
        borderColor(transparent)
      ),
      media.maxWidth(1299.px) - (
        paddingTop(12.px)
        )
    )
    val replyMarginTop = style(
      marginTop(20.px)
    )
    val footPreferences = style (
      padding(22.px) ,
      backgroundColor(c"#00767c")
    )
    val inputBtnRadius = style (
      border.none,
      padding(0.2.em, 0.6.em, 0.1.em)
    )
    val horizontalImg = style(
      display.flex,
      justifyContent.center
    )
    val verticalImg = style(
      display.flex,
      justifyContent.center,
      flexDirection.column
    )
    val overlay = style (
      position.absolute ,
      left(0.px),
      top(0.px),
      bottom(0.px),
      right(0.px),
      //background(gray),
      backgroundColor(c"#000"),
      opacity(0.8)
      //filter.alpha(opacity=80);
    )
    val loading = style (
      width (50.px),
      height(57.px),
      position.absolute,
      top(50.%%),
      left(50.%%),
      zIndex(10000)
    )
    val addLoaderImg = style (
      visibility.visible
    )
    val removeLoaderImg = style (
      visibility.hidden
    )
    val attentionContainer =style (
      padding(20.px),
      backgroundColor(c"#CCE6FF"),
      textAlign.center,
      height(300.px),
      margin(15.px)
    )
    val opportunitiesContainer =style (
      padding(20.px),
      backgroundColor(c"#E6CCFF"),
      textAlign.center,
      height(300.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px)
    )
    val suggestionsContainer =style (
      padding(20.px),
      backgroundColor(c"#FFD9B3"),
      //  textAlign.center,
      height(200.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px),
      marginLeft(15.px)
    )
    val browseforContainer =style (
      padding(20.px),
      backgroundColor(c"#CCE6FF"),
      // textAlign.center,
      height(200.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px)
    )
    val introduceColleaguesContainer = style (
      padding(20.px),
      backgroundColor(c"#E6CCFF"),
      // textAlign.center,
      height(200.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px),
      marginLeft(15.px)
    )
    val headerFontDashboard = style (
      fontSize(20.px),
      fontWeight.bold
    )

  }
}