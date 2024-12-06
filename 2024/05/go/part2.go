package main

import (
	"strconv"
	"strings"
)

func solvePart2(in string) string {
	parts := strings.Split(strings.TrimSpace(in), "\r\n\r\n")
	rulesSection := strings.Split(parts[0], "\r\n")
	updatesSection := strings.Split(parts[1], "\r\n")

	// Build rules graph
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

	sum := 0
	for _, update := range updatesSection {
		var nums []int
		for _, numStr := range strings.Split(update, ",") {
			num, _ := strconv.Atoi(numStr)
			nums = append(nums, num)
		}

		if !isValidOrder(nums, rules) {
			sorted := topologicalSort(nums, rules)
			middleIdx := len(sorted) / 2
			sum += sorted[middleIdx]
		}
	}

	return strconv.Itoa(sum)
}

func topologicalSort(nums []int, rules map[int]map[int]bool) []int {
	// Build adjacency list and in-degree count
	graph := make(map[int][]int)
	inDegree := make(map[int]int)

	numSet := make(map[int]bool)
	for _, n := range nums {
		numSet[n] = true
		inDegree[n] = 0
	}

	// Build graph for numbers in this update only
	for i := range nums {
		for j := range nums {
			if i != j && rules[nums[i]] != nil && rules[nums[i]][nums[j]] {
				graph[nums[i]] = append(graph[nums[i]], nums[j])
				inDegree[nums[j]]++
			}
		}
	}

	// Kahn's algorithm
	var result []int
	var queue []int

	for _, n := range nums {
		if inDegree[n] == 0 {
			queue = append(queue, n)
		}
	}

	for len(queue) > 0 {
		n := queue[0]
		queue = queue[1:]
		result = append(result, n)

		for _, next := range graph[n] {
			inDegree[next]--
			if inDegree[next] == 0 {
				queue = append(queue, next)
			}
		}
	}

	return result
}
