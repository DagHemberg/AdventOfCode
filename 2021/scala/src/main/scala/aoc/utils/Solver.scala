package aoc.utils

import scala.io.Source.*
import scala.util.{Try, Success, Failure}
import Console.*

def clearScreen = println("\u001b[2J")

abstract class Solver[A](day: String, expectedExampleSolution: A) extends App:

    private def readFile(folder: String, file: String): Vector[String] =
        Try(fromFile(s"./input/$folder/$file").getLines.toVector) match
            case Success(lines) => lines
            case Failure(e) =>
                println(s"""|[${RED}Error${RESET}] Something went wrong when reading $file in $folder:
                            |[${RED}Error${RESET}] $e""".stripMargin)
                Vector.empty

    def solve(data: Vector[String]): A

    clearScreen

    // val expectedExampleSolution = readFile("expected", fileName)
    val exampleInput = readFile("examples", s"$day.txt")
    val puzzleInput = readFile("puzzles", s"$day.txt")

    if Vector(exampleInput, puzzleInput).exists(_.size == 0) then System.exit(0)
    
    val result = solve(exampleInput)
    val examplePassed = result == expectedExampleSolution

    println(s"[${BLUE}-${RESET}] Evaluating example input...")
    if examplePassed then 
        println(s"""|[${GREEN}!${RESET}] Example input passed!
                    |  Output:   ${YELLOW}${result}${RESET}\n""".stripMargin)
    else 
        println(s"""|[${RED}!${RESET}] Example failed!
                    |  Expected: ${YELLOW}${expectedExampleSolution}${RESET}
                    |  Actual:   ${YELLOW}${result}${RESET}\n""".stripMargin)

    if examplePassed then
        println(s"[${BLUE}-${RESET}] Evaluating puzzle input...")
        val result = solve(puzzleInput)
        println(s"""|[${GREEN}!${RESET}] Result found!
                    |  Output:   ${YELLOW}$result${RESET}\n""".stripMargin)
    else
        println(s"[${YELLOW}!${RESET}] Aborting run...")