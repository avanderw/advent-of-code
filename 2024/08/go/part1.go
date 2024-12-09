package main

import (
	"fmt"
	"strings"
)

type Point struct {
	x, y int
}

func solvePart1(in string) string {
	// Parse the input into a grid
	lines := strings.Split(strings.TrimSpace(in), "\r\n")
	height := len(lines)
	width := len(lines[0])

	// Map to store antennas by frequency
	antennaMap := make(map[rune][]Point)

	// Find all antennas and group by frequency
	for y, line := range lines {
		for x, ch := range line {
			if ch != '.' {
				antennaMap[ch] = append(antennaMap[ch], Point{x, y})
			}
		}
	}

	// Keep track of antinode locations
	antinodes := make(map[Point]bool)

	// For each frequency group
	for _, positions := range antennaMap {
		// For each pair of antennas with same frequency
		for i := 0; i < len(positions); i++ {
			for j := i + 1; j < len(positions); j++ {
				// Find the two antinode points for this pair
				points := findAntinodePoints(positions[i], positions[j])
				// Add points if they're within bounds
				for _, p := range points {
					if p.x >= 0 && p.x < width && p.y >= 0 && p.y < height {
						antinodes[p] = true
					}
				}
			}
		}
	}

	return fmt.Sprintf("%d", len(antinodes))
}

func findAntinodePoints(a1, a2 Point) []Point {
	// Calculate vector from a1 to a2
	dx := a2.x - a1.x
	dy := a2.y - a1.y

	// First antinode: extend past a2 by the full vector distance
	point1 := Point{
		x: a2.x + dx,
		y: a2.y + dy,
	}

	// Second antinode: extend past a1 by the full vector distance (in opposite direction)
	point2 := Point{
		x: a1.x - dx,
		y: a1.y - dy,
	}

	return []Point{point1, point2}
}
