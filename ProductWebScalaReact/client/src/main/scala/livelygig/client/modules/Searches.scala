package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.css._
import livelygig.client.models.UserModel
import org.querki.jquery.JQueryEventObject
import scala.scalajs.js
import scalacss.ScalaCssReact._
import org.querki.facades.bootstrap.datepicker._
import scala.scalajs.js
import js.{Date, UndefOr}
import js.annotation.JSName
import js.JSConverters._
import org.scalajs.dom._
import org.querki.jsext._
import org.querki.jquery._


object Searches {

  case class Props(ctl: RouterCtl[Loc], view: String)
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
              <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
                <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                  <.div(DashBoardCSS.Style.slctHeaders)("Profile Type"),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    <.input(^.`type` := "checkbox"), " Talent"),
                  <.br(),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    <.input(^.`type` := "checkbox"), " Employer"),
                  <.br(),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
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
                      //                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth),

                      //                    <div class="input-group date" data-provide="datepicker">
                      //                      <input type="text" class="form-control">
                      //                        <div class="input-group-addon">
                      //                          <span class="glyphicon glyphicon-th"></span>
                      //                        </div>
                      //                      </div>
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
                  <.div(DashBoardCSS.Style.slctHeaders)("Posted By"),
                  <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablecontainerskills")(
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
              <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
                <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    <.input(^.`type` := "checkbox"), " Recommended to Me"),
                  <.br(),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    <.input(^.`type` := "checkbox"), " Favorited"),
                  <.br(),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
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
              <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
                <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                  <.div(DashBoardCSS.Style.slctHeaders)("Job Type"),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    //                  <.script( ^.`type` := "text/javascript",
                    //
                    //
                    //                  ),
                    <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject"), " Project"),
                  <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
                    <.label(DashBoardCSS.Style.slctSubCheckboxesLabel)(
                      <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Hourly"),
                    <.br(),
                    <.label(DashBoardCSS.Style.slctSubCheckboxesLabel)(
                      <.input(^.`type` := "checkbox", ^.value := true, ^.id := "jobTypeCheckboxFixed"), " Fixed Scope"),
                    <.br()
                  ),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
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
                  <.div(DashBoardCSS.Style.slctHeaders)("Posted By"),
                  <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablecontainerskills")(
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
              <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
                <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                  <.div(DashBoardCSS.Style.slctHeaders)("Status"),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    <.input(^.`type` := "checkbox"), " Initiating"),
                  <.br(),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    <.input(^.`type` := "checkbox"), " Escrow"),
                  <.br(),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
                    <.input(^.`type` := "checkbox"), " Feedback"),
                  <.br(),
                  <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
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
              <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
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
                  <.div(DashBoardCSS.Style.slctHeaders)("Posted By"),
                  <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablecontainerskills")(
                  )
                )
              )
            )
          }

//                        case "connections" => /*MessagesPresets.component(p.ctl)*/ {
//                          <.div()(
//                          <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
//                            <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
//                          ),
//                          <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
//                            <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
//                              <.div(DashBoardCSS.Style.slctHeaders)("Status"),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " Online"),
//                              <.br(),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " Recommended by Me"),
//                              <.br(),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " Recommended to Me"),
//                              <.br(),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " with Talent Profile"),
//                              <.br(),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " with Employer Profile"),
//                              <.br(),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " with Moderator Profile"),
//                              <.br(),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " Favorited"),
//                              <.br(),
//                              <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
//                                <.input(^.`type` := "checkbox"), " Include Hidden"),
//                              <.div(^.paddingLeft:="15px")(
//                                <.div(^.className := "row")(
//                                  <.div(ProjectCSS.Style.slctProjectInputWidth)(
//                                    <.div("Added Before")
//                                  ),
//                                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
//                                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
//                                  )
//                                ),
//                                <.div(^.className := "row")(
//                                  <.div(ProjectCSS.Style.slctProjectInputWidth)(
//                                    <.div("Added After")
//                                  ),
//                                  // ToDo: this should be a date picker
//                                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
//                                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
//                                  )
//                                ),
//                                <.div(^.className := "row")(
//                                  <.div(ProjectCSS.Style.slctProjectInputWidth)(
//                                    <.div("Groups")
//                                  ),
//                                  // ToDo: this should be a date picker
//                                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
//                                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
//                                  )
//                                )
//                              )
//                            )
//                          )
//                          )
//
//
//                        }
        } //main switch

      }
    }


  private val component = ReactComponentB[Props]("Searches")
    .initialState_P(p => State(new UserModel("", "", "")))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


