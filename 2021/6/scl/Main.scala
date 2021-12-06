@main def run = 
    val input = scala.io.Source.fromFile("../input.txt").getLines.flatMap(_.split(",")).map(_.toInt).toVector
    val fishes = (0 to 8).map(i => input.count(_ == i)).toVector.map(_.toLong)
    val result = (0 until 256).foldLeft(fishes)((c, _) => c.tail.updated(6, c.tail(6) + c.head) :+ c.head).sum
    println(result)