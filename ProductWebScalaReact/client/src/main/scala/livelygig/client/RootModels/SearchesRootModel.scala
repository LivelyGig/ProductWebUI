package livelygig.client.RootModels

import livelygig.client.models.Node

/**
  * Created by shubham.k on 2/23/2016.
  */
case class SearchesRootModel(nodes: Seq[Node]) {
  def updated (newNode: Node) = {
    nodes.indexWhere(_.uid == newNode.uid)
    match {
      case -1 =>
        SearchesRootModel(nodes:+newNode)
      case target =>
        SearchesRootModel(nodes.updated(target, newNode))
    }
  }
}
