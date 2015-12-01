package livelygig.client.css

import scalacss.Defaults._


/**
  * Created by bhagyashree.b on 12/1/2015.
  */
object DashBoardCSS {

  object Style extends StyleSheet.Inline {
    import dsl._

     val mainContainerDiv=style(
       backgroundColor(c"#EAEAEA") ,
       marginTop(75.px),
       paddingLeft(7.%%),
       paddingRight(7.%%),
       overflowX.auto,
       height(800.px)
     )


  }

}
