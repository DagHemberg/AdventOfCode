package aoc.y2022.day07
import problemutils.*, extensions.*

case class Shell(pwd: List[String], folders: Map[List[String], Long])
val root = Shell(List("/"), Map(List("/") -> 0))

extension [A] (ls: List[A]) def parentPaths = ls.inits.toList.init

def parse(data: List[String]) = data
  .foldLeft(root):
    (shell, line) => line match
      case "$ ls"         => shell
      case "$ cd .."      => shell.copy(pwd = shell.pwd.init)
      case "$ cd /"       => shell.copy(pwd = List("/"))
      case s"$$ cd $name" => shell.copy(pwd = shell.pwd :+ name)
      case s"dir $name"   => shell
      case s"$size $name" => 
        val folders = shell.folders
        val newFolders = shell.pwd
          .parentPaths
          .map(path => path -> (folders.getOrElse(path, 0L) + size.toLong))
        shell.copy(folders = folders ++ newFolders)
  .folders
