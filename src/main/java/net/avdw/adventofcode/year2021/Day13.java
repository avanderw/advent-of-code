package net.avdw.adventofcode.year2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day13 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day13(), 5000);
    }

    @Data
    @AllArgsConstructor
    static class Dot {
        int x;
        int y;
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        Set<Dot> dots = new HashSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                continue;
            }
            if (line.startsWith("fold")) {
                int eq = line.lastIndexOf("=");
                boolean xFold = line.charAt(eq - 1) == 'x';
                int value = Integer.parseInt(line.substring(eq + 1));
                if (xFold) {
                    Set<Dot> fold = dots.stream().filter(dot -> dot.x > value).collect(Collectors.toSet());
                    dots.removeAll(fold);
                    fold.forEach(dot -> dots.add(new Dot(value - (dot.x - value), dot.y)));
                } else {
                    Set<Dot> fold = dots.stream().filter(dot -> dot.y > value).collect(Collectors.toSet());
                    dots.removeAll(fold);
                    fold.forEach(dot -> dots.add(new Dot(dot.x, value - (dot.y - value))));
                }
                break;
            } else {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                dots.add(new Dot(x, y));
            }
        }

        return ""+dots.size();
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        Set<Dot> dots = new HashSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                continue;
            }
            if (line.startsWith("fold")) {
                int eq = line.lastIndexOf("=");
                boolean xFold = line.charAt(eq - 1) == 'x';
                int value = Integer.parseInt(line.substring(eq + 1));
                if (xFold) {
                    Set<Dot> fold = dots.stream().filter(dot -> dot.x > value).collect(Collectors.toSet());
                    dots.removeAll(fold);
                    fold.forEach(dot -> dots.add(new Dot(value - (dot.x - value), dot.y)));
                } else {
                    Set<Dot> fold = dots.stream().filter(dot -> dot.y > value).collect(Collectors.toSet());
                    dots.removeAll(fold);
                    fold.forEach(dot -> dots.add(new Dot(dot.x, value - (dot.y - value))));
                }
            } else {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                dots.add(new Dot(x, y));
            }
        }

        int minX = dots.stream().mapToInt(Dot::getX).min().orElseThrow();
        int maxX = dots.stream().mapToInt(Dot::getX).max().orElseThrow();
        int maxY = dots.stream().mapToInt(Dot::getY).max().orElseThrow();
        int minY = dots.stream().mapToInt(Dot::getY).min().orElseThrow();

        StringBuilder password = new StringBuilder("\n");
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                int finalX = x;
                int finalY = y;
                if (dots.stream().anyMatch(d->d.equals(new Dot(finalX, finalY)))) {
                    password.append("#");
                } else {
                    password.append(".");
                }
            }
            password.append("\n");
        }

        return ""+password;
    }
}
