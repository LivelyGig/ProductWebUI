package scalajsreact.template.routes


import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.router.{ Resolution, RouterConfigDsl, RouterCtl, _}

import scalajsreact.template.components.{Footer}
import scalajsreact.template.models.Menu

object AppRouter {

  sealed trait AppPage
  case object Home extends AppPage

  val config = RouterConfigDsl[AppPage].buildConfig {dsl =>
    import dsl._
    (trimSlashes
      | staticRoute(root, Home) ~> render(<.div("TODO"))
      ).notFound(redirectToPage(Home)(Redirect.Replace))
      .renderWith(layout)
  }

  val mainMenu = Vector(
    Menu("Home", Home)
  )

  def layout(c: RouterCtl[AppPage], r: Resolution[AppPage]) = {
    <.div(
      r.render(),
      Footer()
    )
  }

//  val baseUrl = BaseUrl.fromWindowOrigin / "livelygig/"
  val baseUrl = BaseUrl("http://127.0.0.1:8080/livelygig")
  val router = Router(baseUrl, config)

}
