package client.css

import scalacss.Defaults._
import scalacss.internal.LengthUnit.px

object DashBoardCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val mainContainerDiv = style(
      paddingLeft(0.%%),
      paddingRight(0.%%),
      overflowX.auto
      // height(800.px),
    )
    val splitContainer = style(
      position.relative,
      /* height(100.%%),*/
      width(100.%%),
      overflow.hidden
    )


    val rsltContainer = style(
      display.inlineBlock,
      fontSize(1.2.em),
      backgroundColor(c"#EAEAEA"),
      media.maxWidth(1199.px) -
        width(100.%%),
      media.minWidth(1200.px) -
        width(98.2.%%),
      media.minWidth(1200.px) -
        borderRadius(0.px, 25.px, 25.px, 0.px),
      marginLeft(-4.px)

    )

    val dashboardResults2 = style(
      // borderLeft(2.px, solid, c"#005256"),
      paddingLeft(0.px),
      paddingRight(0.px)
    )
    val gigActionsContainer = style(
      backgroundColor(c"#C7DADA"),
      // height(55.px),
      marginTop(0.px),
      marginLeft(0.px),
      marginRight(0.px),
      fontSize(1.1.em),
      marginBottom(0.px),
      verticalAlign.middle,
      width(100.%%),
      paddingTop(4.px),
      paddingBottom(4.px),
      media.maxWidth(375.px) -
        display.none,
      media.minWidth(1200.px) -
        borderRadius(0.px, 25.px, 0.px, 0.px)
    )
    val rsltCheckboxStyle = style(
      height(13.px),
      width(13.px),
      verticalAlign.middle,
      marginTop(-3.px).important,
      media.maxWidth(1365.px) - (
        height(18.px),
        width(18.px)
        )
    )
    val rsltContentBackground = style(
      backgroundColor(c"#D3E7E7"),
      paddingLeft(15.px),
      paddingRight(15.px)
    )
    val displayInlineText = style(
      display.inline
    )
    val rsltCaretStyle = style(
      width(10.px),
      height(15.px),
      borderTop(9.px, dashed),
      borderRight(6.px, solid, transparent),
      borderLeft(6.px, solid, transparent),
      marginTop(3.px),

      media.maxWidth(1367.px) - (
        width(9.px),
        height(9.px),
        borderTop(5.px, dashed)
        )
    )
    val gigMatchButton = style(
      backgroundColor(transparent),
      // fontWeight.normal,
      media.maxWidth(1130.px) -
        fontSize(11.px),
      media.minWidth(1131.px) -
        fontSize(15.px),
      media.minWidth(1367.px) -
        fontSize(1.1.em)
    )
    val rsltCountHolderDiv = style(
      display.inlineBlock,
      fontSize(1.2.em),
      margin(3.%%),
      // fontWeight.bold,
      media.maxWidth(1130.px) -
        fontSize(11.px),
      media.minWidth(1131.px) -
        fontSize(15.px),
      media.minWidth(1367.px) -
        fontSize(18.px)
    )

    val profileNameHolder = style(
      height(40.px),
      padding(0.7.%%),
      fontSize(1.0.em),
      fontWeight.bold,
      display.inline
    )
    val rsltProfileDetailsHolder = style(
      margin(1.%%),
      display.inlineBlock
    )
    val profileImg = style(
      height(5.em),
      width(5.em),
      marginBottom(.5.em)
    )
    val btn = style(
      addClassName("btn"),
      /*  marginRight(5.px),
        padding(4.px, 9.px),*/
      margin(1.px, 5.px, 1.px, 0.px)
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
      media.maxWidth(1130.px) - (
        fontSize(1.em),
        width(135.px),
        paddingLeft(9.%%)
        )
    )

    val scltInputModalLeftContainerMargin = style(
      marginLeft(180.px),
      marginRight(60.px),
      media.maxWidth(1130.px) - (
        marginLeft(135.px),
        marginRight(38.px)
        )
    )
    val scltInputModalContainerMargin = style(
      media.maxWidth(768.px) - (
        marginLeft(0.px),
        marginRight(0.px)
        ),
      media.minWidth(769.px) - (
        marginLeft(150.px),
        marginRight(150.px)
        )
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

    val btnStyle = style(
      width(100.%%),
      height(55.px),
      backgroundColor(c"#4AB8E6"),
      borderRadius(12.px)
    )

    val hidden = style(
      display.none
    )

    val modalHeaderText = style(
      fontSize(1.3.em)
    )
    val padding15px = style(
      padding(15.px)
    )
    val padding5px = style(
      padding(5.px)
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

    val verticalAlignmentHelper = style(
      display.table,
      height(100.%%),
      width(100.%%)
    )
    val verticalAlignCenter = style(
      /* To center vertically */
      display.tableCell,
      verticalAlign.middle,
      media.minWidth(927.px) - (
        paddingRight(17.%%),
        paddingLeft(17.%%))

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
      marginLeft(20.px),
      backgroundColor(c"#FF9E00"),
      /*&.active(
      backgroundColor(blue)
    ),*/
      &.hover(
        backgroundColor(c"#FF7600")
      )
      // addClassName("btnBackground")

    )
    val btnBackground = style(
      backgroundColor(orange)
    )

    val imgLogoLogin = style(
      height(65.px),
      width(65.px)
    )
    val footTextAlign = style(
      textAlign.center
    )
    val linksConatiner = style(
      media.maxWidth(991.px) -
        border.none,
      marginTop(20.px),
      media.minWidth(992.px) -
        borderLeft(2.px, double, c"#000000")
    )
    val modalBorderRadius = style(
      borderRadius(14.px),
      overflow.hidden,
      boxShadow := "13px 13px 20px rgba(0,0,0,0.5)"
    )
    val marginLeftRight = style(
      marginRight(-15.px),
      marginLeft(-15.px),
      padding(0.px),
      height(5.px),
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
      paddingRight(15.px),
      marginTop(0.px) /* makes right bottom icons line up similarly on each result row */
    )

    val footLegalStyle = style(
      color(c"#FFF"),
      backgroundColor(transparent),
      paddingTop(12.px),
      fontSize(15.px),
      borderColor(transparent),
      &.active(
        color(c"#FFF"),
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
      media.maxWidth(1299.px) -
        paddingTop(12.px)
    )
    val replyMarginTop = style(
      marginTop(20.px)
    )

    val inputBtnRadius = style(
      border.none,
      padding(4.px, 7.px, 3.px, 7.px)
    )
    val notificationsBtn = style(
      border.none,
      padding(4.px, 7.px, 4.px, 8.px),
      marginLeft(-13.px)
    )
    val inputBtnRadiusCncx = style(
      border.none,
      padding(3.px, 8.px, 3.px, 8.px)
    )

    val verticalImg = style(
      display.flex,
      justifyContent.center,
      flexDirection.column
    )

    val loading = style(
      width(50.px),
      height(57.px),
      position.absolute,
      top(50.%%),
      left(50.%%),
      zIndex(10000)
    )

    val attentionContainer = style(
      padding(20.px),
      backgroundColor(c"#CCE6FF"),
      textAlign.center,
      height(300.px),
      margin(15.px)
    )
    val opportunitiesContainer = style(
      padding(20.px),
      backgroundColor(c"#E6CCFF"),
      textAlign.center,
      height(300.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px)
    )
    val suggestionsContainer = style(
      padding(20.px),
      backgroundColor(c"#FFD9B3"),
      //  textAlign.center,
      height(200.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px),
      marginLeft(15.px)
    )
    val browseforContainer = style(
      padding(20.px),
      backgroundColor(c"#CCE6FF"),
      // textAlign.center,
      height(200.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px)
    )
    val introduceColleaguesContainer = style(
      padding(20.px),
      backgroundColor(c"#E6CCFF"),
      // textAlign.center,
      height(200.px),
      marginTop(15.px),
      marginRight(15.px),
      marginBottom(15.px),
      marginLeft(15.px)
    )
    val headerFontDashboard = style(
      fontSize(20.px),
      fontWeight.bold
    )

    val paddingLeftModalHeaderbtn = style(
      paddingLeft(15.px),
      color(c"#000")
    )

    val marginResults = style(
      marginLeft(24.%%),
      marginRight(0.px),
      marginBottom(0.px),
      position.absolute,
      media.maxWidth(767.px) -
        marginTop(1.1.%%),
      media.minWidth(768.px).maxWidth(1499.px) -
        marginTop(1.4.%%),
      media.minWidth(1500.px) -
        marginTop(1.5.%%),
      media.minWidth(810.px).maxWidth(1238.px) -
        marginLeft(21.%%),
      media.maxWidth(808.px) -
        marginLeft(16.%%)
    )
    val padding0px = style(
      padding(0.px)
    )
    val DisplayFlex = style(
      display.flex
    )

    val paddingRight0px = style(
      paddingRight(0.px)
    )
    val paddingLeft0px = style(
      paddingLeft(0.px)
    )
    val paddingTop10px = style(
      paddingTop(10.px)
    )
    val paddingLeft7px = style(
      media.maxWidth(767.px) -
        paddingLeft(0.px),
      media.minWidth(768.px) -
        paddingLeft(7.px)
    )
    val imgc = style(
      position.absolute,
      left(0.px),
      right(0.px),
      top(0.px),
      bottom(0.px),
      margin.auto
    )
    val verticalAlignMiddle = style(
      verticalAlign.middle
    )
    val tfootMargin = style(
      media.maxWidth(1024.px) -
        marginRight(0.px),
      media.minWidth(1024.px) -
        marginRight(15.px)
    )

    val chatIcon = style {
      color(green)
    }
    val chatInvisibleIcon = style {
      color(gray)
    }

    //main.less styles

    val modalHeader = style(
      backgroundColor(c"#00767c"),
      color.white
      // font-size : 1.3em;
    )

    val btnDefault = style(
      addClassName("btn-default"),
      backgroundColor(c"#FF9E00")
    )

    val profileDescription = style(
      unsafeChild(".profile-action-buttons")(
        visibility.hidden
      ),
      &.hover(
        unsafeChild(".profile-action-buttons")(
          visibility.visible
        )
      ),
      unsafeChild(".profile-action-buttons:active")(
        visibility.visible
      )
    )


    val rsltSectionContainer = style(
      height(100.%%),
      unsafeChild(".media-list ")(
        unsafeChild("li:nth-child(odd)")(
          backgroundColor(c"#EEEEEE")
        )
      ),
      unsafeChild(".media-list ")(
        unsafeChild("li:nth-child(even)")(
          backgroundColor(c"#F5F5F5")
        )
      )
    )
    val error = style(
      color.red
    )

    val rowStyle = style(

      unsafeChild("tbody")(
        height(350.px),
        overflowY.auto
      ),

      unsafeChild("thead")(display.block),
      unsafeChild("tbody")(display.block),
      unsafeChild("tr")(display.block),
      unsafeChild("td")(display.block),
      unsafeChild("th")(display.block),

      unsafeChild("tbody")(
        unsafeChild("tr:nth-child(even)")(
          backgroundColor(c"#D7E3E3")
        )
      ),

      unsafeChild("tbody")(
        unsafeChild("tr td:nth-child(1)")(
          width(30.%%)
        )
      ), unsafeChild("thead")(
        unsafeChild("tr th:nth-child(1)")(
          width(30.%%)
        )
      ), unsafeChild("tfoot")(
        unsafeChild("tr td:nth-child(1)")(
          width(30.%%)
        )
      ),

      unsafeChild("tbody")(
        unsafeChild("tr td:nth-child(2)")(
          width(18.%%),
          textAlign.center
        )
      ), unsafeChild("thead")(
        unsafeChild("tr th:nth-child(2)")(
          width(18.%%),
          textAlign.center
        )
      ), unsafeChild("tfoot")(
        unsafeChild("tr td:nth-child(2)")(
          width(18.%%),
          textAlign.center
        )
      ),

      unsafeChild("tbody")(
        unsafeChild("tr td:nth-child(3)")(
          width(18.%%),
          textAlign.center
        )
      ), unsafeChild("thead")(
        unsafeChild("tr th:nth-child(3)")(
          width(18.%%),
          textAlign.center
        )
      ), unsafeChild("tfoot")(
        unsafeChild("tr td:nth-child(3)")(
          width(18.%%),
          textAlign.center
        )
      ),

      unsafeChild("tbody")(
        unsafeChild("tr td:nth-child(4)")(
          width(34.%%)
        )
      ), unsafeChild("thead")(
        unsafeChild("tr th:nth-child(4)")(
          width(34.%%)
        )
      ), unsafeChild("tfoot")(
        unsafeChild("tr td:nth-child(4)")(
          width(34.%%)
        )
      ),


      unsafeChild("tr:after")(
        content := " ",
        display.block,
        visibility.hidden,
        clear.both
      ),

      unsafeChild("thead th")(
        height(50.px)
      ),


      unsafeChild("tbody td")(
        width(25.%%),
        float.left
      ),
      unsafeChild("thead th")(
        width(25.%%),
        float.left
      ),
      unsafeChild("tfoot td")(
        width(25.%%),
        float.left
      )
    )

    val inProgress = style(
      unsafeChild("tbody")(
        overflowY.auto,
        height(135.px),
        position.absolute,
        width(97.%%)
      ),
      unsafeChild("tr")(
        width(100.%%),
        display.inlineTable
      ),
      unsafeChild("table")(
        height(190.px)
      )
    )
    val connectionModalWidth = style(
      display.tableCell,
      verticalAlign.middle,
      paddingLeft(30.%%),
      paddingRight(30.%%)
    )

    val msgTime = style(
      color.gray,
      fontSize.smaller,
      width(300.px)
    )

    val imgSize = style(
      paddingTop(2.%%),
      height(120.px),
      width(160.px)
    )

    val marginLR = style(
      marginLeft(10.%%),
      marginRight(10.%%)
    )

    val notificationsText = style(
      marginLeft(50.px),
      marginTop(50.px),
      color(white),
      fontSize(26.px)
    )
  }

}