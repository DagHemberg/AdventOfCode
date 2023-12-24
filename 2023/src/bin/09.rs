use itertools::Itertools;

advent_of_code::solution!(9);

trait Data {
    fn calculate_diffs(&self) -> Self;
    fn make_prediction(&self, backwards: bool) -> i64;
}

impl Data for Vec<i64> {
    fn calculate_diffs(&self) -> Vec<i64> {
        self.iter().tuple_windows().map(|(a, b)| b - a).collect()
    }

    fn make_prediction(&self, backwards: bool) -> i64 {
        if let Ok(&0) = self.iter().all_equal_value() {
            0
        } else {
            let prediction = self.calculate_diffs().make_prediction(backwards);
            if backwards {
                self.first().unwrap_or(&0) - prediction
            } else {
                self.last().unwrap_or(&0) + prediction
            }
        }
    }
}

struct Sensor {
    data: Vec<i64>,
}

impl Sensor {
    fn parse(input: &str) -> Sensor {
        let data = input
            .split_whitespace()
            .flat_map(|s| s.parse::<i64>())
            .collect::<Vec<_>>();

        Sensor { data }
    }

    fn predict(&self) -> Option<i64> {
        Some(self.data.make_prediction(false))
    }

    fn extrapolate(&self) -> Option<i64> {
        Some(self.data.make_prediction(true))
    }
}

pub fn part_one(input: &str) -> Option<i64> {
    input
        .lines()
        .map(Sensor::parse)
        .map(|sensor| sensor.predict())
        .sum()
}

pub fn part_two(input: &str) -> Option<i64> {
    input
        .lines()
        .map(Sensor::parse)
        .map(|sensor| sensor.extrapolate())
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
