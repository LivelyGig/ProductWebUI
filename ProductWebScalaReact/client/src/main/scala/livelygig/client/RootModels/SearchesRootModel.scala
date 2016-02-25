package livelygig.client.RootModels

import livelygig.client.models.SearchesModel

/**
  * Created by shubham.k on 2/23/2016.
  */
case class SearchesRootModel(searchesModel: Seq[SearchesModel]) {
  def updated (newSearch: SearchesModel) = {
    searchesModel.indexWhere(_.uid == newSearch.uid)
    match {
      case -1 =>
        SearchesRootModel(searchesModel:+newSearch)
      case target =>
        SearchesRootModel(searchesModel.updated(target, newSearch))
    }
  }
}
