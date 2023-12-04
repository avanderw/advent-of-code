package main

import (
	"testing"
)

func BenchmarkPart2(b *testing.B) {
	in, err := input("main.txt")
	if err != nil {
		b.Fatal(err)
	}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		part2(in)
	}
}

func TestPart2(t *testing.T) {
	var cases = []struct {
		name, in string
		want     string
	}{
		{"basic", "part2.txt", "30"},
	}

	for _, c := range cases {
		t.Run(c.name, func(t *testing.T) {
			in, err := input(c.in)
			if err != nil {
				t.Fatal(err)
			}
			got := part2(in)
			if got != c.want {
				t.Fatalf("file %s, got %s, want %s", c.in, got, c.want)
			}
		})
	}
}
