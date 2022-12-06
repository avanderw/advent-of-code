package main

import "strconv"

func part2(in string) string {
	return strconv.Itoa(indexFirstUniqueGroup(in, 14) + 14)
}
