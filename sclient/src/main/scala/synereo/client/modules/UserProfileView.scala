package synereo.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import diode.react.ModelProxy
import org.querki.jquery._
import shared.models.UserModel
import synereo.client.components.Icon
import synereo.client.css.UserProfileViewCSS
import synereo.client.modalpopups.NewImage
import scala.scalajs.js
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 3/28/2016.
  */
//scalastyle:off
object UserProfileView {
  val searchContainer: js.Object = "#searchContainer"

  case class Props(proxy: ModelProxy[UserModel])

  case class State()

  class Backend(t: BackendScope[Props, State]) {

    def mounted(props: Props): Callback = Callback {
      //      t.modState(s => s.copy(showLoginForm = true))
      println("user profile view mounted")
    }

    def render(s: State, p: Props) = {
      val userModel = p.proxy.value
      <.div(^.className := "container-fluid")(
//        <.div(^.className := "row")(
//          //Left Sidebar
//          <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ",
//            ^.onMouseEnter --> Callback {
//              $(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")
//            },
//            ^.onMouseLeave --> Callback {
//              $(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")
//            }
//          )(
//            //            Footer(Footer.Props(c, r.page))
//            Sidebar(Sidebar.Props())
//          )
//        ),
        <.div(^.className := "row", UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
          <.div(^.className := "col-md-12",
            <.div(^.className := "row",
              <.div(^.className := "col-md-2"),
              <.div(^.className := "col-md-4 text-right",
                <.img(^.alt := "User Image", ^.className := "", ^.src := userModel.imgSrc, UserProfileViewCSS.Style.userImage),
                NewImage(NewImage.Props("Change Image", Seq(UserProfileViewCSS.Style.newImageBtn), Icon.camera, ""))
              ),
              <.div(^.className := "col-md-4",
                <.h2()(userModel.name)
              ),
              <.div(^.className := "col-md-2")
            ),
            <.div(^.className := "row",
              <.div(^.className := "col-md-2"),
              <.div(^.className := "col-md-4 text-right",
                <.h2()("email:")
              ),
              <.div(^.className := "col-md-4",
                <.h2()(userModel.email)
              ),
              <.div(^.className := "col-md-2")
            )
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props]("UserProfileView")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[UserModel]) = component(Props(proxy))
}

