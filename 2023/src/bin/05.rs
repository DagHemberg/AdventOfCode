#[macro_use]
extern crate lazy_static;
use regex::Regex;

advent_of_code::solution!(5);

lazy_static! {
    static ref SEED_REGEX: Regex = Regex::new(r"seeds: (.*)").unwrap();
    static ref BIND_REGEX: Regex = Regex::new(r"(\d+) (\d+) (\d+)").unwrap();
}

struct Seeds {
    all: Vec<(usize, usize)>,
}

impl Seeds {
    fn parse_start(input: &str) -> impl Iterator<Item = usize> + '_ {
        SEED_REGEX.captures_iter(input).flat_map(|cs| {
            cs[1]
                .split_whitespace()
                .flat_map(|d| d.parse::<usize>())
                .collect::<Vec<_>>()
        })
    }

    fn parse_pairs(input: &str) -> Self {
        Seeds {
            all: Self::parse_start(input)
                .collect::<Vec<_>>()
                .chunks(2)
                .map(|pair| (pair[0], pair[1]))
                .collect::<Vec<_>>(),
        }
    }

    fn parse_individual(input: &str) -> Seeds {
        Seeds {
            all: Self::parse_start(input).map(|d| (d, 1)).collect::<Vec<_>>(),
        }
    }

    fn in_range(&self, index: usize) -> bool {
        self.all
            .iter()
            .any(|(start, range)| index >= *start && index < start + range)
    }
}

struct Binding {
    dst: usize,
    src: usize,
    range: usize,
}

impl Binding {
    fn parse(input: &str) -> Self {
        let captures = BIND_REGEX.captures(input).unwrap();
        Binding {
            dst: captures[1].parse::<usize>().unwrap(),
            src: captures[2].parse::<usize>().unwrap(),
            range: captures[3].parse::<usize>().unwrap(),
        }
    }

    fn in_range(&self, index: usize) -> bool {
        index >= self.src && index < self.src + self.range
    }

    fn apply(&self, index: usize) -> usize {
        if self.src < self.dst {
            index + (self.dst - self.src)
        } else {
            index - (self.src - self.dst)
        }
    }

    fn swap(&self) -> Self {
        Binding {
            dst: self.src,
            src: self.dst,
            range: self.range,
        }
    }
}

struct Map {
    bindings: Vec<Binding>,
}

impl Map {
    fn find_range(&self, index: usize) -> Option<&Binding> {
        self.bindings.iter().find(|b| b.in_range(index))
    }

    fn get(&self, index: usize) -> usize {
        if let Some(binding) = self.find_range(index) {
            binding.apply(index)
        } else {
            index
        }
    }
}

struct Path {
    maps: Vec<Map>,
}

impl Path {
    fn traverse(&self, seed: usize) -> usize {
        let mut index = seed;
        for map in &self.maps {
            index = map.get(index);
        }
        index
    }

    fn traverse_rev(&self, location: usize) -> usize {
        let mut index = location;
        for map in self.maps.iter().rev() {
            index = map.get(index);
        }
        index
    }
}

pub fn part_one(input: &str) -> Option<u64> {
    let mut components = input.split("\n\n");
    let seeds = Seeds::parse_individual(components.next().unwrap());

    let maps = components
        .map(|map| Map {
            bindings: map.lines().skip(1).map(Binding::parse).collect::<Vec<_>>(),
        })
        .collect::<Vec<_>>();

    let path = Path { maps };

    seeds
        .all
        .iter()
        .map(|(seed, _)| path.traverse(*seed) as u64)
        .min()
}

// todo: optimize
// something something binary search?
pub fn part_two(input: &str) -> Option<u64> {
    let mut components = input.split("\n\n");
    let seeds = Seeds::parse_pairs(components.next().unwrap());

    let maps = components
        .map(|map| Map {
            bindings: map
                .lines()
                .skip(1)
                .map(Binding::parse)
                .map(|b| b.swap())
                .collect::<Vec<_>>(),
        })
        .collect::<Vec<_>>();

    let path = Path { maps };

    (0..)
        .find(|loc| seeds.in_range(path.traverse_rev(*loc)))
        .map(|i| i as u64)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(35));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(46));
    }
}
