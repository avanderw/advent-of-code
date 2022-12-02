package main

import (
	"strconv"
	"strings"
)

func part2(in string) string {
	abcMap := map[string]string{
		"A": "R",
		"B": "P",
		"C": "S",
	}

	scoreMap := map[string]int{
		"R": 1,
		"P": 2,
		"S": 3,
	}

	sum := 0
	for _, line := range strings.Split(in, "\r\n") {
		abc := strings.Split(line, " ")[0]
		xyz := strings.Split(line, " ")[1]

		move := calcPlay(abcMap[abc], xyz)
		xyzScore := scoreMap[move]
		score := play(abcMap[abc], move)

		sum += xyzScore + score
	}

	return strconv.Itoa(sum)
}

func calcPlay(abc string, xyz string) string {
	switch xyz {
	case "X":
		switch abc {
		case "R":
			return "S"
		case "P":
			return "R"
		case "S":
			return "P"
		}
	case "Y":
		return abc
	case "Z":
		switch abc {
		case "R":
			return "P"
		case "P":
			return "S"
		case "S":
			return "R"
		}
	}

	return "F"
}
