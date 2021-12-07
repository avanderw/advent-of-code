package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;

public class Day07 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day07(), 5000);
    }

    private long fuel(int move) {
        long sum = 0;
        for (int cost = 1; cost <= move; cost++) {
            sum += cost;
        }

        return sum;
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());
        List<Integer> input = new ArrayList<>(1000);
        for (String str : scanner.nextLine().split(",")) {
            input.add(Integer.parseInt(str));
        }

        int max = input.stream().max(Comparator.comparingInt(i->i)).orElseThrow();
        int min = input.stream().min(Comparator.comparingInt(i->i)).orElseThrow();
        Map<Integer, Long> moves = new HashMap<>();
        for (int target = min; target < max; target++) {
            if (moves.containsKey(target)) {
                continue;
            }

            long sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum += Math.abs(input.get(i) - target);
            }
            moves.put(target, sum);
        }

        return "" + moves.values().stream().min(Comparator.comparingLong(l->l)).orElseThrow();
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());
        List<Integer> input = new ArrayList<>(1000);
        for (String str : scanner.nextLine().split(",")) {
            input.add(Integer.parseInt(str));
        }

        int max = input.stream().max(Comparator.comparingInt(i->i)).orElseThrow();
        int min = input.stream().min(Comparator.comparingInt(i->i)).orElseThrow();
        Map<Integer, Long> moves = new HashMap<>();
        for (int target = min; target < max; target++) {
            if (moves.containsKey(target)) {
                continue;
            }

            long sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum += fuel(Math.abs(input.get(i) - target));
            }
            moves.put(target, sum);
        }

        return "" + moves.values().stream().min(Comparator.comparingLong(l->l)).orElseThrow();
    }
}
