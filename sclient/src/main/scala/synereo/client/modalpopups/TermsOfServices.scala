package synereo.client.modalpopups

import diode.react.ModelProxy
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.{Modal, _}
import synereo.client.components.Icon.Icon
import synereo.client.components.{GlobalStyles, _}
import synereo.client.css.{LoginCSS, UserProfileViewCSS}

import scala.language.reflectiveCalls
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap.Modal
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._

object TermsOfServices {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), icon: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())

  case class State(showTernsOfServicesForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showTernsOfServicesForm = true))
    }

    def addTermsOfServicesForm(): Callback = {
      t.modState(s => s.copy(showTernsOfServicesForm = true))
    }

    def addTermsOfServices(/*submitForm:PostMessage*/): Callback = {
      t.modState(s => s.copy(showTernsOfServicesForm = false))
    }
  }

  val component = ReactComponentB[Props]("TermsOfServices")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(
        Button(Button.Props(B.addTermsOfServicesForm(), CommonStyle.default, P.addStyles, "", P.title, className = P.className), P.buttonName, P.childrenElement),
        if (S.showTernsOfServicesForm) TermsOfServicesForm(TermsOfServicesForm.Props(B.addTermsOfServices, "Terms of Services"))
        else
          Seq.empty[ReactElement]
      )
    })
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object TermsOfServicesForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, header: String)

  case class State(postNewImage: Boolean = false)


  case class TermsOfServicesBackend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(): Callback = Callback {

    }


    def submitForm(e: ReactEventI): Callback = {
      e.preventDefault()
      t.modState(s => s.copy(postNewImage = true))

    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.submitForm*/)
    }
  }

  private val component = ReactComponentB[Props]("TermsOfServicesForm")
    .initialState_P(p => State())
    .backend(new TermsOfServicesBackend(_))
    .renderPS((t, P, S) => {
      Modal(
        Modal.Props(
          header = hide => <.span(
            <.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.h4(^.className := "pull-left")(P.header)),
          closed = () => t.backend.formClosed(S, P)
        ),
        <.form(^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12")(
              <.div(^.className := "row",
                <.div(^.className := "col-md-12")(
                  "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s" +
                    " with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                )
              ),
              <.div(^.className := "row",
                <.div(^.className := "col-md-12 text-right", UserProfileViewCSS.Style.newImageSubmitBtnContainer,
                  <.div()(<.button(^.tpe := "button", ^.className := "btn", ^.onClick --> t.backend.hide, LoginCSS.Style.modalLoginBtn, ^.marginBottom := "20.px")("Back"))
                )
              )
            )
          )
        )
      )
    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postNewImage) {
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(props: Props) = component(props)

}
