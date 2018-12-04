package net.avdw.adventofcode.year2018;

import org.apache.commons.math3.stat.Frequency;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day04.class.getResource("day04-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        List<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        lines.sort(String::compareTo);

        Map<Integer, Frequency> minuteFrequency = new HashMap<>();
        Integer startMinute = 0;
        Integer currentId = 0;
        Pattern linePattern = Pattern.compile("\\[([\\d]+)\\-([\\d]+)\\-([\\d]+) ([\\d]+):([\\d]+)\\] (.*)");
        Pattern actionPattern = Pattern.compile("Guard #([\\d]+).*");
        for (String line : lines) {
            Matcher lineMatcher = linePattern.matcher(line);
            if (lineMatcher.find()) {
                Integer year = new Integer(lineMatcher.group(1));
                Integer month = new Integer(lineMatcher.group(2));
                Integer day = new Integer(lineMatcher.group(3));
                Integer hour = new Integer(lineMatcher.group(4));
                Integer minute = new Integer(lineMatcher.group(5));
                String action = (lineMatcher.group(6));

                if (action.contains("sleep")) {
                    System.out.println(String.format("#%s sleeping", currentId));
                    startMinute = minute;
                } else if (action.contains("wake")) {
                    while(startMinute < minute) {
                        minuteFrequency.get(currentId).addValue(startMinute);
//                        System.out.println(startMinute);
                        startMinute++;
                    }
                    System.out.println(String.format("#%s woke", currentId));
                } else {
                    Matcher actionMatcher = actionPattern.matcher(action);
                    if (actionMatcher.find()) {
                        Integer id = new Integer(actionMatcher.group(1));
                        System.out.println(String.format("swapping current #%s for #%s", currentId, id));
                        minuteFrequency.putIfAbsent(id, new Frequency());
                        currentId = id;
                    } else {
                        System.out.println(action);
                    }
                }
            } else {
                System.out.println(line);
            }
        }

        Integer maxSleep = 0;
        Integer maxId = 0;
        Integer mode = 0;
        for (Map.Entry<Integer, Frequency> entry: minuteFrequency.entrySet()) {
            System.out.println(String.format("#%s slept %s minutes with mode %s", entry.getKey(), entry.getValue().getSumFreq(), entry.getValue().getMode()));
            if (entry.getValue().getSumFreq() > maxSleep) {
                maxSleep = new Integer(entry.getValue().getSumFreq() +"");
                maxId = entry.getKey();
                mode = new Integer(entry.getValue().getMode().get(0)+"");
            }
        }

        System.out.println(String.format("%n#%s slept the most with %s minutes, mode=%s, answer=%s", maxId, maxSleep, mode, maxId*mode));

        Long maxMode = 0L;
        Integer maxMinute =0;
        for (Map.Entry<Integer, Frequency> entry: minuteFrequency.entrySet()) {
            if (entry.getValue().getMode().size() == 1) {
                Long m = entry.getValue().getCount(new Integer(""+entry.getValue().getMode().get(0)));
                if (maxMode < m) {
                    maxMode = m;
                    maxMinute = new Integer(""+entry.getValue().getMode().get(0));
                    maxId = entry.getKey();
                }
            }
        }
        System.out.println(String.format("#%s slept the most at %s times on minute %s, answer=%s", maxId, maxMode, maxMinute, maxId*maxMinute));
    }
}
