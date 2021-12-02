package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day01 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day01());
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());
        int count = 0;
        long prev = Long.MAX_VALUE;
        while (scanner.hasNextLine()) {
            long curr = Long.parseLong(scanner.nextLine());
            if (curr > prev) {
                count++;
            }
            prev = curr;
        }

        return "" + count;
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());
        long count = 0;
        List<Long> bucket = new ArrayList<>();
        while (scanner.hasNextLine()) {
            bucket.add(Long.parseLong(scanner.nextLine()));
            if (bucket.size() > 3) {
                long prevBucket = bucket.subList(0, 3).stream().mapToLong(Long::longValue).sum();
                long currBucket = bucket.subList(1, 4).stream().mapToLong(Long::longValue).sum();
                if (currBucket > prevBucket) {
                    count++;
                }
                bucket.remove(0);
            }
        }

        return count + "";
    }
}
