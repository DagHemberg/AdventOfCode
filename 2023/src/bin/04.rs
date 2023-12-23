use regex::Regex;

advent_of_code::solution!(4);

struct Card {
    winners: Vec<u32>,
    played: Vec<u32>,
}

impl Card {
    fn parse(line: &str, regex: &Regex) -> Card {
        fn to_vec(string: &str) -> Vec<u32> {
            string
                .split_whitespace()
                .map(|x| x.parse::<u32>().unwrap())
                .collect()
        }
        let captures = regex.captures(line).unwrap();
        let winners = to_vec(&captures[2]);
        let played = to_vec(&captures[3]);
        Card {
            winners,
            played,
        }
    }

    fn number_of_winners(&self) -> usize {
        self.played
            .iter()
            .filter(|x| self.winners.contains(x))
            .count()
    }

    fn points(&self) -> usize {
        let count = self.number_of_winners();
        if count == 0 {
            0
        } else {
            1 << (count - 1)
        }
    }
}

pub fn part_one(input: &str) -> Option<usize> {
    let regex = Regex::new(r"Card\s+(\d+):\s+(.*)\s+\|\s+(.*)").unwrap();
    let res: usize = input
        .lines()
        .map(|line| Card::parse(line, &regex).points())
        .sum();
    Some(res)
}

pub fn part_two(input: &str) -> Option<usize> {
    let regex = Regex::new(r"Card\s+(\d+):\s+(.*)\s+\|\s+(.*)").unwrap();   
    let total = input.lines().count();
    let winner_ranges = input
        .lines()
        .enumerate()
        .map(|(id, line)| {
            let start = id+1;
            let len = Card::parse(line, &regex).number_of_winners();
            (start .. (start+len).min(total)).collect::<Vec<_>>()
        })
        .collect::<Vec<_>>();

    let mut counter = vec![1; total];
    for (id, next_winners) in winner_ranges.iter().enumerate().rev() {
        for &winner in next_winners {
            counter[id] += counter[winner];
        }
    }

    Some(counter.iter().sum())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(13));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(30));
    }
}
