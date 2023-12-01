package main

import (
	"testing"
)

func TestPart1(t *testing.T) {
	var cases = []struct {
		input, expected string
	}{
		{"part1.txt", "142"},
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

func TestCalibrationValue(t *testing.T) {
	testCases := []struct {
		input    string
		expected int
	}{
		{input: "1abc2", expected: 12},
		{input: "pqr3stu8vwx", expected: 38},
		{input: "a1b2c3d4e5f", expected: 15},
		{input: "treb7uchet", expected: 77},
	}

	for _, c := range testCases {
		output := calibrationValue(c.input)

		if output != c.expected {
			t.Errorf("For input %s, expected calibration value %d, but got %d", c.input, c.expected, output)
		}
	}
}

func TestFirstDigit(t *testing.T) {
	testCases := []struct {
		input    string
		expected int
	}{
		{input: "1abc2", expected: 1},
		{input: "pqr3stu8vwx", expected: 3},
		{input: "a1b2c3d4e5f", expected: 1},
		{input: "treb7uchet", expected: 7},
	}

	for _, testCase := range testCases {
		output := firstDigit(testCase.input)

		if output != testCase.expected {
			t.Errorf("For input %s, expected first digit %d, but got %d", testCase.input, testCase.expected, output)
		}
	}
}

func TestLastDigit(t *testing.T) {
	testCases := []struct {
		input    string
		expected int
	}{
		{input: "1abc2", expected: 2},
		{input: "pqr3stu8vwx", expected: 8},
		{input: "a1b2c3d4e5f", expected: 5},
		{input: "treb7uchet", expected: 7},
	}

	for _, testCase := range testCases {
		output := lastDigit(testCase.input)

		if output != testCase.expected {
			t.Errorf("For input %s, expected last digit %d, but got %d", testCase.input, testCase.expected, output)
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
