import * as fs from 'fs'

const raw = fs.readFileSync('input.txt', 'utf8')

// fishes grouped by days left
var fishes: number[] = [...Array(9).keys()]
    .map(fishGroup => raw.split(",").map(Number).filter(fish => fish == fishGroup).length) 

for (let i = 0; i < 256; i++) {
    fishes.push(0)
    const zeroes = fishes.shift() as number
    fishes[6] += zeroes
    fishes[8] += zeroes
}

console.log(fishes.reduce((acc, curr) => acc + curr, 0))