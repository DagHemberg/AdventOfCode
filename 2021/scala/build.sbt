val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "aoc",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "com.github.dwickern" %% "scala-nameof" % "4.0.0" % "provided", // blir bara a ju :(
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
  )
