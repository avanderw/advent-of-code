package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Scanner;

public class Day03 {
    static long encounter(char[][] map, int xDelta, int yDelta) {
        int x = 0;
        int count = 0;
        for (int y = yDelta; y < map.length; y += yDelta) {
            x += xDelta;
            x %= map[y].length;
            if (map[y][x] == '#') {
                count++;
            }
//            System.out.printf("%s, %s = %s%n", x, y, map[y][x]);
        }
        System.out.printf("x%s, y%s = %s%n", xDelta, yDelta, count);
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day03.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

//        char[][] map = new char[11][11];
        char[][] map = new char[323][31];
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                map[y][x] = line.charAt(x);
            }
            y++;
        }

        BigInteger t1 = BigInteger.valueOf(encounter(map, 1, 1));
        BigInteger t2 = BigInteger.valueOf(encounter(map, 3, 1));
        BigInteger t3 = BigInteger.valueOf(encounter(map, 5, 1));
        BigInteger t4 = BigInteger.valueOf(encounter(map, 7, 1));
        BigInteger t5 = BigInteger.valueOf(encounter(map, 1, 2));
        System.out.println(t1.multiply(t2).multiply(t3).multiply(t4).multiply(t5));
    }
}
