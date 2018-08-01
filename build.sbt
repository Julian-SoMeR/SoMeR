name := "somer"
 
version := "1.0"

//Disable the new default Filters... for now
lazy val `somer` = (project in file(".")).enablePlugins(PlayJava, PlayEbean).disablePlugins(PlayFilters)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies += guice

libraryDependencies ++= Seq( javaJdbc, ehcache , javaWs )

libraryDependencies += "org.mariadb.jdbc" % "mariadb-java-client" % "2.2.6"

libraryDependencies += "net.java.dev.jna" % "jna" % "4.2.1"

libraryDependencies += "io.ebean" % "ebean-elastic" % "11.18.1"

libraryDependencies += "io.ebean" % "ebean-agent" % "11.11.1"

unmanagedResourceDirectories in Test +=  (baseDirectory ( _ /"target/web/public/test" )).value

