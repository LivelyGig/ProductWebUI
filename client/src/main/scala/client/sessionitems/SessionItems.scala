package client.sessionitems

/**
 * Created by shubham.k on 11-05-2016.
 */
object SessionItems {
  /**
   * This function returns all the session uri names
   * @return All session uri.
   */
  def getAllSessionUriName(): Seq[String] = {
    Seq(MessagesViewItems.MESSAGES_SESSION_URI, ProjectsViewItems.PROJECTS_SESSION_URI,
      ProfilesViewItems.PROFILES_SESSION_URI)
  }

  /*def getAllSessionUriExceptCnxs(): Seq[String] = {
    Seq(window.sessionStorage.getItem() MessagesViewItems.MESSAGES_SESSION_URI, ProjectsViewItems.PROJECTS_SESSION_URI)
  }*/
  object MessagesViewItems {
    val MESSAGES_SESSION_URI = "MESSAGES_SESSION_URI"
    /*val CURRENT_MESSAGE_LABEL_SEARCH = "CURRENT_MESSAGE_LABEL_SEARCH"
    val PREVIOUS_MESSAGE_LABEL_SEARCH = "PREVIOUS_MESSAGE_LABEL_SEARCH"*/
    val MESSAGE_POST_LABEL = "MESSAGEPOSTLABEL"
  }
  object ProjectsViewItems {
    val PROJECTS_SESSION_URI = "PROJECTS_SESSION_URI"
    /*val CURRENT_PROJECTS_LABEL_SEARCH = "CURRENT_PROJECTS_LABEL_SEARCH"
    val PREVIOUS_PROJECTS_LABEL_SEARCH = "PREVIOUS_PROJECTS_LABEL_SEARCH"*/
    val PROJECT_POST_LABEL = "PROJECTPOSTLABEL"
  }
  /*object ConnectionViewItems {
    val CONNECTIONS_SESSION_URI = "CONNECTIONS_SESSION_URI"
    val CONNECTION_LIST = "CONNECTION_LIST"
    val CURRENT_SEARCH_CONNECTION_LIST = "CURRENT_SEARCH_CONNECTION_LIST"
  }*/
  /*object SearchesView {
    val SEARCHES_SESSION_URI = "SEARCHES_SESSION_URI"
    val LIST_OF_LABELS = "LIST_OF_LABELS"
  }*/
  object ProfilesViewItems {
    val PROFILES_SESSION_URI = "PROFILES_SESSION_URI"
    val CURRENT_PROFILES_LABEL_SEARCH = "CURRENT_PROFILES_LABEL_SEARCH"
    /*val PREVIOUS_PROFILES_LABEL_SEARCH = "PREVIOUS_PROFILES_LABEL_SEARCH"
    val PROFILES_POST_LABEL = "PROFILESPOSTLABEL"*/
  }
  object ApiDetails {
    val API_URL = "API_URL"
    val API_HOST = "API_HOST"
    val API_PORT = "API_PORT"
  }
}
