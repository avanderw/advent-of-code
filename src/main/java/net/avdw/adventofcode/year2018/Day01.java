package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day01 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day01.class.getResource("day01-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        List<Integer> inputs = new ArrayList();
        Integer runningBalance = 0;
        Integer input = null;
        while (scanner.hasNext()) {
            input = scanner.nextInt();
            inputs.add(input);
            runningBalance += input;
        }

        System.out.println(runningBalance);

        runningBalance = 0;
        List<Integer> frequencies = new ArrayList();
        Boolean duplicateFound = Boolean.FALSE;
        Integer duplicate = 0;
        while (!duplicateFound) {
            for (Integer idx  = 0; idx < inputs.size() && !duplicateFound; idx++) {
                runningBalance += inputs.get(idx);
                if (frequencies.contains(runningBalance)) {
                    duplicate = runningBalance;
                    duplicateFound = Boolean.TRUE;
                } else {
                    frequencies.add(runningBalance);
                }
            }
        }
        System.out.println(duplicate);
    }
}
