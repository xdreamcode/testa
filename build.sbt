import ReleaseTransformations._

name := "spark-dependecy-a"

scalaVersion := "2.11.12"

version := "0.5.1"


organization := "io.dreamcode"

updateOptions := updateOptions.value.withGigahorse(false)
//publishTo := Some("Xavier Nexus" at "http://35.168.35.94:8081/repository/maven-releases/")

publishTo := {
  val nexus = "http://jitpack.io/repository/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "maven-snapshots/")
  else
    Some("releases"  at nexus + "maven-releases/")
}

releaseProcess := Seq(
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion
)


artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.withClassifier(Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)

val requiredJars = List(
  "spark-daria-2.2.0_0.15.0.jar"
)

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter {_.data.getName != "spark-daria-2.2.0_0.15.0.jar"}

}


//
//lazy val fatJar = project
//  .enablePlugins(AssemblyPlugin)
//  .settings(
//    depend on the good stuff
//      skip in publish := true
//)
//
//lazy val cosmetic = project
//  .settings(
//    name := "shaded-something",
//    // I am sober. no dependencies.
//    packageBin in Compile := (assembly in (fatJar, Compile)).value
//  )



credentials += Credentials("Sonatype Nexus Repository Manager", "35.168.35.94", "admin", "admin123")

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.1"
libraryDependencies += "mrpowers" % "spark-daria" % "2.2.0_0.15.0"%"runtime"
