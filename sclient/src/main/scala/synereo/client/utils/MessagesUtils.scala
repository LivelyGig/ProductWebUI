package synereo.client.utils

import org.scalajs.dom._
import shared.dtos.Connection
import shared.models.Label
import shared.sessionitems.SessionItems
import synereo.client.components.ConnectionsLabelsSelectize
import synereo.client.handlers.StoreCnxnAndLabels

/**
  * Created by shubham.k on 15-07-2016.
  */
object MessagesUtils {
  def storeCnxnAndLabels(cnxseq: Seq[Connection], labels: Seq[String]): Unit = {
    window.sessionStorage.setItem(SessionItems.ConnectionViewItems.CURRENT_SEARCH_CONNECTION_LIST,
      upickle.default.write[Seq[Connection]](cnxseq))
    val searchLabels = LabelsUtils.buildProlog(
      Seq(Label(text = SessionItems.MessagesViewItems.MESSAGE_POST_LABEL)) ++ labels.map(currentLabel => Label(text = currentLabel)
      ), LabelsUtils.PrologTypes.Each)
    window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH, searchLabels)
  }

}
