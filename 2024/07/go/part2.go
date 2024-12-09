package main

import (
	"strconv"
	"strings"
)

// evaluate performs left-to-right evaluation of numbers with given operators
func evaluate(nums []int, ops []string) int {
	result := nums[0]
	for i := 0; i < len(ops); i++ {
		switch ops[i] {
		case "+":
			result += nums[i+1]
		case "*":
			result *= nums[i+1]
		case "||":
			// Convert both numbers to strings, concatenate, then convert back to int
			resultStr := strconv.Itoa(result) + strconv.Itoa(nums[i+1])
			result, _ = strconv.Atoi(resultStr)
		}
	}
	return result
}

func solvePart2(in string) string {
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
		generateOperators(len(nums)-1, []string{}, &operators, []string{"+", "*", "||"})

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
