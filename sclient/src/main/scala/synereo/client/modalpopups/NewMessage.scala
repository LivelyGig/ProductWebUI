package synereo.client.modalpopups

import java.util.UUID

import diode.react.ModelProxy
import japgolly.scalajs.react
import shared.models.{Label, MessagePost, MessagePostContent}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import shared.RootModels.SearchesRootModel
import shared.dtos.LabelPost
import shared.sessionitems.SessionItems
import synereo.client.components.GlobalStyles
import synereo.client.components._
import synereo.client.components.Icon.Icon
import synereo.client.css.NewMessageCSS
import synereo.client.handlers.{CreateLabels, PostData, RefreshMessages}
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.logger
//import diode.AnyAction._
import scala.scalajs.js
import org.scalajs.dom.FileReader
import org.scalajs.dom.raw.UIEvent

import scala.concurrent.Future

//scalastyle:off
object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())

  case class State(showNewMessageForm: Boolean = false)

//  val getSearches =  SYNEREOCircuit.connect(_.searches)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addNewMessageForm(): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addMessage(/*submitForm:PostMessage*/): Callback = {
      //log.debug(s"addNewAgent signUpModel : ${signUpModel} ,addNewAgent: ${showNewMessageForm}")
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
        if (S.showNewMessageForm) SYNEREOCircuit.connect(_.searches)(searchesProxy => NewMessageForm(NewMessageForm.Props(B.addMessage, "New Message", searchesProxy)))
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
                   labelsSelectizeInputId: String = "labelsSelectizeInputId", labelModel: Label, postLabel: Boolean = false)

//  val getUsers = SYNEREOCircuit.connect(_.user)
//  val getConnections =  SYNEREOCircuit.connect(_.connections)
//  val getSearches =  SYNEREOCircuit.connect(_.searches)

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateSubject(e: ReactEventI) = {
      val value = e.target.value
      //            println(value)
      //      t.modState(s => s.copy(submitForm = s.submitForm.copy(postContent = s.submitForm.postContent.copy(subject = value))))
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }

    def updateContent(e: ReactEventI) = {
      val value = e.target.value
      //            println(value)
      //      t.modState(s => s.copy(submitForm = s.submitForm.copy(postContent = s.submitForm.postContent.copy(text = value))))
      t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value)))
    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(): Callback = Callback {

    }

    def getLabelsFromText(): Seq[String] = {
      val value = t.state.runNow().postMessage.text.split(" +")
      value.filter(
        _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
      ).map(_.replace("#", "")).toSet.toSeq
    }

    def postLabels(label: String = ""): Future[String] = {
      val labelPost = LabelPost(dom.window.sessionStorage.getItem(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI), leafParser(), "alias")
      //      println("labelPost = " + labelPost)
      CoreApi.postLabel(labelPost)
    }


    def leafParser(): Seq[String] = {
      val (props, state) = (t.props.runNow(), t.state.runNow())
      println(s"state.labelModel: ${state.labelModel}")
      def leaf(text: String, color: String) = "leaf(text(\"" + s"${text}" + "\"),display(color(\"" + s"${color}" + "\"),image(\"\")))"
       props.proxy().searchesModel.map(e => leaf(e.text, e.color)) ++ getLabelsFromText.map(e => leaf(e, "#CC5C64"))
    }

    def getLabelsToStore() : Seq[String] = {
      val (props, state) = (t.props.runNow(), t.state.runNow())
      def leafMod(text: String, color: String) = "\"leaf(text(\\\"" + s"${text}" + "\\\"),display(color(\\\"" + s"${color}" + "\\\"),image(\\\"\\\")))\""
      props.proxy().searchesModel.map(e => leafMod(e.text, e.color)) ++ getLabelsFromText.map(e => leafMod(e, "#CC5C64"))
    }

    def updateImgSrc(e: ReactEventI): react.Callback = Callback{
      val value = e.target.files.item(0)
      println("Img src = " + value)
      var reader = new FileReader()
      reader.onload = (e: UIEvent) => {
        val contents = reader.result.asInstanceOf[String]
        println(s"in on load $contents")
        t.modState(s => s.copy(postMessage = s.postMessage.copy(imgSrc = contents))).runNow()
      }
      reader.readAsDataURL(value)
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      postLabels().onComplete {
        case Success(responseArray) =>
          dom.window.sessionStorage.setItem(SessionItems.SearchesView.LIST_OF_LABELS, s"[${getLabelsToStore().mkString(",")}]")
          SYNEREOCircuit.dispatch(PostData(state.postMessage,
            Some(state.connectionsSelectizeInputId), SessionItems.MessagesViewItems.MESSAGES_SESSION_URI, Some(state.labelsSelectizeInputId), getLabelsFromText))
          SYNEREOCircuit.dispatch(CreateLabels())
          t.modState(s => s.copy(postNewMessage = true)).runNow()
        case Failure(res) =>
          logger.log.debug("Label Post failure")
          t.modState(s => s.copy(postNewMessage = false))
      }
      t.modState(s => s.copy(postNewMessage = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.submitForm*/)
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.className := "hidden", ^.onClick --> hide, Icon.close), <.div(^.className := "hide")(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(
            SYNEREOCircuit.connect(_.user)(proxy => UserPersona(UserPersona.Props(proxy)))
          ),
          <.div(^.className := "row")(
            <.div(^.id := s.connectionsSelectizeInputId)(
              SYNEREOCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s.connectionsSelectizeInputId)))
            ),
            <.div(NewMessageCSS.Style.textAreaNewMessage, ^.id := s.labelsSelectizeInputId)(
              SYNEREOCircuit.connect(_.searches)(searchesProxy => LabelsSelectize(LabelsSelectize.Props(searchesProxy, s.labelsSelectizeInputId)))
            ),
