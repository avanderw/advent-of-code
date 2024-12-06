package main

import (
	"strconv"
	"strings"
)

func solvePart1(in string) string {
	// Parse input into rules and updates sections
	parts := strings.Split(strings.TrimSpace(in), "\r\n\r\n")
	rulesSection := strings.Split(parts[0], "\r\n")
	updatesSection := strings.Split(parts[1], "\r\n")

	// Build rules map
	rules := make(map[int]map[int]bool)
	for _, rule := range rulesSection {
		nums := strings.Split(rule, "|")
		before, _ := strconv.Atoi(nums[0])
		after, _ := strconv.Atoi(nums[1])

		if rules[before] == nil {
			rules[before] = make(map[int]bool)
		}
		rules[before][after] = true
	}

	// Process each update
	sum := 0
	for _, update := range updatesSection {
		// Parse update numbers
		var nums []int
		for _, numStr := range strings.Split(update, ",") {
			num, _ := strconv.Atoi(numStr)
			nums = append(nums, num)
		}

		if isValidOrder(nums, rules) {
			// Find middle number
			middleIdx := len(nums) / 2
			sum += nums[middleIdx]
		}
	}

	return strconv.Itoa(sum)
}

// isValidOrder checks if the given order satisfies all applicable rules
func isValidOrder(nums []int, rules map[int]map[int]bool) bool {
	// Create a set of numbers in this update for quick lookup
	numSet := make(map[int]bool)
	for _, num := range nums {
		numSet[num] = true
	}

	// Check each pair of numbers in the order
	for i := 0; i < len(nums); i++ {
		for j := i + 1; j < len(nums); j++ {
			// If there's a rule saying j should come before i, the order is invalid
			if rules[nums[j]] != nil && rules[nums[j]][nums[i]] {
				return false
			}
		}
	}

	return true
}
