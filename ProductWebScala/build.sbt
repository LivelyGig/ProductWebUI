enablePlugins(ScalaJSPlugin)

name := "LGigScalaReact_master"

version := "1.0"

scalaVersion := "2.11.7"

val scalaJSReactVersion ="0.10.1"

libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "0.10.1"

// React JS itself (Note the filenames, adjust as needed, eg. to remove addons.)
jsDependencies += "org.webjars.npm" % "react"     % "0.14.1" / "react-with-addons.js" commonJSName "React"    minified "react-with-addons.min.js"
jsDependencies += "org.webjars.npm" % "react-dom" % "0.14.1" / "react-dom.js"         commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js"