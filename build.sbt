organization := "com.github.underscorenico"

name := "sparsal"

version := "0.1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
    <url>https://github.com/underscorenico/sparsal</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com/underscorenico/sparsal.git</url>
      <connection>scm:git:git@github.com:underscorenico/sparsal.git</connection>
    </scm>
    <developers>
      <developer>
        <id>underscorenico</id>
        <name>Nicolas Vinuesa</name>
      </developer>
    </developers>)
