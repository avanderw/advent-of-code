package net.avdw.adventofcode.year2018;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.DeleteCommand;
import org.apache.commons.text.diff.StringsComparator;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class Day02 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day02.class.getResource("day02-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        List<String> inputs = new ArrayList();
        Integer twoCount = 0;
        Integer threeCount = 0;
        while (scanner.hasNext()) {
            Frequency frequency = new Frequency();
            String id = scanner.nextLine();
            inputs.add(id);
            for (Integer idx = 0; idx < id.length(); idx++) {
                frequency.addValue(id.charAt(idx));
            }

            Iterator it = frequency.entrySetIterator();
            Boolean foundTwo = Boolean.FALSE;
            Boolean foundThree = Boolean.FALSE;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue().equals(new Long(2))) {
                    //System.out.println(String.format("%s:%s", entry.getKey(), entry.getValue()));
                    foundTwo = Boolean.TRUE;
                }
                if (entry.getValue().equals(new Long(3))) {
                    //System.out.println(String.format("%s:%s", entry.getKey(), entry.getValue()));
                    foundThree = Boolean.TRUE;
                }
            }
            if (foundTwo) {
                twoCount++;
            }
            if (foundThree) {
                threeCount++;
            }
        }

        System.out.println(String.format("%sx%s=%s", twoCount, threeCount, twoCount * threeCount));

        LevenshteinDistance distance = new LevenshteinDistance();
        for (Integer left = 0; left < inputs.size(); left++) {
            for (Integer right = left + 1; right < inputs.size(); right++) {
                Integer d = distance.apply(inputs.get(left), inputs.get(right));
                if (d == 1) {
                    System.out.println(String.format("%s:%s=%s", inputs.get(left), inputs.get(right), d));
                    String boxId = "";
                    for (Integer idx = 0; idx < inputs.get(left).length(); idx++) {
                        if (inputs.get(left).charAt(idx) == inputs.get(right).charAt(idx)) {
                            boxId += inputs.get(left).charAt(idx);
                        }
                    }
                    System.out.println(boxId);
                }
            }
        }
    }
}