//            <.div()(
////              <.input(^.`type` := "file", ^.onChange ==> updateImgSrc),
//              if (s.postMessage.imgSrc != ""){
//                <.img(^.src := s.postMessage.imgSrc)
//              } else {
//                <.div("")
//              }
//
//            ),
            <.div()(
              <.textarea(^.rows := 1, ^.placeholder := "Title your post", ^.value := s.postMessage.subject, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> updateSubject, ^.required := true)
            ),
            <.div()(
              <.textarea(^.rows := 4, ^.placeholder := "Your thoughts. ", ^.value := s.postMessage.text, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> updateContent, ^.required := true)
            )
          ),
          <.div(^.className := "row")(
            <.div()(
              //              <.input(^.`type` := "file", ^.onChange ==> updateImgSrc),
              if (s.postMessage.imgSrc != ""){
                <.img(^.src := s.postMessage.imgSrc)
              } else {
                <.div("")
              }

            ),
            <.div(^.className := "text-left text-muted")(
              <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.postingShortHandBtn, <.span(^.marginRight := "4.px")(Icon.infoCircle), "posting shorthand")
            ),
            <.div(^.className := "text-right", NewMessageCSS.Style.newMessageActionsContainerDiv)(
              <.div(^.className := "pull-left")(
                <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, <.span(Icon.camera)),
              <.label( ^.`for` := "files")(<.span(^.tpe := "button",  ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn,Icon.paperclip)),
                <.input(^.`type` := "file", ^.visibility :="hidden", ^.position:= "absolute" , ^.id:="files", ^.name:="files", ^.onChange ==> updateImgSrc)
              ),
              <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> hide, "Cancel"),
              <.button(^.tpe := "submit", ^.className := "btn btn-default", NewMessageCSS.Style.createPostBtn, /*^.onClick --> hide, */ "Create")
            )
          ) //                <.div(bss.modal.footer)
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("PostNewMessage")
    //.initialState_P(p => State(p=> new MessagesData("","","")))
    .initialState_P(p => State(new MessagePostContent(), labelModel = Label()))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postNewMessage) {
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted())
    //      .shouldComponentUpdate(scope => false)
    .build

  def apply(props: Props) = component(props)
}
