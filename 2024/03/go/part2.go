package main

import (
	"regexp"
	"sort"
	"strconv"
)

func solvePart2(in string) string {
	// Regular expressions for all instruction types
	mulRe := regexp.MustCompile(`mul\((\d{1,3}),(\d{1,3})\)`)
	doRe := regexp.MustCompile(`do\(\)`)
	dontRe := regexp.MustCompile(`don't\(\)`)

	// Find all indices for each type of instruction
	mulMatches := mulRe.FindAllStringSubmatchIndex(in, -1)
	doMatches := doRe.FindAllStringIndex(in, -1)
	dontMatches := dontRe.FindAllStringIndex(in, -1)

	// Create a sorted list of all control instructions (do/don't)
	type controlInst struct {
		pos       int
		isEnabled bool
	}

	var controls []controlInst
	for _, match := range doMatches {
		controls = append(controls, controlInst{pos: match[0], isEnabled: true})
	}
	for _, match := range dontMatches {
		controls = append(controls, controlInst{pos: match[0], isEnabled: false})
	}

	// Sort controls by position
	sort.Slice(controls, func(i, j int) bool {
		return controls[i].pos < controls[j].pos
	})

	sum := 0
	// For each mul instruction
	for _, match := range mulMatches {
		mulStart := match[0]

		// Find the last control instruction before this mul
		enabled := true // Default state is enabled
		for i := len(controls) - 1; i >= 0; i-- {
			if controls[i].pos < mulStart {
				enabled = controls[i].isEnabled
				break
			}
		}

		if enabled {
			// Extract and convert the numbers
			x, err := strconv.Atoi(in[match[2]:match[3]])
			if err != nil {
				continue
			}

			y, err := strconv.Atoi(in[match[4]:match[5]])
			if err != nil {
				continue
			}

			sum += x * y
		}
	}

	return strconv.Itoa(sum)
}
