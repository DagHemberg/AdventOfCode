use regex::Regex;

advent_of_code::solution!(2);

struct ColorCounter {
    regex: Regex,
}

impl ColorCounter {
    fn new(color: &str) -> ColorCounter {
        let regex = Regex::new(color).unwrap();
        ColorCounter { regex }
    }

    fn minimum_required(&self, line: &str) -> u32 {
        self.regex
            .captures_iter(line)
            .flat_map(|c| c[1].parse::<u32>())
            .max()
            .unwrap_or(0)
    }

    fn count(&self, line: &str) -> u8 {
        self.regex
            .captures_iter(line)
            .last()
            .map(|c| c[1].to_string())
            .unwrap_or("0".to_string())
            .parse::<u8>()
            .unwrap()
    } 
}

struct Round {
    red: u8,
    green: u8,
    blue: u8,
}

impl Round {
    fn new(red: u8, green: u8, blue: u8) -> Round {
        Round { red, green, blue }
    }

    fn valid(&self) -> bool {
        let (max_red, max_green, max_blue) = (12, 13, 14);
        self.red <= max_red && self.green <= max_green && self.blue <= max_blue
    }
}

fn validate(id: u32, rounds: Vec<Round>) -> Option<u32> {
    if rounds.iter().all(|r| r.valid()) {
        Some(id)
    } else {
        None
    }
}

pub fn part_one(input: &str) -> Option<u32> {
    let data = input.lines();
    let game = Regex::new(r"Game (\d+)").unwrap();
    let r = ColorCounter::new(r"(\d+) red");
    let g = ColorCounter::new(r"(\d+) green");
    let b = ColorCounter::new(r"(\d+) blue");

    let sum = data
        .flat_map(|line| {
            let id = game.captures_iter(line).last().unwrap()[1]
                .parse::<u32>()
                .unwrap();

            let after = line.to_string().chars().skip(8).collect::<String>();

            let tail = after.split(';').map(|s| s.trim()).collect::<Vec<&str>>();

            let mut rounds = Vec::new();
            tail.iter().for_each(|line| {
                rounds.push(Round::new(r.count(line), g.count(line), b.count(line)));
            });

            validate(id, rounds)
        })
        .sum::<u32>();

    Some(sum)
}

pub fn part_two(input: &str) -> Option<u32> {
    let data = input.lines();
    let r = ColorCounter::new(r"(\d+) red");
    let g = ColorCounter::new(r"(\d+) green");
    let b = ColorCounter::new(r"(\d+) blue");

    let sum = data.map(|line| {
        let red = r.minimum_required(line);
        let blue = b.minimum_required(line);
        let green = g.minimum_required(line);
        red * blue * green
    }).sum::<u32>();

    Some(sum)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(8));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(2286));
    }
}
