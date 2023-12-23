mod day;
pub mod template;

pub use day::*;


use itertools::Itertools;
use regex::Regex;

pub trait RegexUtils {
    fn all_numbers(&self, input: &str) -> Vec<usize>;
}

impl RegexUtils for Regex {
    fn all_numbers(&self, input: &str) -> Vec<usize> {
        self.captures_iter(input)
            .flat_map(|cap| {
                cap[1]
                    .split_whitespace()
                    .flat_map(|d| d.parse::<usize>())
                    .collect_vec()
            })
            .collect_vec()
    }
}
