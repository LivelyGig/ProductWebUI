package client.rootmodels

import client.models.MessagesModel

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
