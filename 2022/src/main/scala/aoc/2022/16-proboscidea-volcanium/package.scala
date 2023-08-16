package aoc.y2022.day16
import problemutils.*, extensions.*

def getTunnels(data: List[String]) = 
  data.map {
    case s"Valve $name has flow rate=$n; tunnels lead to valves $valves" =>
      name -> valves.split(", ").toSet
    case s"Valve $name has flow rate=$n; tunnel leads to valve $valve" =>
      name -> Set(valve)
  }.toMap

def getFlow(data: List[String]) = 
  data
    .map:
      case s"Valve $name has flow rate=$n; $other" => name -> n.toInt
    .toMap

case class State
  (current: String, openValves: Set[String], elapsedTime: Int)
  (using tunnels: Map[String, Set[String]], flow: Map[String, Int]):
  
  val step = elapsedTime + 1
  
  lazy val open = State(current, openValves + current, step)
  // lazy val cost = max - openValves.toSeq.sumBy(flow) // probably the culprit, is it even fixable?
  lazy val cost = flow.filterKeys(v => !openValves(v)).sumBy(snd)
  
  def fromHere: Set[State] =
    if elapsedTime == 30 then Set.empty
    else 
      val others = tunnels(current).map(State(_, openValves, step))
      if flow(current) != 0 && !openValves(current) then others + open
      else others