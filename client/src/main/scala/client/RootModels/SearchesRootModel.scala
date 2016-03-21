package client.rootmodels

import client.models.Label

case class SearchesRootModel(searchesModel: Seq[Label]) {
  def updated (newSearch: Label) = {
    val searchu = searchesModel
    searchesModel.indexWhere(_.uid == newSearch.uid)
    match {
      case -1 =>
        SearchesRootModel(searchesModel:+newSearch)
      case target =>
        SearchesRootModel(searchesModel.updated(target, newSearch))
    }
  }
}
