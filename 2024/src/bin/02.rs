use itertools::Itertools;

advent_of_code::solution!(2);

fn parse(input: &str) -> impl Iterator<Item = Vec<u32>> + '_ {
    input
        .lines()
        .map(|line| line.split(" ").map(|x| x.parse().unwrap()).collect_vec())
}

fn validate(line: &[u32]) -> bool {
    let increasing = line[0] < line[1];
    let valid = |a: u32, b: u32| -> bool {
        a.abs_diff(b) <= 3 && (increasing && a < b || !increasing && a > b)
    };

    for window in line.windows(2) {
        if !valid(window[0], window[1]) {
            return false;
        }
    }

    true
}

pub fn part_one(input: &str) -> Option<usize> {
    let res: usize = parse(input).filter(|nums| validate(nums)).count();

    Some(res)
}

pub fn part_two(input: &str) -> Option<usize> {
    fn validate_dampened(nums: &[u32]) -> bool {
        nums.iter().enumerate().any(|(i, _)| {
            let mut nums = nums.to_owned();
            nums.remove(i);
            validate(&nums)
        })
    }

    let res: usize = parse(input)
        .filter(|nums| validate(nums) || validate_dampened(nums))
        .count();

    Some(res)
}

#[cfg(test)]
mod tests {
    use super::*;
    use advent_of_code::template::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&read_file("examples", DAY));
        assert_eq!(result, Some(2));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&read_file("examples", DAY));
        assert_eq!(result, Some(4));
    }
}
