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
		{"basic", "part1.txt", "0"},
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
