# Advent of code 2023 - Day 1: Trebuchet?!

## Usage

| Command | Description |
|---------|-------------|
| `go run .` | will run the code for the current day. |
| `go test` | will run the tests for the current day. |
| `go test -bench .` | will run the benchmarks for the current day. |
| `go test -bench Part1` | will run the benchmarks for the current day's first part. |
| `go test -bench Part2` | will run the benchmarks for the current day's second part. |

## Benchmarks

```bash
$ go test -bench .
goos: windows
goarch: amd64
pkg: github.com/avanderw/advent-of-code/2023/01
cpu: Intel(R) Core(TM) i7-8700 CPU @ 3.20GHz
BenchmarkPart1-12          22587             53493 ns/op
BenchmarkPart2-12          22635             53069 ns/op
PASS
ok      github.com/avanderw/advent-of-code/2023/01      3.625s
```

## Learnings

- Github co-pilot, Bard, Claude and ChatGPT 3.5 cannot easily write advent of code solutions for you.
- Github co-pilot is very good at completing your code, but not so good at writing it from scratch.
- Bard is very good at writing code from scratch with proper context.
- Claude is okay at identifying what is wrong, but not correcting it.
- ChatGPT 3.5 is okay at writing code from scratch, though misses a lot of context.
- All of the chat generation programs were very good at writing test cases.
  - Bard kept to script
  - Both Claude and ChatGPT 3.5 wrote test cases that were not in the script.
- The biggest time saver remains Github co-pilot due to the code completion
- I will need to get better at prompt engineering to get better results from the chat generation programs.
