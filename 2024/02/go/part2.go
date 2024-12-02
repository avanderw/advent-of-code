package main

import (
	"strconv"
)

func solvePart2(in string) string {
	reports := parseInput(in)
	safeCount := 0

	for _, report := range reports {
		if isSafeWithDampener(report) {
			safeCount++
		}
	}

	return strconv.Itoa(safeCount)
}

// isSafeWithDampener checks if a report is safe either naturally or by removing one level
func isSafeWithDampener(report []int) bool {
	// First check if it's safe without removing any level
	if isSafe(report) {
		return true
	}

	// Try removing each level one at a time
	for i := range report {
		// Create a new slice without the current level
		dampened := make([]int, 0, len(report)-1)
		dampened = append(dampened, report[:i]...)
		dampened = append(dampened, report[i+1:]...)

		// Check if the dampened version is safe
		if isSafe(dampened) {
			return true
		}
	}

	return false
}
