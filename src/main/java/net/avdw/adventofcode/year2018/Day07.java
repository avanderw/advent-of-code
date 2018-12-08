package net.avdw.adventofcode.year2018;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 {
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        URL inputUrl = Day07.class.getResource("day07-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));

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

        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());

        Integer duration = 0;
        Set<Map.Entry<String, Set<String>>> toProcess = requirements.entrySet();
        List<String> processed = new ArrayList<>();
        while (!toProcess.isEmpty()) {
            Set<Map.Entry<String, Set<String>>> canProcess = toProcess.stream().filter(entry -> processed.containsAll(entry.getValue())).collect(Collectors.toSet());
            System.out.println(String.format("canProcess=%s, workers=%s", canProcess.stream().map(e->e.getKey()).collect(Collectors.toList()), workers));
            List<Worker> available = workers.stream().filter(w -> w.time <= 0).collect(Collectors.toList());
            if (canProcess.isEmpty() || available.isEmpty()) {
                // free worker
                Worker worker = workers.stream().filter(w -> w.time > 0).min(Comparator.comparingInt(w->w.time)).get();
                processed.add(worker.step);
                Integer reduceBy = worker.time;
                workers.forEach(w->w.time-=reduceBy);
                duration += reduceBy;
                System.out.println(String.format("free worker working on %s", worker.step));
                worker.step = null;
            } else {
                // assign worker
                String step = canProcess.stream().map(Map.Entry::getKey).sorted().findFirst().get();
                available.get(0).step = step;
                available.get(0).time = 60 + calc(step);
                toProcess.remove(toProcess.stream().filter(e -> e.getKey().equals(step)).findFirst().get());
                System.out.println(String.format("assign worker to work on %s", step));
            }
        }
        Worker last = workers.stream().filter(w -> w.time > 0).min(Comparator.comparingInt(w->w.time)).get();
        processed.add(last.step);
        duration += last.time;
        System.out.println("BITRAQVSGUWKXYHMZPOCDLJNFE".length() == processed.size());
        System.out.println(StringUtils.join(processed, ""));
        System.out.println(duration);
    }

    static Integer calc(String step) {
        return step.charAt(0) - 64;
    }

    static class Worker {
        Integer time = 0;
        String step;

        public String toString() {
            return String.format("%s=%s", step, time);
        }
    }
}

