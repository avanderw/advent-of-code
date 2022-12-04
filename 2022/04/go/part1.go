package main

import (
	"strconv"
	"strings"
)

func part1(in string) string {
	contained := 0
	for _, line := range strings.Split(in, "\r\n") {
		ranges := strings.Split(line, ",")
		if len(ranges) != 2 {
			panic("invalid input")
		}

		low1, high1 := parseRange(ranges[0])
		low2, high2 := parseRange(ranges[1])

		if isContained(low1, high1, low2, high2) {
			contained++
			continue
		}

		if isContained(low2, high2, low1, high1) {
			contained++
			continue
		}
	}

	return strconv.Itoa(contained)
}

func parseRange(s string) (int, int) {
	ends := strings.Split(s, "-")
	if len(ends) != 2 {
		panic("invalid input")
	}

	low, err := strconv.Atoi(ends[0])
	if err != nil {
		panic(err)
	}

	high, err := strconv.Atoi(ends[1])
	if err != nil {
		panic(err)
	}

	return low, high
}

func isContained(low1, high1, low2, high2 int) bool {
	return low1 >= low2 && high1 <= high2
}
