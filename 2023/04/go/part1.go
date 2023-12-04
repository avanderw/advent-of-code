/*
*
--- Day 4: Scratchcards ---
As far as the Elf has been able to figure out, you have to figure out which of the numbers you have appear in the list of winning numbers.
The first match makes the card worth one point and each match after the first doubles the point value of that card.

For example:

Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11

In the above example, card 1 has five winning numbers (41, 48, 83, 86, and 17) and eight numbers you have (83, 86, 6, 31, 17, 9, 48, and 53).
Of the numbers you have, four of them (48, 83, 17, and 86) are winning numbers!
That means card 1 is worth 8 points (1 for the first match, then doubled three times for each of the three matches after the first).

Card 2 has two winning numbers (32 and 61), so it is worth 2 points.
Card 3 has two winning numbers (1 and 21), so it is worth 2 points.
Card 4 has one winning number (84), so it is worth 1 point.
Card 5 has no winning numbers, so it is worth no points.
Card 6 has no winning numbers, so it is worth no points.

So, in this example, the Elf's pile of scratchcards is worth 13 points.

Take a seat in the large pile of colorful cards. How many points are they worth in total?
*/
package main

import (
	"strconv"
	"strings"
)

type card struct {
	winningNumbers []int
	yourNumbers    []int
}

func part1(in string) string {
	cards := parseCards(in)

	sum := 0
	for _, c := range cards {
		sum += c.score()
	}

	return strconv.Itoa(sum)
}

func parseCards(in string) []card {
	if in == "" {
		return []card{}
	}
	cards := []card{}
	for _, line := range strings.Split(in, "\n") {
		if line == "" {
			continue
		}

		parts := strings.Split(line, ":")
		sets := strings.Split(parts[1], "|")

		winningNumbers := parseNumbers(sets[0])
		yourNumbers := parseNumbers(sets[1])
		cards = append(cards, card{winningNumbers, yourNumbers})
	}

	return cards
}

func parseNumbers(in string) []int {
	if in == "" {
		return []int{}
	}

	if in[len(in)-1] == '\r' {
		in = in[:len(in)-1]
	}

	// if string contains non numeric characters, return empty slice
	for _, c := range in {
		if c != ' ' && (c < '0' || c > '9') {
			return []int{}
		}
	}

	numbers := []int{}
	for _, n := range strings.Split(in, " ") {
		if n == "" {
			continue
		}
		num, _ := strconv.Atoi(strings.Trim(n, " "))
		numbers = append(numbers, num)
	}

	return numbers
}

// count matches between winning numbers and your numbers
func (c *card) score() int {
	matches := 0
	for _, wn := range c.winningNumbers {
		for _, yn := range c.yourNumbers {
			if wn == yn {
				matches++
			}
		}
	}

	if matches == 0 {
		return 0
	}

	score := 1
	for i := 0; i < matches-1; i++ {
		score *= 2
	}
	return score
}
