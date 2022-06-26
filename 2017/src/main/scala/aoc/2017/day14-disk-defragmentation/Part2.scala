package aoc.y2017.day14
import aoc.utils.*

object Part2 extends Problem("2017", "14", "2")(1242):
  def name = "Disk Defragmentation - Part 2"
  def solve(data: Seq[String]) =
    val disk = defrag(data.head)
    given Matrix[Pos2D] = disk.indices

    def adj(pos: Pos2D): Set[Pos2D] = 
      pos.neighboursOrthIn.filter((r, c) => disk(r, c) == '1').toSet

    disk
      .zipWithIndex
      .toVector
      .flatMap(_.collect { case ('1', i) => i })
      .foldLeft((0, Set.empty[Pos2D])) { case ((count, seen), index) =>
        if seen contains index
        then (count, seen)
        else (count + 1, seen ++ Set(index).converge(set => set ++ set.flatMap(adj)))
      }.head
