package main

import (
	"strings"
)

func part2(in string) string {
	state := make([][]byte, 0)
	instructions := make([][]int, 0)
	read := "state"
	for _, line := range strings.Split(in, "\r\n") {
		if len(line) == 0 || line[1] == '1' {
			read = "instruction"
			continue
		}

		switch read {
		case "state":
			state = append(state, readState(line))
		case "instruction":
			instructions = append(instructions, readInstruction(line))
		}

	}

	reverse(state)
	state = pivot(state)
	state = removeEmpty(state)

	for _, instruction := range instructions {
		execute9001(state, instruction)
	}

	out := ""
	for _, row := range state {
		out += string(row[len(row)-1])
	}

	return out
}

func execute9001(state [][]byte, instruction []int) {
	remove := instruction[0]
	from := instruction[1]
	to := instruction[2]

	state[to] = append(state[to], state[from][len(state[from])-remove:]...)
	state[from] = state[from][:len(state[from])-remove]
}
