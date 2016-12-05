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
    "com.lihaoyi" %%% "upickle" % Versions.upickle,
    "com.lihaoyi" %%% "autowire" % Versions.autowire,
    "me.chrons" %%% "boopickle" % Versions.booPickle
  ))

  /** Dependencies only used by the JVM project */
  val jvmDependencies = Def.setting(Seq(
    "com.vmunier" %% "scalajs-scripts" % Versions.scalajsScripts,
    "org.webjars" % "font-awesome" % Versions.fontAwesome % Provided,
    "org.webjars" % "bootstrap" % Versions.bootstrap % Provided,
    "org.json4s" %% "json4s-jackson" % Versions.json4s
  ))

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val scalajsDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % Versions.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % Versions.scalajsReact,
    "com.github.japgolly.scalacss" %%% "ext-react" % Versions.scalaCSS,
    "org.scala-js" %%% "scalajs-dom" % Versions.scalaDom,
    "me.chrons" %%% "diode" % Versions.diode,
    "me.chrons" %%% "diode-react" % Versions.diode,
    "org.querki" %%% "jquery-facade" % Versions.jqueryFacade,
    "org.querki" %%% "querki-jsext" % Versions.querkiJsext,
    "org.querki" %%% "bootstrap-datepicker-facade" % Versions.datePickerFacade,
    "org.denigma" %%% "selectize-facade" % Versions.selectizeFacade,
    "io.github.widok" %%% "scala-js-momentjs" % Versions.momentJSFacade,
    "com.lihaoyi" %%% "upickle" % Versions.upickle,
    "com.github.japgolly.scalajs-react" %%% "test" % Versions.test % "test",
    "org.scalatest" %%% "scalatest" % Versions.scalaTest % "test"
  ))

  /** Dependencies for external JS libs that are bundled into a single .js file according to dependency order */
  val jsDependencies = Def.setting(Seq(
    "org.webjars.npm" % "react" % Versions.react / "react-with-addons.js" commonJSName "React" minified "react-with-addons.min.js",
    "org.webjars.npm" % "react-dom" % Versions.react / "react-dom.js" commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js",
    "org.webjars.npm" % "react-dom" % Versions.react / "react-dom-server.js" commonJSName "ReactDOMServer",
    "org.webjars" % "jquery" % Versions.jQuery / "jquery.js" minified "jquery.min.js",
    "org.webjars" % "bootstrap" % Versions.bootstrap / "bootstrap.js" minified "bootstrap.min.js" dependsOn "jquery.js",
    //    "org.webjars" % "chartjs" % Versions.chartjs / "Chart.js" minified "Chart.min.js",
    "org.webjars" % "log4javascript" % Versions.log4js / "js/log4javascript_uncompressed.js" minified "js/log4javascript.js" dependsOn "jquery.js",
    "org.webjars" % "selectize.js" % Versions.selectizejs / "js/standalone/selectize.js" minified "js/standalone/selectize.min.js" dependsOn "jquery.js",
    "org.webjars" % "bootstrap-datepicker" % "1.6.1" / "bootstrap-datepicker.js" minified "bootstrap-datepicker.min.js" dependsOn "bootstrap.js"
  ))
}
