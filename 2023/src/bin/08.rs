use std::collections::HashMap;

use itertools::Itertools;
use lazy_static::lazy_static;
use regex::Regex;

advent_of_code::solution!(8);

lazy_static! {
    static ref JUNCTION_RX: Regex = Regex::new(r"(\w{3}) = \((\w{3}), (\w{3})\)").unwrap();
}

type Junction = (String, String);

struct Map {
    dirs: Vec<char>,
    graph: HashMap<String, Junction>,
}

impl Map {
    fn parse_graph(input: &str) -> (String, Junction) {
        let parts = JUNCTION_RX
            .captures(input)
            .unwrap();

        (parts[1].to_string(), (parts[2].to_string(), parts[3].to_string()))
    }
    
    fn parse(input: &str) -> Map {
        let [fst, snd] = input.split("\n\n").collect_vec()[0..2] else { panic!() };
        let dirs = fst.chars().collect_vec();
        let graph: HashMap<String, Junction> = snd
            .lines()
            .map(Self::parse_graph)
            .collect();

        Map { dirs, graph }
    }

    fn traverse<F>(&self, start: &String, stop: F) -> Option<usize> where F: Fn(&str) -> bool {
        let mut i: usize = 0;
        let mut place = start;
        for dir in self.dirs.iter().cycle() {
            if stop(place.as_str()) {
                break;
            }
            place = match dir {
                'L' => &self.graph[place].0,
                'R' => &self.graph[place].1,
                _ => return None
            };
            i += 1;
        }
        Some(i)
    }

    fn traverse_many(&self) -> Option<usize> {
        self.graph
            .keys()
            .filter(|start| start.ends_with('A'))
            .map(|from| self.traverse(from, |pos| pos.ends_with('Z')))
            .fold_options(1, num::integer::lcm)
    }
}

pub fn part_one(input: &str) -> Option<usize> {
    Map::parse(input).traverse(&"AAA".to_string(), |pos| pos == "ZZZ")
}

pub fn part_two(input: &str) -> Option<usize> {
    Map::parse(input).traverse_many()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(6));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(6));
    }
}
