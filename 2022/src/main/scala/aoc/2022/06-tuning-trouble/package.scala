package aoc.y2022.day06
import problemutils.*, extensions.*

def findMarker(s: Int)(using buffer: String) = 
  buffer.sliding(s).indexWhere(str => str == str.distinct) + s
