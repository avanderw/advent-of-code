package main

import (
	"strconv"
	"strings"
)

func part2(in string) string {
	sum := 0
	for _, line := range strings.Split(in, "\r\n") {
		ranges := strings.Split(line, ",")
		if len(ranges) != 2 {
			panic("invalid input")
		}

		low1, high1 := parseRange(ranges[0])
		low2, high2 := parseRange(ranges[1])

		if isOverlap(low1, high1, low2, high2) {
			sum++
		}
	}

	return strconv.Itoa(sum)
}

func isOverlap(low1, high1, low2, high2 int) bool {
	return low1 <= high2 && low2 <= high1
}
