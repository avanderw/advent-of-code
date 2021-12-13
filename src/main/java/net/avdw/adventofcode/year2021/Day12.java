package net.avdw.adventofcode.year2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;
import java.util.function.BiFunction;

public class Day12 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day12(), 5000);
    }

    @AllArgsConstructor
    static class Node {
        String id;

        boolean isBig() {
            return id.toUpperCase(Locale.ROOT).equals(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(id, node.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return id;
        }
    }

    Map<Node, Set<Node>> nodeMap = new HashMap<>();
    Node start = new Node("start");
    Node end = new Node("end");

    private int traverse(Node node, List<Node> path, BiFunction<Node, List<Node>, Boolean> canTraverse) {
        path.add(node);
        if (node.equals(end)) {
//            System.out.println(path);
            return 1;
        } else {
            int ends = 0;
            for (Node link : nodeMap.get(node)) {
                if (canTraverse.apply(link, path)) {
                    ends += traverse(link, new ArrayList<>(path), canTraverse);
                }
            }
            return ends;
        }
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        while (scanner.hasNextLine()) {
            String[] nodes = scanner.nextLine().split("-");
            Node from = nodes[0].equals("start") ? start : nodes[0].equals("end") ? end : new Node(nodes[0]);
            Node to = nodes[1].equals("start") ? start : nodes[1].equals("end") ? end : new Node(nodes[1]);
            nodeMap.putIfAbsent(from, new HashSet<>());
            nodeMap.putIfAbsent(to, new HashSet<>());
            nodeMap.get(from).add(to);
            nodeMap.get(to).add(from);
        }

        return "" + traverse(start, new ArrayList<>(), (link, path) -> link.isBig() || !path.contains(link));
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        while (scanner.hasNextLine()) {
            String[] nodes = scanner.nextLine().split("-");
            Node from = nodes[0].equals("start") ? start : nodes[0].equals("end") ? end : new Node(nodes[0]);
            Node to = nodes[1].equals("start") ? start : nodes[1].equals("end") ? end : new Node(nodes[1]);
            nodeMap.putIfAbsent(from, new HashSet<>());
            nodeMap.putIfAbsent(to, new HashSet<>());
            nodeMap.get(from).add(to);
            nodeMap.get(to).add(from);
        }

        return "" + traverse(start, new ArrayList<>(), (link, path) -> {
            if (link.isBig()) {
                return true;
            }

            if (link.equals(start)) {
                return false;
            }

            if (!path.contains(link)) {
                return true;
            }

            long small = path.stream().filter(n -> !n.isBig()).count();
            long smallDistinct = path.stream().filter(n -> !n.isBig()).distinct().count();
            return small - smallDistinct == 0;
        });
    }
}
