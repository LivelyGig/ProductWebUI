object Versions extends WebJarsVersions with ScalaJSVersions with SharedVersions with JsVersions
{
  val scalaVersion = "2.11.8"
  val appVersion = "1.0.1"
  val akkaHttpExtensions = "0.0.10"
}

trait ScalaJSVersions {

  val scalajsReact = "0.11.0"
  val scalaCSS = "0.4.1"
  val scalaDom = "0.9.0"
  val diode = "0.5.2" //"0.5.1"
  val jqueryFacade = "1.0-RC6"//"1.0-RC3"
  val datePickerFacade = "0.5"
  val selectizeFacade = "0.12.1-0.2.1"
  val momentJSFacade = "0.1.4"
  val jQuery = "2.2.4"

}

//versions for libs that are shared between client and server
trait SharedVersions
{

  val upickle = "0.4.1" //"0.3.8"
  val scalatest = "3.0.0-M15"
  val autowire = "0.2.5"
  val booPickle = "1.1.2"
  val scalaTest = "3.0.0-M15"

}


trait WebJarsVersions{

  val bootstrap = "3.3.6"
  val playScripts = "0.4.0"
  val playWS = "2.4.3"
  val fontAwesome = "4.5.0"

}

trait JsVersions {
  val log4js = "1.4.13-1"
  val react =   "15.1.0" //"15.0.1"
  val chartjs = "2.1.3"
  val datePicker = "1.5.0-1"
}