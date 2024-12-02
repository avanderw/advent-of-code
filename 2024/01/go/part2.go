package main

import (
	"fmt"
	"strconv"
	"strings"
)

func solvePart2(in string) string {
	// Split input into lines
	lines := strings.Split(strings.TrimSpace(in), "\n")

	// Extract left and right numbers
	var leftNums []int
	rightFreq := make(map[int]int)

	for _, line := range lines {
		var left, right int
		n, _ := fmt.Sscanf(line, "%d %d", &left, &right)
		if n == 2 {
			// Store left number in slice
			leftNums = append(leftNums, left)
			// Count frequency of right numbers
			rightFreq[right]++
		}
	}

	// Calculate similarity score
	similarityScore := 0
	for _, leftNum := range leftNums {
		// For each number in left list, multiply by its frequency in right list
		similarityScore += leftNum * rightFreq[leftNum]
	}

	return strconv.Itoa(similarityScore)
}
