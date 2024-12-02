package main

import (
	"testing"
)

func BenchmarkPart2(b *testing.B) {
	input, err := readInput("main.txt")
	if err != nil {
		b.Fatal(err)
	}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		solvePart2(input)
	}
}

func TestPart2(t *testing.T) {
	var cases = []struct {
		name, in string
		want     string
	}{
		{"basic", "part2.txt", "4"},
	}

	for _, c := range cases {
		t.Run(c.name, func(t *testing.T) {
			input, err := readInput(c.in)
			if err != nil {
				t.Fatal(err)
			}
			got := solvePart2(input)
			if got != c.want {
				t.Fatalf("file %s, got %s, want %s", c.in, got, c.want)
			}
		})
	}
}
