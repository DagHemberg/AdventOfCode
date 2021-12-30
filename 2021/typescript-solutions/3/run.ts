import * as fs from "fs";

const raw = fs.readFileSync("data.json", "utf-8")
const data: string[] = JSON.parse(raw)

const empty = [..."000000000000"]

const opposite = (n: string) => n == "0" ? "1" : "0"

// "but what if theyre equally common"
// the answer is shut up it just works dont worry about it
const mostCommonAt = (arr: string[], i: number) => 
    arr.map(num => parseInt(num.charAt(i).replace(/0/, "-1"))).reduce((a, b) => a + b) < 0 
        ? "0"
        : "1"

const leastCommonAt = (arr: string[], i: number) => opposite(mostCommonAt(arr, i))

const gamma = empty.map((c, i) => mostCommonAt(data, i))
const epsilon = gamma.map(c => opposite(c))

const powerConsumption = [gamma, epsilon]
    .map(num => parseInt(num.join(""), 2))
    .reduce((a, b) => a * b)

console.log(powerConsumption)

// holy reduction algorithm batman
const bitCriteriaFilter = (arr: string[], mostOptimal: (arr: string[], n: number) => string) =>
    [...empty.keys()].reduce((current: string[], n: number) => {
        const filtered = current.filter(num => num.charAt(n) == mostOptimal(current, n))
        return filtered.length >= 1 ? filtered : current
    }, arr)

const [oxygen, co2scrubber] = [mostCommonAt, leastCommonAt]
    .map(f => bitCriteriaFilter(data, f)) // cursed
    .map(num => parseInt(num.join(""), 2))

const lifeSupport = oxygen * co2scrubber

console.log(lifeSupport)
