use itertools::Itertools;

advent_of_code::solution!(1);

struct DoubleList {
    left: Vec<u32>,
    right: Vec<u32>,
}

impl DoubleList {
    fn add(&mut self, a: u32, b: u32) {
        self.left.push(a);
        self.right.push(b);
    }

    fn sort(&mut self) {
        self.left.sort();
        self.right.sort();
    }

    fn from(input: &str) -> Self {
        let mut dl = Self {
            left: Vec::new(),
            right: Vec::new(),
        };

        for line in input.lines() {
            let elems = line.split("   ").collect_vec();
            dl.add(elems[0].parse().unwrap(), elems[1].parse().unwrap());
        }

        dl
    }
}

impl Iterator for DoubleList {
    type Item = (u32, u32);

    fn next(&mut self) -> Option<Self::Item> {
        if self.left.is_empty() {
            None
        } else {
            Some((self.left.pop().unwrap(), self.right.pop().unwrap()))
        }
    }
}

pub fn part_one(input: &str) -> Option<u32> {
    let mut dl = DoubleList::from(input);
    dl.sort();
    let res = dl.map(|(a, b)| a.abs_diff(b)).sum();

    Some(res)
}

pub fn part_two(input: &str) -> Option<u32> {
    let dl = DoubleList::from(input);
    let counts = dl.right.into_iter().counts();
    let res: u32 = dl
        .left
        .into_iter()
        .flat_map(|n| counts.get(&n).map(|c| (*c as u32) * n))
        .sum();

    Some(res)
}

#[cfg(test)]
mod tests {
    use super::*;
    use advent_of_code::template::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&read_file("examples", DAY));
        assert_eq!(result, Some(11));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&read_file("examples", DAY));
        assert_eq!(result, Some(31));
    }
}
