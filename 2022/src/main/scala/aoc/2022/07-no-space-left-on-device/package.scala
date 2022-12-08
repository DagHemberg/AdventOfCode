package aoc.y2022.day07
import problemutils.*, extensions.*

case class Shell(pwd: List[String], data: Data, traversedPaths: Set[List[String]])
case class Data(size: Long, files: Map[String, Data]):
  def apply(path: String*) = path.foldLeft(this) { (data, name) => data.files(name) }
  def isFolder = files.nonEmpty  

  def put(path: List[String])(data: Data): Data = path match
    case Nil => Data(size + data.size, files ++ data.files)
    case head :: tail =>
      val newFiles = files + (head -> files(head).put(tail)(data))
      Data(size + data.size, newFiles)

  def refresh: Data = 
    if isFolder then this
    else Data(files.sumBy(_._2.size), files)

def parse(data: List[String]) =
  val root = Shell(Nil, Data(0, Map("/" -> Data(0, Map.empty))), Set.empty)  
  data.foldLeft(root) { (shell, line) => 
    line match
      case "$ ls" => shell
      
      case "$ cd .." => 
        shell.copy(
          pwd = shell.pwd.init, 
          traversedPaths = shell.traversedPaths + shell.pwd
        )

      case "$ cd /" => 
        shell.copy(
          pwd = List("/"), 
          traversedPaths = shell.traversedPaths + shell.pwd
        )

      case s"$$ cd $dir" => 
        shell.copy(
          pwd = shell.pwd :+ dir
        )
      
      case s"dir $name" => 
        val dir = Data(0, Map(name -> Data(0, Map.empty)))
        val newPaths = shell.traversedPaths + (shell.pwd :+ name)
        
        shell.copy(
          data = shell.data.put(shell.pwd)(dir), 
          traversedPaths = newPaths
        )
      
      case s"$size $name" => 
        val file = Data(size.toLong, Map(name -> Data(size.toLong, Map.empty)))
        val newPaths = shell.traversedPaths + (shell.pwd :+ name)
        
        shell.copy(
          data = shell.data.put(shell.pwd)(file), 
          traversedPaths = newPaths
        )
  }

def allFolders(shell: Shell, root: Data) = 
  shell
    .traversedPaths
    .filter(_.nonEmpty)
    .map(path => root(path*))
    .filter(_.isFolder)
