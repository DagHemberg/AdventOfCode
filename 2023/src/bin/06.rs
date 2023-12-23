#![allow(unused)]

#[macro_use]
extern crate lazy_static;
use advent_of_code::RegexUtils;
use itertools::Itertools;
use regex::Regex;

advent_of_code::solution!(6);

lazy_static! {}

#[derive(Debug)]
struct Race {
    time: usize,
    record: usize,
}

impl Race {
    fn wait(&self, elapsed: usize) -> usize {
        elapsed * (self.time - elapsed)
    }

    // could be optimized even further by doing ✨math✨ but im lazy
    // really its just the abc formula but that introduces some rounding 
    // errors and i cba to figure them out in the general case
    fn count_winnable(&self) -> usize {
        let pred = |&t: &_| self.wait(t) > self.record;
        let first = (1..=self.time).find(pred).unwrap();
        let last = (1..=self.time).rev().find(pred).unwrap();
        last - first + 1
    }
}

lazy_static! {
    static ref TIMES: Regex = Regex::new(r"Time:\s+(.*)").unwrap();
    static ref DISTS: Regex = Regex::new(r"Distance:\s+(.*)").unwrap();
}

pub fn part_one(input: &str) -> Option<usize> {
    let times = TIMES.all_numbers(input);
    let distances = DISTS.all_numbers(input);

    let res = times
        .iter()
        .zip(distances)
        .map(|(&time, record)| Race { time, record })
        .map(|race| race.count_winnable())
        .product();

    Some(res)
}

pub fn part_two(input: &str) -> Option<usize> {
    let time = TIMES
        .all_numbers(input)
        .iter()
        .join("")
        .parse::<usize>()
        .unwrap();

    let record = DISTS
        .all_numbers(input)
        .iter()
        .join("")
        .parse::<usize>()
        .unwrap();

    let race = Race { time, record };

    Some(race.count_winnable())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(288));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(71503));
    }
}
