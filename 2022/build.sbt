name := "aoc"

lazy val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.3.0",
    libraryDependencies ++= Seq(
      "io.github.daghemberg" %% "paut-program" % "0.1.4",
      "io.github.daghemberg" %% "problemutils" % "0.1.1",
      "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
      // add your own dependencies here!
      ),
      console / initialCommands  := 
        """|import paut.aoc.*
           |import problemutils.*
           |import extensions.*""".stripMargin,
  )
  