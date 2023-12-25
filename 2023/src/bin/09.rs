use itertools::Itertools;

advent_of_code::solution!(9);

trait Processing {
    fn calculate_diffs(&self) -> Self;
    fn make_prediction(&self) -> i64;
}

impl Processing for Vec<i64> {
    fn calculate_diffs(&self) -> Vec<i64> {
        self.iter().tuple_windows().map(|(a, b)| b - a).collect()
    }

    fn make_prediction(&self) -> i64 {
        if let Ok(&0) = self.iter().all_equal_value() {
            0
        } else {
            self.last().unwrap_or(&0) + self.calculate_diffs().make_prediction()
        }
    }
}

struct Reading(Vec<i64>);
impl Reading {
    fn parse(input: &str) -> Reading {
        let data = input
            .split_whitespace()
            .flat_map(|s| s.parse::<i64>())
            .collect_vec();

        Reading(data)
    }

    fn rev(mut self) -> Reading {
        self.0.reverse();
        self
    }

    fn predict(&self) -> Option<i64> {
        Some(self.0.make_prediction())
    }
}

pub fn part_one(input: &str) -> Option<i64> {
    input
        .lines()
        .map(Reading::parse)
        .map(|data| data.predict())
        .sum()
}

pub fn part_two(input: &str) -> Option<i64> {
    input
        .lines()
        .map(Reading::parse)
        .map(|data| data.rev().predict())
        .sum()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(114));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(2));
    }
}
