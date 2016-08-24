package synereo.client.modalpopups

import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, _}
import synereo.client.components.Bootstrap.Modal
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._

import synereo.client.components.Bootstrap._
import synereo.client.components.{GlobalStyles, _}
import synereo.client.css.{DashboardCSS, NewMessageCSS}
import synereo.client.logger
import scala.language.reflectiveCalls
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 8/17/2016.
  */
//scalastyle:off
object AmplifyPostModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), /*reactChildElement: ReactTag = <.span(),*/ title: String, modalId: String = "")

  case class State(showAmplifyPostForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showAmplifyPostForm = true))
    }

    def showAmplifyPostForm(): Callback = {
      t.modState(s => s.copy(showAmplifyPostForm = true))
    }

    def postAmplified(): Callback = {
      t.modState(s => s.copy(showAmplifyPostForm = false))
    }
  }

  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
        Button(Button.Props(B.showAmplifyPostForm(), CommonStyle.default, P.addStyles, "", P.title, className = "")/*, P.reactChildElement*/),
        if (S.showAmplifyPostForm)
          AmplifyPostForm(AmplifyPostForm.Props(B.postAmplified, P.modalId))
        else
          Seq.empty[ReactElement]
      )
    })
    .configure(OnUnmount.install)
    .componentDidUpdate(scope => Callback {
      //      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      //      $("body".asInstanceOf[js.Object]).find(".modal-backdrop").remove()
      //      $(".modal-backdrop".asInstanceOf[js.Object]).remove()

    })
    .build

  def apply(props: Props) = component(props)
}

object AmplifyPostForm {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, modalId: String = "")

  case class State(isAmplified: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def hideModal = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler()
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      t.modState(state => state.copy(isAmplified = true))
    }

    def check(): Boolean = {
      if ($("body".asInstanceOf[js.Object]).hasClass("modal-open"))
        true
      else
        false
    }

    def mounted(props: Props) = Callback {
      //      jQuery(t.getDOMNode()).modal("{backdrop: 'static', keyboard: true, show: false}")
      //      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      //      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
      //      $("body".asInstanceOf[js.Object]).find(".modal-backdrop").remove()
      //      $(s"${props.modalId}".asInstanceOf[js.Object]).removeClass("modal  fade")
      //      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      //      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
      //      $(".modal-backdrop .fade .in".asInstanceOf[js.Object]).removeClass(".modal-backdrop .fade .in")
      logger.log.debug("AmplifyPostForm mounted")
    }

    def render(s: State, p: Props) = {
      val headerText = "Amplify"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.h4(^.className := "text-left", headerText),
          closed = () => modalClosed(s, p),
          id = p.modalId
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.form(^.id := "AmpForm", ^.role := "form", ^.onSubmit ==> submitForm)(
              <.div()(
                <.div(^.marginLeft := "15px")(
                  <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Amps to donate")
                ),
                <.div(^.className := "text-right")(
                  <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, /* ^.onClick --> hide*/ "Amplify"),
                  <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> hideModal, "Cancel")
                )
              )
            )
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("AmplifyPostForm")
    .initialState_P(p => State())
    .renderBackend[Backend]
    //    .componentWillMount(scope => scope.backend.mounted(scope.props))
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .shouldComponentUpdate(scope => scope.$.backend.check)
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.isAmplified)
        scope.$.backend.hideModal
    })
    .build

  def apply(props: Props) = component(props)
}