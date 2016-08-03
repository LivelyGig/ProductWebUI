package synereo.client.modalpopups

import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon.Icon
import synereo.client.css.{NewMessageCSS, UserProfileViewCSS}
import synereo.client.services.SYNEREOCircuit

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.components.Bootstrap._
import org.scalajs.dom._
import org.scalajs.dom.raw.UIEvent
import shared.dtos.{JsonBlob, UpdateUserRequest}
import shared.models.UserModel
import synereo.client.handlers.PostUserUpdate
import diode.AnyAction._
import org.querki.jquery._
import synereo.client.logger

import scala.scalajs.js

//scalastyle:off
/**
  * Created by mandar.k on 7/22/2016.
  */
object NewImage {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), icon: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())

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
        Button(Button.Props(B.addNewImageForm(), CommonStyle.default, P.addStyles, "", P.title, className = P.className), P.buttonName, P.childrenElement),
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

  case class State(updateUserRequest: UpdateUserRequest, postNewImage: Boolean = false)

  val getUsers = SYNEREOCircuit.connect(_.user)

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(): Callback = Callback {
      // logger.log.info("new Image modal mounted")
    }

    def updateImgSrc(e: ReactEventI): react.Callback = Callback {
      val value = e.target.files.item(0)
      val reader = new FileReader()
      reader.onload = (e: UIEvent) => {
        val contents = reader.result.asInstanceOf[String]
        val props = t.props.runNow()
        val uri = SYNEREOCircuit.zoom(_.user.sessionUri).value
        t.modState(s => s.copy(updateUserRequest = s.updateUserRequest.copy(sessionURI = uri, jsonBlob = JsonBlob(imgSrc = contents, name = props.proxy().name)))).runNow()
      }
      reader.readAsDataURL(value)
      $("#image_upload_error".asInstanceOf[js.Object]).addClass("hidden")
    }

    def submitForm(e: ReactEventI): Callback = {
      e.preventDefault()
      if (t.state.runNow().updateUserRequest.jsonBlob.imgSrc.length < 2) {
        $("#image_upload_error".asInstanceOf[js.Object]).removeClass("hidden")
        t.modState(s => s.copy(postNewImage = false))
      } else {
        SYNEREOCircuit.dispatch(PostUserUpdate(t.state.runNow().updateUserRequest))
        t.modState(s => s.copy(postNewImage = true))
      }
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
                  if (s.updateUserRequest.jsonBlob.imgSrc.length < 2) {
                    <.img(^.src := p.proxy.value.imgSrc, UserProfileViewCSS.Style.userImage)
                  } else {
                    <.img(^.src := s.updateUserRequest.jsonBlob.imgSrc, UserProfileViewCSS.Style.userImage)
                  }

                )
              ),
              <.div(^.className := "row",
                <.div(^.className := "col-md-12")(
                  <.input(^.`type` := "file", ^.id := "files", ^.name := "files", ^.onChange ==> updateImgSrc, ^.marginTop := "40.px"),
                  <.div(^.id := "image_upload_error", ^.className := "hidden text-danger")(
                    "Please provide Image to upload ... !!!"
                  )
                )
              ),
              <.div(^.className := "row",
                <.div(^.className := "col-md-12 text-right", UserProfileViewCSS.Style.newImageSubmitBtnContainer,
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
    .initialState_P(p => State(new UpdateUserRequest()))
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
