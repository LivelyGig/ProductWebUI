package synereo.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.UserModel
import synereo.client.css.{NewMessageCSS, SynereoCommanStylesCSS}
import scala.language.existentials
import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 6/2/2016.
  */
//scalastyle:off
object UserPersona {

  def getPersona(): String = {
    ""
  }

  case class Props(proxy: ModelProxy[UserModel])

  case class Backend(t: BackendScope[Props, _]) {


    def mounted(props: Props): Callback = Callback {
      //      println("UserPersona is : " + props.proxy.value)
    }

    def render(props: Props) = {
      val model = props.proxy.value
      <.div(^.className := "row", NewMessageCSS.Style.PersonaContainerDiv)(
        <.div(^.className := "col-md-2 col-sm-2", SynereoCommanStylesCSS.Style.paddingLeftZero)(
          <.img(^.alt := "UserImage", ^.src := model.imgSrc, ^.className := "img-responsive", NewMessageCSS.Style.userImage)
        ),
        <.div(^.className := "col-md-10", SynereoCommanStylesCSS.Style.paddingLeftZero, SynereoCommanStylesCSS.Style.paddingRightZero)(
          <.div(^.className := "")(
            <.button(^.className := "btn", ^.`type` := "button", NewMessageCSS.Style.changePersonaBtn)("Change posting persona", <.span(^.className := "caret", ^.color.blue)),
            <.div(^.className := "pull-right")(MIcon.apply("more_vert", "24"))
          )
        ),
        <.div(NewMessageCSS.Style.userNameOnDilogue)(
          <.div(model.name, <.span(Icon.chevronRight), "public", <.span(Icon.share))
        )
      )
    }
  }

  val component = ReactComponentB[Props]("UserPersona")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}
