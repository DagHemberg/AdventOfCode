package aoc.y2017
import aoc.utils.*

package object day03:
  def spiralPos(num: Int) = 
    val layer = (math.sqrt(num - 1).toInt + 1) / 2
    val offset = (num - math.pow(2 * layer - 1, 2).toInt)
    val quadrant = (1 to 4).maxBy(n => offset - 1 < n * 2 * layer)
    val quadrantOffset = offset - (quadrant - 1) * 2 * layer
    
    quadrant match
      case 1 => (layer - quadrantOffset, layer)
      case 2 => (-layer, layer - quadrantOffset)
      case 3 => (quadrantOffset - layer , -layer)
      case 4 => (layer, quadrantOffset - layer)

