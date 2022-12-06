package aoc.y2022.day06
import problemutils.*, extensions.*

def findMarker(size: Int)(using buffer: String) = 
  buffer.sliding(size).indexWhere(str => str == str.distinct) + size
