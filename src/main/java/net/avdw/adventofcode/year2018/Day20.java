package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Day20 {
    static Stack<Node> branches = new Stack<>();

    public static void main(String[] args) {
        String input = read();
        Node start = new Node(0, 0, 0);
        List<Node> nodes = process(start, input);

        nodes.sort(Comparator.<Node>comparingInt(n-> n.y).thenComparingInt(n-> n.x));
        System.out.println(nodes);

        int minX = nodes.stream().mapToInt(n->n.x).min().getAsInt();
        int minY = nodes.stream().mapToInt(n->n.y).min().getAsInt();
        int maxX = nodes.stream().mapToInt(n->n.x).max().getAsInt();
        int maxY = nodes.stream().mapToInt(n->n.y).max().getAsInt();
        System.out.println(String.format("[%s, %s], [%s, %s]", minX, minY, maxX, maxY));

    }

    private static List<Node> process(Node node, String input) {
        List<Node> nodes = new ArrayList<>();
        while (input.length() > 0) {
            switch (input.charAt(0)) {
                case 'N':
                    System.out.print("N");
                    nodes.add(node);
                    node = new Node(node.x, node.y - 1, node.doors + 1);
                    break;
                case 'S':
                    System.out.print("S");
                    nodes.add(node);
                    node = new Node(node.x, node.y + 1, node.doors + 1);
                    break;
                case 'E':
                    System.out.print("E");
                    nodes.add(node);
                    node = new Node(node.x +1, node.y, node.doors + 1);
                    break;
                case 'W':
                    System.out.print("W");
                    nodes.add(node);
                    node = new Node(node.x -1, node.y, node.doors + 1);
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
                case '$' :
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
        int doors;

        Node(int x, int y, int doors) {
            this.x = x;
            this.y = y;
            this.doors = doors;
        }

        @Override
        public String toString() {
            return String.format("[%s, %s] %s", x, y, doors);
        }
    }
}
