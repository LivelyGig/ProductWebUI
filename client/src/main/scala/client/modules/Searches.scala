package client.modules

import client.components.{Icon, LabelsList}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.handler._
import client.rootmodel.SearchesRootModel
import client.css._
import shared.models.{Label, UserModel}
import client.services.LGCircuit
import client.utils.{AppUtils, ConnectionsUtils, ContentUtils, LabelsUtils}
import scalacss.ScalaCssReact._
import org.querki.facades.bootstrap.datepicker._
import scala.scalajs.js
import org.querki.jquery._
import org.denigma.selectize._
import diode.AnyAction._
import shared.dtos.{Expression, ExpressionContent, SubscribeRequest}


object Searches {

  val searchesProxy = LGCircuit.connect(_.searches)

  case class Props(view: String, proxy: ModelProxy[SearchesRootModel])

  case class State(userModel: UserModel, tags: js.Array[String] = js.Array("scala", "scalajs"))

  // scalastyle:off

  val selectState: js.Object = "#selectize"

  case class Backend(t: BackendScope[Props, State]) {

    def searchClick(props: Props): Unit = {
      val sidebtn: js.Object = "#searchContainer"
      val overlayContainer: js.Object = "#overlayContainer"
      $(sidebtn).toggleClass("sidebar-left sidebar-animate sidebar-md-show")
      if (!$(sidebtn).hasClass("sidebar-left sidebar-animate sidebar-md-show")) {
        $(overlayContainer).addClass("overlaySidebar")
        //        $(sidebtn).next().addClass("LftcontainerCSS_Style-sidebarRightContainer")
      } else {
        $(overlayContainer).removeClass("overlaySidebar")
        //        $(sidebtn).next().removeClass("LftcontainerCSS_Style-sidebarRightContainer")
      }

      val searchLabels = LabelsUtils.buildProlog(
        Seq(LabelsUtils.getSystemLabelModel(props.view)) ++ LGCircuit.zoom(_.searches.searchesModel).value.filter(_.isChecked), LabelsUtils.PrologTypes.Each)
      val expr = Expression("feedExpr", ExpressionContent(ConnectionsUtils.getCnxnForReq(Nil, props.view), searchLabels))
      ContentUtils.cancelPreviousAndSubscribeNew(SubscribeRequest(AppUtils.getSessionUri(props.view), expr), props.view)
    }

    def toggleSidebar = Callback {
      val sidebtn: js.Object = "#searchContainer"
      val overlayContainer: js.Object = "#overlayContainer"
      $(sidebtn).toggleClass("sidebar-left sidebar-animate sidebar-md-show")
      if (!$(sidebtn).hasClass("sidebar-left sidebar-animate sidebar-md-show")) {
        $(overlayContainer).addClass("overlaySidebar")
        //      $(sidebtn).next().addClass("LftcontainerCSS_Style-sidebarRightContainer")
      } else {
        $(overlayContainer).removeClass("overlaySidebar")
        //      $(sidebtn).next().removeClass("LftcontainerCSS_Style-sidebarRightContainer")
      }
    }


    /*val ref = RefHolder[ReactTagsInputM]*/

    val onChange: (js.Array[String]) => Callback =
      tags => t.modState(_.copy(tags = tags)) >> Callback.info(s"New state: $tags")

    def updateDate(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def initializeTagsInput(): Unit = {
      val selectState: js.Object = ".select-state"
      $(selectState).selectize(SelectizeConfig
        .maxItems(10)
        .plugins("remove_button"))

    }

    def initializeDatepicker(): Unit = {
      val baseOpts = BootstrapDatepickerOptions.
        autoclose(true).
        todayHighlight(true).
        todayBtnLinked().
        disableTouchKeyboard(true).
        orientation(Orientation.Bottom)
      // Iff this Date is Optional, show the Clear button:
      val opts =
      if (true)
        baseOpts.clearBtn(true)
      else
        baseOpts
      val availableToDate: js.Object = "#availableToDate"
      val availableFromDate: js.Object = "#availableFromDate"
      val projectStartDate: js.Object = "#projectsStartDate"
      val projectsEndDate: js.Object = "#projectsEndDate"
      val messageBeforeDate: js.Object = "#messagesBeforeDate"
      val messagesFromDate: js.Object = "#messagesFromDate"

      $(availableToDate).datepicker(baseOpts)
      $(availableFromDate).datepicker(baseOpts)
      $(projectStartDate).datepicker(baseOpts)
      $(projectsEndDate).datepicker(baseOpts)
      $(messageBeforeDate).datepicker(baseOpts)
      $(messagesFromDate).datepicker(baseOpts)
    }

    def mounted(): Callback = Callback {
      initializeDatepicker
      initializeTagsInput
      //      toggleSidebar

    }

    def render(s: State, p: Props) = {
      //          @tailrec
      def renderLabel(label: Label): ReactTag = {
        val children = p.proxy().searchesModel.filter(p => p.parentUid == label.uid)
        if (!children.isEmpty) {
          <.li(LftcontainerCSS.Style.checkboxlabel)(
            <.label(^.`for` := "folder1", ^.margin := "0px", DashBoardCSS.Style.padding0px),
            <.input(^.`type` := "checkbox", ^.marginLeft := "20px", ^.checked := label.isChecked, ^.onChange --> p.proxy.dispatchCB(UpdateLabel(label.copy(isChecked = !label.isChecked)))),
            "  " + label.text,
            <.input(^.`type` := "checkbox", ^.className := "treeview", ^.id := "folder1"),
            <.ol(LftcontainerCSS.Style.checkboxlabel)(children map renderLabel)
          )
        } else {

          <.li(LftcontainerCSS.Style.checkboxlabel, ^.className := "checkboxlabel")(
            <.input(^.`type` := "checkbox", ^.marginLeft := "20px", ^.checked := label.isChecked, ^.onChange --> p.proxy.dispatchCB(UpdateLabel(label.copy(isChecked = !label.isChecked)))), "  "
              + label.text
          )
        }
      }

      p.view match {
        case AppModule.PROFILES_VIEW => {
          <.div()(
            <.div(^.className := "HeaderCSS_Style-searchActionsContainer")(

              <.div(^.className := "btn-group")(
                <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                  <.span(^.className := "caret")
                ),
                <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#talent")("Recommended Matches")),
                  <.li()(<.a(^.href := "#talent")("My Profiles")),
                  <.li()(<.a(^.href := "#talent")("Favorited")),
                  <.li()(<.a(^.href := "#talent")("Available")),
                  <.li()(<.a(^.href := "#talent")("Active Unavailable")),
                  <.li()(<.a(^.href := "#talent")("Inactive")),
                  <.li()(<.a(^.href := "#talent")("Hidden")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.href := "#talent")("Videographers w/5+ yrs experience")),
                  <.li()(<.a(^.href := "#talent")("Customize..."))
                )
              ),
              <.button(^.id := "sidebarbtn", ^.className := "btn HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search, ^.onClick --> Callback {
                searchClick(p)
              })
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Profile Type")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Talent"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Talent Agency"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Client"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Moderator"
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Available from")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Available to")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableToDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableToDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("My Labels ")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.div(
                      searchesProxy(searchesProxy => LabelsList(LabelsList.Props(searchesProxy)))
                    ),
                    if (p.proxy().searchesModel != Nil) {

                      <.ol(^.className := "tree", LftcontainerCSS.Style.checkboxlabel)(p.proxy().searchesModel.filter(e => e.parentUid == "self").map(p => renderLabel(p)))
                    } else {
                      <.div("(none)")
                    }
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Capabilities")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. Web Development")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Posted by")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // ToDo: need to selectize this based on connections
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Alice")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Flags")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden"
                    )
                  )
                )
              )
            )
          )
        } //talent
        case AppModule.OFFERINGS_VIEW => {
          <.div()(
            <.div(^.className := "HeaderCSS_Style-searchActionsContainer")(
              <.div(^.className := "btn-group")(
                <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                  <.span(^.className := "caret")
                ),
                <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#offerings")("Recommended to Me")),
                  <.li()(<.a(^.href := "#offerings")("My Posted Offerings")),
                  <.li()(<.a(^.href := "#offerings")("Favorited")),
                  <.li()(<.a(^.href := "#offerings")("Hidden")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.href := "#offerings")("Customize..."))
                )
              ),
              <.button(^.id := "sidebarbtn", ^.className := "btn HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search, ^.onClick --> toggleSidebar)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Flags")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended to Me"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended by Me"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden"
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Added before")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )

                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("My Labels ")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.div(
                      searchesProxy(searchesProxy => LabelsList(LabelsList.Props(searchesProxy)))
                    ),
                    if (p.proxy().searchesModel != Nil) {

                      <.ol(^.className := "tree", LftcontainerCSS.Style.checkboxlabel)(p.proxy().searchesModel.filter(e => e.parentUid == "self").map(p => renderLabel(p)))
                    } else {
                      <.div("(none)")
                    }
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Added after")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
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
        case AppModule.PROJECTS_VIEW => {
          <.div()(
            <.div(^.className := "HeaderCSS_Style-searchActionsContainer")(
              <.div(^.className := "btn-group")(
                <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                  <.span(^.className := "caret")
                ),
                <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#projects")("Recommended Matches")),
                  <.li()(<.a(^.href := "#projects")("Direct from Connection")),
                  <.li()(<.a(^.href := "#projects")("My Posted Jobs")),
                  <.li()(<.a(^.href := "#projects")("Favorited")),
                  <.li()(<.a(^.href := "#projects")("Hidden")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.href := "#projects")("Customize..."))
                )
              ),
              <.button(^.id := "sidebarbtn", ^.className := "btn HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search, ^.onClick --> Callback {
                searchClick(p)
              })
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Job Type")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject"), " Project"
                    ),
                    <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Hourly"
                      ),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed"), " Fixed Scope"
                      )
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Contest"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Part-Time"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Full-Time"
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Start after")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "projectsStartDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Finish before")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "projectsEndDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "projectsEndDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Skills Required")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(//<.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 4, ^.placeholder := "e.g. Web Development")
                    // <.input(^.`type`:="text",^.className:="input-tags", ^.className:="ui vertical orange segment-default",^.placeholder := "e.g. @LivelyGig")
                    //                    <.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
                    //                      <.option(^.value:="")("Select"),
                    //                      <.option(^.value:="LivelyGig")("@LivelyGig"),
                    //                      <.option(^.value:="Synereo")("@Synereo"),
                    //                      <.option(^.value:="LivelyGig1")("@LivelyGig1"),
                    //                      <.option(^.value:="Synereo1")("@Synereo1")
                    //                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Job State")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    //<.input(^.className:="form-control", LftcontainerCSS.Style.inputHeightWidth)
                    <.div(^.className := "btn-group")(
                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.border := "none", ^.paddingLeft := "0px")("Open ")(
                        <.span(^.className := "caret")
                      ),
                      <.ul(^.className := "dropdown-menu")(
                        <.li()(<.a()("Open")),
                        <.li()(<.a()("Bidding")),
                        <.li()(<.a()("Contracted"))
                      )
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Posted by")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(//<.textarea(LftcontainerCSS.Style.textareaWidth,^.className:="input-tags", ^.rows := 2, ^.placeholder := "e.g. @LivelyGig", ^.className:="ui vertical orange segment-default")
                    //  <.input(^.`type`:="text",^.className:="input-tags", ^.className:="ui vertical orange segment-default",^.placeholder := "e.g. @LivelyGig")
                    //                    <.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
                    //                      <.option(^.value:="")("Select"),
                    //                      <.option(^.value:="LivelyGig")("@LivelyGig"),
                    //                      <.option(^.value:="Synereo")("@Synereo"),
                    //                      <.option(^.value:="LivelyGig1")("@LivelyGig1"),
                    //                      <.option(^.value:="Synereo1")("@Synereo1")
                    //                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Payment Escrow")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    //<.input(^.className:="form-control", LftcontainerCSS.Style.inputHeightWidth)
                    <.div(^.className := "btn-group")(
                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.border := "none", ^.paddingLeft := "0px")("Any ")(
                        <.span(^.className := "caret")
                      ),
                      <.ul(^.className := "dropdown-menu")(
                        <.li()(<.a()("Any")),
                        <.li()(<.a()("None")),
                        <.li()(<.a()("Optional")),
                        <.li()(<.a()("Required"))
                      )
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Deliverables Escrow")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    //<.input(^.className:="form-control", LftcontainerCSS.Style.inputHeightWidth)
                    <.div(^.className := "btn-group")(
                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.border := "none", ^.paddingLeft := "0px")("Any ")(
                        <.span(^.className := "caret")
                      ),
                      <.ul(^.className := "dropdown-menu")(
                        <.li()(<.a()("Any")),
                        <.li()(<.a()("None")),
                        <.li()(<.a()("Optional")),
                        <.li()(<.a()("Required"))
                      )
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("My Labels ")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.div(
                      searchesProxy(searchesProxy => LabelsList(LabelsList.Props(searchesProxy)))
                    ),
                    if (p.proxy().searchesModel != Nil) {
                      <.ol(^.className := "tree", LftcontainerCSS.Style.checkboxlabel)(p.proxy().searchesModel.filter(e => e.parentUid == "self").map(p => renderLabel(p)))
                    } else {
                      <.div("(none)")
                    }
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Flags")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden"
                    )
                  )
                )
              )
            )
          )
        } //project
        case AppModule.CONTRACTS_VIEW => {
          <.div()(
            <.div(^.className := "HeaderCSS_Style-searchActionsContainer")(
              <.div(^.className := "btn-group")(
                <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Active ")(
                  <.span(^.className := "caret")
                ),
                <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#contract")("Active")),
                  <.li()(<.a(^.href := "#contract")("Favorited")),
                  <.li()(<.a(^.href := "#contract")("Hidden")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.href := "#contract")("Customize..."))
                )
              ),
              <.button(^.id := "sidebarbtn", ^.className := "btn HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search, ^.onClick --> toggleSidebar)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Status")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
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
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed"), " Rejected by me"
                      ),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed"), " Expired"
                      )
                    ),
                    <.div()(
                      <.label(LftcontainerCSS.Style.checkboxlabel)(
                        <.input(^.`type` := "checkbox"), " Escrow"
                      ),
                      <.label(LftcontainerCSS.Style.checkboxlabel)(
                        <.input(^.`type` := "checkbox"), " In Progress"
                      ),
                      <.label(LftcontainerCSS.Style.checkboxlabel)(
                        <.input(^.`type` := "checkbox"), " Feedback"
                      ),
                      <.label(LftcontainerCSS.Style.checkboxlabel)(
                        <.input(^.`type` := "checkbox"), " Completed"
                      )
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Job ID")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. 70a48a34-2773-4f4f-9f92-8a261ee59ad7")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Client")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Britta")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Talent")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Abed")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("My Labels ")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.div(
                      searchesProxy(searchesProxy => LabelsList(LabelsList.Props(searchesProxy)))
                    ),
                    if (p.proxy().searchesModel != Nil) {

                      <.ol(^.className := "tree", LftcontainerCSS.Style.checkboxlabel)(p.proxy().searchesModel.filter(e => e.parentUid == "self").map(p => renderLabel(p)))
                    } else {
                      <.div("(none)")
                    }
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Flags")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden"
                    )
                  )
                )
              )
            )
          )
        }
        case AppModule.MESSAGES_VIEW => {
          //          @tailrec
          def renderLabel(label: Label): ReactTag = {
            val children = p.proxy().searchesModel.filter(p => p.parentUid == label.uid)
            if (!children.isEmpty) {
              <.li(LftcontainerCSS.Style.checkboxlabel)(
                <.label(^.`for` := "folder1", ^.margin := "0px", DashBoardCSS.Style.padding0px),
                <.input(^.`type` := "checkbox", ^.marginLeft := "20px", ^.checked := label.isChecked, ^.onChange --> p.proxy.dispatchCB(UpdateLabel(label.copy(isChecked = !label.isChecked)))),
                "  " + label.text,
                <.input(^.`type` := "checkbox", ^.className := "treeview", ^.id := "folder1"),
                <.ol(LftcontainerCSS.Style.checkboxlabel)(children map renderLabel)
              )
            } else {

              <.li(LftcontainerCSS.Style.checkboxlabel, ^.className := "checkboxlabel")(
                <.input(^.`type` := "checkbox", ^.marginLeft := "20px", ^.checked := label.isChecked, ^.onChange --> p.proxy.dispatchCB(UpdateLabel(label.copy(isChecked = !label.isChecked)))), "  "
                  + label.text
              )
            }
          }
          <.div()(
            <.div(^.className := "HeaderCSS_Style-searchActionsContainer")(
              /*<.button(^.id:="sidebarbtn",^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search,^.onClick-->)*/
              <.div(^.className := "btn-group")(
                <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Unread ")(
                  <.span(^.className := "caret")
                ),
                <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#messages")("Inbox")),
                  <.li()(<.a(^.href := "#messages")("Sent")),
                  <.li()(<.a(^.href := "#messages")("Unread")),
                  <.li()(<.a(^.href := "#messages")("Favorited")),
                  <.li()(<.a(^.href := "#messages")("Hidden")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.href := "#messages")("Customize..."))
                )
              ),
              <.button(^.tpe := "button", ^.className := "btn HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search, ^.onClick --> Callback {
                searchClick(p)
              })
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Sent After")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "messagesFromDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "messagesFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Sent Before")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "messagesBeforeDate", ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "messagesBeforeDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Sender(s)")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // ToDo: need to selectize this based on connections
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Alice")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Recipient(s)")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    // ToDo: need to selectize this based on connections.
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Bob, @Carol")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("My Labels ")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.div(
                      searchesProxy(searchesProxy => LabelsList(LabelsList.Props(searchesProxy)))
                    ),
                    if (p.proxy().searchesModel != Nil) {
                      <.ol(^.className := "tree", LftcontainerCSS.Style.checkboxlabel)(p.proxy().searchesModel.filter(e => e.parentUid == "self").map(p => renderLabel(p)))
                    } else {
                      <.div("(none)")
                    }
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Tags")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. #tag1, #tag2")
                    // ToDo: need to provide user feedback so these strings are valid alphanumeric with leading # signs, comma-separated.
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-5 col-sm-12 col-xs-12")(
                    <.div("Flags")
                  ),
                  <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden"
                    )
                  )
                )
              )
            )
          )
        }
        case AppModule.CONNECTIONS_VIEW => {
          <.div()(
            <.div(^.className := "HeaderCSS_Style-searchActionsContainer")(
              <.button(^.id := "sidebarbtn", ^.className := "btn HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search, ^.onClick --> toggleSidebar),
              <.div(^.className := "btn-group")(
                <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Favorited ")(
                  <.span(^.className := "caret")
                ),
                <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#connections")("All")),
                  <.li()(<.a(^.href := "#connections")("Available for Chat")),
                  <.li()(<.a(^.href := "#connections")("Favorited")),
                  <.li()(<.a(^.href := "#connections")("Hidden")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.href := "#connections")("Customize..."))
                )
              )
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 215px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Flags")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Available for Chat"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended by Me"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Recommended to Me"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " with Talent Profile"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " with Employer Profile"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " with Moderator Profile"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Favorited"
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox"), " Include Hidden"
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Added before")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "availableFromDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Added after")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
                    // <.div(^.className := "input-group date")(
                    // <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "projectsAfterDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                    // )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.lftMarginTop)(
                  <.div(^.className := "col-md-4 col-sm-12 col-xs-12")(
                    <.div("Groups")
                  ),
                  <.div(^.className := "col-md-8 col-sm-12 col-xs-12")(
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
    .componentDidMount(scope => scope.backend.mounted() /*Callback {
      scope.backend.initializeDatepicker
      scope.backend.initializeTagsInput
      .dispatch(CreateLabels())
    }*/)
    .build

  def apply(props: Props) = component(props)
}

/*object SearchesConnectionList {
  var getSelectedValue = new ListBuffer[String]() /*Seq[Label]()*/
  case class Props(proxy: ModelProxy[Pot[ConnectionsRootModel]], parentIdentifier : String)
  case class Backend(t: BackendScope[Props, _]) {
    def initializeTagsInput(parentIdentifier: String) : Unit = {
      val selectState : js.Object = s"#$parentIdentifier > .selectize-control"
      //    println($(selectState).get())
      if ($(selectState).length < 1){
        val selectizeInput : js.Object = "#selectize"
        $(selectizeInput).selectize(SelectizeConfig
          .maxItems(10)
          .plugins("remove_button"))
      }

    }
    def getSelectedValues = Callback {
      val selectState : js.Object = "#selectize"
      val getSelectedValue =  $(selectState).find("option").text()
      println(getSelectedValue)

      //       var x = document.getElementById("mySelect").value;
      //       document.getElementById("demo").innerHTML = "You selected: " + x;

      //     var x =  document.getElementById("#selectize").textContent
      //       println(x)
    }

    //     def getSelectedValues(e: ReactEventI) = Callback {
    //       val value = e.target.value
    //       println(value)
    //
    //     }

    def mounted(props: Props) : Callback = Callback {
      initializeTagsInput(props.parentIdentifier)
    }

    def render (props: Props) = {
            val parentDiv : js.Object = s"#${props.parentIdentifier}"
            if ($(parentDiv).length == 0) {
      <.select(^.className:="select-state",^.id:="selectize", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig", ^.onChange --> getSelectedValues)(
        <.option(^.value:="")("Select"),
        props.proxy().render(connectionsRootModel =>
          for (connection<-connectionsRootModel.connectionsResponse) yield <.option(^.value:=upickle.default.write(connection.connection) ,^.key:=connection.connection.target)(connection.name)
        ))
            } else {
              <.div()
            }

    }
  }
  val component = ReactComponentB[Props]("SearchesConnectionList")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply (props: Props) = component(props)

}*/
