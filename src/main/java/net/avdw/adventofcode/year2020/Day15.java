package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day15 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day15.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        while (scanner.hasNextLine()) {
            Map<Integer, List<Integer>> numLastIdxMap = new HashMap<>();
            String line = scanner.nextLine();
            Scanner tokenScanner = new Scanner(line);
            tokenScanner.useDelimiter(",");

            int prevNum = -1;
            int thisIdx = 0;
            while (tokenScanner.hasNextInt()) {
                Integer num = tokenScanner.nextInt();
                numLastIdxMap.put(num, new ArrayList<>());
                numLastIdxMap.get(num).add(thisIdx);
                prevNum = num;
                thisIdx++;
            }

//            System.out.println(line);

            while (thisIdx < 30000000) {
                int lastIdx;
                int thisNum;
                if (numLastIdxMap.get(prevNum).size() > 1) {
                    lastIdx = numLastIdxMap.get(prevNum).remove(0);
                    thisNum = numLastIdxMap.get(prevNum).get(0) - lastIdx;
                } else {
                    lastIdx = thisIdx;
                    thisNum = 0;
                }
//                System.out.printf("prev = %s, lastIdx = %s, spoken = %s, idx = %s%n", prevNum, lastIdx, thisNum, thisIdx);
                numLastIdxMap.computeIfAbsent(thisNum, k -> new ArrayList<>());
                numLastIdxMap.get(thisNum).add(thisIdx);
                prevNum = thisNum;
                thisIdx++;
            }

            System.out.println(prevNum);
//            System.out.println(spokenList.get(spokenList.size() - 1));
        }
    }
}
