package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day09 {
    private static boolean isSumOf(final List<Integer> rollingNumList, final Integer num) {
        boolean isSumOf = false;
        for (int i = 0; i < rollingNumList.size(); i++) {
            for (int j = i + 1; j < rollingNumList.size(); j++) {
                isSumOf = isSumOf || rollingNumList.get(i) + rollingNumList.get(j) == num;
            }
        }
        return isSumOf;
    }

    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day09.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        List<Integer> numList = new ArrayList<>();
        while (scanner.hasNextInt()) {
            numList.add(scanner.nextInt());
        }

        int invalid = 556543474;
        int preamble = 25;
//        int invalid = 0;
//        int preamble = 5;
        List<Integer> rollingNumList = new ArrayList<>();
        for (Integer num : numList) {
            if (rollingNumList.size() <= preamble) {
                rollingNumList.add(num);
                continue;
            }

            boolean isValid = isSumOf(rollingNumList, num);
            if (!isValid) {
                System.out.printf("%s is not a sum of two %s%n", num, rollingNumList);
                invalid = num;
                break;
            }

            rollingNumList.add(num);
            rollingNumList.remove(0);
        }

        for (int i = 0; i < numList.size(); i++) {
            List<Integer> contiguousList = new ArrayList<>();
            int sum = 0;
            for (int j = i; j < numList.size(); j++) {
                contiguousList.add(numList.get(j));
                sum += numList.get(j);
                if (sum >= invalid) {
                    break;
                }
            }
            if (sum == invalid) {
                System.out.printf("%s adds up to %s%n", contiguousList, invalid);
                int min = contiguousList.stream().mapToInt(in->in).min().getAsInt();
                int max = contiguousList.stream().mapToInt(in->in).max().getAsInt();
                System.out.printf("min %s, max %s, answer %s", min, max, min+max);
                break;
            }
        }
    }
}
