import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

/**
  * Application settings. Configure the build for your application here.
  * You normally don't have to touch the actual build definition after this.
  */
object Settings {
  /** The name of your application */
  val name = "scalajs-synereo"

  /** Options for the scala compiler */
  val scalacOptions = Seq(
    "-Xlint",
    "-unchecked",
    "-deprecation",
    "-feature"
  )

  /** Set some basic options when running the project with Revolver */
  val jvmRuntimeOptions = Seq(
    "-Xmx1G"
  )

  /**
    * These dependencies are shared between JS and JVM projects
    * the special %%% function selects the correct version for each project
    */
  val sharedDependencies = Def.setting(Seq(
    "com.lihaoyi" %%% "autowire" % Versions.autowire,
    "me.chrons" %%% "boopickle" % Versions.booPickle,
//    "com.lihaoyi" %%% "utest" % Versions.uTest,
	  "com.lihaoyi" %%% "upickle" % "0.3.8",
    "org.scalatest" %%% "scalatest" % Versions.scalaTest % "test"/*,
    "org.scala-js" %%% "scalajs-dom" % Versions.scalaDom,
    "me.chrons" %%% "diode" % Versions.diode,
    "me.chrons" %%% "diode-react" % Versions.diode,
    "io.github.widok" %%% "scala-js-momentjs" % "0.1.4"*/
  ))

  /** Dependencies only used by the JVM project */
  val jvmDependencies = Def.setting(Seq(
    "com.vmunier" %% "play-scalajs-scripts" % Versions.playScripts,
    "com.typesafe.play" %% "play-ws" %  "2.5.3",
    "org.webjars" % "font-awesome" %"4.6.2"  % Provided,
    "org.webjars" % "bootstrap" % Versions.bootstrap % Provided,
    "org.json4s" %% "json4s-jackson" % 	"3.3.0" ,
    "org.scalaj" %% "scalaj-http" %  "2.3.0"
  ))

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val scalajsDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % Versions.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % Versions.scalajsReact,
    "com.github.japgolly.scalacss" %%% "ext-react" % Versions.scalaCSS,
    "org.scala-js" %%% "scalajs-dom" % Versions.scalaDom,
    "me.chrons" %%% "diode" % Versions.diode,
    "me.chrons" %%% "diode-react" % Versions.diode,
    "org.querki" %%% "jquery-facade" % "1.0-RC3",
    "org.querki" %%% "querki-jsext" % "0.6",
    "org.querki" %%% "bootstrap-datepicker-facade" % "0.5",
    "org.denigma" %%% "selectize-facade" % "0.12.1-0.2.1",
    "com.github.nscala-time" %% "nscala-time" % "2.12.0",
    "io.github.widok" %%% "scala-js-momentjs" % "0.1.4"
//    "org.webjars.bower" % "moment-timezone" % "0.5.4"
  ))

  /** Dependencies for external JS libs that are bundled into a single .js file according to dependency order */
  val jsDependencies = Def.setting(Seq(
    "org.webjars.npm" % "react" % Versions.react / "react-with-addons.js" commonJSName "React" minified "react-with-addons.min.js",
    "org.webjars.npm" % "react-dom" % Versions.react / "react-dom.js" commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js",
    "org.webjars" % "jquery" % Versions.jQuery / "2.2.3/jquery.js" minified "jquery.min.js",
    "org.webjars" % "bootstrap" % Versions.bootstrap / "bootstrap.js" minified "bootstrap.min.js" dependsOn "2.2.3/jquery.js",
    "org.webjars" % "chartjs" % Versions.chartjs / "Chart.js" minified "Chart.min.js",
    "org.webjars" % "log4javascript" % Versions.log4js / "js/log4javascript_uncompressed.js" minified "js/log4javascript.js" dependsOn "2.2.3/jquery.js",
    //"org.webjars" % "bootstrap-datepicker" % versions.datePicker / "bootstrap-datepicker.js" minified "bootstrap-datepicker.min.js" dependsOn "bootstrap.js",
    "org.webjars" % "selectize.js" % "0.12.1" / "js/standalone/selectize.js" minified "js/standalone/selectize.min.js" dependsOn "2.2.3/jquery.js"
  ))
}
