import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt.Project.projectToRef
import JsTestBrowsers._

// a special crossProject for configuring a JS/JVM/shared structure
lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared"))
  .settings(
    scalaVersion := Versions.scalaVersion,
    libraryDependencies ++= Settings.sharedDependencies.value
  )

  // set up settings specific to the JS project
  .jsConfigure(_ enablePlugins ScalaJSPlay)

lazy val sharedJVM = shared.jvm.settings(name := "sharedJVM")

lazy val sharedJS = shared.js.settings(name := "sharedJS")

// use eliding to drop some debug code in the production build
lazy val elideOptions = settingKey[Seq[String]]("Set limit for elidable functions")

// instantiate the JS project for SBT with some additional settings
// "client" for livelygig or "sclient" for synereo below
// and edit server/src/main/twirl/views/index.scala.html file appropirately

//lazy val clientsrpDeps = List("sjcl.js", "srp.js", "bitArray.js", "srpBn.js",
//  "codecHex.js", "codecString.js", "sha256.js", "sha512.js", "aes.js", "random.js","srp-client.js", "prolog_parser.js", "validator.js")

lazy val pCompile = "client"
lazy val lessFile = if (pCompile == "sclient") "synereo-main.less" else "main.less"
lazy val clientJSDeps = if (pCompile == "sclient") List("prolog_parser.js", "validator.js", "synereo_app.js", "synereo_selectize.js") else List("prolog_parser.js", "validator.js") /*++ clientsrpDeps*/
lazy val client: Project = (project in file(pCompile))
  .settings(
    name := "client",
    version := Versions.appVersion,
    scalaVersion := Versions.scalaVersion,
    scalacOptions ++= Settings.scalacOptions,
    resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases"), //add resolver
    libraryDependencies ++= Settings.scalajsDependencies.value,
    // by default we do development build, no eliding
    elideOptions := Seq(),
    scalacOptions ++= elideOptions.value,
    jsDependencies ++= Settings.jsDependencies.value,
    // RuntimeDOM is needed for tests
    jsDependencies += RuntimeDOM % "test",
    // yes, we want to package JS dependencies
    skip in packageJSDependencies := false,
    // use Scala.js provided launcher code to start the client app
    persistLauncher := true,
    persistLauncher in Test := false,
    jsResources := Seq(),
    jsTestResources := Seq(),
    jsAsyncWait := false,
    jsAsyncWaitTimeout := None,
    //client side js deps
    jsDependencies ++= clientJSDeps.map(ProvidedJS / _),
    // use uTest framework for tests
    testFrameworks += new TestFramework("utest.runner.Framework")
    // libraryDependencies ++= Seq(
    // Dependencies.tests.scalajsenvs)
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSPlay,SbtWeb,/*ScalaJSWeb,*/JavaServerAppPackaging)
  .dependsOn(sharedJS)

//lazy val server = project.settings(
//  scalaJSProjects := Seq(client),
//  pipelineStages in Assets := Seq(scalaJSPipeline)
//).enablePlugins(SbtWeb)
//
//lazy val client = project.enablePlugins(ScalaJSPlugin, ScalaJSWeb)

//// Indicate that unit tests will access the DOM
//  requiresDOM := true
//// Compile tests to JS using fast-optimisation
//  scalaJSStage in Test := FastOptStage
//jsEnv in Test := new PhantomJS2Env(scalaJSPhantomJSClassLoader.value)
// Client projects (just one in this case)
lazy val clients = Seq(client)

// instantiate the JVM project for SBT with some additional settings
lazy val server = (project in file("server"))
  .settings(
    name := "server",
    version := Versions.appVersion,
    scalaVersion := Versions.scalaVersion,
    scalacOptions ++= Settings.scalacOptions,
    libraryDependencies ++= Settings.jvmDependencies.value,
    commands += ReleaseCmd,
    // connect to the client project
    scalaJSProjects := clients,
    pipelineStages := Seq(scalaJSProd, digest, gzip),
//    pipelineStages in Assets := Seq(scalaJSPipeline),
    // compress CSS
    LessKeys.compress in Assets := true,
    includeFilter in(Assets, LessKeys.less) := lessFile,
    // settings for testing javascript stuff with Jasmine
    // launch js tests with the command:
    //    sbt clean jsLs jsTest
    jsResources := {
      val main = (sourceDirectory in Compile).value
      Seq(
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "sjcl.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "srp.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "bitArray.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "bn.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "codecHex.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "codecString.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "sha256.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "sha512.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "aes.js",
        main / "assets" / "javascripts" / "srp-client" / "sjcl-core" / "random.js",
        main / "assets" / "javascripts" / "srp-client" / "srp-client.js")
    },
    jsTestResources := {
      val test = (sourceDirectory in Test).value
      ((test / "scripts") ** "*.spec.js").get
    },
    jsAsyncWait := false,
    jsAsyncWaitTimeout := None,
    jsTestBrowsers := Seq(Chrome, Firefox38, InternetExplorer11)
  )
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin) // use the standard directory layout instead of Play's custom
  .aggregate(clients.map(projectToRef): _*)
  .dependsOn(sharedJVM)

// Command for building a release
lazy val ReleaseCmd = Command.command("release") {
  state => "set elideOptions in client := Seq(\"-Xelide-below\", \"WARNING\")" ::
    "client/clean" ::
    "client/test" ::
    "server/clean" ::
    "server/test" ::
    "server/dist" ::
    "set elideOptions in client := Seq()" ::
    state
}

// loads the Play server project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
