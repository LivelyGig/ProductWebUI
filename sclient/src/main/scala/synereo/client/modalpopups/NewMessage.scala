package synereo.client.modalpopups

import java.util.UUID

import diode.react.ModelProxy
import japgolly.scalajs.react
import shared.models.{Label, MessagePostContent}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.rootmodels.SearchesRootModel
import shared.dtos.LabelPost
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon.Icon
import synereo.client.css.{NewMessageCSS, SynereoCommanStylesCSS}
import synereo.client.services.SYNEREOCircuit

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.components.Bootstrap._
import org.scalajs.dom._
import org.scalajs.dom.raw.UIEvent
import synereo.client.utils.{ConnectionsUtils, LabelsUtils, MessagesUtils, SelectizeUtils}
import org.querki.jquery._
import synereo.client.handlers.SearchesModelHandler
import synereo.client.modalpopupbackends.NewMessageBackend

import scala.scalajs.js

//scalastyle:off
object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())

  case class State(showNewMessageForm: Boolean = false)

  val getSearches = SYNEREOCircuit.connect(_.searches)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addNewMessageForm(): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addMessage(): Callback = {
      t.modState(s => s.copy(showNewMessageForm = false))
    }
  }

  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(
        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = P.className), P.buttonName, P.childrenElement),
        //        if (S.showNewMessageForm) NewMessageForm(NewMessageForm.Props(B.addMessage, "New Message"))
        if (S.showNewMessageForm) getSearches(searchesProxy => NewMessageForm(NewMessageForm.Props(B.addMessage, "New Message", searchesProxy)))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object NewMessageForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: ( /*PostMessage*/ ) => Callback, header: String, proxy: ModelProxy[SearchesRootModel])

  case class State(postMessage: MessagePostContent, postNewMessage: Boolean = false,
                   connectionsSelectizeInputId: String = "connectionsSelectizeInputId",
                   labelsSelectizeInputId: String = "labelsSelectizeInputId", tags: Seq[String] = Seq())

  val getUsers = SYNEREOCircuit.connect(_.user)
  val getConnections = SYNEREOCircuit.connect(_.connections)
  val getSearches = SYNEREOCircuit.connect(_.searches)


  private val component = ReactComponentB[Props]("PostNewMessage")
    //.initialState_P(p => State(p=> new MessagesData("","","")))
    .initialState_P(p => State(new MessagePostContent()))
    .backend(new NewMessageBackend(_))
      .renderPS((t,P,S)=>{

        val headerText = P.header
        Modal(
          Modal.Props(
            // header contains a cancel button (X)
            header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.className := "hidden", ^.onClick --> hide, Icon.close), <.div(^.className := "hide")(headerText)),
            // this is called after the modal has been hidden (animation is completed)
            closed = () => t.backend.formClosed(S, P),
            id = "newMessage"
          ),
          <.form(^.onSubmit ==> t.backend.submitForm)(
            <.div(
              getUsers(proxy => UserPersona(UserPersona.Props(proxy)))
            ),
            <.div(^.className := "row")(
              <.div(^.id := S.connectionsSelectizeInputId)(
                ConnectionsSelectize(ConnectionsSelectize.Props(S.connectionsSelectizeInputId, t.backend.fromSelecize))
              ),
              <.div(^.id := "cnxnError", ^.className := "hidden text-danger", "Please provide atleast 1 Connection... !!!"),
              <.div(NewMessageCSS.Style.textAreaNewMessage, ^.id := S.labelsSelectizeInputId)(
                LabelsSelectize(LabelsSelectize.Props(S.labelsSelectizeInputId))
              ),
              <.div()(
                <.textarea(^.rows := 1, ^.placeholder := "Title your post", ^.value := S.postMessage.subject, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> t.backend.updateSubject, ^.required := true)
              ),
              <.div()(
                <.textarea(^.rows := 4, ^.placeholder := "Your thoughts. ", ^.value := S.postMessage.text, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> t.backend.updateContent, ^.required := true)
              ),
              <.div()

            ),
            <.div(^.className := "row")(
              <.div()(
                if (S.postMessage.imgSrc != "") {
                  <.img(^.src := S.postMessage.imgSrc, ^.height := "100.px", ^.width := "100.px")
                } else {
                  <.div("")
                }

              ),
              <.div(
                <.ul(^.className := "list-inline")(
                  for (tag <- S.tags.zipWithIndex) yield
                    <.li(^.className := "btn btn-primary", NewMessageCSS.Style.postTagBtn,
                      <.ul(^.className := "list-inline",
                        <.li(^.textTransform := "uppercase", tag._1),
                        <.li(/*<.span(^.className := "hidden", tag._2, ^.onClick ==> deleteInlineLabel),<.span*/
                          "data-count".reactAttr := tag._2, Icon.close, ^.onClick ==> t.backend.deleteInlineLabel
                        )
                      )
                    )
                )
              ),
              <.div(^.className := "text-left text-muted")(
                <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.postingShortHandBtn, <.span(^.marginRight := "4.px")(Icon.infoCircle), "posting shorthand")
              ),
              <.div(^.className := "text-right", NewMessageCSS.Style.newMessageActionsContainerDiv)(
                <.div(^.className := "pull-left")(
                  <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, SynereoCommanStylesCSS.Style.featureHide, <.span(Icon.camera)),
                  <.label(^.`for` := "files")(<.span(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, Icon.paperclip)),
                  <.input(^.`type` := "file", ^.visibility := "hidden", ^.position := "absolute", ^.id := "files", ^.name := "files", ^.onChange ==> t.backend.updateImgSrc)
                ),
                <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> t.backend.hide, "Cancel"),
                <.button(^.tpe := "submit", ^.className := "btn btn-default", NewMessageCSS.Style.createPostBtn, /*^.onClick --> hide, */ "Create")
              )
            ) //                <.div(bss.modal.footer)
          )
        )

      })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postNewMessage) {
        scope.$.backend.hideModal
      }
      //      scope.$.backend.createHashtag(scope.currentState.postMessage.text)
    })
    .componentDidMount(scope => scope.backend.mounted())
    //      .shouldComponentUpdate(scope => false)
    .build

  def apply(props: Props) = component(props)
}
