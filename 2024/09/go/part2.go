package main

import (
	"strconv"
)

func solvePart2(in string) string {
	// Parse input into alternating file and space lengths
	var numbers []int
	for _, c := range in {
		num, _ := strconv.Atoi(string(c))
		numbers = append(numbers, num)
	}

	// Convert lengths into file blocks
	var files []File
	fileID := 0
	for i := 0; i < len(numbers); i += 2 {
		if i < len(numbers) {
			files = append(files, File{id: fileID, length: numbers[i]})
			fileID++
		}
	}

	// Create initial disk layout
	var disk []int // -1 represents empty space
	for i := 0; i < len(numbers); i++ {
		length := numbers[i]
		if i%2 == 0 {
			// File blocks
			fileID := i / 2
			for j := 0; j < length; j++ {
				disk = append(disk, fileID)
			}
		} else {
			// Empty space
			for j := 0; j < length; j++ {
				disk = append(disk, -1)
			}
		}
	}

	// For part 2, compact files by moving whole files in decreasing ID order
	disk = compactDiskPart2(disk, files)

	// Calculate checksum
	checksum := calculateChecksum(disk)

	return strconv.Itoa(checksum)
}

// Compacts the disk by moving whole files to leftmost available space that can fit them
func compactDiskPart2(disk []int, files []File) []int {
	// Process files in decreasing ID order
	for fileID := len(files) - 1; fileID >= 0; fileID-- {
		fileLength := files[fileID].length

		// Find the current position of this file
		startPos := -1
		for i := 0; i < len(disk); i++ {
			if disk[i] == fileID {
				startPos = i
				break
			}
		}

		// Find leftmost span of empty space that can fit the file
		bestEmptySpan := -1
		for i := 0; i < startPos; i++ {
			if disk[i] == -1 {
				// Check if we have enough continuous empty space
				spaceLength := 0
				for j := i; j < len(disk) && disk[j] == -1; j++ {
					spaceLength++
				}
				if spaceLength >= fileLength {
					bestEmptySpan = i
					break
				}
				// Skip to end of this empty span
				i += spaceLength - 1
			}
		}

		// If we found a suitable empty span, move the file there
		if bestEmptySpan != -1 {
			// Clear the old file location
			oldPositions := make([]int, 0)
			for i := 0; i < len(disk); i++ {
				if disk[i] == fileID {
					oldPositions = append(oldPositions, i)
					disk[i] = -1
				}
			}

			// Place the file in its new location
			for i := 0; i < fileLength; i++ {
				disk[bestEmptySpan+i] = fileID
			}
		}
	}
	return disk
}
