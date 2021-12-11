package aoc.day10

object Common:
  val parens = Map("(" -> ")", "[" -> "]", "{" -> "}", "<" -> ">")
  val corruptValue = Map(")" -> 3, "]" -> 57, "}" -> 1197, ">" -> 25137)
  val incompleteValue = Map(")" -> 1, "]" -> 2, "}" -> 3, ">" -> 4)

  extension (str: String)
    def replaced = str
      .replace("()", "")
      .replace("[]", "")
      .replace("{}", "")
      .replace("<>", "")

    def filtered: String =
      if str.replaced == str then str
      else str.replaced.filtered

    def isIncomplete = str.filtered.forall(c => parens.keySet(c.toString))
    def isCorrupt = !str.isIncomplete