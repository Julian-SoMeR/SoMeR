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

libraryDependencies += "io.ebean" % "ebean" % "11.19.1"

libraryDependencies += "io.ebean" % "ebean-elastic" % "11.18.1"

libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "3.11.0"

libraryDependencies += "com.squareup.okio" % "okio" % "1.14.0"


unmanagedResourceDirectories in Test +=  (baseDirectory ( _ /"target/web/public/test" )).value

