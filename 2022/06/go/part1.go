package main

import (
	"strconv"
)

func part1(in string) string {
	return strconv.Itoa(indexFirstUniqueGroup(in, 4) + 4)
}

func indexFirstUniqueGroup(in string, groupSize int) int {
	for i := 0; i < len(in)-groupSize; i++ {
		if isUniqueGroup(in[i:i+groupSize], groupSize) {
			return i
		}
	}
	return -1
}

func isUniqueGroup(in string, groupSize int) bool {
	for i := 0; i < len(in); i++ {
		if count(in, string(in[i])) > 1 {
			return false
		}
	}
	return true
}

func count(in, c string) int {
	count := 0
	for _, r := range in {
		if string(r) == c {
			count++
		}
	}
	return count
}
