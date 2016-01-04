package livelygig.client.css
import scalacss.Defaults._

object DashBoardCSS {
  object Style extends StyleSheet.Inline {
    import dsl._
    val mainContainerDiv = style(
      backgroundColor(c"#EAEAEA"),
      paddingLeft(7.%%),
      paddingRight(7.%%),
      overflowX.auto,
      height(800.px)
    )
    val splitContainer = style(
      position.relative,
      height(100.%%),
      overflow.hidden
    )
    val rsltContainer = style(
      display.inlineBlock
    )
    val slctContainer = style(
      display.inlineBlock
    )
    val gigActionsContainer = style(
      backgroundColor(c"#DFDFDF"),
      height(60.px)
    )
    val rsltGigActionsDropdown = style(
      display.inlineBlock
    )
    val gigMatchButton = style(
      backgroundColor(transparent),
      border(1.px, solid)

    )
    val rsltCountHolderDiv = style(
      display.inlineBlock,
      fontSize(1.2.em),
      margin(2.%%)
    )
    val listIconPadding = style(
      padding(14.px, 0.px, 14.px, 0.px)
    )
    val gigConversation = style(
    )
    val profileNameHolder = style(
      height(40.px),
      padding(0.7.%%),
      fontSize(1.2.em)
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
      border(2.px, solid, c"#005256"),
      marginRight(5.px)
    )
    val inputHeightWidth = style(
      height(25.px),
      marginBottom(12.px),
      display.inline,
      color(c"#333")
    )
    val slctInputWidth = style(
      width(20.px)
    )
    val slctInputWidthLabel =style(
     width(170.px),
    paddingLeft(7.%%)
    )
    val slctInputWidthValidateLabel =style(
      width(170.px),
      marginLeft(36.%%)
    )
    val slctInputLeftContainerMargin = style(
      marginLeft(45.px),
      marginRight(19.px)
    )
    val scltInputModalLeftContainerMargin = style(
      marginLeft(180.px),
      marginRight(60.px)
    )
    val scltInputModalContainerMargin = style(
      marginLeft(150.px),
      marginRight(150.px)
    )
    val slctHeaders=style(
      paddingTop(15.px),
      paddingBottom(2.px),
      fontSize(1.em),
      fontWeight.bold
    )
    val checkboxLbl = style(
      &.before(
        fontWeight.normal,
        fontSize(8.px),
        color(green),
        backgroundColor(c"#FAFAFA"),
        border(1.px, solid, rgb(51, 51, 51)),
        borderRadius(0.px),
        display.inlineBlock,
        textAlign.center,
        verticalAlign.middle,
        height(13.px),
        lineHeight(13.px),
        minWidth(13.px),
        marginRight(11.px),
        marginTop(-3.px),
        marginLeft(-15.px)
      ))

    /*CreateNewAgent Css Styles*/

     val modalContainer = style (
      height(500.px),
      width(400.px),
      backgroundColor(yellow)
    )
    val borderColorStyle = style (
      height(400.px),
      width(600.px),
      border(1.px , solid, c"#4AB8E6"),
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

    val btnClose= style(
     display.inlineBlock,
     backgroundColor(c"#D6EEF8"),
     float.right,
     borderRadius(5.px),
     marginTop(3.px)
    )

    val inputContainer = style (
      marginTop(3.%%),
      marginLeft(10.%%),
      marginRight(10.%%)
    )

    val inputStyle = style (
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

    val modalBodyText = style (
        fontSize(2.5.em),
//        height(150.px),
        textAlign.center,
        verticalAlign.middle
    )
    val inputModalMargin = style (
      marginBottom(2.%%)
    )
    val marginLeftchk = style (
    marginLeft(2.%%)
    )
    val MarginLeftchkproduct = style (
      marginLeft(15.px)
    )
    val MarginLeftchknotification = style (
      //marginLeft(-52.%%)
    )
    val MarginLeftchkagree = style (
      // marginLeft(-10.%%)
    )

    val headerbtnstyle =  style (
      height(0.px),
      width(0.px),
      padding(0.px)
    )

      val verticalAlignmentHelper = style (
      display.table,
      height(100.%%),
      width(100.%%)
    )
      val verticalAlignCenter = style (
      /* To center vertically */
      display.tableCell,
      verticalAlign.middle,
      paddingRight(25.%%),
      paddingLeft(25.%%)
      )
      val modalContent = style (
      /* Bootstrap sets the size of the modal in the modal-dialog class, we need to inherit it */
      width.inherit ,
      height.inherit ,
      /* To center horizontally */
      margin(0.px , auto)

    )
    val marginTop10px =style (
     marginTop(10.px)
    )
     val modalContentFont = style (
      fontSize(15.px)
     )
   val modalHeaderPadding = style (
     padding(10.px)
   )
    val modalHeaderFont = style (
     fontSize(2.em),
     paddingBottom(15.px)
    )
    val marginLeftCloseBtn = style (
      marginLeft(20.px)
    )
    val imgLogoLogin = style(
      height(65.px),
      width(65.px)
    )
    val footTextAlign = style(
      textAlign.center
    )
    val modalBorderRadius = style (
    borderRadius(0.px)
    )
    val marginLeftRight = style (
      marginRight(-15.px),
      marginLeft(-15.px),
      padding(22.px) ,
      backgroundColor(c"#00767c")
    )
    val btnWidth = style (
    width(100.%%)
    )

     val marginTop5p = style(
     marginTop(5.%%)
     )

     val modalBodyPadding = style(
       paddingLeft(15.px),
     paddingBottom(0.px),
     paddingTop(15.px),
     paddingRight(15.px)

    )

  }
}