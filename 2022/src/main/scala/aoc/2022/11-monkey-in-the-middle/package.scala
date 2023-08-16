package aoc.y2022.day11
import problemutils.*, extensions.*

case class Monkey (
  id:          Int,
  items:       List[Long],
  operation:   Long => Long, 
  next:        Long => Int,
  inspections: Long = 0 
):
  override def toString = s"Monkey $id: [${items.mkString(", ")}]"

def mods(data: List[String]) = data
  .map(_.trim())
  .collect:
    case s"Test: divisible by $mod" => mod.toLong
  .product

def parse(data: List[String]) = data
  .split("")
  .map: monkey => 
    val attributes = monkey.map(_.trim()) 
    
    val index = attributes(0) match 
      case s"Monkey $n:" => n.toInt

    val items = attributes(1) match 
      case s"Starting items: $items" => items.split(", ").map(_.toLong).toList
    
    val own = attributes(3) match
      case s"Test: divisible by $value" => value.toInt
    
    val operation = attributes(2) match 
      case s"Operation: new = old $op" => 
        op match
          case "* old" => (old: Long) => old * old
          case s"+ $value" => (old: Long) => old + value.toInt
          case s"* $value" => (old: Long) => old * value.toInt

    val test = (n: Long) => n % own == 0

    val recipient = (attributes(4), attributes (5)) match
      case (s"If true: throw to monkey $t", s"If false: throw to monkey $f") =>
        (pred: Boolean) => if pred then t.toInt else f.toInt

    Monkey(index, items, operation, test andThen recipient)
