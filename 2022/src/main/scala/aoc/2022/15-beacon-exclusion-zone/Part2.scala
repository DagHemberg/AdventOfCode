package aoc.y2022.day15
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(15, 2022)(2)(Skip):
  def name = "Beacon Exclusion Zone - Part 2"
  def solve(data: List[String]) = 

    val sensors = parse(data)
    var found = false
    def validate(p: Pos2D) = sensors.forall(s => s.isInsideMap(p) && !s.isInsideRange(p))

    given size: Int = 4000000

    var latest = sensors.head.top
    for sensor <- sensors if !found do
      given Sensor = sensor
      val top = sensor.top
      val candidate = top.step.doUntil(p => found || p == top) { p =>
        val stepped = p.step
        if validate(stepped) then found = true
        stepped
      }
      latest = candidate

    latest.x.toLong * 4000000 + latest.y.toLong
