package main

import (
	"strconv"
	"strings"
)

func solvePart2(in string) string {
	lines := strings.Split(strings.TrimSpace(in), "\n")
	grid := make([][]rune, len(lines))
	for i, line := range lines {
		grid[i] = []rune(strings.TrimSpace(line))
	}

	count := 0
	rows := len(grid)
	if rows == 0 {
		return "0"
	}
	cols := len(grid[0])

	// For each A in the grid
	for row := 1; row < rows-1; row++ {
		for col := 1; col < cols-1; col++ {
			if grid[row][col] != 'A' {
				continue
			}

			// Check first diagonal (top-left to bottom-right)
			d1HasM := (grid[row-1][col-1] == 'M' || grid[row+1][col+1] == 'M')
			d1HasS := (grid[row-1][col-1] == 'S' || grid[row+1][col+1] == 'S')
			diagonal1Valid := d1HasM && d1HasS

			// Check second diagonal (top-right to bottom-left)
			d2HasM := (grid[row-1][col+1] == 'M' || grid[row+1][col-1] == 'M')
			d2HasS := (grid[row-1][col+1] == 'S' || grid[row+1][col-1] == 'S')
			diagonal2Valid := d2HasM && d2HasS

			if diagonal1Valid && diagonal2Valid {
				count++
			}
		}
	}

	return strconv.Itoa(count)
}
