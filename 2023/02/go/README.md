# Advent of code 2023 - Day 2: Cube Conundrum

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
pkg: github.com/avanderw/advent-of-code/2023/02
cpu: Intel(R) Core(TM) i7-8700 CPU @ 3.20GHz
BenchmarkPart1-12           6936            164651 ns/op
BenchmarkPart2-12           7610            163332 ns/op
PASS
ok      github.com/avanderw/advent-of-code/2023/02      2.565s
```

## Learnings

- Largest challenge remained parsing the file and remembering to trim spaces.
- The chatbots made things longer in this case.
  - The logic around what was in the bag messed with their output.
  - They assumed that the bad would not refill.
- I should stick to using the chatbots to help with function creation.
- Github co-pilot remains better at creating functions.
- Bard remains good at building test cases.
- I should start testing the bots as a soundboard instead of a code generator.
