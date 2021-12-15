package net.avdw.adventofcode.year2021;

import lombok.*;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day15(), 5000);
    }

    @Data
    @RequiredArgsConstructor
    static class Node {
        final Index index;
        final int enterRisk;
        @ToString.Exclude Node prev;
        int distance = Integer.MAX_VALUE;
        @ToString.Exclude boolean inQueue = true;
        @ToString.Exclude List<Node> adjacent;
    }

    @Value
    static class Index {
        int x;
        int y;

        List<Index> getAdjacent() {
            return Arrays.asList(
                    new Index(x, y - 1),
                    new Index(x + 1, y),
                    new Index(x, y + 1),
                    new Index(x - 1, y)
            );
        }
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        List<Node> nodes = new ArrayList<>();
        int maxY = 0;
        int maxX = 0;
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("");
            maxX = line.length;
            for (int x = 0; x < line.length; x++) {
                nodes.add(new Node(new Index(x, maxY), Integer.parseInt(line[x])));
            }
            maxY++;
        }

        List<Node> queue = new ArrayList<>(nodes);
        queue.get(0).distance = 0;
        while (!queue.isEmpty()) {
            queue.sort(Comparator.comparingInt(Node::getDistance));
            Node u = queue.remove(0);

            List<Index> adjacent = u.index.getAdjacent();
            queue.stream()
                    .filter(v -> adjacent.contains(v.index))
                    .filter(v -> u.distance + v.enterRisk < v.distance)
                    .forEach(v -> {
                        v.distance = u.distance + v.enterRisk;
                        v.prev = u;
                    });
        }

        int endX = maxX - 1;
        int endY = maxY - 1;
        Node endNode = nodes.stream().filter(n -> n.index.equals(new Index(endX, endY))).findAny().orElseThrow();

        return "" + endNode;
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        List<Node> template = new ArrayList<>();
        int maxY = 0;
        int maxX = 0;
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("");
            maxX = line.length;
            for (int x = 0; x < line.length; x++) {
                template.add(new Node(new Index(x, maxY), Integer.parseInt(line[x])));
            }
            maxY++;
        }

        Node[][] lookup = new Node[maxY * 5][maxX * 5];
        List<Node> nodes = new ArrayList<>();
        for (Node stamp : template) {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    Index index = new Index((x * maxX) + stamp.index.x,
                            (y * maxY) + stamp.index.y);
                    int enterRisk = 1 + (stamp.enterRisk - 1 + x + y) % 9;
                    Node node = new Node(index, enterRisk);
                    lookup[index.y][index.x] = node;
                    nodes.add(node);
                }
            }
        }

        for (int y = 0; y < lookup.length; y++) {
            for (int x = 0; x < lookup[y].length; x++) {
                lookup[y][x].adjacent = new ArrayList<>();
                if (y != 0) lookup[y][x].adjacent.add(lookup[y - 1][x]);
                if (x != 0) lookup[y][x].adjacent.add(lookup[y][x - 1]);
                if (y < lookup.length - 1) lookup[y][x].adjacent.add(lookup[y + 1][x]);
                if (x < lookup[y].length - 1) lookup[y][x].adjacent.add(lookup[y][x + 1]);
            }
        }

        nodes.get(0).distance = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing(Node::getDistance));
        queue.add(nodes.get(0));
        while (!queue.isEmpty()) {
            Node u = queue.remove();
            u.inQueue = false;

            u.adjacent.stream()
                    .filter(v -> v.inQueue)
                    .filter(v -> u.distance + v.enterRisk < v.distance)
                    .forEach(v -> {
                        queue.remove(v);
                        v.distance = u.distance + v.enterRisk;
                        v.prev = u;
                        queue.add(v);
                    });
        }

        int endX = 5 * maxX - 1;
        int endY = 5 * maxY - 1;
        Node endNode = nodes.stream().filter(n -> n.index.equals(new Index(endX, endY))).findAny().orElseThrow();

        return "" + endNode;
    }
}
