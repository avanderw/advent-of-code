package main

import (
	"strconv"
	"strings"
)

func part1(in string) string {
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
		execute9000(state, instruction)
	}

	out := ""
	for _, row := range state {
		out += string(row[len(row)-1])
	}

	return out
}

func reverse(s [][]byte) {
	for i, j := 0, len(s)-1; i < j; i, j = i+1, j-1 {
		s[i], s[j] = s[j], s[i]
	}
}

func pivot(s [][]byte) [][]byte {
	p := make([][]byte, len(s[0]))
	for i := range p {
		p[i] = make([]byte, len(s))
	}

	for i := range s {
		for j := range s[i] {
			p[j][i] = s[i][j]
		}
	}

	return p
}

func removeEmpty(s [][]byte) [][]byte {
	for i := range s {
		for j := range s[i] {
			if s[i][j] == ' ' {
				s[i] = s[i][:j]
				break
			}
		}
	}
	return s
}

func readState(line string) []byte {
	crates := make([]byte, 0)
	i := 1
	stack := 0
	for i < len(line) {
		crates = append(crates, line[i])
		i += 4
		stack++
	}

	return crates
}

func readInstruction(line string) []int {
	split := strings.Split(line, " ")
	instruction := make([]int, 3)
	instruction[0], _ = strconv.Atoi(split[1])
	instruction[1], _ = strconv.Atoi(split[3])
	instruction[2], _ = strconv.Atoi(split[5])

	instruction[1]--
	instruction[2]--

	return instruction
}

func execute9000(state [][]byte, instruction []int) {
	remove := instruction[0]
	from := instruction[1]
	to := instruction[2]

	for i := 0; i < remove; i++ {
		state[to] = append(state[to], state[from][len(state[from])-1])
		state[from] = state[from][:len(state[from])-1]
	}
}
