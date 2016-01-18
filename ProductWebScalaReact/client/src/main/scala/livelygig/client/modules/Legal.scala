//package livelygig.client.modules
//
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.extra.router.RouterCtl
//import japgolly.scalajs.react.vdom.prefix_<^._
//import livelygig.client.LGMain.DashboardLoc
//import livelygig.client.LGMain.Loc
//import livelygig.client.LGMain.Loc
//import livelygig.client.components.GlobalStyles
//import livelygig.client.components.Icon
//import livelygig.client.components.Icon
//import livelygig.client.css.FooterCSS
//import livelygig.client.css.{FooterCSS, CreateAgentCSS, DashBoardCSS, HeaderCSS}
//import livelygig.client.models.AgentLoginModel
//import livelygig.client.modules.Footer.FooterItem
//import livelygig.client.services.{ApiResponseMsg, CoreApi}
//import org.scalajs.dom._
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.util.{Failure, Success}
//import scalacss.ScalaCssReact._
//
//object Legal {
//  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc)
//  case class LegalItem(idx: Int, label: (Props) => ReactNode, location: Loc)
//  private val legalItems = Seq(
//    LegalItem(1, _ => "Privacy Policy", DashboardLoc),
//    LegalItem(2, _ => "End User Agreement", DashboardLoc),
//    LegalItem(3, _ => "Terms of Service", DashboardLoc),
//    LegalItem(2, _ => "Trademarks", DashboardLoc),
//    LegalItem(3, _ => "Copyright", DashboardLoc)
//  )
//
//  val component = ReactComponentB[Props]("Legal")
//    .render_P((P) => {
//      <.div (^.id:="mainContainer", DashBoardCSS.Style.mainContainerDiv)(
//        <.ul()(
//          // build a list of menu items
//          for (item <- legalItems) yield {
//            <.li(^.key := item.idx, (P.currentLoc == item.location) ?= FooterCSS.Style.footerNavLi,
//              if(item.idx == 3 ) {
//                P.ctl.link(item.location)(FooterCSS.Style.footerNavA, " ", Icon.copyright, item.label(P))
//              }
//              else if (item.idx==2){
//                LegalModal(LegalModal.Props(P.ctl))
//              }
//              else
//              {
//                P.ctl.link(item.location)(FooterCSS.Style.footerNavA, " ", item.label(P))
//              }
//            )
//          }
//        )
//      ) //mainContainer
//    })
//    .build
//
//  def apply(props: Props) = component(props)
//}
//
