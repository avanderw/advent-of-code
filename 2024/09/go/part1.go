package main

import (
	"strconv"
)

// Represents a file block with its ID and length
type File struct {
	id     int
	length int
}

func solvePart1(in string) string {
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

	// Compact files by moving them to the left
	disk = compactDisk(disk)

	// Calculate checksum
	checksum := calculateChecksum(disk)

	return strconv.Itoa(checksum)
}

// Compacts the disk by moving files to the leftmost available space
func compactDisk(disk []int) []int {
	for i := len(disk) - 1; i >= 0; i-- {
		if disk[i] != -1 { // Found a file block
			// Find leftmost empty space
			leftmostSpace := -1
			for j := 0; j < i; j++ {
				if disk[j] == -1 {
					leftmostSpace = j
					break
				}
			}

			// If we found empty space to the left, move the block
			if leftmostSpace != -1 {
				disk[leftmostSpace] = disk[i]
				disk[i] = -1
				i++ // Check this position again in case we need to move more blocks
			}
		}
	}
	return disk
}

// Calculates the checksum by multiplying position by file ID
func calculateChecksum(disk []int) int {
	checksum := 0
	for pos, fileID := range disk {
		if fileID != -1 {
			checksum += pos * fileID
		}
	}
	return checksum
}
