package client.rootmodel

import shared.models.MessagePost

case class MessagesRootModel(messagesModelList: Seq[MessagePost]) {
  def updated(newMessagesResponse: MessagePost): MessagesRootModel = {
    messagesModelList.indexWhere(_.uid == newMessagesResponse.uid) match {
      case -1 =>
        MessagesRootModel(messagesModelList :+ newMessagesResponse)
      case target =>
        MessagesRootModel(messagesModelList.updated(target, newMessagesResponse))
    }
  }
}
