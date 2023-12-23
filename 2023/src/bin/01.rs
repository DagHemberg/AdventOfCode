use std::collections::HashMap;

advent_of_code::solution!(1);

pub fn part_one(input: &str) -> Option<u32> {
    let data = input.lines();
    let mut sum = 0;
    for line in data {
        let digit: String = line.chars().filter(|c| c.is_ascii_digit()).collect();
        let first = digit.chars().next().unwrap_or('0');
        let last = digit.chars().last().unwrap_or('0');
        sum += format!("{}{}", first, last)
            .parse::<u32>()
            .ok()?
    }
    Some(sum)
}

#[allow(unused)]
pub fn part_two(input: &str) -> Option<u32> {
    use regex::Regex;
    let mut sum = 0;
    let regex_base = "(one|two|three|four|five|six|seven|eight|nine)".to_string();
    let first_regex = Regex::new(&regex_base).unwrap();
    let last_regex = Regex::new(format!(r"(?m)(^.*){regex_base}").as_str()).unwrap();
    let wordy_representation: HashMap<&str, &str> = HashMap::from([
        ("one", "one1one"),
        ("two", "two2two"),
        ("three", "three3three"),
        ("four", "four4four"),
        ("five", "five5five"),
        ("six", "six6six"),
        ("seven", "seven7seven"),
        ("eight", "eight8eight"),
        ("nine", "nine9nine"),
    ]);
    for (pos, line) in input.lines().enumerate() {
        let mut line = line.to_string();
        
        if let Some(num) = first_regex.find(&line) {
            line.replace_range(num.range(), wordy_representation[num.as_str()]);
        }

        if let Some(caps) = last_regex.captures_iter(&line).last() {
            if let Some(num) = caps.get(2) {
                line.replace_range(num.range(), wordy_representation[num.as_str()]);
            }
        }

        let digit: String = line.chars().filter(|c| c.is_ascii_digit()).collect();
        let first = digit.chars().next()?;
        let last = digit.chars().last()?;
        let res = format!("{}{}", first, last)
            .parse::<u32>()
            .ok()?;
        sum += res;
    }

    Some(sum)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(142));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(281));
    }
}
