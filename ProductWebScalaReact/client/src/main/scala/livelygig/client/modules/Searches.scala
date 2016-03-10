package livelygig.client.modules

import livelygig.client.components.{Icon}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.handlers.{RefreshMessages, SearchWithLabels, UpdateLabel, CreateLabels}
import livelygig.client.rootmodels.SearchesRootModel
import livelygig.client.css._
import livelygig.client.dtos.{Connection, ExpressionContent, Expression, SubscribeRequest}
import livelygig.client.models.{Label, UserModel}
import livelygig.client.services.LGCircuit
import org.scalajs.dom._
import scala.annotation.tailrec
import scalacss.ScalaCssReact._
import org.querki.facades.bootstrap.datepicker._
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._

object Searches {

  case class Props(view: String, proxy: ModelProxy[SearchesRootModel])

  case class State(userModel: UserModel)

  case class Backend(t: BackendScope[Props, State]) {

    def searchClick(props: Props): Unit = {
      println("in searchClick")
      window.sessionStorage.setItem("messageSearchLabel","any([Splicious])")
      val label = props.proxy().searchesModel
      println(label)
      /*Callback{*/LGCircuit.dispatch(RefreshMessages())/*}.runNow()*/
//      SubscribeRequest(window.sessionStorage.getItem("sessionURI"), Expression(msgType = "feedExpr", ExpressionContent(Seq(Connection("", "", "")), "alias")))
    }

    def updateDate(e: ReactEventI) = {
      println(e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def mounted(props: Props): Callback = Callback {
    }

    /*def labelChecked: Leaf = Callback{

    }*/

    val baseOpts = BootstrapDatepickerOptions.
      autoclose(true).
      todayHighlight(true).
      todayBtnLinked().
      disableTouchKeyboard(true).
      orientation(Orientation.Top)
    // Iff this Date is Optional, show the Clear button:
    val opts =
      if (true)
        baseOpts.clearBtn(true)
      else
        baseOpts

    $().find("#availableToDate").datepicker(baseOpts)
    $().find("#availableFromDate").datepicker(baseOpts)
    $().find("#projectsStartDate").datepicker(baseOpts)
    $().find("#projectsEndDate").datepicker(baseOpts)
    $().find("#messagesBeforeDate").datepicker(baseOpts)
    $().find("#messagesFromDate").datepicker(baseOpts)

    //    $("#dateid").on("changeDate", { rawEvt:JQueryEventObject =>
    //      save()
    //    })

    def render(s: State, p: Props) = {

      p.view match {
        case "talent" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Profile Type")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Talent"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Client"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Moderator")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Available from")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Available to")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableToDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableToDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Capabilities")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. Web Development")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden")
                  )
                )
              )
            )
          )
        } //talent
        case "offerings" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended to Me"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended by Me"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Added before")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )

                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Added after")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                )
              )
            )
          )
        }
        case "projects" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Job Type")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject"), " Project"
                    ),
                    <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Hourly"
                      ),
                      <.br(),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed"), " Fixed Scope"
                      )
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Contest"
                    ),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Part-Time"
                    ),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Full-Time"
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Start after")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "projectsStartDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Finish before")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "projectsEndDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "projectsEndDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Skills Required")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 4, ^.placeholder := "e.g. Web Development")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Project State")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    //<.input(^.className:="form-control", LftcontainerCSS.Style.inputHeightWidth)
                    <.div(^.className := "btn-group")(
                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.border := "none", ^.paddingLeft := "0px")("Open ")(
                        <.span(^.className := "caret")
                      ),
                      <.ul(^.className := "dropdown-menu")(
                        <.li()(<.a()("Item 1")),
                        <.li()(<.a()("Item 2")),
                        <.li()(<.a()("Item 3"))
                      )
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Payment Escrow")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "paymentEscrow"), " Required"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "paymentEscrow", ^.checked := "true"), " Optional"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "paymentEscrow"), " None")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Deliverables Escrow")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "deliverablesEscrow"), " Required"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "deliverablesEscrow"), " Optional"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "deliverablesEscrow", ^.checked := "true"), " None")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden")
                  )
                )
              )
            )
          )
        } //project
        case "contract" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Status")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject"), " Initiating offer"
                    ),
                    <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Outstanding to me"
                      ),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Outstanding to other"
                      ),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Rejected by other"
                      ),
                      <.br(),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed"), " Rejected by me"
                      ),
                      <.br(),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed"), " Expired"
                      )
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Escrow"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " In Progress"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Feedback"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Completed")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Job ID")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. 70a48a34-2773-4f4f-9f92-8a261ee59ad7")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Client")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Britta")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Talent")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Abed")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden")
                  )
                )
              )
            )
          )
        }
        case "messages" => {
          //          @tailrec
          def renderLabel(label: Label): ReactTag = {
            val children = p.proxy().searchesModel.filter(p => p.parentUid == label.uid)
            if (!children.isEmpty) {
              <.li(LftcontainerCSS.Style.checkboxlabel)(
                <.label(^.`for` := "folder1", ^.margin := "0", ^.padding := "0"),
                <.input(^.`type` := "checkbox", ^.marginLeft := "20px", ^.checked := label.isChecked, ^.onChange --> p.proxy.dispatch(UpdateLabel(label.copy(isChecked = !label.isChecked)))),
                "  " + label.text,
                <.input(^.`type` := "checkbox", ^.className := "treeview", ^.id := "folder1"),
                <.ol(LftcontainerCSS.Style.checkboxlabel)(children map renderLabel))
            } else {

              <.li(LftcontainerCSS.Style.checkboxlabel)(

                <.input(^.`type` := "checkbox", ^.marginLeft := "20px", ^.checked := label.isChecked, ^.onChange --> p.proxy.dispatch(UpdateLabel(label.copy(isChecked = !label.isChecked)))), "  "
                  + label.text)
            }

          }
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button",^.onClick -->Callback{searchClick(p)}, ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("From")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "messagesFromDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "messagesFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("To")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "messagesBeforeDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "messagesBeforeDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Labels")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    if (p.proxy().searchesModel != Nil) {

                      <.ol(^.className := "tree", LftcontainerCSS.Style.checkboxlabel)(p.proxy().searchesModel.filter(e => e.parentUid == "self").map(p => renderLabel(p)))
                    }
                    else {
                      <.div("(none)")
                    }
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden")
                  )
                )
              )
            )
          )
        }
        case "connections" => /*MessagesPresets.component(p.ctl)*/ {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Available for Chat"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended by Me"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended to Me"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " with Talent Profile"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " with Employer Profile"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " with Moderator Profile"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Added before")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Added after")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "projectsAfterDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Groups")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. Brazil")
                  )
                )
              )
            )
          )
        }
      } //main switch

    }
  }


  private val component = ReactComponentB[Props]("Searches")
    .initialState_P(p => State(new UserModel("", "", "")))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      LGCircuit.dispatch(CreateLabels())
    })
    .build

  def apply(props: Props) = component(props)
}


