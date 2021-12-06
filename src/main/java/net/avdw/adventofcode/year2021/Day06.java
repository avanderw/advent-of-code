package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day06 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day06(), 5000);
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());
        List<Integer> fish = new LinkedList<>();
        Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).forEach(fish::add);

        int days = 80;
        for (int i = 0; i < days; i++) {
            List<Integer> newFish = new LinkedList<>();

            fish.forEach(f->{
                if (f == 0) {
                    newFish.add(6);
                    newFish.add(8);
                } else {
                    newFish.add(f-1);
                }
            });

            fish = newFish;
        }

        return "" + (long) fish.size();
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());
        Map<Integer, Long> ageMap = new HashMap<>();
        Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).forEach(f->{
            ageMap.putIfAbsent(f, 0L);
            ageMap.put(f, ageMap.get(f)+1);
        });

        Map<Integer, Long> generation = ageMap;
        int days = 256;
        for (int i = 0; i < days; i++) {
            Map<Integer, Long> nextGen = new HashMap<>();
            nextGen.put(0, 0L);
            nextGen.put(1, 0L);
            nextGen.put(2, 0L);
            nextGen.put(3, 0L);
            nextGen.put(4, 0L);
            nextGen.put(5, 0L);
            nextGen.put(6, 0L);
            nextGen.put(7, 0L);
            generation.forEach((age, count)->{
                if (age == 0) {
                    nextGen.put(8, count);
                    nextGen.put(6, count + nextGen.get(6));
                } else {
                    nextGen.put(age-1, nextGen.get(age-1) + count);
                }
            });
            generation = nextGen;
        }

        return "" + generation.values().stream().mapToLong(l->l).sum();
    }
}
