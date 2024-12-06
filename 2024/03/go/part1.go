package main

import (
	"regexp"
	"strconv"
)

func solvePart1(in string) string {
	// Regular expression to match valid mul(X,Y) patterns
	// X and Y must be 1-3 digits each
	re := regexp.MustCompile(`mul\((\d{1,3}),(\d{1,3})\)`)

	// Find all matches in the input string
	matches := re.FindAllStringSubmatch(in, -1)

	sum := 0
	for _, match := range matches {
		// Convert first number (capturing group 1)
		x, err := strconv.Atoi(match[1])
		if err != nil {
			continue
		}

		// Convert second number (capturing group 2)
		y, err := strconv.Atoi(match[2])
		if err != nil {
			continue
		}

		// Multiply the numbers and add to sum
		sum += x * y
	}

	// Convert final sum to string
	return strconv.Itoa(sum)
}
