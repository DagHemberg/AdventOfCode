object Raw:
  val raw = scala.io.Source.fromFile("data.txt").getLines.toVector
  val horizontals = raw filter (_.startsWith("forward"))
  val verticals = raw diff horizontals


object One:
  import Raw.*

  val horizontalPos = horizontals
    .map(x => x.split(" ")(1).toInt)
    .sum

  val depth = verticals
    .map(x => x.split(" "))
    .map { case Array(a, b) => if a == "up" then -(b.toInt) else b.toInt }
    .sum

  val out = horizontalPos * depth

  // @main 
  def run =
    println(out)

object Two:
  import Raw.*

  var aim   = 0
  var hPos  = 0
  var depth = 0

  def updatePos(command: String, value: Int) = command match {
    case "down" => aim += value
    case "up"   => aim -= value
    case "forward" => 
      hPos += value
      depth += aim * value 
  }

  raw.foreach(x => 
    val command = x.split(" ")(0)
    val value = x.split(" ")(1).toInt
    updatePos(command, value)
  )

  val out = hPos * depth

  @main def run =
    println(out)