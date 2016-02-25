package livelygig.client.modules


import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.Handlers.CreateLabels
import livelygig.client.RootModels.SearchesRootModel
import livelygig.client.css._
import livelygig.client.models.{Leaf, UserModel}
import livelygig.client.services.LGCircuit
import scalacss.ScalaCssReact._
import org.querki.facades.bootstrap.datepicker._
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._

object Searches {
  case class Props(view: String, proxy : ModelProxy[SearchesRootModel])
  case class State(userModel: UserModel)
  case class Backend(t: BackendScope[Props, State]) {

    def updateDate(e: ReactEventI) = {
      println(e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def mounted(props: Props): Callback = Callback {
    }

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

    $("#availableToDate").datepicker(baseOpts)
    $("#availableFromDate").datepicker(baseOpts)
    $("#projectsStartDate").datepicker(baseOpts)
    $("#projectsEndDate").datepicker(baseOpts)
    $("#messagesBeforeDate").datepicker(baseOpts)
    $("#messagesFromDate").datepicker(baseOpts)
    //    $("#dateid").on("changeDate", { rawEvt:JQueryEventObject =>
    //      save()
    //    })

    def render(s:State,p: Props) = {
      p.view match {
        case "talent" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.marginRightsearchpane,DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                <.div(LftcontainerCSS.Style.slctsearchpaneheader)("Profile Type"),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Talent"),
                <.br(),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Employer"),
                <.br(),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Moderator"),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Available From Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "availableFromDate",^.value := s.userModel.email, ^.onChange ==> updateDate)
                    )
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Available To Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker",DashBoardCSS.Style.inputHeightWidth, ^.id := "availableToDate")
                    )
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Capabilities")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                  )
                ),
                <.div(LftcontainerCSS.Style.slctsearchpaneheader)("Posted By"),
                <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizabletalentpostedby")(
                  <.textarea(ProjectCSS.Style.textareaWidth,^.rows:=4)
                )
              )
            )
          )
        } //talent
        case "offerings" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer",LftcontainerCSS.Style.marginRightsearchpane, DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Recommended to Me"),
                <.br(),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Favorited"),
                <.br(),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Include Hidden"),
                <.div(^.paddingLeft := "15px")(
                  <.div(^.className := "row")(
                    <.div(ProjectCSS.Style.slctProjectInputWidth)(
                      <.div("Added Before")
                    ),
                    <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                      <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                    )
                  ),
                  <.div(^.className := "row")(
                    <.div(ProjectCSS.Style.slctProjectInputWidth)(
                      <.div("Added After")
                    ),
                    <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                      <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                    )
                  )
                )
              )
            )
          )
        }
        case "projects" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer",LftcontainerCSS.Style.marginRightsearchpane, DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                <.div(LftcontainerCSS.Style.slctsearchpaneheader)("Job Type"),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject"), " Project"),
                <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
                  <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                    <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Hourly"),
                  <.br(),
                  <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                    <.input(^.`type` := "checkbox", ^.value := true, ^.id := "jobTypeCheckboxFixed"), " Fixed Scope"),
                  <.br()
                ),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Contest"),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Start Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)

                    <.div(^.className := "input-group date")(
                      <.input(/*^.className := "form-control",*/ "data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "projectsStartDate")
                    )
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("End Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                    <.div(^.className := "input-group date")(
                      <.input(/*^.className := "form-control",*/ "data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "projectsEndDate")
                    )
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Skill Required")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    //                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth),
                    <.textarea(ProjectCSS.Style.textareaWidth,^.rows:=4)
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Project State")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin, DashBoardCSS.Style.marginTop10px)(
                    //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                    <.div(^.className := "btn-group")(
                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select One")(
                        <.span(^.className := "caret")
                      ),
                      <.ul(^.className := "dropdown-menu")(
                        <.li()(<.a(^.href := "#")("Item 1")),
                        <.li()(<.a(^.href := "#")("Item 2")),
                        <.li()(<.a(^.href := "#")("Item 3"))
                      )
                    )
                  )
                ),
                <.div(LftcontainerCSS.Style.slctsearchpaneheader)("Posted By"),
                <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizableprojectspostedby")(
                  <.textarea(ProjectCSS.Style.textareaWidth,^.rows:=4)
                )
              )
            )
          )
        } //project
        case "contract" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer",LftcontainerCSS.Style.marginRightsearchpane, DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                <.div(LftcontainerCSS.Style.slctsearchpaneheader)("Status"),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Initiating"),
                <.br(),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Escrow"),
                <.br(),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Feedback"),
                <.br(),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Completed")
              )
            )
          )
        }
        case "messages" => /*MessagesPresets.component(p.ctl)*/ {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer",LftcontainerCSS.Style.marginRightsearchpane, DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(

                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", MessagesCSS.Style.slctMessagesInputWidth)(
                    <.div("From Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    // <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                    <.div(^.className := "input-group date")(
                      <.input(/*^.className := "form-control",*/ "data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "messagesFromDate")
                    )
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", MessagesCSS.Style.slctMessagesInputWidth)(
                    <.div("Before Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(

                    <.div(^.className := "input-group date")(
                      <.input(/*^.className := "form-control",*/ "data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "messagesBeforeDate")
                    )

                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", MessagesCSS.Style.slctMessagesInputWidth)(
                    <.div("From Time")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", MessagesCSS.Style.slctMessagesInputWidth)(
                    <.div("Keywords")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.`type` := "textarea", ProjectCSS.Style.textareaWidth, ^.lineHeight := 4)
                  )
                ),
                <.div(LftcontainerCSS.Style.slctsearchpaneheader)("Posted By"),
                <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablemessagespostedby")(
                  <.textarea(ProjectCSS.Style.textareaWidth,^.rows:=4)
                ),
                if (p.proxy().searchesModel != Nil) {
                  p.proxy().searchesModel.map { model => {
                    <.div()(
                      model.node match {
                        case None =>
                          <.div(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "checkbox"), model.leaf.get.text)
                        case Some(node) =>
                          <.div(LftcontainerCSS.Style.marginBottomSearchmodelNode)(
                            <.div(LftcontainerCSS.Style.slctsearchpaneheader)(node.text),
                            <.div()(node.progeny.map(
                              leaf => <.div(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "checkbox"), leaf.text)
                            )))
                      })
                  }
                  }

                } else {
                  <.div()
                }

              )
            )
          )
        }

        case "connections" => /*MessagesPresets.component(p.ctl)*/ {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer",LftcontainerCSS.Style.marginRightsearchpane, DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                <.div(LftcontainerCSS.Style.slctsearchpaneheader)("Status"),
                <.label(LftcontainerCSS.Style.checkboxlabel)(
                  <.input(^.`type` := "checkbox"), " Online"),
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
                  <.input(^.`type` := "checkbox"), " Include Hidden"),
                <.div(^.paddingLeft:="15px")(
                  <.div(^.className := "row")(
                    <.div(ProjectCSS.Style.slctProjectInputWidth)(
                      <.div("Added Before")
                    ),
                    <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                      //<.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                      <.div(^.className := "input-group date")(
                        <.input("data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "addedBefore")
                      )
                    )
                  ),
                  <.div(^.className := "row")(
                    <.div(ProjectCSS.Style.slctProjectInputWidth)(
                      <.div("Added After")
                    ),
                    // ToDo: this should be a date picker
                    <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                      //                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                      <.div(^.className := "input-group date")(
                        <.input("data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "addedAfter")
                      )
                    )
                  ),
                  <.div(^.className := "row")(
                    <.div(ProjectCSS.Style.slctProjectInputWidth)(
                      <.div("Groups")
                    ),
                    // ToDo: this should be a date picker
                    <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                      <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)

                    )
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
    .componentDidMount(scope => Callback{LGCircuit.dispatch(CreateLabels())})
    .build

  def apply(props: Props) = component(props)
}


