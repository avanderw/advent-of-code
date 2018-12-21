package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Day18 {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        String test = ".#.#...|#.\n" +
                ".....#|##|\n" +
                ".|..|...#.\n" +
                "..|#.....#\n" +
                "#.#|||#|#|\n" +
                "...#.||...\n" +
                ".|....|...\n" +
                "||...#|.#|\n" +
                "|.||||..|.\n" +
                "...#.|..|.";
        URL inputUrl = Day18.class.getResource("day18-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));
//        Scanner scanner = new Scanner(test);

        int height = 50;
        int width = 50;
        Character[][] map = new Character[height][width];
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                map[y][x] = line.charAt(x);
            }
            y++;
        }

        List<Integer> calculations = new ArrayList<>();
        int generations = 1_000;
        for (int g = 0; g < generations; g++) {
            int count1 = 0;
            int count2 = 0;
            for (y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (map[y][x] == '|') {
                        count1++;
                    } else if (map[y][x] == '#') {
                        count2++;
                    }
                }
            }
            calculations.add(count1 * count2);

            Character[][] iteration = new Character[height][width];
            for (y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int trees = 0;
                    int lumberyard = 0;

                    if (y - 1 >= 0 && x - 1 >= 0) {
                        trees += map[y - 1][x - 1] == '|' ? 1 : 0;
                        lumberyard += map[y - 1][x - 1] == '#' ? 1 : 0;
                    }
                    if (y - 1 >= 0 && x - 0 >= 0) {
                        trees += map[y - 1][x - 0] == '|' ? 1 : 0;
                        lumberyard += map[y - 1][x - 0] == '#' ? 1 : 0;
                    }
                    if (y - 1 >= 0 && x + 1 < width) {
                        trees += map[y - 1][x + 1] == '|' ? 1 : 0;
                        lumberyard += map[y - 1][x + 1] == '#' ? 1 : 0;
                    }
                    if (x - 1 >= 0) {
                        trees += map[y - 0][x - 1] == '|' ? 1 : 0;
                        lumberyard += map[y - 0][x - 1] == '#' ? 1 : 0;
                    }
                    if (x + 1 < width) {
                        trees += map[y - 0][x + 1] == '|' ? 1 : 0;
                        lumberyard += map[y - 0][x + 1] == '#' ? 1 : 0;
                    }
                    if (y + 1 < height && x - 1 >= 0) {
                        trees += map[y + 1][x - 1] == '|' ? 1 : 0;
                        lumberyard += map[y + 1][x - 1] == '#' ? 1 : 0;
                    }
                    if (y + 1 < height && x - 0 >= 0) {
                        trees += map[y + 1][x - 0] == '|' ? 1 : 0;
                        lumberyard += map[y + 1][x - 0] == '#' ? 1 : 0;
                    }
                    if (y + 1 < height && x + 1 < width) {
                        trees += map[y + 1][x + 1] == '|' ? 1 : 0;
                        lumberyard += map[y + 1][x + 1] == '#' ? 1 : 0;
                    }

                    if (map[y][x] == '.') {
                        iteration[y][x] = (trees >= 3) ? '|' : '.';
                    } else if (map[y][x] == '|') {
                        iteration[y][x] = (lumberyard >= 3) ? '#' : '|';
                    } else if (map[y][x] == '#') {
                        iteration[y][x] = (trees > 0 && lumberyard > 0) ? '#' : '.';
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
            }
            map = iteration;
        }

        print(height, width, map);

        Map<Integer, Integer> repeat = new HashMap<>();
        for (int i = 0; i < calculations.size(); i++) {
            for (int j = i + 1; j < calculations.size(); j++) {
                if (calculations.get(i).equals(calculations.get(j))) {
//                    System.out.println(String.format("%s: next found %s = %s (%s)", i, j - i, calculations.get(i), (i - 465) % 28));
                    repeat.put((i - 465) % 28, calculations.get(i));
                    break;
                }
            }
        }
        System.out.println(String.format("repetition=28, start=465, 1000000000 is offset by %s = %s", (1000000000 - 465) % 28, repeat.get((1000000000 - 465) % 28)));
    }

    private static void print(int height, int width, Character[][] map) {
        int y;
        int count1 = 0;
        int count2 = 0;
        for (y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(map[y][x]);
                if (map[y][x] == '|') {
                    count1++;
                } else if (map[y][x] == '#') {
                    count2++;
                }
            }
            System.out.println();
        }
        System.out.println(String.format("%s * %s = %s", count1, count2, count1 * count2));
    }
}
