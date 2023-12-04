package main

import "strconv"

func part2(in string) string {
	cards := parseCards(in)
	copies := make(map[int]int, len(cards))

	totalCards := 0
	// process cards
	for i, c := range cards {
		totalCards++
		copy := c.matches()
		for j := 0; j < copy; j++ {
			copies[i+1+j]++
		}
	}

	// process copies
	for i := 0; i < len(cards); i++ {
		if copies[i] == 0 {
			continue
		}
		totalCards += copies[i]
		copy := cards[i].matches()
		for j := 0; j < copy; j++ {
			copies[i+1+j] += copies[i]
		}
	}

	return strconv.Itoa(totalCards)
}

func (c *card) matches() int {
	matches := 0
	for _, n := range c.yourNumbers {
		for _, w := range c.winningNumbers {
			if n == w {
				matches++
			}
		}
	}
	return matches
}
