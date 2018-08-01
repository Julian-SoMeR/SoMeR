logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.16")

// Workaround for missing static SLF4J binder. Shouldn't be an issue after play version upgrade.
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.1.3")

