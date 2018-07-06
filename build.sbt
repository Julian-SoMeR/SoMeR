name := "somer"
 
version := "1.0" 
      
lazy val `somer` = (project in file(".")).enablePlugins(PlayJava)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq( javaJdbc, cache , javaWs )

libraryDependencies += "org.mariadb.jdbc" % "mariadb-java-client" % "1.1.7"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

