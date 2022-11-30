package main

import (
	"testing"
)

func TestPart02(t *testing.T) {
	var cases = []struct {
		name, in string
		want     int64
	}{
		{"basic", "input_test.txt", 0},
	}

	for _, c := range cases {
		t.Run(c.name, func(t *testing.T) {
			in, err := input(c.in)
			if err != nil {
				t.Fatal(err)
			}
			got := part02(in)
			if got != c.want {
				t.Fatalf("file %s, got %d, want %d", c.in, got, c.want)
			}
		})
	}
}

func BenchmarkPart02(b *testing.B) {
	in, err := input("input.txt")
	if err != nil {
		b.Fatal(err)
	}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		part02(in)
	}
}
