package main

import (
	"strconv"
	"strings"
)

func part1(in string) string {
	heights := parse(in)
	visible := make([][]int, len(heights))

	for i := range heights {
		visible[i] = make([]int, len(heights[i]))
		scanLeft(heights, &visible, i)
		scanRight(heights, &visible, i)
	}

	for i := range heights[0] {
		scanUp(heights, &visible, i)
		scanDown(heights, &visible, i)
	}

	count := 0
	for _, row := range visible {
		for _, v := range row {
			if v == 1 {
				count++
			}
		}
	}

	return strconv.Itoa(count)
}

func parse(in string) [][]int {
	heights := [][]int{}
	for i, line := range strings.Split(in, "\r\n") {
		heights = append(heights, []int{})
		for _, c := range line {
			height, _ := strconv.Atoi(string(c))
			heights[i] = append(heights[i], height)
		}
	}
	return heights
}

func scanLeft(heights [][]int, visible *[][]int, row int) {
	max := 0
	for i := 0; i < len(heights[row]); i++ {
		if heights[row][i] > max || i == 0 || i == len(heights[row])-1 {
			max = heights[row][i]
			(*visible)[row][i] = 1
		}
	}
}

func scanRight(heights [][]int, visible *[][]int, row int) {
	max := 0
	for i := len(heights[row]) - 1; i >= 0; i-- {
		if heights[row][i] > max {
			max = heights[row][i]
			(*visible)[row][i] = 1
		}
	}
}

func scanUp(heights [][]int, visible *[][]int, col int) {
	max := 0
	for i := 0; i < len(heights); i++ {
		if heights[i][col] > max || i == 0 || i == len(heights)-1 {
			max = heights[i][col]
			(*visible)[i][col] = 1
		}
	}
}

func scanDown(heights [][]int, visible *[][]int, col int) {
	max := 0
	for i := len(heights) - 1; i >= 0; i-- {
		if heights[i][col] > max {
			max = heights[i][col]
			(*visible)[i][col] = 1
		}
	}
}
