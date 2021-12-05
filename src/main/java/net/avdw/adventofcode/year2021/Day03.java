package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day03 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day03(), 5000);
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());
        List<String> report = new ArrayList<>();
        while (scanner.hasNextLine()) {
            report.add(scanner.nextLine());
        }

        long[][] power = power(report);
        long[] gamma = power[0];
        long[] epsilon = power[1];

        long gammaD = Long.parseLong(Arrays.stream(gamma).mapToObj(Long::toString).reduce("", (a, b) -> a + b), 2);
        long epsilonD = Long.parseLong(Arrays.stream(epsilon).mapToObj(Long::toString).reduce("", (a, b) -> a + b), 2);

        return (gammaD * epsilonD) + "";
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        List<String> report = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String bitString = scanner.nextLine();
            report.add(bitString);
        }

        List<String> o2 = new ArrayList<>(report);
        List<String> co2 = new ArrayList<>(report);
        for (int i = 0; i < report.get(0).length(); i++) {
            int finalI = i;
            if (o2.size() > 1) {
                long[] gamma = power(o2)[0];
                o2 = o2.stream().filter(b -> Character.getNumericValue(b.charAt(finalI)) == gamma[finalI]).collect(Collectors.toList());
            }
            if (co2.size() > 1) {
                long[] epsilon = power(co2)[1];
                co2 = co2.stream().filter(b -> Character.getNumericValue(b.charAt(finalI)) == epsilon[finalI]).collect(Collectors.toList());
            }
        }

        long oxygenD = Long.parseLong(o2.get(0), 2);
        long co2D = Long.parseLong(co2.get(0), 2);

        return "" + (oxygenD * co2D);
    }

    private long[][] power(List<String> subset) {
        int size = subset.get(0).length();
        long[] gamma = new long[size];
        long[] epsilon = new long[size];
        subset.forEach(bitString -> {
            for (int i = 0; i < bitString.length(); i++) {
                if (bitString.charAt(i) == '0') {
                    epsilon[i]++;
                } else {
                    gamma[i]++;
                }
            }
        });

        for (int i = 0; i < size; i++) {
            if (gamma[i] >= epsilon[i]) {
                gamma[i] = 1;
                epsilon[i] = 0;
            } else {
                gamma[i] = 0;
                epsilon[i] = 1;
            }
        }

        return new long[][]{gamma, epsilon};
    }
}
