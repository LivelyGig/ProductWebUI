package synereo.client.css

import scala.language.postfixOps
import scalacss.Defaults._

/**
 * Created by Mandar on 4/1/2016.
 */
object MarketPlaceFullCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

//    val mainContainer = style(
//      minHeight(870.px)
//    )
//    val marketplaceActionsUL = style(
//      paddingTop(10.%%),
//      paddingBottom(8.%%)
//    )
//    val marketplaceActionsLI = style(
//      paddingBottom(3.px),
//      paddingTop(3.px),
//      fontSize(1.4.em)
//
//    )
    val seeMoreBtn = style( //fontSize(1.4.em)
    )
    val cardsHeading = style(
      fontSize(1.2.em),
      display.inlineBlock,
      fontWeight._700
    )
//    val footerUL = style(
//      position.relative,
//      bottom(-170.px)
//    )
    val cardsAndButtonContainerDiv = style(
      marginTop(30.px),
      marginBottom(30.px)
    )
    val headingImageContainerDiv = style()
    val appCard = style(
      width(20.%%),
      display.inlineBlock,
      float.left,
      paddingRight(20.px)
    )

  }

}
