name := "livelyGigSR_1.0"

version := "1.0"

scalaVersion := "2.11.7"

enablePlugins(ScalaJSPlugin)

scalaJSStage in Global := FastOptStage

libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "0.10.1"
libraryDependencies += "com.github.japgolly.scalajs-react" %%% "extra" % "0.10.1"

jsDependencies ++=  Seq("org.webjars.npm" % "react"     % "0.14.1" / "react-with-addons.js" commonJSName "React"    minified "react-with-addons.min.js")


// creates single js resource file for easy integration in html page
skip in packageJSDependencies := false



// copy  javascript files to js folder,that are generated using fastOptJS/fullOptJS

crossTarget in (Compile, fullOptJS) := file("js")

crossTarget in (Compile, fastOptJS) := file("js")

crossTarget in (Compile, packageJSDependencies) := file("js")

crossTarget in (Compile, packageScalaJSLauncher) := file("js")

crossTarget in (Compile, packageMinifiedJSDependencies) := file("js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))



scalacOptions += "-feature"