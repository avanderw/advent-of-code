package main

import (
	"sort"
	"strconv"
	"strings"
)

func part2(in string) string {
	sums := []int{}
	var sum int
	for _, line := range strings.Split(in, "\r\n") {
		if line == "" {
			sums = append(sums, sum)
			sum = 0
			continue
		}

		i, err := strconv.Atoi(line)
		if err != nil {
			panic(err)
		}
		sum += i
	}
	sums = append(sums, sum)

	sort.Slice(sums, func(i, j int) bool {
		return sums[i] < sums[j]
	})

	var top int
	for i := 0; i < 3; i++ {
		top += sums[len(sums)-1-i]
	}

	return strconv.Itoa(top)
}
