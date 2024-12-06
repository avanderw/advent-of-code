package main

import (
	"strconv"
	"strings"
)

type Position struct {
	x, y int
}

type Direction struct {
	dx, dy int
}

func solvePart1(in string) string {
	// Parse input into 2D grid
	var grid [][]rune
	var startPos Position
	var startDir Direction

	// Parse input and find starting position
	for y, line := range strings.Split(strings.TrimSpace(in), "\n") {
		row := []rune(line)
		for x, ch := range row {
			if ch == '^' {
				startPos = Position{x, y}
				startDir = Direction{0, -1} // facing up
				row[x] = '.'                // Replace start position with empty space
			}
		}
		grid = append(grid, row)
	}

	height := len(grid)
	width := len(grid[0])

	// Track visited positions
	visited := make(map[Position]bool)
	visited[startPos] = true

	pos := startPos
	dir := startDir

	// Keep moving until we leave the grid
	for {
		// Calculate next position
		nextPos := Position{pos.x + dir.dx, pos.y + dir.dy}

		// Check if we're leaving the grid
		if nextPos.x < 0 || nextPos.x >= width || nextPos.y < 0 || nextPos.y >= height {
			break
		}

		// Check if there's an obstacle ahead
		if grid[nextPos.y][nextPos.x] == '#' {
			// Turn right
			dir = Direction{-dir.dy, dir.dx}
			continue
		}

		// Move forward
		pos = nextPos
		visited[pos] = true
	}

	return strconv.Itoa(len(visited))
}
