// #![allow(dead_code, unused)]

use std::{cmp::Ordering, collections::HashMap};

use count_where::CountWhere;
use itertools::Itertools;
use lazy_static::lazy_static;
use regex::Regex;

advent_of_code::solution!(7);

#[derive(PartialEq, Eq, PartialOrd, Ord, Hash, Clone, Copy, Debug)]
enum Card {
    Joker,
    Two,
    Three,
    Four,
    Five,
    Six,
    Seven,
    Eight,
    Nine,
    Ten,
    Jack,
    Queen,
    King,
    Ace,
}

#[derive(PartialEq, Eq, PartialOrd, Ord, Debug, Clone, Copy)]
enum Category {
    HighCard,
    OnePair,
    TwoPair,
    ThreeOfAKind,
    FullHouse,
    FourOfAKind,
    FiveOfAKind,
}

lazy_static! {
    static ref CARDS: HashMap<char, Card> = {
        use Card::*;
        [
            ('A', Ace),
            ('K', King),
            ('Q', Queen),
            ('J', Jack),
            ('T', Ten),
            ('9', Nine),
            ('8', Eight),
            ('7', Seven),
            ('6', Six),
            ('5', Five),
            ('4', Four),
            ('3', Three),
            ('2', Two),
        ]
        .iter()
        .copied()
        .collect()
    };
}

impl Card {
    pub fn parse(input: char) -> Option<Card> {
        CARDS.get(&input).copied()
    }

    pub fn values() -> impl Iterator<Item = Card> {
        CARDS.values().copied()
    }
}

#[derive(PartialEq, Eq, Debug)]
struct Tally {
    counter: HashMap<Card, usize>,
}

impl Tally {
    fn calculate(cards: &[Card]) -> Tally {
        Tally {
            counter: cards
                .iter()
                .counts()
                .iter()
                .map(|(&card, count)| (*card, *count))
                .collect::<HashMap<_, _>>(),
        }
    }

    fn determine_category(&self) -> Category {
        use Category::*;
        if self.counter.len() == 1 {
            FiveOfAKind
        } else if self.counter.values().contains(&4) {
            FourOfAKind
        } else if self.counter.values().contains(&3) && self.counter.values().contains(&2) {
            FullHouse
        } else if self.counter.values().contains(&3) {
            ThreeOfAKind
        } else if self.counter.values().count_where(|&v| *v == 2) == 2 {
            TwoPair
        } else if self.counter.values().contains(&2) {
            OnePair
        } else {
            HighCard
        }
    }

    fn replace_joker(&self, card: Card) -> Tally {
        let mut counter = self.counter.clone();
        if let Some(amount) = counter.remove(&Card::Jack) {
            counter.insert(card, counter.get(&card).unwrap_or(&0) + amount);
        }

        Tally { counter }
    }
}

#[derive(Eq, Debug)]
struct Hand {
    cards: Vec<Card>,
    tally: Tally,
    bid: usize,
}

lazy_static! {
    static ref HAND_REGEX: Regex = Regex::new(r"([AKQJT98765432]{5}) (\d+)").unwrap();
}

impl Hand {
    fn parse(input: &str) -> Hand {
        let caps = HAND_REGEX.captures(input).unwrap();
        let cards = caps[1].chars().flat_map(Card::parse).collect::<Vec<_>>();
        let tally = Tally::calculate(&cards);
        let bid = caps[2].parse::<usize>().unwrap();

        Hand { cards, tally, bid }
    }

    pub fn category(&self) -> Category {
        self.tally.determine_category()
    }

    // apparently im allowed do this?
    pub fn with_joker(mut self) -> Hand {
        self.cards.iter_mut().for_each(|card| {
            if *card == Card::Jack {
                *card = Card::Joker;
            }
        });

        let imitation = Card::values()
            .map(|card| self.tally.replace_joker(card))
            .max_by(|a, b| a.determine_category().cmp(&b.determine_category()))
            .unwrap();

        Hand {
            tally: imitation,
            ..self
        }
    }
}

impl PartialEq for Hand {
    fn eq(&self, other: &Self) -> bool {
        self.cards == other.cards
    }
}

impl PartialOrd for Hand {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl Ord for Hand {
    fn cmp(&self, other: &Hand) -> Ordering {
        let category_ordering = self.category().cmp(&other.category());
        if let Ordering::Equal = category_ordering {
            self.cards.cmp(&other.cards)
        } else {
            category_ordering
        }
    }
}

pub fn part_one(input: &str) -> Option<usize> {
    let res: usize = input
        .lines()
        .map(Hand::parse)
        .sorted()
        .enumerate()
        .map(|(rank, hand)| (rank + 1) * hand.bid)
        .sum();

    Some(res)
}

pub fn part_two(input: &str) -> Option<usize> {
    // let res = Hand::parse(input.lines().next().unwrap()).with_joker();
    // dbg!(res);
    // None
    let res: usize = input
        .lines()
        .map(Hand::parse)
        .map(|hand| hand.with_joker())
        .sorted()
        .enumerate()
        .map(|(rank, hand)| (rank + 1) * hand.bid)
        .sum();

    Some(res)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_part_one() {
        let result = part_one(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(6440));
    }

    #[test]
    fn test_part_two() {
        let result = part_two(&advent_of_code::template::read_file("examples", DAY));
        assert_eq!(result, Some(5905));
    }
}
