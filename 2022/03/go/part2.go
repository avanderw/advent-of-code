package main

import (
	"strconv"
	"strings"
)

func part2(in string) string {
	sum := 0
	one, two, three := map[byte]bool{}, map[byte]bool{}, map[byte]bool{}
	for i, line := range strings.Split(in, "\r\n") {
		switch i % 3 {
		case 0:
			one = convert(line)
		case 1:
			two = convert(line)
		case 2:
			three = convert(line)
		}

		if (i+1)%3 != 0 {
			continue
		}

		inter := one
		inter = intersection(inter, two)
		inter = intersection(inter, three)
		for k := range inter {
			sum += priority(k)
		}
	}

	return strconv.Itoa(sum)
}

func convert(s string) map[byte]bool {
	out := map[byte]bool{}
	for i := 0; i < len(s); i++ {
		out[s[i]] = true
	}
	return out
}

func intersection(a, b map[byte]bool) map[byte]bool {
	if len(a) > len(b) {
		a, b = b, a
	}

	out := map[byte]bool{}
	for k := range a {
		if b[k] {
			out[k] = true
		}
	}
	return out
}
