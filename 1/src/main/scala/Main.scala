def readFile(file: String) =
  scala.io.Source.fromFile(file).getLines.toVector

val raw = readFile("data.txt")
val data = raw.map(_.toInt)
val out1 = data.sliding(2).toVector.map(x => x(0) < x(1)).count(_ == true)
val out2 = data.sliding(3).toVector.sliding(2).toVector.map(x => x(0).sum < x(1).sum).count(_ == true)

@main def run = 
  println(out1)
  println(out2)