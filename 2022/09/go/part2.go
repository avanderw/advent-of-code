package main

import (
	"fmt"
	"strconv"
	"strings"
)

func part2(in string) string {
	x9, y9 := 0, 0
	x8, y8 := 0, 0
	x7, y7 := 0, 0
	x6, y6 := 0, 0
	x5, y5 := 0, 0
	x4, y4 := 0, 0
	x3, y3 := 0, 0
	x2, y2 := 0, 0
	x1, y1 := 0, 0
	hX, hY := 0, 0
	route := map[string]bool{}

	for _, line := range strings.Split(in, "\r\n") {
		split := strings.Split(line, " ")
		direction := split[0]
		distance, _ := strconv.Atoi(split[1])

		for i := 0; i < distance; i++ {
			switch direction {
			case "U":
				hY++
			case "D":
				hY--
			case "L":
				hX--
			case "R":
				hX++
			}

			x1, y1 = moveTail(x1, y1, hX, hY)
			x2, y2 = moveTail(x2, y2, x1, y1)
			x3, y3 = moveTail(x3, y3, x2, y2)
			x4, y4 = moveTail(x4, y4, x3, y3)
			x5, y5 = moveTail(x5, y5, x4, y4)
			x6, y6 = moveTail(x6, y6, x5, y5)
			x7, y7 = moveTail(x7, y7, x6, y6)
			x8, y8 = moveTail(x8, y8, x7, y7)
			x9, y9 = moveTail(x9, y9, x8, y8)
			route[strconv.Itoa(x9)+","+strconv.Itoa(y9)] = true
		}
	}

	return strconv.Itoa(len(route))
}

func printRoute(route map[string]bool) {
	for key := range route {
		fmt.Println(key)
	}
}
