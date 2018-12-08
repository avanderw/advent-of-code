package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day08 {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URL inputUrl = Day08.class.getResource("day08-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));


        Node root = exploreNode(scanner.nextInt(), scanner.nextInt(), scanner);
        System.out.println(sumMetaData);

        System.out.println(nodeValue(root));
    }

    static Integer nodeValue(Node thisNode) {
        if (thisNode.children.isEmpty()) {
//            System.out.println(thisNode.metaData.stream().mapToInt(m->m).sum());
            return thisNode.metaData.stream().mapToInt(m->m).sum();
        }
        Integer value = 0;
        for (Integer idx: thisNode.metaData) {
            idx--;
            if (idx < thisNode.children.size()) {
                value += nodeValue(thisNode.children.get(idx));
            }
        }
        return value;
    }

    static Integer sumMetaData = 0;
    static Node exploreNode(Integer numChildren, Integer numMetaData, Scanner scanner) {
        Node thisNode = new Node();
        for (int c = 0; c < numChildren; c++) {
            Node child = exploreNode(scanner.nextInt(), scanner.nextInt(), scanner);
            thisNode.children.add(child);
        }
        for (int m = 0; m < numMetaData; m++) {
            Integer metaData =scanner.nextInt();
            thisNode.metaData.add(metaData);
            sumMetaData += metaData;
        }
        return thisNode;
    }

    static class Node {
        List<Node> children = new ArrayList<>();
        List<Integer> metaData = new ArrayList<>();

        public String toString() {
            return String.format("children=%s, metaData=%s", children, metaData);
        }
    }
}
