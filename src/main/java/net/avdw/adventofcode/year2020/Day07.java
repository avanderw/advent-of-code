package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Day07 {
    static int countBags(Node node, int multiplier) {
        int count = multiplier;
        List<Node> childrenList = node.childrenList;
        for (int i = 0; i < childrenList.size(); i++) {
            Node child = childrenList.get(i);
            count += countBags(child, multiplier * node.childCountList.get(i));
        }
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day07.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        Set<Node> nodeSet = new HashSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String outerBag = line.replaceAll("(.*) bags contain.*", "$1");
            String innerBags = line.replaceAll(".* bags contain (.*)\\.", "$1");
            System.out.printf("[ %s ] contains:%n", outerBag);
            Node parent = nodeSet.stream().filter(n -> n.color.equals(outerBag)).findAny().orElse(new Node(outerBag));
            Arrays.stream(innerBags.split(", ")).forEach(bag -> {
                int bagCount = bag.equals("no other bags") ? 0 : Integer.parseInt(bag.replaceAll("(\\d+).*", "$1").trim());
                String color = bag.replaceAll("\\d+ (.*) bags?", "$1");
                System.out.printf("[ %3s ] %s%n", bagCount, color);
                if (bagCount > 0) {
                    Node child = nodeSet.stream().filter(n -> n.color.equals(color)).findAny().orElse(new Node(color));
                    child.parentList.add(parent);
                    parent.childrenList.add(child);
                    parent.childCountList.add(bagCount);
                    nodeSet.add(child);
                }
            });
            nodeSet.add(parent);
        }
        System.out.println();

//        nodeSet.forEach(n -> System.out.printf("%64s <= %16s => %s%n", n.parentList, n.color, n.childrenList));
//        System.out.println();

        Set<Node> parentSet = new HashSet<>();
        Node shinyGold = nodeSet.stream().filter(n -> n.color.equals("shiny gold")).findAny().get();
        Stack<Iterator<Node>> nodeIteratorStack = new Stack<>();
        nodeIteratorStack.push(shinyGold.parentList.iterator());
        while (!nodeIteratorStack.isEmpty()) {
            Iterator<Node> nodeIterator = nodeIteratorStack.pop();
            nodeIterator.forEachRemaining(node -> {
                parentSet.add(node);
                nodeIteratorStack.push(node.parentList.iterator());
            });
        }

        System.out.printf("%3s %s%n", parentSet.size(), parentSet);

        System.out.println();
        System.out.printf("%s contains:%n" +
                "   %s%n" +
                "   %s%n", shinyGold, shinyGold.childrenList, shinyGold.childCountList);
        System.out.println(countBags(shinyGold, 1) - 1);
    }

    static class Node {
        final String color;
        List<Integer> childCountList = new ArrayList<>();
        List<Node> childrenList = new ArrayList<>();
        List<Node> parentList = new ArrayList<>();

        Node(final String color) {
            this.color = color;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return color.equals(node.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color);
        }

        @Override
        public String toString() {
            return color;
        }
    }
}
