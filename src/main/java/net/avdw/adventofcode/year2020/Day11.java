package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day11.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        List<List<Character>> seats = new ArrayList<>();
        while (scanner.hasNextLine()) {
            List<Character> row = new ArrayList<>();
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                row.add(line.charAt(i));
            }
            seats.add(row);
        }
        print(seats);

        List<List<Character>> prevSeats = seats;
        List<List<Character>> newSeats = iterate02(seats);
        print(newSeats);

        while (!checkSeats(prevSeats, newSeats)) {
            prevSeats = newSeats;
            newSeats = iterate02(newSeats);
            print(newSeats);
        }

        System.out.println();
        System.out.println(count(newSeats));
    }

    private static int count(List<List<Character>> seats) {
        int count = 0;
        for (int i = 0; i < seats.size(); i++) {
            for (int j = 0; j < seats.get(i).size(); j++) {
                count += seats.get(i).get(j) == '#' ? 1 : 0;
            }

        }
        return count;
    }

    private static void print(List<List<Character>> seats) {
        System.out.println();
        seats.forEach(row -> {
            row.forEach(System.out::print);
            System.out.println();
        });
    }

    private static List<List<Character>> iterate01(List<List<Character>> seats) {
        List<List<Character>> newSeats = new ArrayList<>();
        for (int i = 0; i < seats.size(); i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < seats.get(i).size(); j++) {
                if (seats.get(i).get(j) == '.') {
                    row.add('.');
                } else {
                    int adjacent = adjacentRuleset01(seats, i, j);
                    if (adjacent == 0) {
                        row.add('#');
                    } else if (adjacent >= 4) {
                        row.add('L');
                    } else {
                        row.add(seats.get(i).get(j));
                    }
                }
            }
            newSeats.add(row);
        }
        return newSeats;
    }
    private static List<List<Character>> iterate02(List<List<Character>> seats) {
        List<List<Character>> newSeats = new ArrayList<>();
        for (int y = 0; y < seats.size(); y++) {
            List<Character> row = new ArrayList<>();
            for (int x = 0; x < seats.get(y).size(); x++) {
                if (seats.get(y).get(x) == '.') {
                    row.add('.');
                } else {
                    int adjacent = adjacentRuleset02(seats, x, y);
                    if (adjacent == 0) {
                        row.add('#');
                    } else if (adjacent >= 5) {
                        row.add('L');
                    } else {
                        row.add(seats.get(y).get(x));
                    }
                }
            }
            newSeats.add(row);
        }
        return newSeats;
    }

    private static char raycast(List<List<Character>> seats, int startX, int startY, int toX, int toY) {
        int nextX = startX + toX;
        int nextY = startY + toY;
        while (nextY >= 0 && nextX >= 0 && nextY < seats.size() && nextX < seats.get(nextY).size()) {
            if (seats.get(nextY).get(nextX) != '.') {
                return seats.get(nextY).get(nextX);
            }
            nextX += toX;
            nextY += toY;
        }
        return '.';
    }

    private static int adjacentRuleset02(List<List<Character>> seats, int x, int y) {
        int adjacent = 0;
        adjacent += raycast(seats, x, y, -1, -1) == '#' ? 1 : 0;
        adjacent += raycast(seats, x, y, -1, +0) == '#' ? 1 : 0;
        adjacent += raycast(seats, x, y, -1, +1) == '#' ? 1 : 0;
        adjacent += raycast(seats, x, y, +0, -1) == '#' ? 1 : 0;
        adjacent += raycast(seats, x, y, +0, +1) == '#' ? 1 : 0;
        adjacent += raycast(seats, x, y, +1, -1) == '#' ? 1 : 0;
        adjacent += raycast(seats, x, y, +1, +0) == '#' ? 1 : 0;
        adjacent += raycast(seats, x, y, +1, +1) == '#' ? 1 : 0;
        return adjacent;
    }

    private static int adjacentRuleset01(List<List<Character>> seats, int i, int j) {
        int adjacent = 0;
        if (i != 0) {
            if (j != 0) {
                adjacent += seats.get(i - 1).get(j - 1) == '#' ? 1 : 0;
            }
            adjacent += seats.get(i - 1).get(j) == '#' ? 1 : 0;
            if (j != seats.get(i).size() - 1) {
                adjacent += seats.get(i - 1).get(j + 1) == '#' ? 1 : 0;
            }
        }

        if (j != 0) {
            adjacent += seats.get(i).get(j - 1) == '#' ? 1 : 0;
        }
        // seats.get(i).get(j) == '#' ? 1 : 0;
        if (j != seats.get(i).size() - 1) {
            adjacent += seats.get(i).get(j + 1) == '#' ? 1 : 0;
        }

        if (i != seats.size() - 1) {
            if (j != 0) {
                adjacent += seats.get(i + 1).get(j - 1) == '#' ? 1 : 0;
            }
            adjacent += seats.get(i + 1).get(j) == '#' ? 1 : 0;
            if (j != seats.get(i).size() - 1) {
                adjacent += seats.get(i + 1).get(j + 1) == '#' ? 1 : 0;
            }
        }
        return adjacent;
    }

    private static boolean checkSeats(List<List<Character>> seats, List<List<Character>> newSeats) {
        if (newSeats.size() == 0) {
            return false;
        }
        boolean isEqual = true;
        for (int i = 0; i < seats.size(); i++) {
            for (int j = 0; j < seats.get(i).size(); j++) {
                isEqual = isEqual && seats.get(i).get(j).equals(newSeats.get(i).get(j));
            }
        }
        return isEqual;
    }
}
