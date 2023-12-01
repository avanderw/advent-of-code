package main

import (
	"testing"
)

func TestPart2(t *testing.T) {
	var cases = []struct {
		input, expected string
	}{
		{"part2.txt", "281"},
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

func TestCalibrationValueWithWords(t *testing.T) {
	testCases := []struct {
		input    string
		expected int
	}{
		{input: "two1nine", expected: 29},
		{input: "eightwothree", expected: 83},
		{input: "abcone2threexyz", expected: 13},
		{input: "xtwone3four", expected: 24},
		{input: "4nineeightseven2", expected: 42},
		{input: "zoneight234", expected: 14},
		{input: "7pqrstsixteen", expected: 76},
	}

	for _, testCase := range testCases {
		output := calibrationValueWithWords(testCase.input)

		if output != testCase.expected {
			t.Errorf("For input %s, expected calibration value %d, but got %d", testCase.input, testCase.expected, output)
		}
	}
}

func TestFirstDigitWithWords(t *testing.T) {
	testCases := []struct {
		input    string
		expected int
	}{
		{input: "two1nine", expected: 2},
		{input: "eightwothree", expected: 8},
		{input: "abcone2threexyz", expected: 1},
		{input: "xtwone3four", expected: 2},
		{input: "4nineeightseven2", expected: 4},
		{input: "zoneight234", expected: 1},
		{input: "7pqrstsixteen", expected: 7},
	}

	for _, testCase := range testCases {
		output := firstDigitWithWords(testCase.input)

		if output != testCase.expected {
			t.Errorf("For input %s, expected first digit %d, but got %d", testCase.input, testCase.expected, output)
		}
	}
}

func TestLastDigitWithWords(t *testing.T) {
	testCases := []struct {
		input    string
		expected int
	}{
		{input: "two1nine", expected: 9},
		{input: "eightwothree", expected: 3},
		{input: "abcone2threexyz", expected: 3},
		{input: "xtwone3four", expected: 4},
		{input: "4nineeightseven2", expected: 2},
		{input: "zoneight234", expected: 4},
		{input: "7pqrstsixteen", expected: 6},
	}

	for _, c := range testCases {
		output := lastDigitWithWords(c.input)

		if output != c.expected {
			t.Errorf("For input %s, expected last digit %d, but got %d", c.input, c.expected, output)
		}
	}
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
