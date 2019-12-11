package net.avdw.adventofcode.year2019;

import net.avdw.adventofcode.LoggingActivator;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;

public class Day03 {
    public static void main(String[] args) throws FileNotFoundException {
        LoggingActivator.activate();

        Coordinate origin = new Coordinate(0, 0);

        List<List<Coordinate>> paths = new ArrayList<>();
        paths.add(createPath(origin, "R8,U5,L5,D3"));
        paths.add(createPath(origin, "U7,R6,D4,L4"));
        Set<Coordinate> intersections = intersect(paths);
        Coordinate closestCoordinate = closestTo(origin, intersections);
        Logger.debug("Closest intersection manhattan distance = {}", distance(origin, closestCoordinate));

        paths = new ArrayList<>();
        paths.add(createPath(origin, "R75,D30,R83,U83,L12,D49,R71,U7,L72"));
        paths.add(createPath(origin, "U62,R66,U55,R34,D71,R55,D58,R83"));
        intersections = intersect(paths);
        closestCoordinate = closestTo(origin, intersections);
        Logger.debug("Closest intersection manhattan distance = {}", distance(origin, closestCoordinate));

        paths = new ArrayList<>();
        paths.add(createPath(origin, "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"));
        paths.add(createPath(origin, "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"));
        intersections = intersect(paths);
        closestCoordinate = closestTo(origin, intersections);
        Logger.debug("Closest intersection manhattan distance = {}", distance(origin, closestCoordinate));

        URL inputUrl = Day01.class.getResource("day03.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        paths = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String actions = scanner.nextLine();
            paths.add(createPath(origin, actions));
        }
        scanner.close();
        intersections = intersect(paths);
        closestCoordinate = closestTo(origin, intersections);
        Logger.info("---< Part 1 >---");
        Logger.info("Closest intersection manhattan distance = {} at intersection {}", distance(origin, closestCoordinate), closestCoordinate);

        Logger.info("---< Part 2 >---");
        long minTravel = Long.MAX_VALUE;
        Coordinate minCoordinate = null;
        for (Coordinate intersection : intersections) {
            long totalTravel = 0;
            for (List<Coordinate> path : paths) {
                for (int i = 0; i < path.size(); i++) {
                    if (path.get(i).equals(intersection)) {
                        int travel = i + 1;
                        totalTravel += travel;
                        break;
                    }
                }
            }
            if (totalTravel < minTravel) {
                minTravel = totalTravel;
                minCoordinate = intersection;
            }
        }
        Logger.info("Shortest intersection {} with total steps {}", minCoordinate, minTravel);
    }

    private static long distance(final Coordinate origin, final Coordinate coordinate) {
        return Math.abs(origin.x - coordinate.x) + Math.abs(origin.y - coordinate.y);
    }

    private static Coordinate closestTo(final Coordinate origin, final Set<Coordinate> coordinates) {
        long minDistance = Long.MAX_VALUE;
        Coordinate closest = null;
        for (Coordinate coordinate : coordinates) {
            long manhattan = distance(origin, coordinate);
            if (manhattan < minDistance) {
                minDistance = manhattan;
                closest = coordinate;
            }
        }
        return closest;
    }

    private static Set<Coordinate> intersect(final List<List<Coordinate>> paths) {
        Set<Coordinate> intersect = new HashSet<>(paths.get(0));
        for (int i = 1; i < paths.size(); i++) {
            intersect.retainAll(paths.get(i));
        }
        return intersect;
    }

    private static List<Coordinate> createPath(final Coordinate start, final String actions) {
        Logger.debug("Creating path from actions: {}", actions);
        Map<Direction, Function<Coordinate, Coordinate>> operations = new HashMap<>();
        operations.put(Direction.UP, (Coordinate c) -> new Coordinate(c.x, c.y - 1));
        operations.put(Direction.DOWN, (Coordinate c) -> new Coordinate(c.x, c.y + 1));
        operations.put(Direction.LEFT, (Coordinate c) -> new Coordinate(c.x - 1, c.y));
        operations.put(Direction.RIGHT, (Coordinate c) -> new Coordinate(c.x + 1, c.y));

        List<Coordinate> path = new ArrayList<>();
        Coordinate walk = start;

        Scanner scanner = new Scanner(actions);
        scanner.useDelimiter(",");
        while (scanner.hasNext()) {
            String move = scanner.next();
            Direction direction = Direction.get("" + move.charAt(0));
            int distance = Integer.parseInt(move.substring(1));

            Logger.trace("Walking {} {} units", direction, distance);
            List<Coordinate> segment = new ArrayList<>();
            for (int i = 1; i <= distance; i++) {
                walk = operations.get(direction).apply(walk);
                segment.add(walk);
            }
            path.addAll(segment);
            Logger.trace("Adding to path: {}", segment);
        }
        return path;
    }

    static class Coordinate {
        final int x, y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x &&
                    y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT;

        static Direction get(String letter) {
            switch (letter) {
                case "U":
                    return UP;
                case "D":
                    return DOWN;
                case "L":
                    return LEFT;
                case "R":
                    return RIGHT;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
