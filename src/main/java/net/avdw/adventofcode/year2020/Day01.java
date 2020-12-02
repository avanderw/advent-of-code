package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day01 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day01.class.getResource("day01.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        List<Integer> expenseList = new ArrayList<>();
        while (scanner.hasNext()) {
            expenseList.add(scanner.nextInt());
        }

        for (int i = 0; i < expenseList.size(); i++) {
            for (int j = i + 1; j < expenseList.size(); j++) {
                for (int k = j + 1; k < expenseList.size(); k++) {
                    Integer first = expenseList.get(i);
                    Integer second = expenseList.get(j);
                    Integer third = expenseList.get(k);
                    if (first + second + third == 2020) {
                        System.out.println(first * second * third);
                    }
                }

            }
        }
    }
}
