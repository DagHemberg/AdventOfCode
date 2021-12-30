import * as fs from 'fs'
const data = fs.readFileSync("input.txt", "utf8").split(",").map(Number)

// fairly naive approach but the input is pretty small so its fine
const minEffort = Math.min(...[...Array(Math.max(...data) + 1).keys()].map(diff => data
    .map(n => Math.abs(n - diff) * ((1 + Math.abs(n - diff)) / 2)) // arithmetic series sum
    .reduce((acc, curr) => acc + curr, 0))
)

console.log(minEffort)