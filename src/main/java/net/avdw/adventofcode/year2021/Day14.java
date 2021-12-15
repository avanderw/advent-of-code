package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;

public class Day14 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day14(), 5000);
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        String code = scanner.nextLine();
        char last = code.charAt(code.length() - 1);
        scanner.nextLine();

        Map<String, String> replaceMap = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] template = scanner.nextLine().split(" -> ");
            replaceMap.put(template[0], template[0].charAt(0) + template[1]);
        }

        for (int i = 0; i < 10; i++) {
            StringBuilder iter = new StringBuilder();
            for (int j = 0; j < code.length() - 1; j++) {
                String key = code.substring(j, j + 2);
                iter.append(replaceMap.get(key));
            }
            code = iter.toString() + last;
        }

        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < code.length(); i++) {
            countMap.putIfAbsent(code.charAt(i), 0);
            countMap.computeIfPresent(code.charAt(i), (key, value) -> value + 1);
        }

        return "" + (countMap.values().stream().max(Integer::compareTo).orElseThrow() - countMap.values().stream().min(Integer::compareTo).orElseThrow());
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        Map<String, Long> countMap = new HashMap<>();
        String code = scanner.nextLine();
        for (int j = 0; j < code.length() - 1; j++) {
            String key = code.substring(j, j + 2);
            countMap.putIfAbsent(key, 0L);
            countMap.computeIfPresent(key, (k, value) -> value + 1);
        }

        scanner.nextLine();

        Map<String, List<String>> expansionMap = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] template = scanner.nextLine().split(" -> ");
            expansionMap.put(template[0], Arrays.asList(template[0].charAt(0) + template[1], template[1] + template[0].charAt(1)));
        }

        for (int i = 0; i < 40; i++) {
            Map<String, Long> additionMap = new HashMap<>();
            Map<String, Long> finalCountMap = countMap;
            countMap.forEach((ckey, cval) ->
                    expansionMap.get(ckey)
                            .forEach(ekey -> {
                                long keyCount = finalCountMap.getOrDefault(ckey, 1L);
                                additionMap.computeIfPresent(ekey, (k, v) -> v + keyCount);
                                additionMap.computeIfAbsent(ekey, k -> keyCount);
                            })
            );
            countMap = additionMap;

        }

        Map<Character, Long> characterMap = new HashMap<>();
        countMap.forEach((key, value) -> {
            characterMap.computeIfPresent(key.charAt(0), (k, v) -> v + value);
            characterMap.computeIfAbsent(key.charAt(0), k -> value);
        });
        characterMap.computeIfPresent(code.charAt(code.length()-1), (k,v)->v+1);

        return "" + (characterMap.values().stream().max(Long::compareTo).orElseThrow() - characterMap.values().stream().min(Long::compareTo).orElseThrow());
    }
}
