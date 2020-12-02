package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Day02 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day02.class.getResource("day02.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        int valid = 0;
        int valid2 = 0;
        int num = 0;
        while (scanner.hasNextLine()) {
            num++;
            String line = scanner.nextLine();
            String[] split = line.split(":");
            int min = Integer.parseInt(split[0].substring(0, split[0].indexOf("-")));
            int max = Integer.parseInt(split[0].substring(split[0].indexOf("-") + 1, split[0].indexOf(" ")));
            char key = split[0].charAt(split[0].length()-1);

            int count = 0;
            System.out.printf("%5s [%2s-%-2s:%s] %25s {", num, min,max, key, split[1]);
            for (int i = 1; i < split[1].length(); i++) {
                if (split[1].charAt(i) == key) {
                    count++;
                    System.out.print("y");
                } else {
                    System.out.print("n");
                }
            }

            if (count >= min && count <= max) {
                valid++;
                System.out.print("} valid");
            } else {
                System.out.print("} !valid");
            }

            boolean rule1 = split[1].charAt(min) == key;
            boolean rule2 = split[1].charAt(max) == key;
            System.out.printf(" [%-5s %5s]", rule1, rule2);
            if (rule1 && rule2) {
                System.out.println(" !valid2");
            } else if (rule1 || rule2) {
                System.out.println(" valid2");
                valid2++;
            } else {
                System.out.println(" !valid2");
            }
        }
        System.out.printf("Answer  : %s%n", valid);
        System.out.printf("Answer 2: %s%n", valid2);
    }
}
