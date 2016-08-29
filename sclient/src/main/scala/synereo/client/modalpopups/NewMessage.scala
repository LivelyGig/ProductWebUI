package synereo.client.modalpopups

import java.util.UUID

import diode.react.ModelProxy
import japgolly.scalajs.react
import shared.models.{Label, MessagePost, MessagePostContent}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import synereo.client.rootmodels.SearchesRootModel
import shared.dtos.LabelPost
import synereo.client.sessionitems.SessionItems
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon.Icon
import synereo.client.css.{DashboardCSS, NewMessageCSS, SynereoCommanStylesCSS}
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
import org.scalajs.dom._
import org.scalajs.dom.raw.UIEvent
import synereo.client.utils.{ConnectionsUtils, LabelsUtils, MessagesUtils}
import diode.AnyAction._
import org.querki.jquery._
import synereo.client.handlers.{ContentHandler, SearchesModelHandler}

import scala.concurrent.Future
import scala.collection.mutable.ListBuffer
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

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateSubject(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }

    def updateContent(e: ReactEventI) = {
      val value = e.target.value
      val words: Seq[String] = value.split(" ")
      var tagsCreatedInline: Seq[String] = Seq()
      words.foreach(
        word => if (word.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")) {
          tagsCreatedInline = tagsCreatedInline :+ word
        }
      )
      //      tagsCreatedInline.exis
      //      println(tagsCreatedInline)
      t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value), tags = tagsCreatedInline))
    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(): Callback = Callback {

    }

    def filterLabelStrings(value: Seq[String]): Seq[String] = {
      value.filter(
        _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
      ).map(_.replace("#", "")).toSet.toSeq
    }

    def labelsTextFromMsg: Seq[String] = {
      filterLabelStrings(t.state.runNow().postMessage.text.split(" +"))
    }

    def labelsToPostMsg: Seq[Label] = {
      val textSeq = labelsTextFromMsg ++ filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(t.state.runNow().labelsSelectizeInputId))

      //      println(s"labelsToPostMsg ${textSeq.distinct}")
      textSeq.distinct.map(LabelsUtils.getLabelModel)
    }

    def getAllLabelsText: Seq[String] = {
      val (props, state) = (t.props.runNow(), t.state.runNow())
      val allLabels = props.proxy().searchesModel.map(e => e.text) ++ labelsTextFromMsg ++ filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(state.labelsSelectizeInputId))
      allLabels.distinct
    }

    def deleteInlineLabel(e: ReactEventI) = {
      val value = e.target.parentElement.getAttribute("data-count")
      val state = t.state.runNow()
      val newlist = for {
        (x, i) <- state.tags.zipWithIndex
        if i != value.toInt
      } yield x
      t.modState(state => state.copy(tags = newlist))
    }

    def updateImgSrc(e: ReactEventI): react.Callback = Callback {
      val value = e.target.files.item(0)
      println(s"value of img = ${value.size}")
      val reader = new FileReader()
      reader.onload = (e: UIEvent) => {
        val contents = reader.result.asInstanceOf[String]
        t.modState(s => s.copy(postMessage = s.postMessage.copy(imgSrc = contents))).runNow()
      }
      reader.readAsDataURL(value)
    }

    def fromSelecize(): Callback = Callback {}

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      val props = t.props.runNow()
      val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.connectionsSelectizeInputId)
      if (connections.length < 1) {
        $("#cnxnError".asInstanceOf[js.Object]).removeClass("hidden")
        t.modState(s => s.copy(postNewMessage = false))
      } else {
        $("#cnxnError".asInstanceOf[js.Object]).addClass("hidden")
        val cnxns = ConnectionsUtils.getCnxnForReq(ConnectionsSelectize.getConnectionsFromSelectizeInput(state.connectionsSelectizeInputId))
        val labelsToPost = (props.proxy().searchesModel.map(e => e.text) union getAllLabelsText distinct) diff props.proxy().searchesModel.map(e => e.text)
        if (labelsToPost.nonEmpty) {
          val labelPost = LabelPost(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, getAllLabelsText.map(SearchesModelHandler.leaf), "alias")
          ContentHandler.postLabelsAndMsg(labelPost, MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
        } else {
          ContentHandler.postMessage(MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
        }
        //          SYNEREOCircuit.dispatch(PostLabelsAndMsg(getAllLabelsText, MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg)))

        t.modState(s => s.copy(postNewMessage = true))
      }
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
          closed = () => formClosed(s, p),
          id = "newMessage"
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(
            getUsers(proxy => UserPersona(UserPersona.Props(proxy)))
          ),
          <.div(^.className := "row")(
            <.div(^.id := s.connectionsSelectizeInputId)(
              ConnectionsSelectize(ConnectionsSelectize.Props(s.connectionsSelectizeInputId, fromSelecize))
            ),
            <.div(^.id := "cnxnError", ^.className := "hidden text-danger", "Please provide atleast 1 Connection... !!!"),
            <.div(NewMessageCSS.Style.textAreaNewMessage, ^.id := s.labelsSelectizeInputId)(
              LabelsSelectize(LabelsSelectize.Props(s.labelsSelectizeInputId))
            ),
            <.div()(
              <.textarea(^.rows := 1, ^.placeholder := "Title your post", ^.value := s.postMessage.subject, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> updateSubject, ^.required := true)
            ),
            <.div()(
              <.textarea(^.rows := 4, ^.placeholder := "Your thoughts. ", ^.value := s.postMessage.text, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> updateContent, ^.required := true)
            ),
            <.div()

          ),
          <.div(^.className := "row")(
            <.div()(
              if (s.postMessage.imgSrc != "") {
                <.img(^.src := s.postMessage.imgSrc, ^.height := "100.px", ^.width := "100.px")
              } else {
                <.div("")
              }

            ),
            <.div(
              <.ul(^.className := "list-inline")(
                for (tag <- s.tags.zipWithIndex) yield
                  <.li(^.className := "btn btn-primary", NewMessageCSS.Style.postTagBtn,
                    <.ul(^.className := "list-inline",
                      <.li(^.textTransform := "uppercase", tag._1),
                      <.li(/*<.span(^.className := "hidden", tag._2, ^.onClick ==> deleteInlineLabel),<.span*/
                        "data-count".reactAttr := tag._2, Icon.close, ^.onClick ==> deleteInlineLabel
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
                <.input(^.`type` := "file", ^.visibility := "hidden", ^.position := "absolute", ^.id := "files", ^.name := "files", ^.onChange ==> updateImgSrc)
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
    .initialState_P(p => State(new MessagePostContent()))
    .renderBackend[Backend]
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
