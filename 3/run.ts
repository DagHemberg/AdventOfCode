import * as fs from "fs";

const raw = fs.readFileSync("data.json", "utf-8")
const data: string[] = JSON.parse(raw)

const empty = [..."000000000000"]

// -- problem 1 --

function opposite(n: string) {
    return n == "0" ? "1" : "0"
}

// takes all the digits from the sequences at the given index,
// replaces all the 0s with -1s, and sums the resulting array
// if the result is less than 0, then there had to have been
// more 0s than 1s, and so returns a 0, otherwise 1
// if the sum is 0, then there were equally manys 0s and 1s,
// in which case it also returns a 0 because of how the second
// problem was formulated
function mostCommonAt(arr: string[], i: number) {
    return arr.map(num => parseInt(num.charAt(i).replace(/0/, "-1"))).reduce((a, b) => a + b) < 0
        ? "0"
        : "1"
}

// it feels like this one shouldnt work
// but it does
function leastCommonAt(arr: string[], i: number) {
    return opposite(mostCommonAt(arr, i))
}

const gamma = empty.map((c, i) => mostCommonAt(data, i))
const epsilon = gamma.map(c => opposite(c))

// make them actual numbers and multply them
const powerConsumtion = [gamma, epsilon].map(num => parseInt(num.join(""), 2)).reduce((a, b) => a * b)

console.log(powerConsumtion)



// -- problem 2 --

// reduction algorith, starting with the full data set, 
// that for each index in a binary sequence of set length
// filters out the sequences where the bit at that index 
// is the least "optimal" (i.e. the most/least common)
function bitCriteriaFilter(arr: string[], mostOptimal: (arr: string[], n: number) => string) {
    return [...empty.keys()].reduce((current: string[], n: number) => {
        const filtered = current.filter(num => num.charAt(n) == mostOptimal(current, n))
        return filtered.length >= 1 ? filtered : current
    }, arr)
}

const oxygen = bitCriteriaFilter(data, mostCommonAt)
const CO2scrubber = bitCriteriaFilter(data, leastCommonAt)
const [decOxygen, decCO2Scrubber] = [oxygen, CO2scrubber].map(num => parseInt(num.join(""), 2))
const lifeSupport = decOxygen * decCO2Scrubber

console.log(lifeSupport)
