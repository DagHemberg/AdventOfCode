val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "aoc",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    // crossScalaVersions ++= Seq("2.13.6", "3.0.0"),
    // libraryDependencies += ("com.github.dwickern" %% "scala-nameof" % "3.0.0" % "provided").cross(CrossVersion.for3Use2_13)
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
  )
