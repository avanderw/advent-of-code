/*
*
Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits".

Equipped with this new information, you now need to find the real first and last digit on each line. For example:

two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76. Adding these together produces 281.

What is the sum of all of the calibration values?
*/
package main

import (
	"strconv"
	"strings"
)

func part2(in string) string {
	sum := 0
	for _, line := range strings.Split(in, "\r\n") {
		sum += calibrationValueWithWords(line)
	}
	return strconv.Itoa(sum)
}

func calibrationValueWithWords(line string) int {
	first := firstDigitWithWords(line)
	last := lastDigitWithWords(line)
	return first*10 + last
}

// iterate until finding the first digit catering for words
func firstDigitWithWords(line string) int {
	spelledDigits := []string{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}

	for i, c := range line {
		if c >= '0' && c <= '9' {
			return int(c - '0')
		}

		for j, spelledDigit := range spelledDigits {
			if strings.HasPrefix(strings.ToLower(line[i:]), spelledDigit) {
				return j + 1
			}
		}
	}

	return 0
}

// iterate backwards until finding the last digit catering for words
func lastDigitWithWords(line string) int {
	spelledDigits := []string{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}

	for i := len(line) - 1; i >= 0; i-- {
		c := line[i]
		if c >= '0' && c <= '9' {
			return int(c - '0')
		}

		for j, spelledDigit := range spelledDigits {
			backIndex := i - len(spelledDigit)
			if i >= len(spelledDigit) && strings.HasSuffix(strings.ToLower(line[backIndex:i+1]), spelledDigit) {
				return j + 1
			}
		}
	}
	return 0
}
