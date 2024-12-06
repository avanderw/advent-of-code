package main

import (
	"strconv"
	"strings"
)

// Directions represents all possible directions to search for words
var directions = [][2]int{
	{0, 1},   // right
	{1, 0},   // down
	{1, 1},   // diagonal down-right
	{-1, 1},  // diagonal up-right
	{0, -1},  // left
	{-1, 0},  // up
	{-1, -1}, // diagonal up-left
	{1, -1},  // diagonal down-left
}

func solvePart1(in string) string {
	// Convert input string to 2D grid
	lines := strings.Split(strings.TrimSpace(in), "\n")
	grid := make([][]rune, len(lines))
	for i, line := range lines {
		grid[i] = []rune(strings.TrimSpace(line))
	}

	rows := len(grid)
	if rows == 0 {
		return "0"
	}
	cols := len(grid[0])

	count := 0
	target := "XMAS"

	// Check each starting position
	for row := 0; row < rows; row++ {
		for col := 0; col < cols; col++ {
			// Try each direction from this starting position
			for _, dir := range directions {
				if checkWord(grid, row, col, dir, target) {
					count++
				}
			}
		}
	}

	return strconv.Itoa(count)
}

// checkWord checks if the word exists starting from (row, col) in the given direction
func checkWord(grid [][]rune, row, col int, dir [2]int, target string) bool {
	rows, cols := len(grid), len(grid[0])
	if !isValidStart(row, col, dir, len(target), rows, cols) {
		return false
	}

	// Check each character of the target word
	for i, char := range target {
		newRow := row + i*dir[0]
		newCol := col + i*dir[1]
		if grid[newRow][newCol] != char {
			return false
		}
	}
	return true
}

// isValidStart checks if starting from this position in this direction would stay within bounds
func isValidStart(row, col int, dir [2]int, length, rows, cols int) bool {
	endRow := row + (length-1)*dir[0]
	endCol := col + (length-1)*dir[1]
	return endRow >= 0 && endRow < rows && endCol >= 0 && endCol < cols
}
