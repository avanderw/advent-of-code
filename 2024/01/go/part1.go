package main

import (
	"fmt"
	"math"
	"sort"
	"strconv"
	"strings"
)

func solvePart1(in string) string {
	// Parse input into two slices
	var left, right []int
	lines := strings.Split(strings.TrimSpace(in), "\n")

	for _, line := range lines {
		var l, r int
		// Using sscanf to parse two numbers separated by whitespace
		n, _ := fmt.Sscanf(line, "%d %d", &l, &r)
		if n == 2 {
			left = append(left, l)
			right = append(right, r)
		}
	}

	// Sort both slices
	sort.Ints(left)
	sort.Ints(right)

	// Calculate total distance
	totalDistance := 0
	for i := 0; i < len(left); i++ {
		distance := int(math.Abs(float64(left[i] - right[i])))
		totalDistance += distance
	}

	return strconv.Itoa(totalDistance)
}
