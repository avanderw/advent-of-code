package main

import (
	"testing"
)

func BenchmarkPart1(b *testing.B) {
	in, err := input("main.txt")
	if err != nil {
		b.Fatal(err)
	}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		part1(in)
	}
}

func TestPart1(t *testing.T) {
	var cases = []struct {
		name, in string
		want     string
	}{
		{"basic", "part1.txt", "13"},
	}

	for _, c := range cases {
		t.Run(c.name, func(t *testing.T) {
			in, err := input(c.in)
			if err != nil {
				t.Fatal(err)
			}
			got := part1(in)
			if got != c.want {
				t.Fatalf("file %s, got %s, want %s", c.in, got, c.want)
			}
		})
	}
}

func TestParseNumbers(t *testing.T) {
	testCases := []struct {
		input    string
		expected []int
	}{
		{input: "", expected: []int{}},
		{input: "123", expected: []int{123}},
		{input: "1 2 3 4 5", expected: []int{1, 2, 3, 4, 5}},
		{input: "1 2 3 4 5\r", expected: []int{1, 2, 3, 4, 5}},
		{input: "   123  5  \r", expected: []int{123, 5}},
		{input: "abc", expected: []int{}},
	}

	for _, testCase := range testCases {
		t.Run(testCase.input, func(t *testing.T) {
			numbers := parseNumbers(testCase.input)
			if len(numbers) != len(testCase.expected) {
				t.Errorf("given input %s the length of the numbers should be %d, got %d \n", testCase.input, len(testCase.expected), len(numbers))
			}

			for i, expectedNumber := range testCase.expected {
				if numbers[i] != expectedNumber {
					t.Errorf("given input %s the number at index %d should be %d, got %d \n", testCase.input, i, expectedNumber, numbers[i])
				}
			}
		})
	}
}

func TestParseCards(t *testing.T) {
	input := `Card 1: 41 48 83 86 17 | 83 86 6 31 17 9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3: 1 21 53 59 44 | 69 82 63 72 16 21 14 1
Card 4: 41 92 73 84 69 | 59 84 76 51 58 5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11`

	expected := []card{{
		winningNumbers: []int{41, 48, 83, 86, 17},
		yourNumbers:    []int{83, 86, 6, 31, 17, 9, 48, 53},
	}, {
		winningNumbers: []int{13, 32, 20, 16, 61},
		yourNumbers:    []int{61, 30, 68, 82, 17, 32, 24, 19},
	}, {
		winningNumbers: []int{1, 21, 53, 59, 44},
		yourNumbers:    []int{69, 82, 63, 72, 16, 21, 14, 1},
	}, {
		winningNumbers: []int{41, 92, 73, 84, 69},
		yourNumbers:    []int{59, 84, 76, 51, 58, 5, 54, 83},
	}, {
		winningNumbers: []int{87, 83, 26, 28, 32},
		yourNumbers:    []int{88, 30, 70, 12, 93, 22, 82, 36},
	}, {
		winningNumbers: []int{31, 18, 13, 56, 72},
		yourNumbers:    []int{74, 77, 10, 23, 35, 67, 36, 11},
	}}

	cards := parseCards(input)
	if len(cards) != len(expected) {
		t.Errorf("parseCards(%s) should return %d cards, got %d", input, len(expected), len(cards))
	}

	for i, card := range cards {
		if len(card.winningNumbers) != len(expected[i].winningNumbers) {
			t.Errorf("parseCards(%s) should return %d winning numbers for card %d, got %d", input, len(expected[i].winningNumbers), i, len(card.winningNumbers))
		}

		if len(card.yourNumbers) != len(expected[i].yourNumbers) {
			t.Errorf("parseCards(%s) should return %d your numbers for card %d, got %d", input, len(expected[i].yourNumbers), i, len(card.yourNumbers))
		}

	}
}

func TestScore(t *testing.T) {
	cases := []struct {
		winningNumbers []int
		yourNumbers    []int
		expectedScore  int
	}{
		{
			winningNumbers: []int{41, 48, 83, 86, 17},
			yourNumbers:    []int{83, 86, 6, 31, 17, 9, 48, 53},
			expectedScore:  8,
		},
		{
			winningNumbers: []int{32, 61},
			yourNumbers:    []int{32, 61, 1, 21},
			expectedScore:  2,
		},
		{
			winningNumbers: []int{1, 21},
			yourNumbers:    []int{32, 61, 1, 21},
			expectedScore:  2,
		},
		{
			winningNumbers: []int{84},
			yourNumbers:    []int{84, 1, 21},
			expectedScore:  1,
		},
		{
			winningNumbers: []int{},
			yourNumbers:    []int{32, 61, 1, 21},
			expectedScore:  0,
		},
		{
			winningNumbers: []int{},
			yourNumbers:    []int{84, 1, 21},
			expectedScore:  0,
		},
	}

	for _, c := range cases {
		card := &card{winningNumbers: c.winningNumbers, yourNumbers: c.yourNumbers}
		actualScore := card.score()
		if actualScore != c.expectedScore {
			t.Errorf("Expected score for card %v to be %d, but got %d", card, c.expectedScore, actualScore)
		}
	}
}
