use itertools::Itertools;
use std::{
    cmp::Ordering::{self, Greater, Less},
    collections::{HashMap, HashSet},
};

advent_of_code::solution!(5);

struct Rules {
    following: HashMap<usize, HashSet<usize>>,
}

impl Rules {
    fn validate(&self, list: &[usize]) -> bool {
        (0..list.len() - 1).all(|i| {
            list[i + 1..].iter().all(|n| {
                self.following
                    .get(&list[i])
                    .map_or(false, |set| set.contains(n))
            })
        })
    }

    fn order(&self, a: &usize, b: &usize) -> Ordering {
        if !self.following.contains_key(a) {
            // self not first in any rule
            // -> no number is greater than self
            Greater
        } else {
            match self.following[a].get(b) {
                // other is in list of numbers after self
                // -> self is before other
                Some(_) => Less,

                // other is not in list of numbers after self
                // -> self is after other
                None => Greater,
            }
        }
    }
}

impl From<Vec<(usize, usize)>> for Rules {
    fn from(value: Vec<(usize, usize)>) -> Self {
        let following = value
            .into_iter()
            .sorted_by_key(|&(a, _)| a)
            .chunk_by(|&(a, _)| a)
            .into_iter()
            .map(|(key, rules)| (key, rules.map(|r| r.1).collect()))
            .collect();

        Rules { following }
    }
}

peg::parser! {
    grammar parser() for str {
        rule n() -> usize = 
            n:$(['0'..='9']+) { n.parse().unwrap() }

        rule ord() -> (usize, usize) = 
            b:n() "|" a:n() { (b, a) }

        rule list() -> Vec<usize> = 
            n() ** ","

        pub rule split() -> (Rules, Vec<Vec<usize>>) = 
            ords:(ord() ** "\n") "\n\n" ls:(list() ** "\n") { (ords.into(), ls) }
    }
}

pub fn part_one(input: &str) -> Option<usize> {
    let (rules, lists) = parser::split(input.trim()).unwrap();

    lists
        .iter()
        .filter(|list| rules.validate(list))
        .map(|list| list[list.len() / 2])
        .sum::<usize>()
        .into()
}

pub fn part_two(input: &str) -> Option<usize> {
    let (rules, lists) = parser::split(input.trim()).unwrap();

    lists
        .iter()
        .filter(|list| !rules.validate(list))
        .map(|list| {
            list.iter()
                .sorted_by(|a, b| rules.order(a, b))
                .collect_vec()
        })
        .map(|list| list[list.len() / 2])
        .sum::<usize>()
        .into()
}

#[cfg(test)]
mod tests {
    use super::*;
    use advent_of_code::template::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&read_file("examples", DAY));
        assert_eq!(result, Some(143));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&read_file("examples", DAY));
        assert_eq!(result, Some(123));
    }
}
