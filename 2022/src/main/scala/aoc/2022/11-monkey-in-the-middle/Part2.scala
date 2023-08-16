package aoc.y2022.day11
import paut.aoc.*
import problemutils.*, extensions.*

object Part2 extends Problem(11, 2022)(2)(2713310158L):
  def name = "Monkey in the Middle - Part 2"
  def solve(data: List[String]) = 
    val mod = mods(data)
    var monkeys = parse(data)
    for i <- 1 to 10_000 do
      for n <- monkeys.indices do
        monkeys(n).items.foreach: item => 
          val current @ Monkey(id, _, operation, determineNext, _) = monkeys(n)

          val res = operation(item) % mod
          val recipient = determineNext(res)
          val nextMonkey = monkeys(recipient)

          monkeys = monkeys
            .updated(recipient, nextMonkey.copy(items = nextMonkey.items :+ res))
            .updated(n, current.copy(
              items = current.items.tail,
              inspections = current.inspections + 1
            ))

    monkeys
      .map(_.inspections)
      .sorted
      .takeRight(2)
      .product
