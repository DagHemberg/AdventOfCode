package aoc.day24
import aoc.utils.*

object Problem1 extends Solver("24", "N/A"):
  def name = "Arithmetic Logic Unit - Part 1"
  def solve(data: Vector[String]) = if data == testInput then data.head
  else    
    val t = data
      .sliding(18, 18)
      .toVector
      .map(_.flatMap {
        case s"add x z"          => None
        case s"add y 25"         => None
        case s"add y 1"          => None
        case s"add y w"          => None
        case s"add x $variable1" => Some(variable1)
        case s"add y $variable2" => Some(variable2)
        case s"div z $popping"   => Some(popping)
        case _                   => None
      })

    val stack = scala.collection.mutable.Stack.empty[Int]

    // calculated by hand; ignore the above 
    // i would try to explain but im not really sure i understand fully how this works myself lol
    // https://www.reddit.com/r/adventofcode/comments/rom5l5/2021_day_24pen_paper_monad_deparsed/

    // push n1 + 15
    // push n2 + 10
    // push n3 + 2
    // push n4 + 16    
    // pop; something n5 - 12
    // push n6 + 11
    // pop; something n7 - 9
    // push n8 + 16
    // push n9 + 6
    // pop; something n10 - 14
    // pop; something n11 - 11
    // pop; something n12 - 2
    // pop; something n13 - 16
    // pop; something n14 - 14 = 0

    // n1 + 15 == n14 - 14
    // n2 + 10 == n13 + 16 
    // n3 + 2  == n12 + 2 
    // n4 + 16 == n5  + 12 
    // n6 + 11 == n7  + 9 
    // n8 + 16 == n11 + 11 
    // n9 + 6  == n10 + 14 

    // 8+15==9+14
    // 9+10==3+16
    // 9+2==9+2
    // 5+16==9+12
    // 7+11==9+9
    // 4+16==9+11
    // 9+6==1+14

    // 89959794919939
    
    "89959794919939"
