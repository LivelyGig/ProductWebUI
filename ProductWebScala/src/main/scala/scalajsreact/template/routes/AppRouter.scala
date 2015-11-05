package scalajsreact.template.routes


import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.router.{ Resolution, RouterConfigDsl, RouterCtl, _}

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
      r.render()
    )
  }

  val baseUrl = BaseUrl.fromWindowOrigin / "scalajs-react-template/"

  val router = Router(baseUrl, config)

}
