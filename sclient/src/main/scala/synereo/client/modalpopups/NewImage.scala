package synereo.client.modalpopups

import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import shared.RootModels.SearchesRootModel
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon.Icon
import synereo.client.css.NewMessageCSS
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.logger
import diode.AnyAction._

import scala.scalajs.js
import org.scalajs.dom._
import org.scalajs.dom.raw.UIEvent
import shared.dtos.UpdateUserRequest
import shared.models.UserModel
import shared.sessionitems.SessionItems

//scalastyle:off
/**
  * Created by mandar.k on 7/22/2016.
  */
object NewImage {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())

  case class State(showNewImageForm: Boolean = false)

  val userProxy = SYNEREOCircuit.connect(_.user)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewImageForm = true))
    }

    def addNewImageForm(): Callback = {
      t.modState(s => s.copy(showNewImageForm = true))
    }

    def addImage(/*submitForm:PostMessage*/): Callback = {
      t.modState(s => s.copy(showNewImageForm = false))
    }
  }

  val component = ReactComponentB[Props]("ProfileImageUploader")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(
        Button(Button.Props(B.addNewImageForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = P.className), P.buttonName, P.childrenElement),
        if (S.showNewImageForm) userProxy(userProxy => ProfileImageUploaderForm(ProfileImageUploaderForm.Props(B.addImage, "Profile Image Uploader", userProxy)))
        else
          Seq.empty[ReactElement]
      )
    })
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object ProfileImageUploaderForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, header: String, proxy: ModelProxy[UserModel])

  case class State(postNewImage: Boolean = false, updateUserRequest: UpdateUserRequest)

  val getUsers = SYNEREOCircuit.connect(_.user)

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(): Callback = Callback {

    }

    def updateImgSrc(e: ReactEventI): react.Callback = Callback {
      val uri = window.sessionStorage.getItem(SessionItems.ProfilesViewItems.PROFILES_SESSION_URI)
      val value = e.target.files.item(0)
      //      println("Img src = " + value)
      val reader = new FileReader()
      reader.onload = (e: UIEvent) => {
        val contents = reader.result.asInstanceOf[String]
        t.modState(s => s.copy(updateUserRequest = s.updateUserRequest.copy(jsonBlob = s.updateUserRequest.jsonBlob.copy(imgSrc = contents))))
        t.modState(s => s.copy(updateUserRequest = s.updateUserRequest.copy(sessionURI = uri)))
      }
      reader.readAsDataURL(value)
    }


    def submitForm(e: ReactEventI): Callback = Callback {
      e.preventDefault()
      val state = t.state.runNow()
      println(s"state.updateUserRequest: ${state.updateUserRequest}")
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.submitForm*/)
    }

    def render(s: State, p: Props) = {
      Modal(
        Modal.Props(
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.className := "hidden", ^.onClick --> hide, Icon.close),
            <.h1(^.className := "pull-left")(p.header)),
          closed = () => formClosed(s, p),
          id = "newImage"
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12")(
              <.div(^.className := "row",
                <.div(^.className := "col-md-12")(
                  <.input(^.`type` := "file", ^.id := "files", ^.name := "files", ^.onChange ==> updateImgSrc)
                ),
                <.div(^.className := "col-md-12")(
                  if (p.proxy.value.imgSrc != "") {
                    <.img(^.src := p.proxy.value.imgSrc)
                  } else {
                    <.div("")
                  }

                )
              ),
              <.div(^.className := "row",
                <.div(^.className := "col-md-12",
                  <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> hide, "Cancel"),
                  <.button(^.tpe := "submit", ^.className := "btn btn-default", NewMessageCSS.Style.createPostBtn, /*^.onClick --> hide, */ "Set Profile Image")
                )
              )
            )
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State(updateUserRequest = new UpdateUserRequest()))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postNewImage) {
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(props: Props) = component(props)

}
