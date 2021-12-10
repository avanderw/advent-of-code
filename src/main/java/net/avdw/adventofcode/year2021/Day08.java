package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day08 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day08(), 5000);
    }


    private Day08() {
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        int count = 0;
        int idx = 0;
        while (scanner.hasNext()) {
            String code = scanner.next();
            if (idx < 10) {
            } else if (idx == 10) {
            } else if (idx < 14) {
                switch (code.length()) {
                    case 3: // 7
                    case 4: // 4
                    case 2: // 1
                    case 7: // 8
                        count++;
                }
            } else if (idx == 14) {
                switch (code.length()) {
                    case 3: // 7
                    case 4: // 4
                    case 2: // 1
                    case 7: // 8
                        count++;
                }
                idx = -1;
            }
            idx++;
        }

        return "" + count;
    }

    // 0 a b c   e f g | len=6, !contains 4, contains 7, unique
    // 1     c     f   | len=2, unique
    // 2 a   c d e   g | len=5, !contains 7, 6 !contains, unique
    // 3 a   c d   f g | len=5, contains 7, unique
    // 4   b c d   f   | len=4, unique
    // 5 a b   d   f g | len=5, !contains 7, 6 contains, unique
    // 6 a b   d e f g | len=6, !contains 4, !contains 7, unique
    // 7 a   c     f   | len=3, unique
    // 8 a b c d e f g | len=7, unique
    // 9 a b c d   f g | len=6, contains 4, unique
    @SneakyThrows
    public String part02() {
        Map<Integer, Code> matched = new HashMap<>();
        Predicate<Code> isOne = (code) -> code.length() == 2;
        Predicate<Code> isFour = (code) -> code.length() == 4;
        Predicate<Code> isSeven = (code) -> code.length() == 3;
        Predicate<Code> isEight = (code) -> code.length() == 7;
        Predicate<Code> isSix = (code) -> code.length() == 6 && code.notContains(matched.get(4)) && code.notContains(matched.get(7));
        Predicate<Code> isZero = (code) -> code.length() == 6 && code.notContains(matched.get(4)) && code.contains(matched.get(7));
        Predicate<Code> isTwo = (code) -> code.length() == 5 && code.notContains(matched.get(7)) && matched.get(6).notContains(code);
        Predicate<Code> isThree = (code) -> code.length() == 5 && code.contains(matched.get(7));
        Predicate<Code> isFive = (code) -> code.length() == 5 && code.notContains(matched.get(7)) && matched.get(6).contains(code);
        Predicate<Code> isNine = (code) -> code.length() == 6 && code.contains(matched.get(4));

        Scanner scanner = new Scanner(getInput());
        int idx = 0;
        List<Code> codes = new ArrayList<>();
        String number = "";
        long sum = 0;
        while (scanner.hasNext()) {
            if (idx < 10) {
                codes.add(new Code(scanner.next()));
            } else if (idx == 10) {
                scanner.next();
                matched.put(1, codes.stream().filter(isOne).findFirst().orElseThrow());
                matched.put(4, codes.stream().filter(isFour).findFirst().orElseThrow());
                matched.put(7, codes.stream().filter(isSeven).findFirst().orElseThrow());
                matched.put(8, codes.stream().filter(isEight).findFirst().orElseThrow());
                matched.put(6, codes.stream().filter(isSix).findFirst().orElseThrow());
                matched.put(0, codes.stream().filter(isZero).findFirst().orElseThrow());
                matched.put(2, codes.stream().filter(isTwo).findFirst().orElseThrow());
                matched.put(3, codes.stream().filter(isThree).findFirst().orElseThrow());
                matched.put(5, codes.stream().filter(isFive).findFirst().orElseThrow());
                matched.put(9, codes.stream().filter(isNine).findFirst().orElseThrow());
            } else if (idx < 15) {
                Code code = new Code(scanner.next());
                for (Map.Entry<Integer, Code> entry : matched.entrySet()) {
                    Integer key = entry.getKey();
                    Code value = entry.getValue();
                    if (code.equals(value)) {
                        number += key;
                    }
                }
            }

            idx++;
            if (idx == 15) {
                sum += Long.parseLong(number);
                idx = 0;
                codes = new ArrayList<>();
                number = "";
            }
        }
        return "" + sum;
    }

    static class Code {
        Set<String> segments = new HashSet<>();

        public Code(String token) {
            segments.addAll(Arrays.asList(token.split("")));
        }

        public int length() {
            return segments.size();
        }

        public boolean notContains(Code other) {
            return !contains(other);
        }

        public boolean contains(Code other) {
            return this.segments.containsAll(other.segments);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Code code = (Code) o;
            return Objects.equals(segments, code.segments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(segments);
        }

        @Override
        public String toString() {
            return "Code{" +
                    "segments=" + segments +
                    '}';
        }
    }
}
