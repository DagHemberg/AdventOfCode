val scala3Version = "3.5.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "aoc-2021",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-native" % "4.0.3",
    )
  )
