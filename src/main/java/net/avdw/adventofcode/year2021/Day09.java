package net.avdw.adventofcode.year2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

public class Day09 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day09(), 5000);
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        List<List<Integer>> map = new ArrayList<>();
        while (scanner.hasNextLine()) {
            List<Integer> row = new ArrayList<>();
            map.add(row);

            for (String s : scanner.nextLine().split("")) {
                row.add(Integer.parseInt(s));
            }
        }

        long sumRisk = 0;
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                int cell = map.get(y).get(x);
                boolean hasLowerAdjacent = false;
                if (y > 0) {
                    hasLowerAdjacent |= cell >= map.get(y - 1).get(x);
                }

                if (x > 0) {
                    hasLowerAdjacent |= cell >= map.get(y).get(x - 1);
                }

                if (x < map.get(y).size() - 1) {
                    hasLowerAdjacent |= cell >= map.get(y).get(x + 1);
                }

                if (y < map.size() - 1) {
                    hasLowerAdjacent |= cell >= map.get(y + 1).get(x);
                }

                if (!hasLowerAdjacent) {
                    int risk = cell + 1;
                    sumRisk += risk;
                }
            }
        }

        return "" + sumRisk;
    }

    @AllArgsConstructor
    @Data
    static class Cell {
        int x;
        int y;
        int value;
        UUID uuid;
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        Map<UUID, Long> basinMap = new HashMap<>();
        UUID defaultUuid = UUID.randomUUID();
        List<Cell> map = new ArrayList<>();
        int height = 0;
        int width = 0;
        while (scanner.hasNextLine()) {
            int x = 0;
            for (String s : scanner.nextLine().split("")) {
                map.add(new Cell(x, height, Integer.parseInt(s), defaultUuid));
                x++;
            }
            height++;
            width = x;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int top = y - 1;
                int bottom = y + 1;
                int left = x - 1;
                int right = x + 1;

                int finalY = y;
                int finalX = x;
                Cell current = map.stream()
                        .filter(cell -> cell.x == finalX)
                        .filter(cell -> cell.y == finalY)
                        .findAny().orElseThrow();
                List<Cell> lowestAdjacent = map.stream()
                        .filter(cell -> cell.y >= top && cell.y <= bottom)
                        .filter(cell -> cell.x >= left && cell.x <= right)
                        .filter(cell -> !(cell.x != finalX && cell.y != finalY))
                        .filter(cell -> !(cell.x == finalX && cell.y == finalY))
                        .filter(cell -> cell.value <= current.value)
                        .collect(Collectors.toList());

                if (lowestAdjacent.isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    flood(map, current, uuid);
                    basinMap.put(uuid, map.stream().filter(cell -> cell.uuid.equals(uuid)).count());
                }
            }
        }

        long result = basinMap.values().stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(v->v)
                .reduce(1, (a,b)->a*b);

        return "" + result;
    }

    private void flood(List<Cell> map, Cell current, UUID uuid) {
        if (current.uuid.equals(uuid) || current.value == 9) {
            return;
        }

        int top = current.y - 1;
        int bottom = current.y + 1;
        int left = current.x - 1;
        int right = current.x + 1;

        current.setUuid(uuid);
        map.stream().filter(cell -> cell.x == left).filter(cell -> cell.y == current.y).findAny().ifPresent(cell -> flood(map, cell, uuid));
        map.stream().filter(cell -> cell.x == right).filter(cell -> cell.y == current.y).findAny().ifPresent(cell -> flood(map, cell, uuid));
        map.stream().filter(cell -> cell.x == current.x).filter(cell -> cell.y == top).findAny().ifPresent(cell -> flood(map, cell, uuid));
        map.stream().filter(cell -> cell.x == current.x).filter(cell -> cell.y == bottom).findAny().ifPresent(cell -> flood(map, cell, uuid));
    }
}
