package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class Day20 {
    static Stack<Node> branches = new Stack<>();

    public static void main(String[] args) {
        String input = read();
        Node start = new Node(0, 0);
        List<Node> nodes = process(start, input);
        nodes.sort(Comparator.<Node>comparingInt(n -> n.y).thenComparingInt(n -> n.x));
        System.out.println(nodes);

        int minX = nodes.stream().mapToInt(n -> n.x).min().getAsInt();
        int minY = nodes.stream().mapToInt(n -> n.y).min().getAsInt();
        int maxX = nodes.stream().mapToInt(n -> n.x).max().getAsInt();
        int maxY = nodes.stream().mapToInt(n -> n.y).max().getAsInt();
        System.out.println(String.format("[%s, %s], [%s, %s]", minX, minY, maxX, maxY));

        start.doors = 0;
        updateShortestPath(start);
        Node[][] grid = new Node[100][100];
        for (Node node : nodes) {
            grid[51 + node.y][51 + node.x] = node;
        }

        print(grid);
        System.out.println(nodes.stream().mapToInt(n -> n.doors).max().getAsInt());
        List<Node> list= nodes.stream().distinct().filter(n->n.doors >=1000).collect(Collectors.toList());
        list.sort(Comparator.comparingInt(n->n.doors));
        System.out.println(list.size());
    }

    private static void print(Node[][] grid) {
        for (int y = 0; y < 100; y++) {
            String line1 = "";
            String line2 = "";
            for (int x = 0; x < 100; x++) {
                if (grid[y][x] != null) {
                    line1 += (grid[y][x].north != null) ? "#------" : "#######";
                    if (grid[y][x].doors == 0) {
                        line2 += (grid[y][x].west != null) ? "| XXXX " : "# XXXX ";
                    } else {
                        line2 += String.format((grid[y][x].west != null) ? "| %-5s" : "# %-5s", grid[y][x].doors);
                    }
                } else {
                    line1+="#######";
                    line2+="# NULL ";
                }
            }
            line1 += "#";
            line2 += "#";
            System.out.println(line1);
            System.out.println(line2);
        }

    }

    private static void updateShortestPath(Node node) {
        List<Node> toProcess = new ArrayList<>();
        toProcess.add(node);

        while (!toProcess.isEmpty()) {
            Node process = toProcess.remove(0);
            if (process.north != null && process.north.doors == -1) {
                process.north.doors = process.doors + 1;
                toProcess.add(process.north);
            }
            if (process.south != null && process.south.doors == -1) {
                process.south.doors = process.doors + 1;
                toProcess.add(process.south);
            }
            if (process.east != null && process.east.doors == -1) {
                process.east.doors = process.doors + 1;
                toProcess.add(process.east);
            }
            if (process.west != null && process.west.doors == -1) {
                process.west.doors = process.doors + 1;
                toProcess.add(process.west);
            }
        }
    }

    private static List<Node> process(Node node, String input) {
        Node newNode;
        List<Node> nodes = new ArrayList<>();
        while (input.length() > 0) {
            Node prevNode = node;
            switch (input.charAt(0)) {
                case 'N':
                    System.out.print("N");
                    newNode = new Node(node.x, node.y - 1);
                    if (!nodes.contains(newNode)) {
                        nodes.add(newNode);
                        prevNode.north = newNode;
                        newNode.south = prevNode;
                    }
                    node = newNode;
                    break;
                case 'S':
                    System.out.print("S");
                    newNode = new Node(node.x, node.y + 1);
                    if (!nodes.contains(newNode)) {
                        nodes.add(newNode);
                        prevNode.south = newNode;
                        newNode.north = prevNode;
                    }
                    node = newNode;
                    break;
                case 'E':
                    System.out.print("E");
                    newNode = new Node(node.x + 1, node.y);
                    if (!nodes.contains(newNode)) {
                        nodes.add(newNode);
                        prevNode.east = newNode;
                        newNode.west = prevNode;
                    }
                    node = newNode;
                    break;
                case 'W':
                    System.out.print("W");
                    newNode = new Node(node.x - 1, node.y);
                    if (!nodes.contains(newNode)) {
                        nodes.add(newNode);
                        prevNode.west = newNode;
                        newNode.east = prevNode;
                    }
                    node = newNode;
                    break;
                case '^':
                    System.out.print("^");
                    break;
                case '(':
                    System.out.print("(");
                    branches.push(node);
                    break;
                case '|':
                    System.out.print("|");
                    nodes.add(node);
                    node = branches.peek();
                    break;
                case ')':
                    System.out.print(")");
                    nodes.add(node);
                    node = branches.pop();
                    break;
                case '$':
                    System.out.println("$");
                    if (input.length() != 1 || branches.size() != 0) {
                        throw new RuntimeException();
                    }
                    break;
            }
            input = input.substring(1);
        }
        return nodes;
    }


    private static String read() {
        String line = null;
        URL inputUrl = Day20.class.getResource("day20-input.txt");
        try {
            Scanner scanner = new Scanner(new File(inputUrl.toURI()));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        return line;
    }

    static class Node {
        private final int x;
        private final int y;
        Node north;
        Node south;
        Node east;
        Node west;
        int doors = -1;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("%-5s", doors);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x &&
                    y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
