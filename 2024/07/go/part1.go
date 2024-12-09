package main

import (
	"strconv"
	"strings"
)

// generateOperators generates all possible combinations of +, *, and || operators
func generateOperators(n int, current []string, result *[][]string, operators []string) {
	if len(current) == n {
		temp := make([]string, len(current))
		copy(temp, current)
		*result = append(*result, temp)
		return
	}

	// Try all three operators
	for _, op := range operators {
		current = append(current, op)
		generateOperators(n, current, result, operators)
		current = current[:len(current)-1]
	}
}

func solvePart1(in string) string {
	lines := strings.Split(strings.TrimSpace(in), "\n")
	sum := 0

	for _, line := range lines {
		// Split into test value and numbers
		parts := strings.Split(line, ": ")
		testValue, _ := strconv.Atoi(parts[0])

		// Parse numbers
		numStrs := strings.Fields(parts[1])
		nums := make([]int, len(numStrs))
		for i, ns := range numStrs {
			nums[i], _ = strconv.Atoi(ns)
		}

		// Generate all possible operator combinations
		var operators [][]string
		generateOperators(len(nums)-1, []string{}, &operators, []string{"+", "*"})

		// Try each combination
		for _, ops := range operators {
			if evaluate(nums, ops) == testValue {
				sum += testValue
				break // Found a valid combination, move to next line
			}
		}
	}

	return strconv.Itoa(sum)
}
