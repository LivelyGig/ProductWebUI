// repository for Typesafe plugins
//resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "local-maven-cache repo" at "file://" + Path.userHome.absolutePath + "/.m2/repository/"
)

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.5")
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.6")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0")
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.4")
addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.2.8")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")
