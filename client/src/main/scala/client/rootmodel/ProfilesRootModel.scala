package client.rootmodel

import shared.models.ProfilesPost

/**
  * Created by shubham.k on 26-05-2016.
  */
case class ProfilesRootModel (profilesList: Seq[ProfilesPost]) {
  def updated(newProfile: ProfilesPost): ProfilesRootModel = {
    profilesList.indexWhere(_.uid == newProfile.uid) match {
      case -1 =>
        ProfilesRootModel(profilesList :+ newProfile)
      case target =>
        ProfilesRootModel(profilesList.updated(target, newProfile))
    }
  }
}
