package aoc.y2022.day08
import problemutils.*, extensions.*

def parse(data: List[String]) = 
  Matrix.from(data.map(_.map(_.asDigit).toSeq).toSeq)

def shed[A](mat: Matrix[A]) = 
  mat.slice(1, 1)(mat.height - 2, mat.width - 2)
