package synereo.client.modalpopups

import diode.react.ModelProxy
import shared.models.{Label, MessagePost, MessagePostContent}
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.rootmodels.SearchesRootModel
import synereo.client.components.GlobalStyles
import synereo.client.css.NewMessageCSS

import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap.Modal
import synereo.client.utils.{ConnectionsUtils, ContentUtils, LabelsUtils, MessagesUtils}
import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom.raw.{FileReader, UIEvent}
import org.widok.moment.Moment
import shared.dtos.LabelPost
import synereo.client.components.{ConnectionsSelectize, LabelsSelectize, _}
import synereo.client.handlers.{SearchesModelHandler, SetPreventNavigation}
import synereo.client.services.{RootModel, SYNEREOCircuit}
import synereo.client.components.Bootstrap._
import synereo.client.facades.SynereoSelectizeFacade
import diode.AnyAction._
import diode.{ModelR, ModelRO}

import scala.scalajs.js

//scalastyle:off
//object NewMessage {
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String, className: String = "", childrenElement: ReactTag = <.span())
//
//  case class State(showNewMessageForm: Boolean = false)
//
//  val searchesProxy = SYNEREOCircuit.connect(_.searches)
//
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//    def mounted(props: Props): Callback = {
//      t.modState(s => s.copy(showNewMessageForm = true))
//    }
//
//    def addNewMessageForm(): Callback = {
//      t.modState(s => s.copy(showNewMessageForm = true))
//    }
//
//    def addMessage(): Callback = {
//      t.modState(s => s.copy(showNewMessageForm = false))
//    }
//  }
//
//  val component = ReactComponentB[Props]("NewMessage")
//    .initialState(State())
//    .backend(new Backend(_))
//    .renderPS(($, P, S) => {
//      val B = $.backend
//      <.div(
//        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = P.className), P.buttonName, P.childrenElement),
//        if (S.showNewMessageForm) searchesProxy(searchesProxy => NewMessageForm(NewMessageForm.Props(B.addMessage, "New Message", searchesProxy)))
//        else
//          Seq.empty[ReactElement]
//      )
//    })
//    .configure(OnUnmount.install)
//    .build
//
//  def apply(props: Props) = component(props)
//}

object NewMessageForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: ( /*PostMessage*/ ) => Callback,
                   header: String,
                   proxy: ModelProxy[SearchesRootModel],
                   messagePost: MessagePost = new MessagePost(postContent = new MessagePostContent()),
                   replyPost: Boolean = false)

  case class State(postMessage: MessagePostContent,
                   postNewMessage: Boolean = false,
                   //keep this selectize id same as, a css class is used for@allContacts hide list feature
                   connectionsSelectizeInputId: String = "connectionsSelectizeInputId",
                   //keep this selectize id same as, a css class has been overiden for avoiding overflowing contents out of window using this id
                   //see synereo-main.less for more details
                   labelsSelectizeInputId: String = "labelsSelectizeInputId",
                   tags: Seq[String] = Seq(),
                   lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  private val userProxy = SYNEREOCircuit.connect(_.user)

  case class NewMessageBackend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateSubject(e: ReactEventI) = {
      val value = e.target.value
      SYNEREOCircuit.dispatch(SetPreventNavigation())
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def updateContent(e: ReactEventI) = {
      val value = e.target.value
      SYNEREOCircuit.dispatch(SetPreventNavigation())
      //      val tagsCreatedInline = getTagsWithoutPunctuations(value.split(" ").filter(_.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")).distinct)
      val tagsCreatedInline = getTagsWithoutPunctuations(filterLabelStrings(value.split(" ")))
      t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value), tags = tagsCreatedInline))
    }

    /**
      *
      * @param hashTagsCreated :List of hashtags  which we fetched from text area depending on matched regular expr
      * @return List of Tags with removed the punctuation marks
      */
    def getTagsWithoutPunctuations(hashTagsCreated: Seq[String]) = hashTagsCreated flatMap { tag =>
      "[a-zA-Z]+".r findAllIn tag map (_.toLowerCase)
    }


    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(): Callback = {
      val props = t.props.runNow()
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
      if (props.replyPost) {
        val contentHeader = s"Date : ${Moment(props.messagePost.created).format("LLL").toLocaleString} \nFrom : ${props.messagePost.sender.name} \nTo : ${props.messagePost.receivers.map(_.name).mkString(", ")} \n-------------------------------------------------------------------"
        t.modState(state => state.copy(postMessage = MessagePostContent(text = contentHeader, subject = s"Re : ${props.messagePost.postContent.subject}")))

      } else {
        t.modState(state => state.copy(postMessage = MessagePostContent(text = props.messagePost.postContent.text,
          subject = props.messagePost.postContent.subject)))
      }
    }

    /**
      *
      * @param value
      * @return unique filtered labels without the hash symbol
      */
    def filterLabelStrings(value: Seq[String]): Seq[String] = {
      value.filter(
        _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
      ).map(_.replace("#", "")).distinct
    }

    def labelsToPostMsg: Seq[Label] = {
      val state = t.state.runNow()
      val textSeq = state.tags ++ filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(t.state.runNow().labelsSelectizeInputId))
      textSeq.distinct.map(LabelsUtils.getLabelModel)
    }

    def getAllLabelsText: Seq[String] = {
      val (props, state) = (t.props.runNow(), t.state.runNow())
      val allLabels = props.proxy().searchesModel.map(e => e.text) ++ state.tags ++
        filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(state.labelsSelectizeInputId).map(label => s"#$label"))
      allLabels.distinct
    }

    def deleteInlineLabel(e: ReactEventI) = {
      val value = e.target.parentElement.getAttribute("data-count")
      val state = t.state.runNow()
      val ListWithTagsDeleted = for {
        (x, i) <- state.tags.zipWithIndex
        if i != value.toInt
      } yield x
      t.modState(state => state.copy(tags = ListWithTagsDeleted))
    }

    def clearImage(e: ReactEventI) = {
      t.modState(s => s.copy(postMessage = s.postMessage.copy(imgSrc = "")))
    }

    def updateImgSrc(e: ReactEventI): react.Callback = Callback {
      val value = e.target.files.item(0)
      if (value.`type` == "image/jpeg" || value.`type` == "image/png")
        $("#file-type-not-supported-err".asInstanceOf[js.Object]).addClass("hidden")
      else
        $("#file-type-not-supported-err".asInstanceOf[js.Object]).removeClass("hidden")
      if (value.size <= 4000000) {
        val reader = new FileReader()
        reader.onload = (e: UIEvent) => {
          val contents = reader.result.asInstanceOf[String]
          t.modState(s => s.copy(postMessage = s.postMessage.copy(imgSrc = contents))).runNow()
        }
        reader.readAsDataURL(value)
        $("#no-image-upload-err".asInstanceOf[js.Object]).addClass("hidden")
        $("#image-size-upload-err".asInstanceOf[js.Object]).addClass("hidden")
      } else {
        $("#image-size-upload-err".asInstanceOf[js.Object]).removeClass("hidden")
      }
    }

    def fromSelecize(): Callback = Callback {}

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.connectionsSelectizeInputId)
      if (connections.length < 1) {
        $("#cnxn-error".asInstanceOf[js.Object]).removeClass("hidden")
        t.modState(s => s.copy(postNewMessage = false))
      } else {
        $("#cnxn-error".asInstanceOf[js.Object]).addClass("hidden")
        val cnxns = ConnectionsUtils.getCnxnForReq(ConnectionsSelectize.getConnectionsFromSelectizeInput(state.connectionsSelectizeInputId))
        val newLabels = LabelsUtils.getNewLabelsText(getAllLabelsText)
        if (newLabels.nonEmpty) {
          val labelPost = LabelPost(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, getAllLabelsText.map(SearchesModelHandler.leaf), "alias")
          ContentUtils.postLabelsAndMsg(labelPost, MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
          //          newLabels.foreach(label => SynereoSelectizeFacade.addOption("SearchComponentCnxnSltz-selectize", s"#$label", UUID.randomUUID().toString.replaceAll("-", "")))
          newLabels.foreach(label => SynereoSelectizeFacade.addOption("SearchComponentCnxnSltz-selectize", s"#$label", label))
        } else {
          ContentUtils.postMessage(MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
        }
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
    .renderPS((t, props, state) => {
      val headerText = props.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, /*^.className := "hidden", */ ^.onClick --> hide, Icon.close), <.div(^.className := "hide")(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => t.backend.formClosed(state, props),
          id = "newMessage"
        ),
        <.form(^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "container-fluid")(
            <.div(
              userProxy(proxy => UserPersona(UserPersona.Props(proxy)))
            ),
            <.div(^.className := "row")(
              <.div(^.id := state.connectionsSelectizeInputId, ^.width := "100%")(
                ConnectionsSelectize(ConnectionsSelectize.Props(state.connectionsSelectizeInputId, t.backend.fromSelecize, Option(0), props.messagePost.receivers, props.messagePost.sender, props.replyPost,
                  enableAllContacts = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.nonEmpty)) //,
              ),
              <.div(^.id := "cnxn-error", ^.className := "hidden text-danger",
                state.lang.selectDynamic("PROVIDE_ATLEAST_ONE_CONNECTION").toString),
              <.div(NewMessageCSS.Style.textAreaNewMessage, ^.id := state.labelsSelectizeInputId, ^.width := "100%")(
                LabelsSelectize(LabelsSelectize.Props(state.labelsSelectizeInputId))
              )),
            <.div(^.className := "row")(
              <.div()(
                <.textarea(^.rows := 1, ^.placeholder := state.lang.selectDynamic("TITLE_YOUR_POST").toString, ^.value := state.postMessage.subject, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> t.backend.updateSubject, ^.required := true)
              ),
              <.div()(
                <.textarea(^.rows := 4, ^.placeholder := state.lang.selectDynamic("YOUR_THOUGHTS").toString, ^.value := state.postMessage.text, NewMessageCSS.Style.textAreaNewMessage, ^.onChange ==> t.backend.updateContent, ^.required := true)
              )
            ),
            <.div(^.className := "row")(
              <.div()(
                if (state.postMessage.imgSrc != "") {
                  <.img(^.src := state.postMessage.imgSrc, ^.height := "100.px", ^.width := "100.px")
                } else if ((props.messagePost.postContent.imgSrc != "") && (props.replyPost == false)) {
                  <.img(^.src := props.messagePost.postContent.imgSrc, ^.height := "100.px", ^.width := "100.px")
                } else {
                  <.span()
                }
              ),
              <.div(
                <.ul(^.className := "list-inline")(
                  for (tag <- state.tags.zipWithIndex) yield
                    <.li(^.className := "btn btn-primary", NewMessageCSS.Style.createPostTagBtn,
                      <.ul(^.className := "list-inline",
                        <.li(^.textTransform := "uppercase", tag._1),
                        <.li(<.span(^.className := "hidden", tag._2, ^.onClick ==> t.backend.deleteInlineLabel), <.span(
                          "data-count".reactAttr := tag._2, Icon.close, ^.onClick ==> t.backend.deleteInlineLabel
                        )
                        )
                      )
                    ))
              ),
              <.div(^.className := "text-left text-muted")(
                <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.postingShortHandBtn, <.span(^.marginRight := "4.px")(Icon.infoCircle), "posting shorthand")
              ),
              <.div(^.className := "text-right", NewMessageCSS.Style.newMessageActionsContainerDiv)(
                <.div(^.className := "pull-left")(
                  <.button(^.onClick ==> t.backend.clearImage, ^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, <.span(Icon.close)),
                  <.label(^.`for` := "files")(<.span(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, Icon.paperclip)),
                  <.input(^.`type` := "file", ^.visibility := "hidden", ^.accept := "image/*", ^.position := "absolute", ^.id := "files", ^.name := "files", ^.onChange ==> t.backend.updateImgSrc),
                  <.div(^.id := "no-image-upload-err", ^.className := "hidden text-danger")(
                    state.lang.selectDynamic("PROVIDE_A_PICTURE_FILE_TO_UPLOAD").toString
                  ),
                  <.div(^.id := "image-size-upload-err", ^.className := "hidden text-danger")(
                    state.lang.selectDynamic("PROVIDE_A_PICTURE_FILE_LESS_THAN_FIVE_MB_UPLOAD").toString
                  ),
                  <.div(^.id := "file-type-not-supported-err", ^.className := "hidden text-danger")(
                    state.lang.selectDynamic("ONLY_JPEG_OR_PNG_FILES_CAN_BE_UPLOADED").toString
                  )
                ),
                <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn,
                  ^.onClick --> t.backend.hide, state.lang.selectDynamic("CANCEL_BTN").toString),
                <.button(^.tpe := "submit", ^.className := "btn btn-default", NewMessageCSS.Style.createPostBtn,
                  state.lang.selectDynamic("CREATE_POST").toString)
              )
            )
          )
        )
      )
    })
    .componentWillMount(scope => scope.backend.mounted())
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postNewMessage) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}
