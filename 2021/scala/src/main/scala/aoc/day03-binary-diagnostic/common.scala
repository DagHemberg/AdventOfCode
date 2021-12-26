package aoc.day03

def opposite(c: Char) = if c == '1' then '0' else '1'
def mostCommonAt(i: Int)(using data: Vector[String]) = data.map(_(i)).groupBy(identity).maxBy(_._2.size)._1
def leastCommonAt(i: Int)(using data: Vector[String]) = opposite(mostCommonAt(i))
extension (s: String) def toDecimal = BigInt(s, 2).toString(10).toLong
