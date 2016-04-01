package client.modules

import client.components.{Icon}
import diode.data.Pot
import diode.react._
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.handlers.{RefreshMessages, SubscribeSearch, UpdateLabel, CreateLabels}
import client.rootmodels.{ConnectionsRootModel, SearchesRootModel}
import client.css._
import org.denigma.selectize
import shared.dtos.{Connection, ExpressionContent, Expression, SubscribeRequest}
import client.models.{Label, UserModel}
import client.services.{CoreApi, LGCircuit}
import org.scalajs.dom._
import scala.annotation.tailrec
import scalacss.ScalaCssReact._
import org.querki.facades.bootstrap.datepicker._
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._
import org.denigma.selectize._


object Searches {

  case class Props(view: String, proxy: ModelProxy[SearchesRootModel])

  case class State(userModel: UserModel)

  def sidebar = Callback{
    val sidebtn : js.Object = "#searchContainer"
    $(sidebtn).toggleClass("sidebar-left sidebar-animate sidebar-md-show")
  }
  def initializeTagsInput() : Unit = {
    val selectState : js.Object = ".select-state"
//    println($(selectState).get())
    $(selectState).selectize(SelectizeConfig
      .maxItems(10)
      .plugins("remove_button"))
  }

  case class Backend(t: BackendScope[Props, State]) {

    def searchClick(props: Props): Unit = {
      val sidebtn : js.Object = "#searchContainer"
      $(sidebtn).toggleClass("sidebar-left sidebar-animate sidebar-md-show")
      window.sessionStorage.setItem("messageSearchLabel","any([Spilicious])")
      LGCircuit.dispatch(SubscribeSearch())
      LGCircuit.dispatch(RefreshMessages())
    }

    def updateDate(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }




    def initializeDatepicker() : Unit = {
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
      val availableToDate : js.Object = "#availableToDate"
      val availableFromDate : js.Object =   "#availableFromDate"
      val projectStartDate : js.Object = "#projectsStartDate"
      val projectsEndDate : js.Object = "#projectsEndDate"
      val messageBeforeDate:js.Object = "#messagesBeforeDate"
      val messagesFromDate:js.Object = "#messagesFromDate"

      $(availableToDate).datepicker(baseOpts)
      $(availableFromDate).datepicker(baseOpts)
      $(projectStartDate).datepicker(baseOpts)
      $(projectsEndDate).datepicker(baseOpts)
      $(messageBeforeDate).datepicker(baseOpts)
      $(messagesFromDate).datepicker(baseOpts)
      //    $("#dateid").on("changeDate", { rawEvt:JQueryEventObject =>
      //      save()
      //    })
    }

    def mounted(): Callback = Callback {
      initializeDatepicker
      initializeTagsInput
      LGCircuit.dispatch(CreateLabels())
    }

    def render(s: State, p: Props) = {

      p.view match {
        case "talent" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right"/*, ^.height := "55px"*/)(
              <.button(^.id:="sidebarbtn",^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search,^.onClick-->sidebar)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div( LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
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
                    //<.textarea(LftcontainerCSS.Style.textareaWidth,^.className:="input-tags",^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
                    //<.input(^.`type`:="text",^.className:="input-tags", ^.className:="ui vertical orange segment-default",^.placeholder := "e.g. @LivelyGig")
                    <.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
                      <.option(^.value:="")("Select"),
                      <.option(^.value:="LivelyGig")("@LivelyGig"),
                      <.option(^.value:="Synereo")("@Synereo"),
                      <.option(^.value:="LivelyGig1")("@LivelyGig1"),
                      <.option(^.value:="Synereo1")("@Synereo1")
                    )

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
            <.div(^.wrap := "pull-right", ^.textAlign := "right"/*, ^.height := "55px"*/)(
              <.button(^.id:="sidebarbtn",^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search,^.onClick-->sidebar)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div( LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
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
            <.div(^.wrap := "pull-right", ^.textAlign := "right"/*, ^.height := "55px"*/)(
              <.button(^.id:="sidebarbtn",^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search,^.onClick-->sidebar)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div( LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
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
                    //<.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 4, ^.placeholder := "e.g. Web Development")
                   // <.input(^.`type`:="text",^.className:="input-tags", ^.className:="ui vertical orange segment-default",^.placeholder := "e.g. @LivelyGig")
                    <.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
                      <.option(^.value:="")("Select"),
                      <.option(^.value:="LivelyGig")("@LivelyGig"),
                      <.option(^.value:="Synereo")("@Synereo"),
                      <.option(^.value:="LivelyGig1")("@LivelyGig1"),
                      <.option(^.value:="Synereo1")("@Synereo1")
                    )
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
                    //<.textarea(LftcontainerCSS.Style.textareaWidth,^.className:="input-tags", ^.rows := 2, ^.placeholder := "e.g. @LivelyGig", ^.className:="ui vertical orange segment-default")
                   //  <.input(^.`type`:="text",^.className:="input-tags", ^.className:="ui vertical orange segment-default",^.placeholder := "e.g. @LivelyGig")
                    <.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
                      <.option(^.value:="")("Select"),
                      <.option(^.value:="LivelyGig")("@LivelyGig"),
                      <.option(^.value:="Synereo")("@Synereo"),
                      <.option(^.value:="LivelyGig1")("@LivelyGig1"),
                      <.option(^.value:="Synereo1")("@Synereo1")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Payment Escrow")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "paymentEscrow"), " Required"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "paymentEscrow"/*, ^.checked := "true"*/), " Optional"),
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
                    <.label(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "radio", ^.name := "deliverablesEscrow"/*, ^.checked := "true"*/), " None")
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
            <.div(^.wrap := "pull-right", ^.textAlign := "right"/*, ^.height := "55px"*/)(
              <.button(^.id:="sidebarbtn",^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search,^.onClick-->sidebar)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div( LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
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
                <.label(^.`for` := "folder1", ^.margin := "0",DashBoardCSS.Style.padding0px),
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
            <.div(^.wrap := "pull-right", ^.textAlign := "right"/*, ^.height := "55px"*/)(
              /*<.button(^.id:="sidebarbtn",^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search,^.onClick-->)*/
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search, ^.onClick-->Callback{searchClick(p)})
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div( LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
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
                    //                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
                    //                      <.input(^.`type`:="text",^.className:="input-tags", ^.className:="ui vertical orange segment-default")
                    <.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
                      <.option(^.value:="")("Select"),
                      <.option(^.value:="LivelyGig")("@LivelyGig"),
                      <.option(^.value:="Synereo")("@Synereo"),
                      <.option(^.value:="LivelyGig1")("@LivelyGig1"),
                      <.option(^.value:="Synereo1")("@Synereo1")
                    )
                    //                      LGCircuit.connect(_.connections)(conProxy => SearchesConnectionList(SearchesConnectionList.Props(conProxy)))
                    //                    )
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
            <.div(^.wrap := "pull-right", ^.textAlign := "right"/*, ^.height := "55px"*/)(
              <.button(^.id:="sidebarbtn",^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search,^.onClick-->sidebar)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div( LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
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
    .componentDidMount(scope => scope.backend.mounted()/*Callback {
      scope.backend.initializeDatepicker
      scope.backend.initializeTagsInput
      LGCircuit.dispatch(CreateLabels())
    }*/)
    .build

  def apply(props: Props) = component(props)
}

object SearchesConnectionList {
  case class Props(proxy: ModelProxy[Pot[ConnectionsRootModel]])
/*case class Bac*/
case class Backend(t: BackendScope[Props, _]) {
  /*def f(str: String,_) = {

  }*/
//  val hum = (s: String) => println(s)
  def initializeTagsInput() : Unit = {
    val selectState : js.Object = ".select-state"
//    println($(selectState).get())
    val yo = $(selectState).selectize(SelectizeConfig
      .maxItems(10)
      .plugins("remove_button")
//    .onChange((g:String)=>println(g))
)

  }
  def mounted() : Callback = Callback {
    initializeTagsInput()
  }
  def render (props: Props) = {
    <.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
      <.option(^.value:="")("Select"),
      props.proxy().render(connectionsRootModel =>
        for (connection<-connectionsRootModel.connectionsResponse) yield <.option(^.value:=upickle.default.write(connection.connection) ,^.key:=connection.connection.target)(connection.name)
      ))
  }
}
  val component = ReactComponentB[Props]("SearchesConnectionList")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply (props: Props) = component(props)

}


