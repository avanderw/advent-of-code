package main

import (
	"strconv"
	"strings"
)

func solvePart1(in string) string {
	reports := parseInput(in)
	safeCount := 0

	for _, report := range reports {
		if isSafe(report) {
			safeCount++
		}
	}

	return strconv.Itoa(safeCount)
}

// parseInput converts the input string into a slice of integer slices
func parseInput(in string) [][]int {
	lines := strings.Split(strings.TrimSpace(in), "\n")
	reports := make([][]int, 0, len(lines))

	for _, line := range lines {
		numbers := strings.Fields(line)
		report := make([]int, 0, len(numbers))

		for _, num := range numbers {
			n, _ := strconv.Atoi(num)
			report = append(report, n)
		}

		reports = append(reports, report)
	}

	return reports
}

// isSafe checks if a report meets all safety criteria
func isSafe(report []int) bool {
	if len(report) < 2 {
		return true
	}

	// Check first difference to determine if we should be increasing or decreasing
	isIncreasing := report[1] > report[0]

	for i := 1; i < len(report); i++ {
		diff := report[i] - report[i-1]

		// Check if difference is between 1 and 3 (inclusive)
		if abs(diff) < 1 || abs(diff) > 3 {
			return false
		}

		// Check if direction matches the initial direction
		if isIncreasing && diff <= 0 {
			return false
		}
		if !isIncreasing && diff >= 0 {
			return false
		}
	}

	return true
}

// abs returns the absolute value of an integer
func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}
