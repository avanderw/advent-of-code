package main

import (
	"reflect"
	"testing"
)

func TestPart1(t *testing.T) {
	var cases = []struct {
		input, expected string
	}{
		{"part1.txt", "8"},
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

func TestParseGame(t *testing.T) {
	var cases = []struct {
		input    string
		expected game
	}{
		{"", game{}},
		{"Game 1: 3 blue, 4 red; 1 red, 2 green", game{1, []string{"3 blue, 4 red", "1 red, 2 green"}}},
		{"Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue", game{2, []string{"1 blue, 2 green", "3 green, 4 blue, 1 red", "1 green, 1 blue"}}},
		{"Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", game{3, []string{"8 green, 6 blue, 20 red", "5 blue, 4 red, 13 green", "5 green, 1 red"}}},
		{"Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red", game{4, []string{"1 green, 3 red, 6 blue", "3 green, 6 red", "3 green, 15 blue, 14 red"}}},
		{"Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green; 1 red, 1 green", game{5, []string{"6 red, 1 blue, 3 green", "2 blue, 1 red, 2 green", "1 red, 1 green"}}},
		{"Game 80: 2 blue, 11 red, 15 green; 6 blue, 9 red, 19 green; 16 green, 3 red", game{80, []string{"2 blue, 11 red, 15 green", "6 blue, 9 red, 19 green", "16 green, 3 red"}}},
		{"Game 100: 4 blue, 1 green; 13 red, 2 blue; 16 red; 15 red, 2 blue; 9 red, 1 green, 1 blue; 7 red, 4 blue", game{100, []string{"4 blue, 1 green", "13 red, 2 blue", "16 red", "15 red, 2 blue", "9 red, 1 green, 1 blue", "7 red, 4 blue"}}},
	}

	for _, c := range cases {
		got := parseGame(c.input)
		if !reflect.DeepEqual(got, c.expected) {
			t.Errorf("parseGame() = %v, want %v", got, c.expected)
		}
	}
}

func TestIsValid(t *testing.T) {
	testCases := []struct {
		game     game
		expected bool
	}{
		{game: game{id: 1, cubes: []string{"3 blue, 4 red", "1 red, 2 green, 6 blue", "2 green"}}, expected: true},
		{game: game{2, []string{"1 blue, 2 green", "3 green, 4 blue, 1 red", "1 green, 1 blue"}}, expected: true},
		{game: game{3, []string{"8 green, 6 blue, 20 red", "5 blue, 4 red, 13 green", "5 green, 1 red"}}, expected: false},
		{game: game{4, []string{"1 green, 3 red, 6 blue", "3 green, 6 red", "3 green, 15 blue, 14 red"}}, expected: false},
		{game: game{5, []string{"6 red, 1 blue, 3 green", "2 blue, 1 red, 2 green", "1 red, 1 green"}}, expected: true},
		{game: game{80, []string{"2 blue, 11 red, 15 green", "6 blue, 9 red, 19 green", "16 green, 3 red"}}, expected: false},
		{game: game{100, []string{"4 blue, 1 green", "13 red, 2 blue", "16 red", "15 red, 2 blue", "9 red, 1 green, 1 blue", "7 red, 4 blue"}}, expected: false},
	}

	for _, c := range testCases {
		result := isValid(c.game, 12, 13, 14)
		if result != c.expected {
			t.Errorf("Game ID: %d, Expected: %t, Got: %t\n", c.game.id, c.expected, result)
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
