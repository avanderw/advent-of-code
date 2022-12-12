package main

import (
	"strconv"
	"strings"
)

type path struct {
	name string
	size int
}

func part1(in string) string {
	dirSizeMap := readInput(in)
	updateSizes(&dirSizeMap)

	sum := 0
	for dir := range dirSizeMap {
		size := dirSize(&dirSizeMap, dir)
		if size < 100000 {
			sum += size
		}
	}

	return strconv.Itoa(sum)
}

func updateSizes(dirSizeMap *map[string][]path) {
	for dir, paths := range *dirSizeMap {
		if len(paths) == 0 {
			continue
		}

		for i, path := range paths {
			if path.size >= 0 {
				continue
			}

			paths[i].size = dirSize(dirSizeMap, dir+"/"+strings.Split(path.name, " ")[1])
		}
	}
}

func dirSize(dirSizeMap *map[string][]path, dir string) int {
	size := 0
	for _, path := range (*dirSizeMap)[dir] {
		if path.size >= 0 {
			size += path.size
		} else {
			size += dirSize(dirSizeMap, dir+"/"+strings.Split(path.name, " ")[1])
		}
	}
	return size
}

func readInput(in string) map[string][]path {
	dirSizeMap := make(map[string][]path)
	dirStack := []string{}
	for _, line := range strings.Split(in, "\r\n") {
		if strings.Contains(line, "$ cd") {
			changeDir(strings.Split(line, "$ cd")[1], &dirStack)
			continue
		}

		if strings.Contains(line, "$ ls") {
			continue
		}

		currDir := getPath(dirStack)
		if strings.Index(line, "dir ") == 0 {
			dirSizeMap[currDir] = append(dirSizeMap[currDir], path{name: line, size: -1})
			continue
		}

		size, name := parseFile(line)
		dirSizeMap[currDir] = append(dirSizeMap[currDir], path{name: name, size: size})

	}
	return dirSizeMap
}

func changeDir(dir string, dirStack *[]string) {
	dir = strings.Trim(dir, " ")
	if dir == ".." {
		*dirStack = (*dirStack)[:len(*dirStack)-1]
	} else if dir == "/" {
		*dirStack = []string{""}
	} else {
		*dirStack = append(*dirStack, dir)
	}
}

func getPath(dirStack []string) string {
	return strings.Join(dirStack, "/")
}

func parseFile(line string) (int, string) {
	split := strings.Split(line, " ")
	size, _ := strconv.Atoi(split[0])
	return size, split[1]
}
