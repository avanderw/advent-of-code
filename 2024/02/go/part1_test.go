package main

import (
	"testing"
)

func BenchmarkPart1(b *testing.B) {
	input, err := readInput("main.txt")
	if err != nil {
		b.Fatal(err)
	}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		solvePart1(input)
	}
}

func TestPart1(t *testing.T) {
	var cases = []struct {
		name, input string
		want        string
	}{
		{"basic", "part1.txt", "2"},
	}

	for _, c := range cases {
		t.Run(c.name, func(t *testing.T) {
			in, err := readInput(c.input)
			if err != nil {
				t.Fatal(err)
			}
			got := solvePart1(in)
			if got != c.want {
				t.Fatalf("file %s, got %s, want %s", c.input, got, c.want)
			}
		})
	}
}
