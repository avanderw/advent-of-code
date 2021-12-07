package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.Scanner;

public class Day02 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day02(), 5000);
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());
        long forward = 0;
        long depth = 0;
        while (scanner.hasNextLine()) {
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "forward":
                    forward += Long.parseLong(command[1]);
                    break;
                case "down":
                    depth += Long.parseLong(command[1]);
                    break;
                case "up":
                    depth -= Long.parseLong(command[1]);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        return "" + (forward * depth);
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());
        long horizontal = 0;
        long aim = 0;
        long depth = 0;
        while (scanner.hasNextLine()) {
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "forward":
                    long x = Long.parseLong(command[1]);
                    horizontal += x;
                    depth += aim * x;
                    break;
                case "down":
                    aim += Long.parseLong(command[1]);
                    break;
                case "up":
                    aim -= Long.parseLong(command[1]);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        return "" + (horizontal * depth);
    }
}
