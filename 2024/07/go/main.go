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
	input, err := readInput("main.txt")
	if err != nil {
		return err
	}
	fmt.Printf("Part 1: %s\n", solvePart1(input))
	fmt.Printf("Part 2: %s\n", solvePart2(input))
	return nil
}

func readInput(path string) (string, error) {
	buf, err := os.ReadFile(path)
	if err != nil {
		return "", err
	}
	return string(buf), nil
}
