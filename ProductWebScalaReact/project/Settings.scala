import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

/**
  * Application settings. Configure the build for your application here.
  * You normally don't have to touch the actual build definition after this.
  */
object Settings {
  /** The name of your application */
  val name = "scalajs-livelygig"

  /** The version of your application */
  val version = "1.0.1"

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

  /** Declare global dependency versions here to avoid mismatches in multi part dependencies */
  object versions {
    val scala = "2.11.7"
    val scalaDom = "0.8.2"
    val scalajsReact = "0.10.0"
    val scalaCSS = "0.3.1"
    val scalaRx = "0.2.8"
    val log4js = "1.4.10"
    val autowire = "0.2.5"
    val booPickle = "1.1.0"
    val uTest = "0.3.1"

    val react = "0.14.0"
    val jQuery = "2.1.4"
    val bootstrap = "3.3.4"
    val chartjs = "1.0.1"
    val bootstrapTagsinput="0.6.1"
    val playScripts = "0.3.0"
  }

  /**
    * These dependencies are shared between JS and JVM projects
    * the special %%% function selects the correct version for each project
    */
  val sharedDependencies = Def.setting(Seq(
    "com.lihaoyi" %%% "autowire" % versions.autowire,
    "me.chrons" %%% "boopickle" % versions.booPickle,
    "com.lihaoyi" %%% "utest" % versions.uTest
  ))

  /** Dependencies only used by the JVM project */
  val jvmDependencies = Def.setting(Seq(
    "com.vmunier" %% "play-scalajs-scripts" % versions.playScripts,
    "org.webjars" % "font-awesome" % "4.3.0-1" % Provided,
    "org.webjars" % "bootstrap" % versions.bootstrap % Provided
  ))

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val scalajsDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % versions.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % versions.scalajsReact,
    "com.github.japgolly.scalacss" %%% "ext-react" % versions.scalaCSS,
    "org.scala-js" %%% "scalajs-dom" % versions.scalaDom,
    "com.lihaoyi" %%% "scalarx" % versions.scalaRx
  ))

  /** Dependencies for external JS libs that are bundled into a single .js file according to dependency order */
  val jsDependencies = Def.setting(Seq(
    "org.webjars.npm" % "react" % versions.react / "react-with-addons.js" commonJSName "React" minified "react-with-addons.min.js",
    "org.webjars.npm" % "react-dom" % versions.react / "react-dom.js" commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js",
    "org.webjars" % "jquery" % versions.jQuery / "jquery.js" minified "jquery.min.js",
    "org.webjars" % "bootstrap" % versions.bootstrap / "bootstrap.js" minified "bootstrap.min.js" dependsOn "jquery.js",
    //  "org.webjars.bower" % "split-pane" % "0.5.1",
    "org.webjars" % "chartjs" % versions.chartjs / "Chart.js" minified "Chart.min.js",
    "org.webjars" % "log4javascript" % versions.log4js / "js/log4javascript_uncompressed.js" minified "js/log4javascript.js",
    "org.webjars.bower" % "perfect-scrollbar" % "0.6.7"/ "perfect-scrollbar.js"
//    "org.webjars" % "typeahead.js" % "0.9.3",
//    "org.webjars" % "bootstrap-tagsinput" % "0.6.1"/ "bootstrap-tagsinput.js" dependsOn "typeahead.js"
  ))
}
