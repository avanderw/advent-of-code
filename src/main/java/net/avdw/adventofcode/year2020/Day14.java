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

public class Day14 {
    private static List<Long> expand(final String appliedMask) {
        List<Long> expandedList = new ArrayList<>();

        if (appliedMask.contains("X")) {
            expandedList.addAll(expand(appliedMask.replaceFirst("X", "0")));
            expandedList.addAll(expand(appliedMask.replaceFirst("X", "1")));
        } else {
            expandedList.add(Long.parseLong(appliedMask, 2));
        }

        return expandedList;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(Long.MAX_VALUE);
        URL inputUrl = Year2020.class.getResource("day14.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

//        part01(scanner);
        part02(scanner);
    }

    private static void part01(final Scanner scanner) {
        String mask = "";
        Map<Integer, Long> map = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("mask = ")) {
                mask = line.substring("mask = ".length());
//                System.out.printf("Using mask = %s%n", mask);
            } else if (line.startsWith("mem[")) {
                int address = Integer.parseInt(line.replaceFirst(".*\\[(\\d+)].*", "$1"));
                long value = Long.parseLong(line.replaceFirst(".* = (\\d+)", "$1"));
                String bitValue = Long.toBinaryString(value);
                StringBuilder maskedValue = new StringBuilder();
                for (int i = 1; i <= mask.length(); i++) {
                    char c = mask.charAt(mask.length() - i);
                    if (c == 'X') {
                        if (i > bitValue.length()) {
                            maskedValue.insert(0, 0);
                        } else {
                            maskedValue.insert(0, bitValue.charAt(bitValue.length() - i));
                        }
                    } else {
                        maskedValue.insert(0, c);
                    }
                }
                System.out.printf("bit   %36s%n", bitValue);
                System.out.printf("mask  %s%n", mask);
                System.out.printf("value %s%n", maskedValue);
                value = Long.parseLong(maskedValue.toString(), 2);
                System.out.printf("Writing [%s] = %s%n%n", address, value);
                map.put(address, value);
            }
        }
        System.out.println(map);
        System.out.printf("sum = %s%n", map.values().stream().mapToLong(l -> l).sum());
    }

    private static void part02(final Scanner scanner) {
        String mask = "";
        Map<Long, Long> map = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("mask = ")) {
                mask = line.substring("mask = ".length());
//                System.out.printf("Using mask = %s%n", mask);
            } else if (line.startsWith("mem[")) {
                int address = Integer.parseInt(line.replaceFirst(".*\\[(\\d+)].*", "$1"));
                long value = Long.parseLong(line.replaceFirst(".* = (\\d+)", "$1"));
                String bitValue = Long.toBinaryString(address);
                StringBuilder appliedMask = new StringBuilder();
                for (int i = 1; i <= mask.length(); i++) {
                    char c = mask.charAt(mask.length() - i);
                    if (c == 'X') {
                        appliedMask.insert(0, "X");
                    } else if (c == '0') {
                        if (i <= bitValue.length()) {
                            appliedMask.insert(0, bitValue.charAt(bitValue.length() - i));
                        } else {
                            appliedMask.insert(0, "0");
                        }
                    } else {
                        appliedMask.insert(0, "1");
                    }
                }
                System.out.printf("bit    %36s%n", bitValue);
                System.out.printf("mask   %36s%n", mask);
                System.out.printf("result %36s%n", appliedMask);
                List<Long> addresses = expand(appliedMask.toString());
                for (Long add : addresses) {
                    System.out.printf("Writing [%s] = %s%n", add, value);
                    map.put(add, value);
                }
            }
        }
        System.out.println(map);
        System.out.printf("sum = %s%n", map.values().stream().map(BigInteger::valueOf).reduce(BigInteger.ZERO, BigInteger::add));
        // 224006888116
    }
}
