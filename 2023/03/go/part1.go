/**
--- Day 3: Gear Ratios ---

The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.

The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)

Here is an example engine schematic:

467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.

Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?
*/

package main

import (
	"strconv"
	"strings"
	"unicode"
)

func part1(in string) string {
	sum := 0

	rows := strings.Split(in, "\n")
	for y, row := range rows {
		for x, c := range row {
			if unicode.IsDigit(c) && isStart(x, y, rows) {
				num := parseRightNumber(row[x:])
				dist := len(strconv.Itoa(num))
				for i := 0; i < dist; i++ {
					if isAdjacentSymbol(x+i, y, rows) {
						sum += num
						break
					}
				}
			}
		}
	}

	return strconv.Itoa(sum)
}

func isSymbol(rows []string, y, x int) bool {
	if x < 0 || y < 0 || y >= len(rows) || x >= len(rows[y]) {
		return false
	}

	c := rune(rows[y][x])
	return c != '.' && c != '\r' && c != '\n' && (c < '0' || c > '9')
}

func isStart(x, y int, rows []string) bool {
	return x == 0 || isBoundary(rune(rows[y][x-1]))
}

func isAdjacentSymbol(x, y int, rows []string) bool {
	if x < 0 || y < 0 || y >= len(rows) || x >= len(rows[y]) {
		return false
	}

	return isSymbol(rows, y-1, x-1) || isSymbol(rows, y-1, x) || isSymbol(rows, y-1, x+1) ||
		isSymbol(rows, y, x-1) || isSymbol(rows, y, x+1) ||
		isSymbol(rows, y+1, x-1) || isSymbol(rows, y+1, x) || isSymbol(rows, y+1, x+1)
}

func getNumber(rows []string, x, y int) int {
	if x < 0 || y < 0 || y >= len(rows) || x >= len(rows[y]) {
		return 0
	}
	c := rune(rows[y][x])
	if c >= '0' && c <= '9' {
		isStart := x == 0 || isBoundary(rune(rows[y][x-1]))
		isEnd := x == len(rows[y])-1 || isBoundary(rune(rows[y][x+1]))

		if isStart {
			return parseRightNumber(rows[y][x:])
		} else if isEnd {
			return parseLeftNumber(rows[y][:x+1])
		} else {
			return 0
		}
	}

	return 0
}

func isBoundary(c rune) bool {
	return !unicode.IsDigit(c)
}

func parseRightNumber(s string) int {
	if len(s) == 0 || s[0] < '0' || s[0] > '9' {
		return 0
	}

	num := ""
	for i, c := range s {
		if c < '0' || c > '9' {
			num = s[:i]
			break
		}
	}

	if len(num) == 0 {
		num = s
	}

	n, err := strconv.Atoi(num)
	if err != nil {
		panic(err)
	}
	return n
}

func parseLeftNumber(s string) int {
	if len(s) == 0 || s[len(s)-1] < '0' || s[len(s)-1] > '9' {
		return 0
	}
	num := ""
	for i := len(s) - 1; i >= 0; i-- {
		c := rune(s[i])
		if c < '0' || c > '9' {
			num = s[i+1:]
			break
		}
	}

	if len(num) == 0 {
		num = s
	}

	n, err := strconv.Atoi(num)
	if err != nil {
		panic(err)
	}
	return n
}
