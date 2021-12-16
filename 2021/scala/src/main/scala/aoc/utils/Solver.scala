package aoc.utils

import scala.io.Source.*
import scala.util.{Try, Success, Failure}
import Console.*
import TimedEval.*

abstract class Solver[A](day: String, expectedTestSolution: A) extends App:

  def name: String
  def solve(data: Vector[String]): A

  override def toString = s"Day $day: $name"

  private def error(msg: String, trim: Boolean = false) =
    s"[${RED}X${RESET}] ${if !trim then s"${RED}Something went wrong${RESET} " else ""}$msg"

  private def success(msg: String) =
    s"[${GREEN}O${RESET}] ${GREEN}$msg${RESET}"

  private def info(msg: String) =
    s"[${CYAN}+${RESET}] $msg"

  private def tinyStack(e: Throwable) =
    s"""|[${RED}X${RESET}] ${e.getClass.getSimpleName}: ${e.getMessage}
        |${e.getStackTrace
            .dropWhile(s => !s.toString.startsWith("aoc"))
            .takeWhile(s => !s.toString.startsWith("aoc.utils.Solver"))
            .dropRight(1)
            .toVector
            .map(s => s"      $s")
            .mkString("\n")}""".stripMargin

  private def readFile(folder: String, file: String) =
    Try(fromFile(s"input/$folder/$file").getLines.toVector) match
      case Success(lines) => lines
      case Failure(e) =>
        println(s"""|${error(s"when reading $file in $folder")}:
                    |    ${e}""".stripMargin)
        Vector.empty

  for i <- 1 to 100 do println()
  println(info(toString))

  val testInput = readFile("examples", s"$day.txt")
  val puzzleInput = readFile("puzzles", s"$day.txt")

  if Vector(testInput, puzzleInput).exists(_.size == 0) then System.exit(1)

  println(info("Evaluating example input..."))

  private val testEval = Try(time(solve(testInput))) match
    case Success(eval) => eval
    case Failure(e) =>
      println(s"""|${error("when solving the example problem:")}
                  |${tinyStack(e)}""".stripMargin)
      System.exit(1)
      TimedEval(0, expectedTestSolution) // never reached

  private val examplePassed = testEval.result == expectedTestSolution

  if examplePassed then
    println(f"""|${success("Example input passed!")}
                |    Output: ${YELLOW}${testEval.result}${RESET}
                |    Time: ${testEval.duration}%2.6f s%n""".stripMargin)

    println(info("Evaluating puzzle input..."))
    Try(time(solve(puzzleInput))) match
      case Success(eval) =>
        println(f"""|${success("Solution found!")}
                    |    Output: ${YELLOW}${eval.result}${RESET}
                    |    Time: ${eval.duration}%2.6f s%n""".stripMargin)
      case Failure(e) =>
        print(s"""|${error("when solving the puzzle:")}
                  |${tinyStack(e)}\n""".stripMargin)
        System.exit(1)
  else
    println(f"""|${error(s"${RED}Example failed!${RESET}", trim = true)}
                |    Expected: ${CYAN}${expectedTestSolution}${RESET}
                |    Actual:   ${YELLOW}${testEval.result}${RESET}
                |    Time: ${testEval.duration}%2.6f s""".stripMargin)
    System.exit(1)
