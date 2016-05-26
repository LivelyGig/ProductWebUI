package shared.sessionitems

/**
 * Created by shubham.k on 11-05-2016.
 */
object SessionItems {
  /**
   * This function returns all the session uri names
   * except fot connection session uri.
   * Why? Well if you look at its usage in agentlogin.scala
   * you see its initialising multiple sessions however the session
   * for connections is already initialised in processlogin function.
   * Reason behind such a structure is that at this point We really don't
   * know if we require multiple sessions. Even if we do, We don't want the
   * user to wait for all the session initialisation. Its more of a background process
   * @return All session uri except for connections.
   */
  def getAllSessionUriNameExceptCnxs(): Seq[String] = {
    Seq(MessagesViewItems.MESSAGES_SESSION_URI, ProjectsViewItems.PROJECTS_SESSION_URI)
  }

  /*def getAllSessionUriExceptCnxs(): Seq[String] = {
    Seq(window.sessionStorage.getItem() MessagesViewItems.MESSAGES_SESSION_URI, ProjectsViewItems.PROJECTS_SESSION_URI)
  }*/
  object MessagesViewItems {
    val MESSAGES_SESSION_URI = "MESSAGES_SESSION_URI"
    val CURRENT_MESSAGE_LABEL_SEARCH = "CURRENT_MESSAGE_LABEL_SEARCH"
    val PREVIOUS_MESSAGE_LABEL_SEARCH = "PREVIOUS_MESSAGE_LABEL_SEARCH"
    val MESSAGE_POST_LABEL = "MESSAGEPOSTLABEL"
  }
  object ProjectsViewItems {
    val PROJECTS_SESSION_URI = "PROJECTS_SESSION_URI"
    val CURRENT_PROJECTS_LABEL_SEARCH = "CURRENT_PROJECTS_LABEL_SEARCH"
    val PREVIOUS_PROJECTS_LABEL_SEARCH = "PREVIOUS_PROJECTS_LABEL_SEARCH"
    val PROJECT_POST_LABEL = "PROJECTPOSTLABEL"
  }
  object ConnectionViewItems {
    val CONNECTIONS_SESSION_URI = "CONNECTIONS_SESSION_URI"
    val CONNECTION_LIST = "CONNECTION_LIST"
  }
  object SearchesView {
    val LIST_OF_LABELS = "LIST_OF_LABELS"
  }
  object ProfilesViewItems {
    val PROFILES_SESSION_URI = "PROFILES_SESSION_URI"
    val CURRENT_PROFILES_LABEL_SEARCH = "CURRENT_PROFILES_LABEL_SEARCH"
    val PREVIOUS_PROFILES_LABEL_SEARCH = "PREVIOUS_PROFILES_LABEL_SEARCH"
    val PROFILES_POST_LABEL = "PROFILESPOSTLABEL"
  }
}
