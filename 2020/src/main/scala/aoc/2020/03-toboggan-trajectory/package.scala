package aoc.y2020.day03
import problemutils.*, extensions.*

def parse(data: List[String]) = data
  .map(_.split("").toVector)
  .toVector
  .toMatrix

def calculateTrajectory(map: Matrix[String], angle: Vec2) = 
  List.tabulate
    (map.height / angle.y)
    (i => (i * angle.y, (i * angle.x) % map.width))
