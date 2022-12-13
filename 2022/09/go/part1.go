package main

import (
	"strconv"
	"strings"
)

func part1(in string) string {
	tX, tY := 0, 0
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
			tX, tY = moveTail(tX, tY, hX, hY)
			route[strconv.Itoa(tX)+","+strconv.Itoa(tY)] = true
		}

	}

	return strconv.Itoa(len(route))
}

func moveTail(tX, tY, hX, hY int) (int, int) {
	tX1, tY1 := tX, tY
	dist := dist(tX, tY, hX, hY)
	if tX != hX && tY != hY {
		if dist > 2 {
			if tX < hX {
				tX1 = tX + 1
			} else if tX > hX {
				tX1 = tX - 1
			}
			if tY < hY {
				tY1 = tY + 1
			} else if tY > hY {
				tY1 = tY - 1
			}
		}
	} else if dist > 1 {
		if tX < hX {
			tX1 = tX + 1
		} else if tX > hX {
			tX1 = tX - 1
		} else if tY < hY {
			tY1 = tY + 1
		} else if tY > hY {
			tY1 = tY - 1
		}
	}

	return tX1, tY1
}

func dist(x1, y1, x2, y2 int) int {
	return abs(x1-x2) + abs(y1-y2)
}

func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}
