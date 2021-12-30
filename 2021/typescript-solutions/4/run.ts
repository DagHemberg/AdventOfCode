import * as fs from "fs"

class board {
    numbers: number[][]
    found: boolean[][]
    won: boolean = false

    constructor(numbers: number[][]) {
        this.numbers = numbers
        this.found = this.numbers.map(row => row.map(() => false))
    }

    checkWin() {
        const rows = this.found
            .some(row => row.every(b => b))
        
        const cols = this.found
            .map((_, row) => this.found.map((_, col) => this.found[col][row])) // transpose
            .some(col => col.every(b => b))

        return rows || cols
    }

    update(n: number) {
        for (let row = 0; row < this.numbers.length; row++) {
            for (let col = 0; col < this.numbers[row].length; col++) {
                if (this.numbers[row][col] == n) {
                    this.found[row][col] = true
                    if (this.checkWin()) this.won = true
                    return this.won
                }
            }
        }
    }

    nonFound() {
        return this.numbers
            .map((row, ri) => row.filter((_, ci) => !this.found[ri][ci]))
            .reduce((acc, val) => acc.concat(val), [])
    }
}

const raw = fs.readFileSync("data.txt", "utf8")
const [_, ...boards] = raw              // removes first line 
    .split("\r\n\r\n")                  // array of bingo board strings
    .map(s => s.split("\r\n"))          // splits each row of every bingo board into its own array
    .map(row => row.map(col => col      
        .split(/ +/)
        .map(n => parseInt(n))
        .filter(n => !isNaN(n)))        // probably dont need this if i get make the regex better
    )
    .map(m => new board(m))

const inputs = raw
    .split("\r\n\r\n")[0]
    .split(",")
    .map(n => parseInt(n))


var winners: { board: board, winningNumber: number }[] = []
for (let num of inputs) {
    for (let board of boards) {
        if (!board.won && board.update(num)) winners.push({ board: board, winningNumber: num })
    }
}

const first = winners[0]
const last = winners[winners.length - 1]

const res = [first, last].forEach(winner => {
    const nf = winner.board.nonFound()
    console.log(nf.join(" + "), "*", winner.winningNumber, "=", nf.reduce((a, b) => a + b, 0) * winner.winningNumber)
})