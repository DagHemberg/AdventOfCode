package aoc.day18
import aoc.utils.*

extension (branch: Branch) 
  def reduce: Branch = 
    import Operation.*
    branch.determineAction match
      case Neither   => branch
      case Splitting => branch.firstSpl match
        case Some(leaf) => branch
          .updated(leaf.index, leaf.split)
          .refresh
          .reduce
        case None => branch
      case Exploding => branch.firstExp match
        case Some(brunch) => 
          val l: Leaf = brunch.left.asInstanceOf[Leaf]
          val r: Leaf = brunch.right.asInstanceOf[Leaf]
          val bef = branch.leafAt(l.index - 1).getOrElse(l)
          val aft = branch.leafAt(r.index + 1).getOrElse(r)
          branch
            .updated(l.index - 1, Leaf(bef.value + l.value))
            .updated(r.index + 1, Leaf(aft.value + r.value))
            .removeExplodedNode(l.index, r.index)
            .refresh
            .reduce
        case None => branch

extension (str: String)
  def addIndex = 
    val str2 = raw"\d+".r.replaceAllIn(str, x => s"($x,A)")
    str2.zipWithIndex.map((c, i) => if c == 'A' then str2.take(i).count(_ == c) else c).mkString
  
  def parse(depth: Int = 1): Branch = 
    def getPairs(start: Int, str: String): Int = 
      if str(1) == '(' then str.indexOf(')') + 1
      else
        var i = start
        var x = 0
        var found = false
        while !found && i < str.length do
          if str(i) == '[' then x += 1
          else if str(i) == ']' then x -= 1
          if x == 0 then found = true
          i += 1
        i

    val firstStop = getPairs(1, str)
    val Vector(first, second) = Vector(
      str.slice(1, firstStop), 
      str.slice(firstStop + 1, str.size - 1)
    ).map(x => x match
      case s"($value,$index)" => Leaf(value.toInt, depth, index.toInt)
      case _ => x.parse(depth + 1)
    )
    
    Branch(first, second, depth)

trait Tree:
  def magnitude: Long
  def refresh = toString.addIndex.parse()

case class Branch(left: Tree, right: Tree, depth: Int = 1) extends Tree:
  override def toString = s"[$left,$right]"
  def magnitude = 3 * left.magnitude + 2 * right.magnitude

  infix def +(other: Branch): Branch = 
    Branch(
      Branch(left, right), 
      Branch(other.left, other.right)
    ).refresh.reduce

case class Leaf(value: Int, depth: Int = 0, index: Int = 0) extends Tree:
  override def toString = value.toString
  def magnitude = value
  def split = if value >= 10 then 
    Branch(
      Leaf((value / 2.0).floor.toInt), 
      Leaf((value / 2.0).ceil.toInt),
    )
  else Leaf(value)

enum Operation:
  case Exploding, Splitting, Neither

extension (tree: Tree)
  def updated(index: Int, newTree: Tree): Tree = 
    tree.update(index, newTree).refresh
  
  def removeExplodedNode(index1: Int, index2: Int): Tree = tree match
    case Branch(Leaf(_, _, i1), Leaf(_, _, i2), _) if i1 == index1 && i2 == index2 => Leaf(0)
    case Branch(Leaf(_, _, i1), Leaf(_, _, i2), _) => tree
    case Branch(l, r, d) => Branch(l.removeExplodedNode(index1, index2), r.removeExplodedNode(index1, index2), d)
    case _ => tree

  def leafAt(index: Int): Option[Leaf] = tree match
    case Leaf(value, depth, i) if i == index => Some(Leaf(value, depth, i))
    case Branch(left, right, depth) => left.leafAt(index) orElse right.leafAt(index)
    case _ => None

  def update(index: Int, newTree: Tree): Tree = tree match
    case Leaf(v, d, i) if i == index => newTree
    case Leaf(v, d, i) => Leaf(v, d, i)
    case Branch(l, r, d) => Branch(l.update(index, newTree), r.update(index, newTree), d)
    
  def firstExp: Option[Branch] = tree match
    case Branch(left, right, depth) if depth == 5 => Some(Branch(left, right, depth))
    case Branch(left, right, depth) => left.firstExp orElse right.firstExp
    case _ => None

  def firstSpl: Option[Leaf] = tree match
    case Leaf(value, depth, index) if value >= 10 => Some(Leaf(value, depth, index))
    case Branch(left, right, depth) => left.firstSpl orElse right.firstSpl
    case _ => None

  def determineAction: Operation = 
    import Operation.*
    (tree.firstExp, tree.firstSpl) match
      case (Some(branch), Some(leaf)) => Exploding
      case (Some(branch), None) => Exploding
      case (None, Some(leaf))   => Splitting
      case neither              => Neither
