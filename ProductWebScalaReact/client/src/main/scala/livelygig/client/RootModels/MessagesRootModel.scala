package livelygig.client.rootmodels
import livelygig.client.models.MessagesModel

/**
  * Created by shubham.k on 1/25/2016.
  */
case class MessagesRootModel(messagesModelList: Seq[MessagesModel]) {
  def updated (newMessagesResponse: MessagesModel) = {
//    println(newConnectionResponse)
    messagesModelList.indexWhere(_.uid == newMessagesResponse.uid)
    match {
      case -1 =>
        MessagesRootModel(messagesModelList:+newMessagesResponse)
      case target =>
        MessagesRootModel(messagesModelList.updated(target, newMessagesResponse))
    }
  }
}
