package main

import "strconv"

func part2(in string) string {
	heights := parse(in)

	maxScore := 0
	for i := range heights {
		for j := range heights[i] {
			if i == 0 || i == len(heights)-1 || j == 0 || j == len(heights[i])-1 {
				continue
			}
			score := getScore(heights, i, j)
			if score > maxScore {
				maxScore = score
			}
		}
	}

	return strconv.Itoa(maxScore)
}

func getScore(heights [][]int, row, col int) int {
	left := scoreLeft(heights, row, col)
	right := scoreRight(heights, row, col)
	up := scoreUp(heights, row, col)
	down := scoreDown(heights, row, col)

	return left * right * up * down
}

func scoreLeft(heights [][]int, row, col int) int {
	max := heights[row][col]
	score := 0
	for i := col - 1; i >= 0; i-- {
		if heights[row][i] >= max {
			score++
			break
		}

		score++
	}
	return score
}

func scoreRight(heights [][]int, row, col int) int {
	max := heights[row][col]
	score := 0
	for i := col + 1; i < len(heights[row]); i++ {
		if heights[row][i] >= max {
			score++
			break
		}

		score++
	}
	return score
}

func scoreUp(heights [][]int, row, col int) int {
	max := heights[row][col]
	score := 0
	for i := row - 1; i >= 0; i-- {
		if heights[i][col] >= max {
			score++
			break
		}

		score++
	}
	return score
}

func scoreDown(heights [][]int, row, col int) int {
	max := heights[row][col]
	score := 0
	for i := row + 1; i < len(heights); i++ {
		if heights[i][col] >= max {
			score++
			break
		}

		score++
	}
	return score
}
