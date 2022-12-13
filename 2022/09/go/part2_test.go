package main

import (
	"testing"
)

func TestPart2(t *testing.T) {
	var cases = []struct {
		name, in string
		want     string
	}{
		{"basic", "input_test.txt", "1"},
		{"basic2", "input_test2.txt", "36"},
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

func TestMoveTail(t *testing.T) {
	var cases = []struct {
		name           string
		tX, tY, hX, hY int
		wantX, wantY   int
	}{
		{"no move", 0, 0, 0, 0, 0, 0},
		{"move up", 0, 0, 0, 2, 0, 1},
		{"move down", 0, 0, 0, -2, 0, -1},
		{"move left", 0, 0, -2, 0, -1, 0},
		{"move right", 0, 0, 2, 0, 1, 0},
		{"move top-right", 0, 0, 2, 2, 1, 1},
		{"move top-left", 0, 0, -2, 2, -1, 1},
		{"move bottom-right", 0, 0, 2, -2, 1, -1},
		{"move bottom-left", 0, 0, -2, -2, -1, -1},
		{"move up-right-funny1", 0, 0, 1, 2, 1, 1},
		{"move up-right-funny2", 0, 0, 2, 1, 1, 1},
		{"move up-left-funny1", 0, 0, -1, 2, -1, 1},
		{"move up-left-funny2", 0, 0, -2, 1, -1, 1},
		{"move down-right-funny1", 0, 0, 1, -2, 1, -1},
		{"move down-right-funny2", 0, 0, 2, -1, 1, -1},
		{"move down-left-funny1", 0, 0, -1, -2, -1, -1},
		{"move down-left-funny2", 0, 0, -2, -1, -1, -1},
		{"specific1", 2, 3, 1, 5, 1, 4},
	}

	for _, c := range cases {
		t.Run(c.name, func(t *testing.T) {
			gotX, gotY := moveTail(c.tX, c.tY, c.hX, c.hY)
			if gotX != c.wantX || gotY != c.wantY {
				t.Fatalf("got %d,%d, want %d,%d", gotX, gotY, c.wantX, c.wantY)
			}
		})
	}
}

func BenchmarkPart2(b *testing.B) {
	in, err := input("input.txt")
	if err != nil {
		b.Fatal(err)
	}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		part2(in)
	}
}
