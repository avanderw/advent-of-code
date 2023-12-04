# Advent of Code 2023 - Day 4: Scratchcards

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
pkg: avanderw.co.za/advent-of-code
cpu: Intel(R) Core(TM) i7-8700 CPU @ 3.20GHz
BenchmarkPart1-12           3163            385960 ns/op
BenchmarkPart2-12           2583            471976 ns/op
PASS
ok      avanderw.co.za/advent-of-code   2.668s
```

## Learnings

- I had a better grip on the problem writing the overall solution myself.
  - I got to follow my train of thought instead of trying to understand someone else's.
  - It also meant that I could architect and craft to my preferences.
  - Keeping to my style helped a lot with the speed of development.
- Providing bard with a function and the text examples meant I could quickly test my solution.
- When I wanted structure to my tests, I would aid bard with the struct I used in a follow up prompt.
- Bard's tests helped to catch edge cases I had not considered.
- I assume because Github Co-pilot keeps to your style it is easier to read and understand.
- AI pair programming helps a lot, but it should be used when you get stuck or need a second opinion.
- Using AI chatbots for the full solution is more of a black box approach to coding.
- Really looking forward to Github Co-pilot commandline integration.
