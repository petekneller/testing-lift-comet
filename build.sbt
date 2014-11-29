import java.lang.System.getProperty
import java.nio.charset.StandardCharsets.UTF_8
import sbt.{Tests, File}
import sbt.Keys._
import sbtassembly.Plugin.{MergeStrategy, AssemblyKeys}
import AssemblyKeys._

assemblySettings

test in assembly := {}

mainClass in assembly := Some("bootstrap.liftweb.Start")

name := "bond"

version := "0.3"

scalaVersion := "2.11.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test"

//Lift stuff
resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases"
)

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

webSettings

unmanagedResourceDirectories in Test <+= (baseDirectory) {
  _ / "src/main/webapp"
}

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  val jettyVersion = "9.2.3.v20140905"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container, test, compile",
    "org.eclipse.jetty" % "jetty-plus" % jettyVersion % "container, test, compile",
    "org.eclipse.jetty" % "jetty-servlets" % jettyVersion % "container, test, compile",
//    "net.liftmodules"   %% "lift-jquery-module_2.6" % "2.4",
    "ch.qos.logback" % "logback-classic" % "1.1.2" % "compile"
  )
}

libraryDependencies += "org.scalaz.stream" %% "scalaz-stream" % "0.5a"

//the following is required to copy webapp to the right place in sbt
resourceGenerators in Compile <+= (resourceManaged, baseDirectory) map { (managedBase, base) =>
  val webappBase = base / "src" / "main" / "webapp"
  for {
    (from, to) <- webappBase ** "*" x rebase(webappBase, managedBase /
      "main" / "webapp")
  } yield {
    Sync.copy(from, to)
    to
  }
}

//the following block are needed for assembly
mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) => {
  case "about.html" => MergeStrategy.rename
  case "rootdoc.txt" => MergeStrategy.rename
  case x => old(x)
}
}

