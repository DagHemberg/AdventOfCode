package aoc.day12

type Cave = String
type Path = Vector[Cave]

extension (data: Vector[String])
  def caves = data
    .flatMap { case s"$first-$second" => Vector(first, second) }
    .toSet

extension (cave: Cave)
  def isBig = cave.toUpperCase == cave
  def isSmall = cave.toLowerCase == cave

extension (caves: Set[Cave])
  def connections(using data: Vector[Cave]): Map[Cave, Set[Cave]] = caves
    .map(cave => cave -> data
      .filter(_.contains(cave))
      .map(_.replaceAll(s"-?${cave}-?", ""))
      .toSet)
    .toMap