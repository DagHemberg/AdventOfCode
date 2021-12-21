package aoc.day20
import aoc.utils.*

case class Image(pixels: Matrix[String], iteration: Int):
  def apply(x: Int, y: Int): String = pixels(x, y)
  def apply(i: Index): String = pixels(i)
  val (height, width) = pixels.size
  def countLit = pixels.count(_ == "#")

extension (img: Image)
  def decode(ind: Index)(using algorithm: String) = 
    def outside = 
      if algorithm.head != '.' && (img.iteration + 1) % 2 == 0 
        then "#" 
        else "."

    // they pulled a sneaky one
    val bin = Vector
      .tabulate(3,3)((r, c) => Index(r + ind.row - 1, c + ind.col - 1))
      .flatten
      .map(i => if img.pixels.indexOutsideBounds(i) 
        then outside
        else img(i))
      .map(c => if c == "#" then 1 else 0)
      .mkString
    
    algorithm(BigInt(bin, 2).toString(10).toInt).toString
  
  def enhance(using algorithm: String) = Image(Matrix
    (img.height + 2, img.width + 2)
    ((r, c) => Index(r - 1, c - 1))
    .map(img.decode(_)), img.iteration + 1)
