use regex::*;

advent_of_code::solution!(3);

pub fn part_one(input: &str) -> Option<u32> {
    let number_finder = Regex::new(r"\d+").unwrap();
    let neighbours: Vec<(i32, i32)> = vec![
        (-1, -1),
        (-1, 0),
        (-1, 1),
        (0, -1),
        /*(0, 0),*/
        (0, 1),
        (1, -1),
        (1, 0),
        (1, 1),
    ];
    let grid: Vec<Vec<char>> = input
        .lines()
        .map(|row| row.chars().collect::<Vec<char>>())
        .collect();

    let width = grid[0].len() as i32;
    let height = grid.len() as i32;

    let x = input
        .lines()
        .map(|row| {
            number_finder
                .captures_iter(row)
                .map(|cap| cap.get(0).unwrap())
                .collect::<Vec<Match>>()
        })
        .enumerate()
        .map(|(a, b)| (a as i32, b))
        .collect::<Vec<(i32, Vec<Match>)>>();

    // dbg!(&x);
    let y: u32 = x
        .iter()
        .flat_map(|(row, ms)| {
            ms.iter().filter(|m| {
                for col in m.range() {
                    let found = !neighbours
                        .iter()
                        .flat_map(|(r, c)| {
                            let col = col as i32;
                            if *row + r >= 0
                                && *row + r < height
                                && col + c >= 0
                                && col + c < width
                            {
                                Some(grid[(*row + r) as usize][(col + c) as usize])
                            } else {
                                None
                            }
                        })
                        .all(|c| c == '.' || c.is_ascii_digit());
                    if found {
                        return true;
                    }
                }
                false
            })
        })
        .map(|m| m.as_str().parse::<u32>().unwrap())
        .sum();
    Some(y)
}

struct Grid {
    grid: Vec<Vec<char>>,
    width: i32,
    height: i32,
}

impl Grid {
    // const UP: Vec<(i32, i32)> = vec![
    //     (-1, -1),
    //     (-1, 0),
    //     (-1, 1),
    // ];
    // const DOWN: Vec<(i32, i32)> = vec![
    //     (1, -1),
    //     (1, 0),
    //     (1, 1),
    // ];
    // const LEFT: Vec<(i32, i32)> = vec![
    //     (0, -1),
    // ];
    // const RIGHT: Vec<(i32, i32)> = vec![
    //     (0, 1),
    // ];

    // const NEIGHBOURS: Vec<(i32, i32)> = vec![
    //     Self::UP,
    //     Self::DOWN,
    //     Self::LEFT,
    //     Self::RIGHT,
    // ].concat();
    
    fn new(input: &str) -> Grid {
        let grid: Vec<Vec<char>> = input.lines().map(|row| row.chars().collect()).collect();
        let width = grid[0].len() as i32;
        let height = grid.len() as i32;

        Grid {
            grid,
            width,
            height,
        }
    }

    fn get(&self, row: i32, col: i32) -> Option<char> {
        if row >= 0 && row < self.height && col >= 0 && col < self.width {
            Some(self.grid[row as usize][col as usize])
        } else {
            None
        }
    }
}

pub fn part_two(input: &str) -> Option<u32> {
    // let number_finder = Regex::new(r"\d+").unwrap();
    // let x = input
    //     .lines()
    //     .flat_map(|row| number_finder
    //         .captures_iter(&row)
    //         .map(|cap| cap.get(0).unwrap())
    //         .collect::<Vec<Match>>()
    //     )
    //     .enumerate()
    //     .map(|(a, b)| (a as i32, b))
    //     .collect::<Vec<(i32, Vec<Match>)>>();
    
    // let gear_regex = Regex::new(r"\*").unwrap();
    // let gear_positions: Vec<(usize, usize)> = input
    //     .lines()
    //     .enumerate()
    //     .flat_map(|(row, line)| gear_regex.find_iter(line).map(|m| (row, m.start())))
    //     .collect();

    // let grid = Grid::new(input);

    None
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(4361));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, None);
    }
}
