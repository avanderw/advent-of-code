/**
The newly-improved calibration document consists of lines of text; each line originally contained a specific calibration value that the Elves now need to recover. On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.

For example:

1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet

In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
*/

package main

import (
	"strconv"
	"strings"
)

func part1(in string) string {
	sum := 0
	for _, line := range strings.Split(in, "\r\n") {
		sum += calibrationValue(line)
	}
	return strconv.Itoa(sum)
}

func calibrationValue(line string) int {
	first := firstDigit(line)
	last := lastDigit(line)
	return first*10 + last
}

// iterate until finding the first digit
func firstDigit(line string) int {
	for _, c := range line {
		if c >= '0' && c <= '9' {
			return int(c - '0')
		}
	}
	return 0
}

// iterate backwards until finding the last digit
func lastDigit(line string) int {
	for i := len(line) - 1; i >= 0; i-- {
		c := line[i]
		if c >= '0' && c <= '9' {
			return int(c - '0')
		}
	}
	return 0
}
