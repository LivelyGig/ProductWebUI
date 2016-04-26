package client.rootmodels

import shared.models.LabelModel

case class SearchesRootModel(searchesModel: Seq[LabelModel]) {
  def updated (newSearch: LabelModel) = {
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
