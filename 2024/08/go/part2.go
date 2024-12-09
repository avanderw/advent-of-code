package main

import (
	"fmt"
	"strings"
)

func solvePart2(in string) string {
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
				// Find antinode points and extend them
				extendAntinodePoints(positions[i], positions[j], width, height, antinodes)
			}
		}
	}

	return fmt.Sprintf("%d", len(antinodes))
}

func extendAntinodePoints(a1, a2 Point, width, height int, antinodes map[Point]bool) {
	// Calculate vector from a1 to a2
	dx := a2.x - a1.x
	dy := a2.y - a1.y

	// Extend from point1 in vector direction
	curr := a2
	for curr.x >= 0 && curr.x < width && curr.y >= 0 && curr.y < height {
		antinodes[curr] = true
		curr = Point{curr.x + dx, curr.y + dy}
	}

	// Extend from point2 in opposite direction
	curr = a1
	for curr.x >= 0 && curr.x < width && curr.y >= 0 && curr.y < height {
		antinodes[curr] = true
		curr = Point{curr.x - dx, curr.y - dy}
	}
}
