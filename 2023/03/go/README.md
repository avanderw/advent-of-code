# Advent of code 2023 - Day 3: Gear Ratios

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
$ go test --bench .
goos: windows
goarch: amd64
pkg: github.com/avanderw/advent-of-code/2023/02
cpu: Intel(R) Core(TM) i7-8700 CPU @ 3.20GHz
BenchmarkPart1-12          10000            118251 ns/op
BenchmarkPart2-12           9999            117678 ns/op
PASS
ok      github.com/avanderw/advent-of-code/2023/02      2.529s
```

## Learnings

- Should have searched for numbers instead of symbols in the input file.
- Searching for symbols means you have to backtrack and deal with duplicates.
- Bard remains the best in producing test code.
- Bard, GPT-3.5 and Claude all search for symbols first.
- Golangs testing facilities are definitely make it easy to fix bugs.
- How am I still mixing the order of x and y in the same codebase?
  - I must standardise on one or the other.
  - I tend to talk about x, y coords, but I tend to code y, x coords.
  - I think I should standardise on y, x coords.
- Parsing the input was a pain when I was searching for symbols.
- I wonder how difficult it would be to get the chat programs to write everything.
  - I have yet to massage the prompts to get what I want.
