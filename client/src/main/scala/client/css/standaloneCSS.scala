//package client.css
//
//import scalacss.Defaults._
//
///**
//  * Created by bhagyashree.b on 3/15/2016.
//  */
//
//object standaloneCSS extends StyleSheet.Standalone {
//  import dsl._
//  "div.h2" - (
//    color.red,
//    fontSize.large,
//    margin(100 px)
//    )
//
//  "div.std" - (
//    margin(12 px, auto),
//    textAlign.left,
//    cursor.pointer,
//
//    &.hover -
//      cursor.zoomIn,
//
//    media.not.handheld.landscape.maxWidth(640 px) -
//      width(400 px),
//
//    &("span") -
//      color.red
//    )
//
//  "h1".firstChild -
//    fontWeight.bold
//
//  for (i <- 0 to 3)
//    s".indent-$i" -
//      paddingLeft(i * 2.ex)
//
//  //  println(standaloneCSS.render)
//}
//
////object YOLO extends StyleSheet.Inline {
////  import dsl._
////
////  val top = style(
////  backgroundColor(c"#ccc"),
////  padding(1.em),
////  unsafeChild("h1")(
////  color(c"#ff0")
////  ),
////  unsafeChild("p")(
////  color.red,
////  fontWeight.bold
////  )
////  )
////}