package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day10 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day10.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        List<Integer> adapterList = new ArrayList<>();
        while (scanner.hasNextInt()) {
            adapterList.add(scanner.nextInt());
        }

        Collections.sort(adapterList);
        adapterList.add(adapterList.get(adapterList.size() - 1) + 3);
        System.out.println(adapterList);

        part01(adapterList);
        part02(adapterList);
    }

    private static void part02(List<Integer> adapterList) {
        int[] tribonacci = {0, 1, 1, 2, 4, 7}; // paths to get to node

        int prev = 0;
        long result = 1;
        int consecutiveCount = 1;

        for (int i : adapterList) {
            if (i == prev + 1) {
                consecutiveCount++;
            } else {
                result *= tribonacci[consecutiveCount];
                consecutiveCount = 1;
            }
            prev = i;
        }
        System.out.println(result);
    }

    private static void part01(List<Integer> adapterList) {
        int diff1Count = 0;
        int diff2Count = 0;
        int diff3Count = 0;
        int current = 0;
        for (Integer adapter : adapterList) {
            int diff = adapter - current;
            switch (diff) {
                case 1:
                    diff1Count++;
                    break;
                case 2:
                    diff2Count++;
                    break;
                case 3:
                    diff3Count++;
                    break;
            }
            current = adapter;
        }

        System.out.printf("1=%s, 2=%s, 3=%s [1*3]=%s%n", diff1Count, diff2Count, diff3Count, diff1Count * diff3Count);
    }
}
