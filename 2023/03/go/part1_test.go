package main

import (
	"testing"
)

func TestPart1(t *testing.T) {
	var cases = []struct {
		input, expected string
	}{
		{"part1.txt", "4361"},
	}

	for _, c := range cases {
		in, err := readInput(c.input)
		if err != nil {
			t.Fatal(err)
		}
		got := part1(in)
		if got != c.expected {
			t.Errorf("part1(%q) == %q, want %q", c.input, got, c.expected)
		}
	}
}

func TestGetNumber(t *testing.T) {
	tests := []struct {
		rows   []string
		x      int
		output int
	}{
		{rows: []string{"123"}, x: 0, output: 123},
		{rows: []string{"...123"}, x: 5, output: 123},
		{rows: []string{"123..."}, x: 0, output: 123},
		{rows: []string{"...123..."}, x: 0, output: 0},
		{rows: []string{"...123..."}, x: 3, output: 123},
		{rows: []string{"...123..."}, x: 5, output: 123},
		{rows: []string{""}, x: 0, output: 0},
	}

	for _, test := range tests {
		got := getNumber(test.rows, test.x, 0)
		if got != test.output {
			t.Errorf("getNumber(%s, %d) = %d, expected %d", test.rows, test.x, got, test.output)
		}
	}
}

func TestParseRightNumber(t *testing.T) {
	tests := []struct {
		input  string
		output int
	}{
		{input: "123...", output: 123},
		{input: "...123", output: 0},
		{input: "123", output: 123},
		{input: "...123...", output: 0},
		{input: "", output: 0},
	}

	for _, test := range tests {
		got := parseRightNumber(test.input)
		if got != test.output {
			t.Errorf("parseRightNumber(%s) = %d, expected %d", test.input, got, test.output)
		}
	}
}

func TestParseLeftNumber(t *testing.T) {
	tests := []struct {
		input  string
		output int
	}{
		{input: "123...", output: 0},
		{input: "...123", output: 123},
		{input: "123", output: 123},
		{input: "...123...", output: 0},
		{input: "", output: 0},
	}

	for _, test := range tests {
		got := parseLeftNumber(test.input)
		if got != test.output {
			t.Errorf("parseRightNumber(%s) = %d, expected %d", test.input, got, test.output)
		}
	}
}

func TestIsSymbol(t *testing.T) {
	tests := []struct {
		input    []string
		y, x     int
		expected bool
	}{
		{input: []string{"*"}, y: 0, x: 0, expected: true},
		{input: []string{"#"}, y: 0, x: 0, expected: true},
		{input: []string{"+"}, y: 0, x: 0, expected: true},
		{input: []string{"&"}, y: 0, x: 0, expected: true},
		{input: []string{"$"}, y: 0, x: 0, expected: true},
		{input: []string{"/"}, y: 0, x: 0, expected: true},
		{input: []string{"="}, y: 0, x: 0, expected: true},
		{input: []string{"@"}, y: 0, x: 0, expected: true},
		{input: []string{"%"}, y: 0, x: 0, expected: true},
		{input: []string{"-"}, y: 0, x: 0, expected: true},
		{input: []string{"1"}, y: 0, x: 0, expected: false},
		{input: []string{"9"}, y: 0, x: 0, expected: false},
		{input: []string{"0"}, y: 0, x: 0, expected: false},
		{input: []string{"."}, y: 0, x: 0, expected: false},
		{input: []string{"\r"}, y: 0, x: 0, expected: false},
	}

	for _, test := range tests {
		got := isSymbol(test.input, test.y, test.x)
		if got != test.expected {
			t.Errorf("isSymbol(%v, %d, %d) = %t, expected %t", test.input, test.y, test.x, got, test.expected)
		}
	}
}

func TestIsAdjacentSymbol(t *testing.T) {
	tests := []struct {
		rows     []string
		x, y     int
		expected bool
	}{
		{rows: []string{"...", ".*."}, x: 0, y: 0, expected: true},
		{rows: []string{"...", ".*."}, x: 1, y: 0, expected: true},
		{rows: []string{"...", ".*."}, x: 2, y: 0, expected: true},
		{rows: []string{".*."}, x: 0, y: 0, expected: true},
		{rows: []string{".*."}, x: 1, y: 0, expected: false},
		{rows: []string{".*."}, x: 2, y: 0, expected: true},
		{rows: []string{".*.", "..."}, x: 0, y: 1, expected: true},
		{rows: []string{".*.", "..."}, x: 1, y: 1, expected: true},
		{rows: []string{".*.", "..."}, x: 2, y: 1, expected: true},
		{rows: []string{"..", ".*"}, x: 0, y: 0, expected: true},
		{rows: []string{"..", "*."}, x: 1, y: 0, expected: true},
		{rows: []string{"...", "..."}, x: 2, y: 0, expected: false},
		{rows: []string{".\n"}, x: 0, y: 0, expected: false},
		{rows: []string{".\r"}, x: 0, y: 0, expected: false},
	}
	for _, test := range tests {
		got := isAdjacentSymbol(test.x, test.y, test.rows)
		if got != test.expected {
			t.Errorf("isAdjacentSymbol(%d, %d, %v) = %t, expected %t", test.x, test.y, test.rows, got, test.expected)
		}
	}
}

func TestIsBoundary(t *testing.T) {
	tests := []struct {
		input    rune
		expected bool
	}{
		{input: '.', expected: true},
		{input: '#', expected: true},
		{input: '1', expected: false},
		{input: '9', expected: false},
		{input: '0', expected: false},
		{input: ' ', expected: true},
		{input: '\n', expected: true},
		{input: '\r', expected: true},
	}

	for _, test := range tests {
		got := isBoundary(test.input)
		if got != test.expected {
			t.Errorf("isBoundary(%c) = %t, expected %t", test.input, got, test.expected)
		}
	}
}

func BenchmarkPart1(b *testing.B) {
	in, err := readInput("main.txt")
	if err != nil {
		b.Fatal(err)
	}
	for i := 0; i < b.N; i++ {
		part1(in)
	}
}
