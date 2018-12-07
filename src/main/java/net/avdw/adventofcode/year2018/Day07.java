package net.avdw.adventofcode.year2018;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day07.class.getResource("day07-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        Map<String, Set<String>> requirements = new HashMap<>();
        Pattern linePattern = Pattern.compile("Step ([A-Z]) must be finished before step ([A-Z]).*");
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            System.out.println(line);

            Matcher matcher = linePattern.matcher(line);
            if (matcher.matches()) {
                String required = (matcher.group(1));
                String step = (matcher.group(2));

                requirements.putIfAbsent(step, new HashSet<>());
                requirements.putIfAbsent(required, new HashSet<>());
                requirements.get(step).add(required);
            }
        }

        List<Integer> workerCountdown =new ArrayList<>();
        Set<Map.Entry<String, Set<String>>> toProcess = requirements.entrySet();
        List<String> processed = new ArrayList<>();
        while (!toProcess.isEmpty()) {
            Set<Map.Entry<String, Set<String>>> canProcess = toProcess.stream().filter(entry -> processed.containsAll(entry.getValue())).collect(Collectors.toSet());
            processed.add(canProcess.stream().map(entry->entry.getKey()).sorted().findFirst().get());
            toProcess.remove(toProcess.stream().filter(e->e.getKey() == processed.get(processed.size()-1)).findFirst().get());
        }
        System.out.println(StringUtils.join(processed,""));
    }
}
