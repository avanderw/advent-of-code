package net.avdw.adventofcode.year2019;

import net.avdw.adventofcode.LoggingActivator;
import org.apache.commons.lang3.StringUtils;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day06 {
    public static void main(String[] args) throws FileNotFoundException {
        LoggingActivator.activate();
        Logger.getConfiguration().level(Level.DEBUG).activate();
        URL inputUrl = Day01.class.getResource("day06.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        scanner.useDelimiter("[)\\n]");
        Map<String, String> isOrbiting = new HashMap<>();
        Map<String, List<String>> hasOrbiting = new HashMap<>();
        while (scanner.hasNextLine()) {
            String body1 = StringUtils.trim(scanner.next());
            String body2 = StringUtils.trim(scanner.next());
            if (isOrbiting.containsKey(body2)) {
                throw new UnsupportedOperationException();
            } else {
                isOrbiting.put(body2, body1);
            }

            hasOrbiting.putIfAbsent(body1, new ArrayList<>());
            hasOrbiting.get(body1).add(body2);
            Logger.trace("{}){}", isOrbiting.get(body2), body2);
        }
        Logger.debug("isOrbiting.containsKey(COM) = {}", isOrbiting.containsKey("COM"));
        Logger.debug("hasOrbiting.containsKey(COM) = {}", hasOrbiting.containsKey("COM"));

        AtomicInteger totalOrbits = new AtomicInteger();
        isOrbiting.forEach((key, value) -> {
            Logger.trace("key={}", key);
            totalOrbits.getAndAdd(jumpsToCOM(key, isOrbiting));
        });
        Logger.info("Total orbits = {}", totalOrbits);

        String destination = isOrbiting.get("SAN");
        String current = isOrbiting.get("YOU");
        Logger.info("Transferring from {} to {}", current, destination);
        visited.add("YOU");
        List<String> path = findPathTo(destination, current, isOrbiting, hasOrbiting);
        Logger.info("Path from {} to {} has size {} and path {}", current, destination, path.size() - 1, path);
    }

    private static Set<String> visited = new HashSet<>();

    private static List<String> findPathTo(final String destination, final String current, final Map<String, String> isOrbiting, final Map<String, List<String>> hasOrbiting) {
        Logger.debug("Evaluating {}", current);
        visited.add(current);
        List<String> path = new ArrayList<>();
        path.add(current);
        if (current.equals(destination)) {
            return path;
        }
        String parent = isOrbiting.get(current);
        List<String> children = hasOrbiting.get(current);
        Logger.debug("{} has a parent {} and children {}", current, parent, children);
        for (String child : children) {
            try {
                if (!visited.contains(child)) {
                    Logger.debug("Following child");
                    path.addAll(findPathTo(destination, child, isOrbiting, hasOrbiting));
                    return path;
                }
            } catch (RuntimeException e) {
                Logger.debug(e.getMessage());
            }
        }

        if (visited.contains(parent)) {
            throw new RuntimeException(String.format("No path found from %s", current));
        } else {
            Logger.debug("Following parent");
            path.addAll(findPathTo(destination, parent, isOrbiting, hasOrbiting));
            return path;
        }
    }

    private static int jumpsToCOM(final String body, final Map<String, String> isOrbiting) {
        Logger.trace("body {} orbits {}", body, isOrbiting.get(body));
        if (isOrbiting.get(body).equals("COM")) {
            return 1;
        } else {
            return 1 + jumpsToCOM(isOrbiting.get(body), isOrbiting);
        }
    }
}
