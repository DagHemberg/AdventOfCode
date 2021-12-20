package aoc.day20
import aoc.utils.*

extension (image: Matrix[String])
  def decode(ind: Index, round: Int)(using algorithm: String) = 
    // bastards pulled a sneaky one
    def determineOutside(round: Int) = 
      if algorithm.head == '.' then "."
      else if round % 2 != 0 then "." else "#"

    val bin = Vector
      .tabulate(3,3)((r, c) => Index(r + ind.row - 1, c + ind.col - 1))
      .flatten
      .map(i => if image.indexOutsideBounds(i) then determineOutside(round) else image(i))
      .map(c => if c == "#" then 1 else 0)
      .mkString
    
    algorithm(BigInt(bin, 2).toString(10).toInt).toString
  
  def enhance(round: Int)(using algorithm: String) = Matrix
    (image.height + 2, image.width + 2)
    ((r, c) => Index(r - 1, c - 1))
    .map(image.decode(_, round))
