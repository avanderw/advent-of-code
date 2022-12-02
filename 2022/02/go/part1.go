package main

import (
	"strconv"
	"strings"
)

func part1(in string) string {
	abcMap := map[string]string{
		"A": "R",
		"B": "P",
		"C": "S",
	}

	xyzMap := map[string]string{
		"X": "R",
		"Y": "P",
		"Z": "S",
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

		xyzScore := scoreMap[xyzMap[xyz]]
		score := play(abcMap[abc], xyzMap[xyz])

		sum += xyzScore + score
	}

	return strconv.Itoa(sum)
}

func play(opponent string, myMove string) int {
	if opponent == "R" && myMove == "P" {
		return 6
	} else if opponent == "P" && myMove == "S" {
		return 6
	} else if opponent == "S" && myMove == "R" {
		return 6
	} else if opponent == "R" && myMove == "S" {
		return 0
	} else if opponent == "P" && myMove == "R" {
		return 0
	} else if opponent == "S" && myMove == "P" {
		return 0
	} else {
		return 3
	}
}
