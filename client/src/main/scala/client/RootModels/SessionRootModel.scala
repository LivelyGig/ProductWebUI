package client.RootModels

/**
  * Created by shubham.k on 05-08-2016.
  */
case class SessionRootModel (messagesSessionUri: String = "", projectSessionUri: String = "", profileSessionUri: String = "",
                             msgPinger: Boolean = false, profilePinger: Boolean = false, projectPinger: Boolean = false)
