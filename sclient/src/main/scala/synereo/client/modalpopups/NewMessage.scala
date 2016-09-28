package synereo.client.modalpopups

import java.util.UUID

import diode.react.ModelProxy
import japgolly.scalajs.react
import shared.models.{Label, MessagePost, MessagePostContent}
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
import synereo.client.utils.{ConnectionsUtils, ContentUtils, LabelsUtils, MessagesUtils}
import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom.raw.{FileReader, UIEvent}
import shared.dtos.LabelPost
import synereo.client.components.{ConnectionsSelectize, LabelsSelectize, _}
import synereo.client.handlers.SearchesModelHandler
import synereo.client.services.SYNEREOCircuit
import synereo.client.components.Bootstrap._
import synereo.client.facades.SynereoSelectizeFacade

import scala.scalajs.js

//scalastyle:off
object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())

  case class State(showNewMessageForm: Boolean = false)

  val searchesProxy = SYNEREOCircuit.connect(_.searches)

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
        if (S.showNewMessageForm) searchesProxy(searchesProxy => NewMessageForm(NewMessageForm.Props(B.addMessage, "New Message", searchesProxy)))
        else
          Seq.empty[ReactElement]
      )
    })
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object NewMessageForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: ( /*PostMessage*/ ) => Callback, header: String, proxy: ModelProxy[SearchesRootModel], messagePost: MessagePost = new MessagePost(postContent = new MessagePostContent()))

  case class State(postMessage: MessagePostContent, postNewMessage: Boolean = false,
                   connectionsSelectizeInputId: String = "connectionsSelectizeInputId",
                   labelsSelectizeInputId: String = "labelsSelectizeInputId", tags: Seq[String] = Seq())

  val userProxy = SYNEREOCircuit.connect(_.user)

  case class NewMessageBackend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateSubject(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }

    def updateContent(e: ReactEventI) =  {
      var value = e.target.value
      val words: Seq[String] = value.split(" ")
      var tagsCreatedInline: Seq[String] = Seq()
      words.foreach(
        word => if (word.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")) {
          tagsCreatedInline = tagsCreatedInline :+ word
        }
      )
      t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value), tags = tagsCreatedInline))
    }


    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    //    def willUnmount(): Callback = Callback {
    //      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
    //      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
    //    }

    def mounted(): Callback = Callback {
      val props = t.props.runNow()
      //      println(s"messagePost from Props : ${props.messagePost} ")
      t.modState(state => state.copy(postMessage = MessagePostContent(text = props.messagePost.postContent.text, subject = props.messagePost.postContent.subject))).runNow()
    }

    def filterLabelStrings(value: Seq[String]): Seq[String] = {
      value.filter(
        _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
      ).map(_.replace("#", "")).distinct
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
      //    println(s"filtered labels from selectize ${filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(state.labelsSelectizeInputId))}")
      val allLabels = props.proxy().searchesModel.map(e => e.text) ++ labelsTextFromMsg ++
        filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(state.labelsSelectizeInputId).map(label => s"#$label"))
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
      //    println(s"value of img = ${value.size}")
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
        //        val labelsToPost = (props.proxy().searchesModel.map(e => e.text) union allLabelsText distinct) diff props.proxy().searchesModel.map(e => e.text)
        val newLabels = LabelsUtils.getNewLabelsText(getAllLabelsText)
        if (newLabels.nonEmpty) {
          val labelPost = LabelPost(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, getAllLabelsText.map(SearchesModelHandler.leaf), "alias")
          ContentUtils.postLabelsAndMsg(labelPost, MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
          //          newLabels.foreach(label => SynereoSelectizeFacade.addOption("SearchComponentCnxnSltz-selectize", s"#$label", UUID.randomUUID().toString.replaceAll("-", "")))
          newLabels.foreach(label => SynereoSelectizeFacade.addOption("SearchComponentCnxnSltz-selectize", s"#$label", label))
        } else {
          ContentUtils.postMessage(MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
        }
        //          SYNEREOCircuit.dispatch(PostLabelsAndMsg(allLabelsText, MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg)))
        t.modState(s => s.copy(postNewMessage = true))
      }
    }

    def formClosed(state: NewMessageForm.State, props: NewMessageForm.Props): Callback = {
      props.submitHandler(/*state.submitForm*/)
    }
  }


  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State(new MessagePostContent()))
    .backend(new NewMessageBackend(_))
    .renderPS((t, P, S) => {
      val headerText = P.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, /*^.className := "hidden", */ ^.onClick --> hide, Icon.close), <.div(^.className := "hide")(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => t.backend.formClosed(S, P),
          id = "newMessage"
        ),
        <.form(^.onSubmit ==> t.backend.submitForm)(
          <.div(
            userProxy(proxy => UserPersona(UserPersona.Props(proxy)))
          ),
          <.div(^.className := "row")(
            <.div(^.id := S.connectionsSelectizeInputId)(
              ConnectionsSelectize(ConnectionsSelectize.Props(S.connectionsSelectizeInputId, t.backend.fromSelecize,
                enableAllContacts = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.nonEmpty))
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
    //    .componentWillUnmount(scope => scope.backend.willUnmount)
    //      .shouldComponentUpdate(scope => false)
    .build

  def apply(props: Props) = component(props)
}
