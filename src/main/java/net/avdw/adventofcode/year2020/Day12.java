package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day12 {
    public static void main(String[] args) throws FileNotFoundException {
        Map<Character, Character> left = new HashMap<>();
        left.put('N', 'W');
        left.put('W', 'S');
        left.put('S', 'E');
        left.put('E', 'N');
        Map<Character, Character> right = new HashMap<>();
        right.put('N', 'E');
        right.put('E', 'S');
        right.put('S', 'W');
        right.put('W', 'N');

        URL inputUrl = Year2020.class.getResource("day12.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        int waypointY = 1;
        int waypointX = 10;
        int y = 0;
        int x = 0;
        char dir = 'E';
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char direction = line.charAt(0);
            int units = Integer.parseInt(line.substring(1));
            System.out.printf("waypoint %s by %3s units to ", direction, units);
            switch (direction) {
                case 'N':
                    waypointY += units;
                    break;
                case 'S':
                    waypointY -= units;
                    break;
                case 'E':
                    waypointX += units;
                    break;
                case 'W':
                    waypointX -= units;
                    break;
                case 'L':
                    for (int i = 0; i < units / 90; i++) {
                        int newX = -waypointY;
                        int newY = waypointX;
                        waypointY = newY;
                        waypointX = newX;
//                        dir = left.get(dir);
                    }
                    break;
                case 'R':
                    for (int i = 0; i < units / 90; i++) {
                        int newX = waypointY;
                        int newY = -waypointX;
                        waypointY = newY;
                        waypointX = newX;
//                        dir = right.get(dir);
                    }
                    break;
                case 'F':
                    y += waypointY * units;
                    x += waypointX * units;
//                    switch (dir) {
//                        case 'N':
//                            waypointY += units;
//                            break;
//                        case 'S':
//                            waypointY -= units;
//                            break;
//                        case 'E':
//                            waypointX += units;
//                            break;
//                        case 'W':
//                            waypointX -= units;
//                            break;
//                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            System.out.printf("%5s, %-5s [%8s, %-8s]%n", waypointX, waypointY, x, y);
        }
        System.out.printf("distance = %s%n", Math.abs(x) + Math.abs(y));
    }
}
