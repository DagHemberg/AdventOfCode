use std::collections::HashSet;

use itertools::Itertools;
use pathfinding::prelude::Matrix;

advent_of_code::solution!(10);

#[derive(Clone, Copy, PartialEq, Eq)]
enum Direction {
    Up,
    Down,
    Left,
    Right,
}

impl Direction {
    fn to_tuple(self) -> (i32, i32) {
        use Direction::*;
        match self {
            Up => (-1, 0),
            Down => (1, 0),
            Left => (0, -1),
            Right => (0, 1),
        }
    }

    fn step_from(self, (x, y): (usize, usize)) -> (usize, usize) {
        let (dx, dy) = self.to_tuple();
        ((x as i32 + dx) as usize, (y as i32 + dy) as usize)
    }

    fn calculate_next_from(self, pipe: char) -> Option<Direction> {
        use Direction::*;
        match pipe {
            '-' => match self {
                Left | Right => Some(self),
                _ => None,
            },
            '|' => match self {
                Up | Down => Some(self),
                _ => None,
            },
            'F' => match self {
                Up => Some(Right),
                Left => Some(Down),
                _ => None,
            },
            '7' => match self {
                Up => Some(Left),
                Right => Some(Down),
                _ => None,
            },
            'L' => match self {
                Down => Some(Right),
                Left => Some(Up),
                _ => None,
            },
            'J' => match self {
                Down => Some(Left),
                Right => Some(Up),
                _ => None,
            },
            _ => None,
        }
    }
}

struct PipeTraverser<'a> {
    pipes: &'a Matrix<char>,
    position: (usize, usize),
    direction: Direction,
}

impl PipeTraverser<'_> {
    fn build(pipes: &Matrix<char>) -> Option<PipeTraverser> {
        use Direction::*;
        let start = pipes.keys().find(|&ix| pipes[ix] == 'S')?;
        let direction = pipes
            .neighbours(start, false)
            .find_map(|ix| match (ix, pipes[ix]) {
                ((r, _), '|' | 'F' | '7') if r < start.0 => Some(Up),
                ((r, _), '|' | 'J' | 'L') if r > start.0 => Some(Down),
                ((_, c), '-' | 'F' | 'L') if c < start.1 => Some(Left),
                ((_, c), '-' | 'J' | '7') if c > start.1 => Some(Right),
                _ => None,
            })?;

        Some(PipeTraverser {
            pipes,
            position: start,
            direction,
        })
    }
}

impl Iterator for PipeTraverser<'_> {
    type Item = (usize, usize);

    fn next(&mut self) -> Option<Self::Item> {
        self.position = self.direction.step_from(self.position);
        self.direction = self
            .direction
            .calculate_next_from(self.pipes[self.position])?;
        Some(self.position)
    }
}

pub fn part_one(input: &str) -> Option<usize> {
    let pipes: Matrix<char> = input
        .lines()
        .map(|line| line.chars().collect_vec())
        .collect();

    let traverser = PipeTraverser::build(&pipes)?;

    Some((traverser.into_iter().count() + 1) / 2)
}

pub fn part_two(input: &str) -> Option<u32> {
    let pipes: Matrix<char> = input
        .lines()
        .map(|line| line.chars().collect_vec())
        .collect();

    let traverser = PipeTraverser::build(&pipes)?;
    let path = traverser.into_iter().collect::<HashSet<_>>();

    let mut counter = 0;
    let mut currently_inside = false;
    for pos in pipes.keys() {
        if path.contains(&pos) {
            if let Some('|' | 'L' | 'J') = pipes.get(pos) {
                currently_inside = !currently_inside;
            }
        } else if currently_inside {
            counter += 1;
        }
    }

    Some(counter)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(8));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(1));
    }
}
