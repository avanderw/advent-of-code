package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day04 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day04(), 5000);
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());
        String[] numbers = scanner.nextLine().split(",");

        List<Board> boards = new ArrayList<>();
        Board board = null;
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                board = new Board();
                boards.add(board);
                row = 0;
                continue;
            }

            for (int col = 0; col < 5; col++) {
                int idx = col * 3;
                board.set(row, col, Long.parseLong(line.substring(idx, idx+2).trim()));
            }
            row++;
        }
        board = null;

        long last = -1;
        Board complete = null;
        for (int i = 0; i < numbers.length; i++ ) {
            long number = Long.parseLong(numbers[i]);
            for (int j = 0; j < boards.size(); j++) {
                board = boards.get(j);
                board.find(number);
                if (board.test()) {
                    complete = board;
                    last = number;
                    break;
                }
            }
            if (complete != null) {
                break;
            }
        }

        if (complete == null) {
            return "failure";
        }

        return "" + (board.sumRemaining() * last);
    }

    static class Board {
        boolean won = false;
        long[][] numbers = new long[5][5];
        boolean[][] matched = new boolean[5][5];

        Board() {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    numbers[y][x] = -1;
                    matched[y][x] = false;
                }
            }
        }

        public void set(int row, int col, long number) {
            numbers[row][col] = number;
        }

        public void find(long number) {
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (numbers[row][col] == number) {
                        matched[row][col] = true;
                    }
                }
            }
        }

        public boolean test() {
            for (int row = 0; row < 5; row++) {
                boolean winRow = true;
                boolean winCol = true;
                for (int col = 0; col < 5; col++) {
                    winRow &= matched[row][col];
                    winCol &= matched[col][row];
                }
                if (winRow || winCol) {
                    won = true;
                    return true;
                }
            }
            return false;
        }

        public long sumRemaining() {
            long sum = 0;
            for (int row = 0; row < 5; row ++) {
                for (int col = 0; col < 5; col++) {
                    if (!matched[row][col]) {
                        sum += numbers[row][col];
                    }
                }
            }
            return sum;
        }

        public void print() {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    System.out.printf("%3d", numbers[i][j]);
                }
                System.out.println();
            }

            System.out.println();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    System.out.printf("%6s", matched[i][j]);
                }
                System.out.println();
            }
        }
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());
        String[] numbers = scanner.nextLine().split(",");

        List<Board> boards = new ArrayList<>();
        Board board = null;
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                board = new Board();
                boards.add(board);
                row = 0;
                continue;
            }

            for (int col = 0; col < 5; col++) {
                int idx = col * 3;
                board.set(row, col, Long.parseLong(line.substring(idx, idx+2).trim()));
            }
            row++;
        }
        board = null;

        long last = -1;
        Board complete = null;
        for (int i = 0; i < numbers.length; i++ ) {
            long number = Long.parseLong(numbers[i]);
            for (int j = 0; j < boards.size(); j++) {
                board = boards.get(j);
                board.find(number);
                if (board.test()) {
                    if (boards.stream().allMatch(b->b.won)) {
                        complete = board;
                        last = number;
                        break;
                    }
                }
            }
            if (complete != null) {
                break;
            }
        }

        if (complete == null) {
            return "failure";
        }

        return "" + (board.sumRemaining() * last);
    }

}
