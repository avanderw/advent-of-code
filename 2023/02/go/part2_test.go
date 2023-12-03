package main

import (
	"testing"
)

func TestPart2(t *testing.T) {
	var cases = []struct {
		input, expected string
	}{
		{"part2.txt", "2286"},
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

func TestPower(t *testing.T) {
	testCases := []struct {
		game     game
		expected int
	}{
		{game: game{id: 1, cubes: []string{"3 blue, 4 red", "1 red, 2 green, 6 blue", "2 green"}}, expected: 48},
		{game: game{2, []string{"1 blue, 2 green", "3 green, 4 blue, 1 red", "1 green, 1 blue"}}, expected: 12},
		{game: game{3, []string{"8 green, 6 blue, 20 red", "5 blue, 4 red, 13 green", "5 green, 1 red"}}, expected: 1560},
		{game: game{4, []string{"1 green, 3 red, 6 blue", "3 green, 6 red", "3 green, 15 blue, 14 red"}}, expected: 630},
		{game: game{5, []string{"6 red, 1 blue, 3 green", "2 blue, 1 red, 2 green"}}, expected: 36},
	}

	for _, testCase := range testCases {
		result := power(testCase.game)
		if result != testCase.expected {
			t.Errorf("Game ID: %d, Expected: %d, Got: %d\n", testCase.game.id, testCase.expected, result)
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
