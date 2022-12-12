package main

import (
	"strconv"
)

func part2(in string) string {
	dirSizeMap := readInput(in)
	updateSizes(&dirSizeMap)

	total := 70000000
	targetFree := 30000000
	currFree := total - dirSize(&dirSizeMap, "")
	mustFree := targetFree - currFree

	min := total
	for dir := range dirSizeMap {
		size := dirSize(&dirSizeMap, dir)
		if size > mustFree && size < min {
			min = size
		}
	}

	return strconv.Itoa(min)
}
