package i18n

/**
  * Created by a4tech on 9/28/2016.
  */
object I18NLoc {
  val projectRoot = "server/src/main/scala/i18n/"
  val en_us = projectRoot+"en_us.json"
  val fr = projectRoot+"fr.json"
}
/*
trait I18N {
    val WHAT_IS_SYNEREO: String
    val WATCH_THE_VIDEO: String
    val ABOUT: String
    val CHANGE_PROFILE_PICTURE: String
    val NODE_SETTINGS: String
    val SIGN_OUT: String
}

object I18N {
  private class EN_US extends I18N {
    val WHAT_IS_SYNEREO = "WHAT IS SYNEREO"
    val WATCH_THE_VIDEO = "WATCH THE VIDEO"
    val ABOUT = "ABOUT"
    val CHANGE_PROFILE_PICTURE = "CHANGE PROFILE PICTURE"
    val NODE_SETTINGS = "NODE SETTINGS"
    val SIGN_OUT = "SIGN OUT"
  }
  private class FR extends I18N {
    val WHAT_IS_SYNEREO = "QU'EST-CE QUE SYNEREO"
    val WATCH_THE_VIDEO = "VOIR LA VIDÉO"
    val ABOUT = "SUR"
    val CHANGE_PROFILE_PICTURE = "MODIFIER LA PHOTO DE PROFIL"
    val NODE_SETTINGS = "REGLAGES NODE"
    val SIGN_OUT = "SE DÉCONNECTER"
  }
  def apply (lang:String = "en-us"): I18N = {
    lang match {
      case "en-us" =>  new EN_US
      case "fr" => new FR
      case _ => new EN_US
    }
  }
}*/
