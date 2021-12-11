package net.avdw.adventofcode.year2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day11 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day11(), 5000);
    }

    @Data
    @AllArgsConstructor
    static class Cell {
        int x;
        int y;
        int value;
        boolean triggered;
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        List<Cell> cells = new ArrayList<>();
        int y = 0;
        int x;
        while (scanner.hasNextLine()) {
            x = 0;
            for (String s : scanner.nextLine().split("")) {
                cells.add(new Cell(x, y, Integer.parseInt(s), false));
                x++;
            }
            y++;
        }

        AtomicInteger flashes = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            cells.forEach(cell->cell.triggered = false);
            cells.forEach(cell -> cell.value++);
            while (cells.stream().anyMatch(cell -> cell.value > 9)) {
                cells.stream().filter(cell -> cell.value > 9).forEach(cell -> {
                    cells.stream()
                            .filter(c -> c.x >= cell.x - 1 && c.x <= cell.x + 1)
                            .filter(c -> c.y >= cell.y - 1 && c.y <= cell.y + 1)
                            .filter(c-> !c.triggered)
                            .forEach(c -> c.value++);
                    cell.value = 0;
                    cell.triggered = true;
                    flashes.set(flashes.get() + 1);
                });
            }
        }

        return "" + flashes.get();
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        List<Cell> cells = new ArrayList<>();
        int y = 0;
        int x;
        while (scanner.hasNextLine()) {
            x = 0;
            for (String s : scanner.nextLine().split("")) {
                cells.add(new Cell(x, y, Integer.parseInt(s), false));
                x++;
            }
            y++;
        }


        long count = 0;
        while (true) {
            count++;
            cells.forEach(cell->cell.triggered = false);
            cells.forEach(cell -> cell.value++);
            while (cells.stream().anyMatch(cell -> cell.value > 9)) {
                cells.stream().filter(cell -> cell.value > 9).forEach(cell -> {
                    cells.stream()
                            .filter(c -> c.x >= cell.x - 1 && c.x <= cell.x + 1)
                            .filter(c -> c.y >= cell.y - 1 && c.y <= cell.y + 1)
                            .filter(c-> !c.triggered)
                            .forEach(c -> c.value++);
                    cell.value = 0;
                    cell.triggered = true;
                });
            }
            if (cells.stream().allMatch(cell->cell.triggered)) {
                break;
            }
        }

        return "" + count;
    }
}
