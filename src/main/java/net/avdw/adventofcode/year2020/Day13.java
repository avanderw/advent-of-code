package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day13 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day13.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

//        part01(scanner);

        List<Integer> ids = new ArrayList<>();
        List<Integer> deltas = new ArrayList<>();
        scanner.nextLine();
        scanner.useDelimiter(Pattern.compile("[\\s,]+"));
        List<BigInteger> sums = new ArrayList<>();
        int deltaIdx = 0;
        while (scanner.hasNext()) {
            String id = scanner.next();
            if (!id.equals("x")) {
                ids.add(Integer.parseInt(id));
                deltas.add(deltaIdx);
                System.out.printf("Mod[t+%s, %s] == 0, ", deltaIdx, id);
            }
            deltaIdx++;
        }
        System.out.println(); // t = 213890632230818 + 1463175673841141 n

        long lcd = ids.stream().reduce(1, (integer, integer2) -> integer * integer2);
        for (int i = 0; i < ids.size(); i++) {
            sums.add(BigInteger.valueOf(lcd*lcd*16));
        }

        BigInteger step = BigInteger.valueOf(lcd);
        while (!check(sums, deltas)) {
            iterate(sums, ids, step);
//            if (sums.get(0) == 3417) {
//                break;
//            }
        }
        System.out.println(sums); // just use chinese remainder theorem
    }

    private static void iterate(final List<BigInteger> sums, final List<Integer> ids, final BigInteger step) {
        sums.set(0, sums.get(0).add(step));
        BigInteger min = sums.get(0).max(new BigInteger("100000000000000"));


//        System.out.printf("%nincreasing idx %s by %s to %s%n", minIdx, ids.get(minIdx), min);
        for (int i = 0; i < sums.size(); i++) {
            while (sums.get(i).compareTo(min) < 0) {
//                System.out.println(sums);
                sums.set(i, sums.get(i).add(BigInteger.valueOf(ids.get(i))));
            }
        }
    }

    private static boolean check(final List<BigInteger> sums, final List<Integer> deltas) {
        boolean good = true;
//        System.out.printf("%nsum = %s, diff = %s, good = %s%n", sums.get(0), 0, good);
        for (int i = 1; i < sums.size(); i++) {
            BigInteger diff = sums.get(i).subtract(sums.get(0));
            good = good && diff.intValue() == deltas.get(i);
//            System.out.printf("sum = %s, diff = %s, good = %s%n", sums.get(i), diff, good);
        }
        return good;
    }

    private static void part01(final Scanner scanner) {
        Map<Integer, Integer> busEarliestMap = new HashMap<>();
        int timestamp = scanner.nextInt();
        scanner.useDelimiter(Pattern.compile("[\\s,]+"));
        while (scanner.hasNext()) {
            try {
                int bus = Integer.parseInt(scanner.next());
                System.out.printf("%2s bus", bus);
                int earliest = 0;
                for (int i = 1; i < timestamp; i++) {
                    earliest = i * bus;
                    if (earliest > timestamp) {
                        break;
                    }
                }
                System.out.printf(", earliest %s%n", earliest);
                busEarliestMap.put(bus, earliest);
            } catch (NumberFormatException e) {
            }
        }

        Map.Entry<Integer, Integer> min = busEarliestMap.entrySet().stream().min(Map.Entry.comparingByValue()).get();
        System.out.printf("minimum %s%n", min.getKey() * (min.getValue() - timestamp));
    }
}
