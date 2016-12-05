package client.modals

import shared.models.{Label, MessagePostContent}
import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components.Icon.Icon
import client.components._
import client.css.{DashBoardCSS, ProjectCSS}
import client.modules.AppModule
import japgolly.scalajs.react
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import client.utils.{AppUtils, ConnectionsUtils, ContentUtils, LabelsUtils}
import scala.scalajs.js
import org.scalajs.dom.FileReader
import org.scalajs.dom.raw.UIEvent
import diode.AnyAction._

object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String)

  case class State(showNewMessageForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addNewMessageForm(): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addMessage(/*postMessage:PostMessage*/): Callback = {
      t.modState(s => s.copy(showNewMessageForm = false))
    }
  }

  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(/*ProjectCSS.Style.displayInitialbtn*/
        /*, ^.onMouseOver --> B.displayBtn*/)(
        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = "profile-action-buttons"), P.buttonName),
        if (S.showNewMessageForm) NewMessageForm(NewMessageForm.Props(B.addMessage, "New Message"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

// #todo think about better way for getting data from selectize input
// so that you don't have to pass the parentId explicitly
object NewMessageForm {

  val messageID: js.Object = "#messageID"
//  $("#a".asInstanceOf[js.Object]).validator(ValidatorOptions.validate())

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: ( /*PostMessage*/ ) => Callback, header: String)

  case class State(postMessage: MessagePostContent, postNewMessage: Boolean = false,
                   cnxsSelectizeParentId: String = "postNewMessageSelectizeInput", labelSelectizeParentId: String = "labelsSelectizeParent")

  case class Backend(t: BackendScope[Props, State]) {

    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }

    def updateSubject(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }

    def updateContent(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value)))
    }


    def fromSelecize(): Callback = Callback {}

    def updateImgSrc(e: ReactEventI): react.Callback = Callback {
      val value = e.target.files.item(0)
      val reader = new FileReader()
      reader.onload = (e: UIEvent) => {
        val contents = reader.result.asInstanceOf[String]
        t.modState(s => s.copy(postMessage = s.postMessage.copy(imgSrc = contents))).runNow()
      }
      reader.readAsDataURL(value)
    }

    def hideModal(): Unit = {
      $(t.getDOMNode()).modal("hide")
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
      val textSeq = labelsTextFromMsg ++ filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(t.state.runNow().labelSelectizeParentId))
      textSeq.distinct.map(LabelsUtils.getLabelModel)
    }

    def getAllLabelsText: Seq[String] = {
      val (props, state) = (t.props.runNow(), t.state.runNow())
      val allLabels = LGCircuit.zoom(_.searches).value.searchesModel.map(e => e.text) ++ labelsTextFromMsg ++ filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(state.labelSelectizeParentId))
      allLabels.distinct
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val state = t.state.runNow()
      val messageID: js.Object = "#messageID"
      if ($(messageID).hasClass("disabled"))
        t.modState(s => s.copy(postNewMessage = false))
      else {
        val cnxns = ConnectionsUtils.getCnxnForReq(ConnectionsSelectize.getConnectionsFromSelectizeInput(state.cnxsSelectizeParentId), AppModule.MESSAGES_VIEW)
        ContentUtils.postLabelsAndMsg(getAllLabelsText, AppUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg, AppModule.MESSAGES_VIEW))
        t.modState(s => s.copy(postNewMessage = true))
      }

      t.modState(s => s.copy(postNewMessage = true))

    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.postMessage*/)
    }

    // scalastyle:off
    def render(s: State, p: Props) = {
      val connectionsProxy = LGCircuit.connect(_.connections)
      val searchesProxy = LGCircuit.connect(_.searches)
      val headerText = p.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.form(^.id := "a", ^.onSubmit ==> submitForm ,"data-toggle".reactAttr := "validator")(
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(),
            /*<.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("To"))
            ),*/
            /*val selectizeControl : js.Object =*/
            <.div(^.id := s.cnxsSelectizeParentId)(
              //              val to = "{\"source\":\"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias\", \"label\":\"34dceeb1-65d3-4fe8-98db-114ad16c1b31\",\"target\":\"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias\"}"
              connectionsProxy(connectionsProxy => ConnectionsSelectize(ConnectionsSelectize.Props(connectionsProxy, s.cnxsSelectizeParentId, fromSelecize)))
            ),
            <.div(DashBoardCSS.Style.paddingTop10px, ^.id := s.labelSelectizeParentId)(
              searchesProxy(searchesProxy => LabelsSelectize(LabelsSelectize.Props(searchesProxy, "labelsSelectizeParent")))
            ),
            <.div(DashBoardCSS.Style.paddingTop10px)(
              <.input(^.`type` := "file", ^.onChange ==> updateImgSrc),
              if (s.postMessage.imgSrc != "") {
                <.img(DashBoardCSS.Style.imgSize, ^.src := s.postMessage.imgSrc)
              } else {
                <.div("")
              }

            ),
            <.div()(
              //              <.div(^.className:="form-group")(
              <.textarea(^.rows := 6, ^.placeholder := "Subject", ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.value := s.postMessage.subject, ^.onChange ==> updateSubject, ^.required := true)
            ),
            <.div()(
              //              <.div(^.className:="form-group")(
              <.textarea(^.rows := 6, ^.placeholder := "Enter your message here:", ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.value := s.postMessage.text, ^.onChange ==> updateContent, ^.required := true)
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
              //              <.div(^.className:="form-group")(
              <.button(^.tpe := "submit", ^.id := "messageID", ^.className := "btn", DashBoardCSS.Style.marginLeftCloseBtn, /*DashBoardCSS.Style.btnDefault,*/ /*^.onClick --> hide, */ "Send"),
              <.button(^.tpe := "button", ^.className := "btn", DashBoardCSS.Style.btnDefault, DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide, "Cancel")
            )
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
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
    })
    .componentDidMount(scope => scope.backend.mounted())
    //      .shouldComponentUpdate(scope => false)
    .build

  def apply(props: Props) = component(props)
}
