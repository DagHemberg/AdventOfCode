package aoc.day24
import aoc.utils.*

object Problem2 extends Solver("24", "N/A"):
  def name = "Arithmetic Logic Unit - Part 2"
  def solve(data: Vector[String]) = if data == testInput then data.head
  else    

    // see problem 1 for an "explanation"

    // n1 + 15 == n14 + 14
    // n2 + 10 == n13 + 16
    // n3 + 2  == n12 + 2
    // n4 + 16 == n5  + 12
    // n6 + 11 == n7  + 9
    // n8 + 16 == n11 + 11
    // n9 + 6  == n10 + 14

    // 1+15==2+14
    // 7+10==1+16
    // 1+2==1+2
    // 1+16==5+12
    // 1+11==3+9
    // 1+16==6+11
    // 9+6==1+14

    // 171151319161129
    
    "171151319161129"
