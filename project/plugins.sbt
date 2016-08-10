// repository for Typesafe plugins
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.webjars.bower" % "jasmine" % "2.4.1" % "runtime"

addSbtPlugin("com.joescii" % "sbt-js-test" % "0.2.0")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.6")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.1")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.3.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

//addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

//addSbtPlugin("com.typesafe.sbt" % "sbt-start-script" % "0.10.0")
//addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")