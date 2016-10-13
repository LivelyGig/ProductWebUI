package synereo.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.scalajs.dom._
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

  //  def getConnectionsFromSelectizeInput(selectizeInputId: String): Seq[Connection] = {
  //    var selectedConnections = Seq[Connection]()
  //    var allSelected: Boolean = false
  //    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"
  //    $(selector).each((ele: Element) =>
  //      if ($(ele).attr("data-value").toString == AppUtils.ALL_CONTACTS_ID) {
  //        allSelected = true
  //        selectedConnections = selectedConnections ++ SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.map(cnxnRes => cnxnRes.connection)
  //      } else if (!allSelected) {
  //        selectedConnections :+= upickle.default.read[Connection]($(ele).attr("data-value").toString)
  //      }
  //    )
  //    selectedConnections
  //  }

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


  case class Props(parentIdentifier: String, fromSelecize: () => Callback, option: Option[Int] = None, receivers: Seq[ConnectionsModel] = Nil , replyPost : Boolean = false,
                   enableAllContacts: Boolean = false)

  case class State(connections: Seq[ConnectionsModel] = Nil)

//  def replyPost (props: Props) = {
//    val cnxns = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
//    for(receiver <-props.receivers) yield  {
//     var getcnxn =  cnxns.filter(r => r.connection == receiver.connection)
//      if(getcnxn != null){
//        println("In getcnxn ")
//        $(s"${props.parentIdentifier}-selectize".asInstanceOf[js.Object]).
//      }
//    }
//  }

  case class Backend(t: BackendScope[Props, State]) {
    //    def initializeTagsInput(): Unit = {
    //      val props = t.props.runNow()
    //      val parentIdentifier = props.parentIdentifier
    //
    //      val count = props.option match {
    //        case Some(a) => a
    //        case None => 7
    //      }
    //
    //      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
    //      val selectizeInput: js.Object = s"#${parentIdentifier}-selectize"
    //      SynereoSelectizeFacade.initilizeSelectize(s"${parentIdentifier}-selectize", count, false)
    //
    //    }

    def initializeTagsInput(props: Props, state: State): Unit = {

      val parentIdentifier = t.props.runNow().parentIdentifier
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      val selectizeInput: js.Object = s"#${parentIdentifier}-selectize"
      //      $(selectizeInput).selectize()
      $(selectizeInput).selectize(SelectizeConfig
        .maxItems(30)
        .plugins("remove_button")
        .onItemAdd((item: String, value: js.Dynamic) => {
          props.fromSelecize().runNow()
          //          println("")
        })
        .onItemRemove((item: String) => {
          props.fromSelecize().runNow()
          //          println("")
        })

      )

    }

    def mounted(props: Props, state: State): Callback = Callback {
      initializeTagsInput(props, state)
      //      SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.map(cnxnRes => cnxnRes.connection).foreach(println)
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
//      if (props.enableAllContacts) {
//        <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo")(
//          <.option(^.value := "")("Select"),
//          <.option(^.value := AppUtils.ALL_CONTACTS_ID)("@All_Contacts"),
//          for (connection <- state.connections) yield <.option(^.value := upickle.default.write(connection.connection),
//            ^.key := connection.connection.target)(
//            if (connection.name.startsWith("@")) {
//              s"${connection.name}"
//            } else {
//              s"@${connection.name}"
//            })
//        )
//      } else {
        <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo" /*, ^.onChange --> getSelectedValues*/)(
          <.option(^.value := "")("Select"),
          for (connection <- state.connections) yield {

            for (receiver <- props.receivers) yield {
            if(/*props.replyPost==true &&*/ receiver.connection == connection.connection){
//              println("Got receiver")
//              println(connection.name)
              <.option(^.value := upickle.default.write(connection.connection),
                ^.key := connection.connection.target,"selected".reactAttr := "selected")(
                if (connection.name.startsWith("@")) {
                  s"${connection.name}"
                } else {
                  s"@${connection.name}"
                })
            } else
            {
//              println("no receiver matched with connection")
//              println(connection.name)
              <.option(^.value := upickle.default.write(connection.connection),
                ^.key := connection.connection.target) (
                if (connection.name.startsWith("@")) {
                  s"${connection.name}"
                } else {
                  s"@${connection.name}"
                })
            }
          }
          }
        )
      }

//    }
  }


  val component = ReactComponentB[Props]("SearchesConnectionList")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props, scope.state))
    .componentWillMount(scope => scope.backend.componentWillMount(scope.props))
    .build

  def apply(props: Props) = component(props)
}
