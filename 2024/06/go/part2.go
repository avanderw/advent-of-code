package main

import (
	"strconv"
	"strings"
	"sync"
)

type State struct {
	pos Position
	dir Direction
}

// Packed grid representation for faster copying
type Grid struct {
	width, height int
	cells         []bool // true = obstacle
}

func NewGrid(width, height int) *Grid {
	return &Grid{
		width:  width,
		height: height,
		cells:  make([]bool, width*height),
	}
}

func (g *Grid) Get(x, y int) bool {
	return g.cells[y*g.width+x]
}

func (g *Grid) Set(x, y int, val bool) {
	g.cells[y*g.width+x] = val
}

func (g *Grid) Copy() *Grid {
	newCells := make([]bool, len(g.cells))
	copy(newCells, g.cells)
	return &Grid{
		width:  g.width,
		height: g.height,
		cells:  newCells,
	}
}

func isInBounds(pos Position, width, height int) bool {
	return pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height
}

// Optimized simulation that uses maps for O(1) lookups
func simulateGuard(grid *Grid, startPos Position, startDir Direction) bool {
	seen := make(map[State]int) // map state to step number
	currentState := State{startPos, startDir}
	step := 0

	for {
		// Check if we've seen this state before
		if _, exists := seen[currentState]; exists {
			// We found a loop
			return true
		}

		// Record current state
		seen[currentState] = step

		// Calculate next position
		nextPos := Position{
			currentState.pos.x + currentState.dir.dx,
			currentState.pos.y + currentState.dir.dy,
		}

		// Check bounds
		if !isInBounds(nextPos, grid.width, grid.height) {
			return false
		}

		// Check for obstacle
		if grid.Get(nextPos.x, nextPos.y) {
			// Turn right
			currentState.dir = Direction{-currentState.dir.dy, currentState.dir.dx}
		} else {
			// Move forward
			currentState.pos = nextPos
		}

		step++
		// Early termination - if we haven't found a loop after visiting all cells multiple times
		if step > grid.width*grid.height*4 {
			return false
		}
	}
}

type job struct {
	x, y int
}

// Worker pool that reuses grid copies
func worker(id int, jobs <-chan job, results chan<- bool, baseGrid *Grid, startPos Position, startDir Direction) {
	// Create a reusable grid copy for this worker
	gridCopy := baseGrid.Copy()

	for j := range jobs {
		// Skip if already obstacle or start position
		if baseGrid.Get(j.x, j.y) || (j.x == startPos.x && j.y == startPos.y) {
			results <- false
			continue
		}

		// Reset grid copy from base grid
		copy(gridCopy.cells, baseGrid.cells)

		// Add new obstacle
		gridCopy.Set(j.x, j.y, true)

		// Check for loop
		hasLoop := simulateGuard(gridCopy, startPos, startDir)
		results <- hasLoop
	}
}

func solvePart2(in string) string {
	lines := strings.Split(strings.TrimSpace(in), "\n")
	height := len(lines)
	width := len(lines[0])

	// Initialize grid and find start position
	grid := NewGrid(width, height)
	var startPos Position
	var startDir Direction

	for y, line := range lines {
		for x, ch := range line {
			if ch == '#' {
				grid.Set(x, y, true)
			} else if ch == '^' {
				startPos = Position{x, y}
				startDir = Direction{0, -1}
			}
		}
	}

	// Use more workers for larger grids
	numWorkers := 16 // increased from 8
	jobs := make(chan job, width*height)
	results := make(chan bool, width*height)

	// Start workers
	var wg sync.WaitGroup
	for w := 0; w < numWorkers; w++ {
		wg.Add(1)
		go func(workerId int) {
			defer wg.Done()
			worker(workerId, jobs, results, grid, startPos, startDir)
		}(w)
	}

	// Send jobs - only for non-obstacle positions
	go func() {
		for y := 0; y < height; y++ {
			for x := 0; x < width; x++ {
				jobs <- job{x, y}
			}
		}
		close(jobs)
	}()

	// Wait for workers to finish in a goroutine
	go func() {
		wg.Wait()
		close(results)
	}()

	// Count valid positions
	validPositions := 0
	for hasLoop := range results {
		if hasLoop {
			validPositions++
		}
	}

	return strconv.Itoa(validPositions)
}
