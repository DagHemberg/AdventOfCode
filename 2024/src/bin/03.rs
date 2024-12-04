use regex::Regex;

advent_of_code::solution!(3);

peg::parser! {
    grammar calc() for str {
        rule number() -> u32 = n:$(['0'..='9']+) { n.parse().unwrap() }

        pub rule mul() -> u32 = "mul(" n:number() "," m:number() ")" { n * m }
    }
}

fn extract(regex: Regex, input: &str) -> Option<u32> {
    regex
        .find_iter(input)
        .flat_map(|m| calc::mul(m.as_str()))
        .sum::<u32>()
        .into()
}

pub fn part_one(input: &str) -> Option<u32> {
    let regex = Regex::new(r"mul\((\d+),(\d+)\)").ok()?;
    extract(regex, input)
}

pub fn part_two(input: &str) -> Option<u32> {
    let only_relevant = Regex::new(r"don't\(\)|do\(\)|mul\(\d+,\d+\)").ok()?;
    let donts = Regex::new(r"((don't\(\))(mul\(\d+,\d+\))+)").ok()?;

    let parts: String = only_relevant.find_iter(input).map(|m| m.as_str()).collect();
    let relevant = donts.replace_all(&parts, "");

    let regex = Regex::new(r"mul\((\d+),(\d+)\)").ok()?;
    extract(regex, &relevant)
}

#[cfg(test)]
mod tests {
    use super::*;
    use advent_of_code::template::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&read_file("examples", DAY));
        assert_eq!(result, Some(161));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&read_file("examples", DAY));
        assert_eq!(result, Some(48));
    }
}
