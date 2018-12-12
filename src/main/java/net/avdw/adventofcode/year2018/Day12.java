package net.avdw.adventofcode.year2018;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URL inputUrl = Day08.class.getResource("day12-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));
        String state = scanner.nextLine().replace("initial state: ","");
        scanner.nextLine();

        Map<String, String> rules = new HashMap<>();
        Pattern linePattern = Pattern.compile("(.*) => (.)");
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = linePattern.matcher(line);
            if (matcher.matches()) {
                String rule = (matcher.group(1));
                String output = (matcher.group(2));
                rules.put(rule, output);
            }
        }

        Long maxGen = 300l;
        Long leftIdx = -3l;
        state = StringUtils.repeat(".", 3) + state + StringUtils.repeat(".", 3);
        System.out.println(String.format("%s: %s", 0, state));
        System.out.println(rules);
        System.out.println();
        for (long generation = 0; generation < maxGen; generation++) {
            if (generation % 50 == 0) {
                System.out.println(String.format("generation:%s, leftIdx:%s, generation-leftIdx = %s", generation, leftIdx, generation - leftIdx));
                System.out.println(state);
            }
            StringBuilder sb = new StringBuilder();
            if (state.charAt(0) == '.' && state.charAt(1) == '.' && state.charAt(2) == '.' && state.charAt(3) == '.') {
                sb.append(".");
                leftIdx++;
            } else {
                sb.append("..");
            }
            for (int idx = 2; idx < state.length()-2; idx++) {
                sb.append(rules.get(state.substring(idx-2, idx+3)));
            }
            sb.append("...");
            state = sb.toString();
//            System.out.println(String.format("%s: %s", generation+1, state));
        }

        leftIdx = 50_000_000_000l - 78;
        Long sum = 0l;
        for (int idx = 0; idx < state.length(); idx++) {
            if (state.charAt(idx)=='#') {
                sum += leftIdx + idx;
            }
        }
        System.out.println("repetition detected");
        System.out.println();
        System.out.println(String.format("sum of pots idx's in generation 50 billion = %s", sum));
    }
}
