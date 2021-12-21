package aoc.day18
import aoc.utils.*

extension (str: String)
  def parse(depth: Int = 1): Branch = 
    // todo remove the ugly
    val lr = raw"[^\[\]]*"
    val side = raw"(\[($lr|\[($lr|\[($lr|\[($lr|\[$lr\])*\])*\])*\])*\]|\d+)"
    val rgxp = s"($side(?=,))|((?<=,)$side)".r
    
    val Vector(first, second) = rgxp.findAllIn(str).toVector.map(x =>
      if x.toIntOption.isDefined then Leaf(depth, x.toInt)
      else x.parse(depth + 1)
    )
    
    Branch(depth, first, second)

trait Tree:
  def magnitude: Long
  def incDepth: Tree

case class Branch(depth: Int, left: Tree, right: Tree) extends Tree:
  override def toString = s"[$left,$right]"
  def incDepth = Branch(depth + 1, left.incDepth, right.incDepth)
  def magnitude = 3 * left.magnitude + 2 * right.magnitude

  infix def +(other: Branch): Branch = 
    import Operation.*
    val newBranch = Branch(
      depth, 
      Branch(depth + 1, left.incDepth, right.incDepth), 
      Branch(other.depth + 1, other.left.incDepth, other.right.incDepth)
    )

    def red(br: Branch): Branch = 
      br.determineAction match
        case Neither   => br
        case Splitting => 
          val splittingNumber = br.firstSpl.get
          red(br
            .toString
            .replaceFirst(
              splittingNumber.toString, 
              splittingNumber.split.toString
            )
            .parse())
        case Exploding => 
          red(br.explode)

    red(newBranch)
        
case class Leaf(depth: Int, value: Int) extends Tree:
  override def toString = value.toString
  def incDepth = Leaf(depth + 1, value)
  def magnitude = value
  def split = if value >= 10 then Branch(
    depth, 
    Leaf(depth + 1, (value / 2.0).floor.toInt), 
    Leaf(depth + 1, (value / 2.0).ceil.toInt)
  ) else Leaf(depth, value)

enum Operation:
  case Exploding, Splitting, Neither

extension (branch: Branch)
  def explode = 
    "".debug
    val raw = branch.debug.toString
    val i = raw.indexOf(branch.firstExp.get.toString.debug).debug
    val Vector(f, l) = raw"\d+".r.findAllIn(branch.firstExp.get.toString).toVector.map(_.toInt)
    val (before, after) = raw.splitAt(i).debug
    val lastBefore = raw"\d+".r.findAllIn(before).toVector.lastOption.debug
    val firstAfter = raw"\d+".r.findAllIn(after.drop(5)).toVector.headOption.debug
    val indB = if lastBefore.isDefined then before.lastIndexOf(lastBefore.get).debug else 0
    val indA = if firstAfter.isDefined then (after.drop(5).indexOf(firstAfter.get) + 5 + before.size).debug else 0
    "".debug
    raw
      .updated(indB, if indB == 0 then raw(0).toChar else 'B').debug
      .updated(indA, if indA == 0 then raw(0).toChar else 'A').debug
      .replaceAll(
        "B", 
        if lastBefore.isDefined then (lastBefore.get.toInt + f).toString else "B"
      ).debug
      .replaceAll(
        "A", 
        if firstAfter.isDefined then (firstAfter.get.toInt + l).toString else "A"
      ).debug
      .replace(branch.firstExp.get.toString, "0").debug
      .parse()

extension (tree: Tree)
  def firstExp: Option[Tree] = tree match
    case Branch(depth, left, right) if depth == 5 => Some(tree)
    case Branch(depth, left, right) => left.firstExp orElse right.firstExp
    case _ => None

  def firstSpl: Option[Leaf] = tree match
    case Leaf(depth, value) if value >= 10 => Some(Leaf(depth, value))
    case Branch(depth, left, right) => left.firstSpl orElse right.firstSpl
    case _ => None

  def determineAction: Operation = 
    import Operation.*
    (tree.debug.firstExp, tree.firstSpl) match
      case (Some(branch), Some(leaf)) => 
        if tree.toString.indexOf(branch.toString) < tree.toString.indexOf(leaf.toString)
        then Exploding.debug
        else Splitting.debug
      case (Some(branch), None) => Exploding.debug
      case (None, Some(leaf))   => Splitting.debug
      case neither              => Neither.debug
