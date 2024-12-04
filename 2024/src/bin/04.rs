use grid::Grid;
use itertools::Itertools;
use regex::Regex;

advent_of_code::solution!(4);

pub fn part_one(input: &str) -> Option<usize> {
    let grid: Grid<char> = input
        .lines()
        .map(|line| line.chars().collect())
        .collect_vec()
        .into();

    let xmas = Regex::new(r"XMAS").ok()?;
    let samx = Regex::new(r"SAMX").ok()?;

    let mut count = 0;

    let mut find = |str: &str| {
        let fwds = xmas.find_iter(str).count();
        let bwds = samx.find_iter(str).count();
        count += fwds + bwds;
    };

    for row in input.lines() {
        find(row);
    }

    for row in grid.iter_cols() {
        let str = &row.collect::<String>();
        find(str);
    }

    for r in 0..grid.rows() - 3 {
        for c in 0..grid.cols() - 3 {
            let dia1: String = (0..4).map(|i| grid[(r + i, c + i)]).collect();
            let dia2: String = (0..4).map(|i| grid[(r + i, c + 3 - i)]).collect();
            find(&dia1);
            find(&dia2);
        }
    }

    Some(count)
}

pub fn part_two(input: &str) -> Option<usize> {
    struct Square<'a> {
        grid: &'a Grid<char>,
        r: usize,
        c: usize,
    }

    impl Square<'_> {
        fn search(&self, regex: &Regex) -> bool {
            let (r, c) = (self.r, self.c);
            let str: String = (0..3)
                .flat_map(|i| (0..3).map(move |j| self.grid[(r + i, c + j)]))
                .collect();

            regex.is_match(&str)
        }
    }

    let grid: Grid<char> = input
        .lines()
        .map(|line| line.chars().collect())
        .collect_vec()
        .into();

    let regex = Regex::new(r"(M.S.A.M.S|M.M.A.S.S|S.M.A.S.M|S.S.A.M.M)").ok()?;
    let mut count = 0;

    for r in 0..grid.rows() - 2 {
        for c in 0..grid.cols() - 2 {
            let square = Square { grid: &grid, r, c };
            if square.search(&regex) {
                count += 1;
            }
        }
    }

    Some(count)
}

#[cfg(test)]
mod tests {
    use super::*;
    use advent_of_code::template::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&read_file("examples", DAY));
        assert_eq!(result, Some(18));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&read_file("examples", DAY));
        assert_eq!(result, Some(9));
    }
}
