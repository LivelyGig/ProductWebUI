package synereo.client.RootModels

import synereo.client.models.Label

/**
  * Created by shubham.k on 2/23/2016.
  */
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
