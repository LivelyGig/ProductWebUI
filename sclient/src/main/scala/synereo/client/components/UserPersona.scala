package synereo.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.prefix_<^.{<, _}
import shared.models.UserModel
import synereo.client.css.{DashboardCSS, NewMessageCSS, SynereoCommanStylesCSS}

import scala.language.existentials
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 6/2/2016.
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
      <.div(^.className := "row",DashboardCSS.Style.dispalyFlex/*, NewMessageCSS.Style.PersonaContainerDiv*/)(
        <.div(^.className := "col-lg-2 col-md-2 col-sm-2 col-xs-2"/*, SynereoCommanStylesCSS.Style.paddingLeftZero*/,NewMessageCSS.Style.userImgDiv)(
          <.img( ^.src := model.imgSrc, ^.className := "img-responsive", NewMessageCSS.Style.userImage)
        ),
        <.div(^.className := "col-lg-8 col-md-8 col-sm-8 col-xs-8"/*, SynereoCommanStylesCSS.Style.paddingLeftZero, SynereoCommanStylesCSS.Style.paddingRightZero*/)(
          <.div(^.className := "btn", ^.`type` := "button", NewMessageCSS.Style.changePersonaBtn)("Change posting persona", <.span(^.className := "caret", ^.color.blue)),
            <.div(NewMessageCSS.Style.userNameOnDilogue)(
          <.div(s"${model.name} "/*, <.span(Icon.chevronRight), " public ", <.span(Icon.share)*/)
        ),
        <.div(^.className := "pull-right hidden-xs",SynereoCommanStylesCSS.Style.featureHide)(MIcon.apply("more_vert", "24"))
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
