import * as fs from 'fs'

class fish {
    daysUntilNew: number
    
    constructor(n: number) {
        this.daysUntilNew = n
    }

    nextDay() {
        return this.daysUntilNew - 1 < 0 ? [new fish(6), new fish(8)] : [new fish(this.daysUntilNew - 1)]
    }
}

const raw = fs.readFileSync("../input.txt", "utf-8")
const fishes: fish[] = raw.split(",").map(x => parseInt(x)).map(age => new fish(age))

// technically works but like O(k^n)
const res = [...Array(80).keys()].reduce((data, _) => data.flatMap(x => x.nextDay()), fishes)

console.log(res.length)
