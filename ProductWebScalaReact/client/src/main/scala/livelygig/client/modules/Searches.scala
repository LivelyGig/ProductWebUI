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

  case class Props(view: String, proxy: ModelProxy[SearchesRootModel])

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

    def render(s: State, p: Props) = {

      p.view match {
        case "talent" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Profile Type")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Talent"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Client"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Moderator")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Available from")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Available to")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableToDate", ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Capabilities")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 2)
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 2)
                  )
                )
              )
            )
          )
        } //talent
        case "offerings" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Recommended to Me"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Include Hidden")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Added before")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Added after")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
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
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Job Type")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject", ^.marginLeft := "-18px"), " Project"),
                    <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly", ^.marginLeft := "-18px"), " Hourly"),
                      <.br(),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed", ^.marginLeft := "-18px"), " Fixed Scope"),
                      <.br()
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Contest")

                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Start after")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "projectsStartDate", ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Finish before")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "projectsEndDate", ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Skills Required")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 4)
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Project State")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin, DashBoardCSS.Style.marginTop10px)(
                    //<.input(^.className:="form-control", LftcontainerCSS.Style.inputHeightWidth)
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 2)
                  )
                )
              )
            )
          )
        } //project
        case "contract" => {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Status")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Initiating"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Escrow"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " In Progress"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Completed")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Client")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 2)
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Talent")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 2)
                  )
                )
              )
            )
          )
        }
        case "messages" => /*MessagesPresets.component(p.ctl)*/ {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", MessagesCSS.Style.slctMessagesInputWidth)(
                    <.div("From")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "messagesFromDate", ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", MessagesCSS.Style.slctMessagesInputWidth)(
                    <.div("To")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "messagesBeforeDate", ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 2)
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Labels")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    if (p.proxy().searchesModel != Nil) {
                      p.proxy().searchesModel.map { model => {
                        <.div()(
                          model.node match {
                            case None =>
                              model.leaf match {
                                case None =>
                                  <.div()
                                case Some(leaf) =>
                                  <.div(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "checkbox"), " " + leaf.text)
                              }
                            case Some(node) =>
                              <.div(LftcontainerCSS.Style.marginBottomSearchmodelNode)(
                                <.div(LftcontainerCSS.Style.slctsearchpaneheader)(" " + node.text),
                                <.div()(node.progeny.map(
                                  leaf => <.div(LftcontainerCSS.Style.checkboxlabel)(<.input(^.`type` := "checkbox"), " " + leaf.text)
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
            )
          )
        }
        case "connections" => /*MessagesPresets.component(p.ctl)*/ {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", "Search")
            ),
            <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Available for Chat"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Recommended by Me"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Recommended to Me"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " with Talent Profile"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " with Employer Profile"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " with Moderator Profile"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Favorited"),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Include Hidden")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Added before")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Added after")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.div(^.className := "input-group date")(
                      <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", LftcontainerCSS.Style.inputHeightWidth, ^.id := "availableFromDate", ^.value := s.userModel.email, ^.onChange ==> updateDate, ^.placeholder := "date")
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
                    <.div("Groups")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(ProjectCSS.Style.textareaWidth, ^.rows := 2)
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


