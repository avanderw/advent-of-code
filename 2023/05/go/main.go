package main

import (
	"fmt"
	"os"
)

func main() {
	if err := run(); err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(1)
	}
}

func run() error {
	in, err := input("main.txt")
	if err != nil {
		return err
	}
	fmt.Printf("Part 1: %s\n", part1(in))
	fmt.Printf("Part 2: %s\n", part2(in))
	return nil
}

func input(path string) (string, error) {
	buf, err := os.ReadFile(path)
	if err != nil {
		return "", err
	}
	return string(buf), nil
}
