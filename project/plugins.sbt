// repository for Typesafe plugins
//resolvers += "denigma-releases" at "http://dl.bintray.com/denigma/denigma-releases/"
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

//resolvers += sbt.Resolver.bintrayRepo("denigma", "denigma-releases") //add resolver

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.7")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.6")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.6")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.2.9")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

//addSbtPlugin("me.lessis" % "bintray-sbt" % "0.2.1")

//addSbtPlugin("com.typesafe.sbt" % "sbt-start-script" % "0.10.0")
//addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
