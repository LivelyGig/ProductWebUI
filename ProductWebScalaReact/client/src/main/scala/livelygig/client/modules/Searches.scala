package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.css._
import scalacss.ScalaCssReact._

object Searches {

  case class Props(ctl: RouterCtl[Loc], view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }

    def render(p: Props) = {
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
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Available To Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
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
        case "projects" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
                <.div(DashBoardCSS.Style.slctHeaders)("Job Type"),
                <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
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
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("End Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Skill Required")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    //                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth),
                    <.input(^.`type` := "textarea", ProjectCSS.Style.textareaWidth, ^.lineHeight := 4)
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
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                  )
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", MessagesCSS.Style.slctMessagesInputWidth)(
                    <.div("Before Date")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
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

        //              case "connections" => /*MessagesPresets.component(p.ctl)*/ {
        //                <.button(^.tpe := "button",^.className:="btn btn-default", "Search")
        //
        //                <.div(DashBoardCSS.Style.slctHeaders)("Connections")
        //                <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
        //                  //<input type="text" value="Amsterdam,Washington" data-role="tagsinput"
        //                  //                <.input (^.`type`:="text" , "data-role".reactAttr:="tagsinput")
        //                )
        //
        //                <.div(DashBoardCSS.Style.slctHeaders)("Search for Connection")
        //                <.div(^.className:="row")(
        //                  <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
        //                    <.div("Keywords")
        //                    //                        <.span(^.className:="checkbox-lbl")
        //                  ),
        //                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
        //                    <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
        //                  ))
        //
        //
        //                <.div(^.className:="row")(
        //                  <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
        //                    <.div("Job title")
        //                    //                        <.span(^.className:="checkbox-lbl")
        //                  ),
        //                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
        //                    <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
        //                  ))
        //
        //                <.div(DashBoardCSS.Style.slctHeaders)("Search by Name")
        //                <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
        //                )
        //
        //                <.div(DashBoardCSS.Style.slctHeaders)("Search by Rating")
        //                <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
        //                )
        //
        //              }
      } //main switch

    }
  }

  private val component = ReactComponentB[Props]("Searches")
    .initialState_P(p => ())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


