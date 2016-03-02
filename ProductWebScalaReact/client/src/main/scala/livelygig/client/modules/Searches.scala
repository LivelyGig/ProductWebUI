package livelygig.client.modules

import livelygig.client.components.{Icon}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.Handlers.{CheckLeaf, CheckNode, UpdateLabel, CreateLabels}
import livelygig.client.RootModels.SearchesRootModel
import livelygig.client.css._
import livelygig.client.dtos.{Connection, ExpressionContent, Expression, SubscribeRequest}
import livelygig.client.models.{Leaf, UserModel}
import livelygig.client.services.LGCircuit
import org.scalajs.dom._
import scalacss.ScalaCssReact._
import org.querki.facades.bootstrap.datepicker._
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._

object Searches {

  case class Props(view: String, proxy: ModelProxy[SearchesRootModel])

  case class State(userModel: UserModel)

  case class Backend(t: BackendScope[Props, State]) {

    def searchClick(e: ReactEventI): Unit = {

      //      val label = t.props.map{root => root.proxy.value.searchesModel}

      SubscribeRequest(window.sessionStorage.getItem("sessionURI"), Expression(msgType = "feedExpr", ExpressionContent(Seq(Connection("", "", "")), "alias")))
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
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Profile Type")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Capabilities")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. Web Development")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
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
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Job Type")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject", ^.marginLeft := "-18px"), " Project"
                    ),
                    <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly", ^.marginLeft := "-18px"), " Hourly"
                      ),
                      <.br(),
                      <.label(LftcontainerCSS.Style.subcheckboxlabel)(
                        <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxFixed", ^.marginLeft := "-18px"), " Fixed Scope"
                      )
                    ),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Contest"
                    ),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Part-Time"
                    ),
                    <.br(),
                    <.label(LftcontainerCSS.Style.checkboxlabel)(
                      <.input(^.`type` := "checkbox", ^.marginLeft := "-18px"), " Full-Time"
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Start after")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.input(^.className := "form-control", "data-provide".reactAttr := "datepicker", ^.id := "projectsStartDate", ^.placeholder := "date",
                      LftcontainerCSS.Style.slctDate)
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Skills Required")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 4, ^.placeholder := "e.g. Web Development")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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
                        <.li()(<.a(^.href := "#")("Item 1")),
                        <.li()(<.a(^.href := "#")("Item 2")),
                        <.li()(<.a(^.href := "#")("Item 3"))
                      )
                    )
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
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
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Status")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
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
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Client")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Britta")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Talent")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @Abed")
                  )
                )
              )
            )
          )
        }
        case "messages" => /*MessagesPresets.component(p.ctl)*/ {
          <.div()(
            <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Posted by")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    <.textarea(LftcontainerCSS.Style.textareaWidth, ^.rows := 2, ^.placeholder := "e.g. @LivelyGig")
                  )
                ),
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Labels")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
                    if (p.proxy().searchesModel != Nil) {
                      p.proxy().searchesModel.map { model => {
                        <.div()(
                          model.node match {
                            case None =>
                              model.leaf match {
                                case None =>
                                  <.div()
                                case Some(leaf) =>
                                  <.div(LftcontainerCSS.Style.slctsearchpaneheader,MessagesCSS.Style.marginLeftLeafs)(<.input(^.`type` := "checkbox", ^.checked := leaf.isChecked, ^.onChange --> {
                                    p.proxy.dispatch(CheckLeaf(leaf.copy(isChecked = !leaf.isChecked)))
                                  }), " " + leaf.text)
                              }
                            case Some(node) =>
                              <.div(LftcontainerCSS.Style.marginBottomSearchmodelNode)(
                                <.ol(^.className := "tree")(
                                  <.li(
                                    <.label(^.`for` := "folder3")(
                                    <.input(^.`type` := "checkbox", ^.checked := node.isChecked, ^.onChange --> {
                                        p.proxy.dispatch(CheckNode(node.copy(isChecked = !node.isChecked)))
                                      }), " " + node.text
                                    ),
                                    <.input(^.`type` := "checkbox", ^.className := "treeview", ^.id := "folder3"),

                                    <.ol()(node.progeny.map(
                                      leaf => <.li(LftcontainerCSS.Style.checkboxlabel)(
                                        <.input(^.`type` := "checkbox", ^.checked := leaf.isChecked, ^.onChange --> {
                                          p.proxy.dispatch(CheckLeaf(leaf.copy(isChecked = !leaf.isChecked)))
                                        }), "  " + leaf.text
                                      )
                                    )
                                    ) //li li
                                  ) // label ol
                                ) //ol main
                             )
                          })
                      }
                      }
                    } else {
                      <.div("(none)")
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
              <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-searchContainerBtn", ^.title := "Search", Icon.search)
            ),
            <.div(^.id := "slctScrollContainer", LftcontainerCSS.Style.slctContainer)(
              <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.height := "calc(100vh - 238px)")(
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12", LftcontainerCSS.Style.slctInputWidth)(
                    <.div("Flags")
                  ),
                  <.div(LftcontainerCSS.Style.slctMessagesInputLeftContainerMargin)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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
                <.div(^.className := "row", LftcontainerCSS.Style.row)(
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


