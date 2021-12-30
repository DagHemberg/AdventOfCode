package aoc.day19
import aoc.utils.*

def sin(deg: Int) = math.sin(math.toRadians(deg)).toInt
def cos(deg: Int) = math.cos(math.toRadians(deg)).toInt

def rotX(a: Int) = Vector(
  Vector(1, 0,      0),
  Vector(0, cos(a), -sin(a)),
  Vector(0, sin(a), cos(a))
).toMatrix

def rotY(a: Int) = Vector(
  Vector(cos(a),  0, sin(a)),
  Vector(0,       1, 0),
  Vector(-sin(a), 0, cos(a))
).toMatrix

def rotZ(a: Int) = Vector(
  Vector(cos(a), -sin(a), 0),
  Vector(sin(a), cos(a),  0),
  Vector(0,      0,       1)
).toMatrix

def rotations(vec: Vector[Int]): Vector[Pos3D] = 
  val fullRot = Vector(0, 90, 180, 270)
  (fullRot.flatMap(x => fullRot.map(z => rotZ(z) * rotX(x) * vec)) ++ 
  fullRot.map(rotZ(_) * rotY(90) * vec) ++
  fullRot.map(rotZ(_) * rotY(270) * vec)).map(_.toPos3D)