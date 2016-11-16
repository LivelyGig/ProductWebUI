package synereo.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.denigma.selectize._
import org.querki.jquery.JQueryExtensions
import scala.language.existentials
import scala.scalajs.js
import shared.dtos.Connection
import shared.models.ConnectionsModel
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils.AppUtils

/**
  * Created by mandar.k on 4/6/2016.
  */
//scalastyle:off
object ConnectionsSelectize {

  def getConnectionsFromSelectizeInput(selectizeInputId: String): Seq[Connection] = {
    val selectedCnxn = $(s"#${selectizeInputId} > .selectize-control> .selectize-input > div".asInstanceOf[js.Object])
    if (selectedCnxn.filter(s"[data-value='${AppUtils.ALL_CONTACTS_ID}']").length == 1)
      SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.map(cnxnRes => cnxnRes.connection)
    else {
      val extn = new JQueryExtensions(selectedCnxn)
      extn.mapElems[Connection](ele => upickle.default.read[Connection]($(ele).attr("data-value").toString))
    }
  }

  def getConnectionNames(selectizeInputId: String): Seq[String] = {
    val cnxns = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
    getConnectionsFromSelectizeInput(selectizeInputId)
      .flatMap(e => cnxns.find(_.connection.target == e.target))
      .map(_.name)
  }

  case class Props(parentIdentifier: String,
                   fromSelecize: () => Callback,
                   option: Option[Int] = None,
                   receivers: Seq[ConnectionsModel] = Nil,
                   sender: ConnectionsModel = ConnectionsModel(),
                   replyPost: Boolean = false,
                   enableAllContacts: Boolean = false)

  case class State(connections: Seq[ConnectionsModel] = Nil,
                   allContactsSelected: Boolean = false)


  case class Backend(t: BackendScope[Props, State]) {

    def initializeTagsInput(props: Props, state: State): Unit = {
      val parentIdentifier = t.props.runNow().parentIdentifier
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      val selectizeInput: js.Object = s"#${parentIdentifier}-selectize"
      $(selectizeInput).selectize(SelectizeConfig
        .maxItems(30)
        .plugins("remove_button")
        .closeAfterSelect(true)
        .openOnFocus(true)
        .onItemAdd((item: String, value: js.Dynamic) => {
          val selectedCnxn = $(s"#connectionsSelectizeInputId > .selectize-control> .selectize-input > div".asInstanceOf[js.Object])
          if (selectedCnxn.filter(s"[data-value='${AppUtils.ALL_CONTACTS_ID}']").length == 1)
            $(".selectize-dropdown-content .option".asInstanceOf[js.Object]).addClass("hidden")
          props.fromSelecize().runNow()
          //          println("")
        })
        .onItemRemove((item: String) => {
          val selectedCnxn = $(s"#connectionsSelectizeInputId > .selectize-control> .selectize-input > div".asInstanceOf[js.Object])
          if (selectedCnxn.filter(s"[data-value='${AppUtils.ALL_CONTACTS_ID}']").length != 1)
            $(".selectize-dropdown-content .option".asInstanceOf[js.Object]).removeClass("hidden")
          props.fromSelecize().runNow()
          //          println("")
        })
      )
    }

    def mounted(props: Props, state: State): Callback = Callback {
      initializeTagsInput(props, state)
    }

    def getCnxnModel(): Seq[ConnectionsModel] = {
      try {
        SYNEREOCircuit.zoom(_.connections).value.connectionsResponse
      } catch {
        case e: Exception =>
          Nil
      }
    }

    def componentWillMount(props: Props) = {
      t.modState(s => s.copy(connections = getCnxnModel()))
    }

    def render(props: Props, state: State) = {
      if (props.replyPost) {
        <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.multiple := true,
          ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo")(
          <.option(^.value := "")("Select"),
          for (receiver <- props.receivers) yield {
            for (connection <- state.connections; if receiver.connection == connection.connection) yield {
              <.option(^.value := upickle.default.write(connection.connection),
                ^.key := connection.connection.target, ^.selected := true)(
                if (connection.name.startsWith("@")) {
                  s"${connection.name}"
                } else {
                  s"@${connection.name}"
                })
            }
          },
          if (props.sender != null && props.sender.name != "me") {
            <.option(^.value := upickle.default.write(props.sender.connection),
              ^.key := props.sender.connection.target, ^.selected := true)(
              if (props.sender.name.startsWith("@")) {
                s"${props.sender.name}"
              } else {
                s"@${props.sender.name}"
              })
          } else {
            <.option()
          }
        )

      } else if (props.enableAllContacts) {
        <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize",
          ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo")(
          <.option(^.value := "")("Select"),
          <.option(^.value := AppUtils.ALL_CONTACTS_ID)("@All_Contacts"),
          for (connection <- state.connections) yield <.option(^.value := upickle.default.write(connection.connection),
            ^.key := connection.connection.target)(
            if (connection.name.startsWith("@")) {
              s"${connection.name}"
            } else {
              s"@${connection.name}"
            })
        )
      } else {
        <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize",
          ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo")(
          <.option(^.value := "")("Select"),
          for (connection <- state.connections) yield
            <.option(^.value := upickle.default.write(connection.connection),
              ^.key := connection.connection.target)(
              if (connection.name.startsWith("@")) {
                s"${connection.name}"
              } else {
                s"@${connection.name}"
              }))
      }

    }
  }


  val component = ReactComponentB[Props]("ConnectionsSelectize")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props, scope.state))
    .componentWillMount(scope => scope.backend.componentWillMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}
