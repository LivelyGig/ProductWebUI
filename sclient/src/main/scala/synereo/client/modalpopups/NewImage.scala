package synereo.client.modalpopups

import diode.react.ModelProxy
import japgolly.scalajs.react.vdom.prefix_<^._
import scala.scalajs.js
import synereo.client.components.{GlobalStyles, _}
import synereo.client.css.{NewMessageCSS, UserProfileViewCSS}
import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap.Modal
import shared.dtos.{ UpdateUserRequest}
import shared.models.UserModel
import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom.raw.{FileReader, UIEvent}
import shared.dtos.JsonBlob
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils.ContentUtils

//object NewImage {
//
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), icon: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())
//
//  case class State(showNewImageForm: Boolean = false)
//
//  val userProxy = SYNEREOCircuit.connect(_.user)
//
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//    def mounted(props: Props): Callback = {
//      t.modState(s => s.copy(showNewImageForm = true))
//    }
//
//    def addNewImageForm(): Callback = {
//      t.modState(s => s.copy(showNewImageForm = true))
//    }
//
//    def addImage(/*submitForm:PostMessage*/): Callback = {
//      t.modState(s => s.copy(showNewImageForm = false))
//    }
//  }
//
//  val component = ReactComponentB[Props]("ProfileImageUploader")
//    .initialState(State())
//    .backend(new Backend(_))
//    .renderPS(($, P, S) => {
//      val B = $.backend
//      <.div(
//        Button(Button.Props(B.addNewImageForm(), CommonStyle.default, P.addStyles, "", P.title, className = P.className), P.buttonName, P.childrenElement),
//        if (S.showNewImageForm) userProxy(userProxy => ProfileImageUploaderForm(ProfileImageUploaderForm.Props(B.addImage, "Profile Picture", userProxy)))
//        else
//          Seq.empty[ReactElement]
//      )
//    })
//    .configure(OnUnmount.install)
//    .build
//
//  def apply(props: Props) = component(props)
//}

object ProfileImageUploaderForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, header: String, proxy: ModelProxy[UserModel])

  case class State(updateUserRequest: UpdateUserRequest, postNewImage: Boolean = false)

  val getUsers = SYNEREOCircuit.connect(_.user)

  case class NewImgBackend(t: BackendScope[Props, State]) {
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
        val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
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
        ContentUtils.postUserUpdate(t.state.runNow().updateUserRequest)
        t.modState(s => s.copy(postNewImage = true))
      }
    }

    def formClosed(state: ProfileImageUploaderForm.State, props: ProfileImageUploaderForm.Props): Callback = {
      props.submitHandler(/*state.submitForm*/)
    }
  }

  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State(new UpdateUserRequest()))
    .backend(new NewImgBackend(_))
    .renderPS((t, P, S) => {
      Modal(
        Modal.Props(
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.h4(^.className := "pull-left")(P.header)),
          closed = () => t.backend.formClosed(S, P),
          id = "newImage"
        ),
        <.form(^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12")(
              <.div(^.className := "row",
                <.div(^.className := "col-md-12")(
                  if (S.updateUserRequest.jsonBlob.imgSrc.length < 2) {
                    <.img(^.src := P.proxy.value.imgSrc, UserProfileViewCSS.Style.userImage)
                  } else {
                    <.img(^.src := S.updateUserRequest.jsonBlob.imgSrc, UserProfileViewCSS.Style.userImage)
                  }

                )
              ),
              <.div(^.className := "row",
                <.div(^.className := "col-md-12")(
                  <.input(^.`type` := "file", ^.id := "files", ^.name := "files", ^.onChange ==> t.backend.updateImgSrc, ^.marginTop := "40.px"),
                  <.div(^.id := "image_upload_error", ^.className := "hidden text-danger")(
                    "Please provide a picture to upload ... !!!"
                  )
                )
              ),
              <.div(^.className := "row",
                <.div(^.className := "col-md-12 text-right", UserProfileViewCSS.Style.newImageSubmitBtnContainer,
                  <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> t.backend.hide, "Cancel"),
                  <.button(^.tpe := "submit", ^.className := "btn btn-default", NewMessageCSS.Style.createPostBtn, /*^.onClick --> hide, */ "Set Picture")
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
