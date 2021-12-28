package aoc.day10

object Common:
  val parens = Map('(' -> ')', '[' -> ']', '{' -> '}', '<' -> '>')
  val corruptValue = Map(')' -> 3, ']' -> 57, '}' -> 1197, '>' -> 25137)
  val incompleteValue = Map(')' -> 1, ']' -> 2, '}' -> 3, '>' -> 4)

  extension (str: String)
    def parensRemoved = str
      .replace("()", "")
      .replace("[]", "")
      .replace("{}", "")
      .replace("<>", "")

    def filtered: String =
      if str.parensRemoved == str then str
      else str.parensRemoved.filtered

    def isIncomplete = str.filtered.forall(parens.keySet)
    def isCorrupt = !str.isIncomplete