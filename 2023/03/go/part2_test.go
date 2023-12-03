package main

import (
	"testing"
)

func TestPart2(t *testing.T) {
	var cases = []struct {
		input, expected string
	}{
		{"part2.txt", "467835"},
	}

	for _, c := range cases {
		in, err := readInput(c.input)
		if err != nil {
			t.Fatal(err)
		}
		got := part2(in)
		if got != c.expected {
			t.Errorf("part2(%q) == %q, want %q", c.input, got, c.expected)
		}
	}
}

func TestIsGear(t *testing.T) {
	testCases := []struct {
		rows     []string
		y, x     int
		expected bool
	}{
		// Test case 1: Valid gear
		{
			rows:     []string{"..*", "...", "..."},
			y:        0,
			x:        2,
			expected: true,
		},
		// Test case 2: Invalid gear (out of bounds)
		{
			rows:     []string{"..*", "...", "..."},
			y:        1,
			x:        3,
			expected: false,
		},
		// Test case 3: Invalid gear (not a gear symbol)
		{
			rows:     []string{"..*", "...", "..."},
			y:        0,
			x:        0,
			expected: false,
		},
	}

	for _, testCase := range testCases {
		result := isGear(testCase.rows, testCase.y, testCase.x)

		if result != testCase.expected {
			t.Errorf("For rows %v, y=%d, x=%d: expected %v, got %v", testCase.rows, testCase.y, testCase.x, testCase.expected, result)
		}
	}
}

func TestGetAdjacentGears(t *testing.T) {
	rows := []string{
		"***",
		"*.*",
		"***",
	}

	testCases := []struct {
		x, y     int
		expected []int
	}{
		// Test case 1: Gears on all sides
		{
			x:        1,
			y:        1,
			expected: []int{hash(0, 0), hash(0, 1), hash(0, 2), hash(1, 0), hash(1, 2), hash(2, 0), hash(2, 1), hash(2, 2)},
		},
		// Test case 2: Corner case
		{
			x:        0,
			y:        0,
			expected: []int{hash(0, 1), hash(1, 0)},
		},
		// Test case 3: Gears only right side
		{
			x:        2,
			y:        1,
			expected: []int{hash(0, 1), hash(0, 2), hash(2, 1), hash(2, 2)},
		},
		// Test case 4: Gears only top side
		{
			x:        1,
			y:        0,
			expected: []int{hash(0, 0), hash(0, 2), hash(1, 0), hash(1, 2)},
		},
	}

	for _, testCase := range testCases {
		result := getAdjacentGears(testCase.x, testCase.y, rows)

		if !intSliceEqual(result, testCase.expected) {
			t.Errorf("For x=%d, y=%d: expected %v, got %v", testCase.x, testCase.y, testCase.expected, result)
		}
	}
}

func intSliceEqual(slice1, slice2 []int) bool {
	if len(slice1) != len(slice2) {
		return false
	}

	for i, v := range slice1 {
		if v != slice2[i] {
			return false
		}
	}

	return true
}

func BenchmarkPart2(b *testing.B) {
	in, err := readInput("main.txt")
	if err != nil {
		b.Fatal(err)
	}
	for i := 0; i < b.N; i++ {
		part1(in)
	}
}
