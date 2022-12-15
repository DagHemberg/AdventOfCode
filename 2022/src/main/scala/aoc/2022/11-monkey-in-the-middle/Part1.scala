package aoc.y2022.day11
import paut.aoc.*
import problemutils.*, extensions.*

object Part1 extends Problem(11, 2022)(1)(10605L):
  def name = "Monkey in the Middle - Part 1"
  def solve(data: List[String]) = 
    var monkeys = parse(data)
    for i <- 1 to 20 do
      for n <- monkeys.indices do
        val items = monkeys(n).items
        items.foreach { item => 
          val current @ Monkey(id, _, operation, determineNext, _) = monkeys(n)

          val res = operation(item) / 3
          val recipient = determineNext(res)
          val nextMonkey = monkeys(recipient)

          monkeys = monkeys
            .updated(recipient, nextMonkey.copy(items = nextMonkey.items :+ res))
            .updated(n, current.copy(
              items = current.items.tail,
              inspections = current.inspections + 1
            ))
        }

    monkeys
      .map(_.inspections)
      .sorted
      .takeRight(2)
      .product
