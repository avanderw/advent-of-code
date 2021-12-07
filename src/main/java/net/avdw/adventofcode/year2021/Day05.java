package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;

public class Day05 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day05(), 5000);
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        Map<String, Long> countMap = new HashMap<>(2000000);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] fromTo = line.split(" -> ");
            String[] fromStr = fromTo[0].split(",");
            String[] toStr = fromTo[1].split(",");

            long x1 = Long.parseLong(fromStr[0]);
            long y1 = Long.parseLong(fromStr[1]);
            long x2 = Long.parseLong(toStr[0]);
            long y2 = Long.parseLong(toStr[1]);

            if (x1 != x2 && y1 != y2) {
                continue;
            }

            long xStep = x2 == x1 ? 0 : x2 - x1 > 0 ? 1 : -1;
            long yStep = y2 == y1 ? 0 : y2 - y1 > 0 ? 1 : -1;

            List<Long> xSet = new ArrayList<>();
            for (long x = x1; x != x2; x += xStep) {
                xSet.add(x);
            }
            xSet.add(x2);

            List<Long> ySet = new ArrayList<>();
            for (long y = y1; y != y2; y += yStep) {
                ySet.add(y);
            }
            ySet.add(y2);

            xSet.forEach(x -> ySet.forEach(y -> {
                String key = String.format("[%d,%d]", x, y);
                if (!countMap.containsKey(key)) {
                    countMap.put(key, 0L);
                }

                countMap.put(key, countMap.get(key) + 1);
            }));

        }

        return "" + countMap.entrySet().stream().filter(e -> e.getValue() > 1).count();
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        Map<String, Long> countMap = new HashMap<>(2000000);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] fromTo = line.split(" -> ");
            String[] fromStr = fromTo[0].split(",");
            String[] toStr = fromTo[1].split(",");

            long x1 = Long.parseLong(fromStr[0]);
            long y1 = Long.parseLong(fromStr[1]);
            long x2 = Long.parseLong(toStr[0]);
            long y2 = Long.parseLong(toStr[1]);

            long xStep = x2 == x1 ? 0 : x2 - x1 > 0 ? 1 : -1;
            long yStep = y2 == y1 ? 0 : y2 - y1 > 0 ? 1 : -1;

            List<Long> xSet = new ArrayList<>();
            for (long x = x1; x != x2; x += xStep) {
                xSet.add(x);
            }
            xSet.add(x2);

            List<Long> ySet = new ArrayList<>();
            for (long y = y1; y != y2; y += yStep) {
                ySet.add(y);
            }
            ySet.add(y2);

            if (x1 != x2 && y1 != y2) {
                for (int i = 0; i < xSet.size(); i++) {
                    String key = String.format("[%d,%d]", xSet.get(i), ySet.get(i));
                    if (!countMap.containsKey(key)) {
                        countMap.put(key, 0L);
                    }

                    countMap.put(key, countMap.get(key) + 1);
                }
            } else {
                xSet.forEach(x -> ySet.forEach(y -> {
                    String key = String.format("[%d,%d]", x, y);
                    if (!countMap.containsKey(key)) {
                        countMap.put(key, 0L);
                    }

                    countMap.put(key, countMap.get(key) + 1);
                }));
            }


        }

        return "" + countMap.entrySet().stream().filter(e -> e.getValue() > 1).count();
    }

}
