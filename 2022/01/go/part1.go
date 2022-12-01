package main

import (
	"strconv"
	"strings"
)

func part1(in string) string {
	var max int
	var sum int
	for _, line := range strings.Split(in, "\r\n") {
		if line == "" {
			if sum > max {
				max = sum
			}
			sum = 0
			continue
		}

		i, err := strconv.Atoi(line)
		if err != nil {
			panic(err)
		}
		sum += i
	}
	return strconv.Itoa(max)
}
