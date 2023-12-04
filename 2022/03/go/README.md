# Advent of Code 2022 - Day 3: Rucksack Reorganization

## Usage

| Command | Description |
|---------|-------------|
| `go run .` | will run the code for the current day. |
| `go test` | will run the tests for the current day. |
| `go test -bench .` | will run the benchmarks for the current day. |
| `go test -bench Part1` | will run the benchmarks for the current day's first part. |
| `go test -bench Part2` | will run the benchmarks for the current day's second part. |

## Performance

```
$ go test -bench .
goos: windows
goarch: amd64
pkg: avanderw.co.za/advent-of-code
cpu: Intel(R) Core(TM) i7-8700 CPU @ 3.20GHz
BenchmarkPart1-12          25843             45745 ns/op
BenchmarkPart2-12           1840            635642 ns/op
PASS
ok      avanderw.co.za/advent-of-code   3.122s
```
