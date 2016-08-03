package shared.RootModels

import shared.dtos.Connection
import shared.models.Label

// scalastyle:off
case class SearchesRootModel(searchesModel: Seq[Label], previousSearchLabel : String = "", previousSearchCnxn: Seq[Connection] = Nil) {
  def updated(newSearch: Label) = {
    searchesModel.indexWhere(_.uid == newSearch.uid) match {
      case -1 =>
        SearchesRootModel(searchesModel :+ newSearch)
      case target =>
        SearchesRootModel(searchesModel.updated(target, newSearch))
    }
  }
}
