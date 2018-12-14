package net.avdw.adventofcode.year2018;

import java.util.ArrayList;
import java.util.List;

public class Day14 {
    public static void main(String[] args) {
        List<Integer> cooks = new ArrayList<>(Integer.MAX_VALUE >> 2);
        cooks.add(0);
        cooks.add(1);

        Node start = new Node(null, 3, null);
        Node last = new Node(start, 7, start);
        start.next = last;
        start.prev = last;

        Node cook1 = start;
        Node cook2 = last;

        StringBuilder text = new StringBuilder();
        String find = "556061";
        Integer recipeCount = 2;
        Boolean inputFound = Boolean.FALSE;
        while (!inputFound) {
            text.delete(0, text.length());
            Integer sum = cook1.value + cook2.value;
            for (int idx = 0; idx < sum.toString().length(); idx++) {
                last.next = new Node(last, Integer.parseInt(String.valueOf(sum.toString().charAt(idx))), start);
                last = last.next;
                start.prev = last;
                recipeCount++;
            }

            for (int i = cook1.value + 1; i > 0; i--) {
                cook1 = cook1.next;
            }
            for (int i = cook2.value + 1; i > 0; i--) {
                cook2 = cook2.next;
            }

            Node findStart = last;
            for (int i = 0; i < find.length(); i++) {
                text.insert(0, findStart.value);
                findStart = findStart.prev;
            }

            if (text.equals(find)) {
                inputFound = Boolean.TRUE;
            }

            if (!inputFound) {
                text.insert(0, findStart.value);

                if (text.substring(0, text.length()-1).equals(find)) {
                    recipeCount--;
                    inputFound = Boolean.TRUE;
                }
            }

//            print(start, cook1, cook2);
        }
        System.out.println(recipeCount - find.length());
    }

    private static void print(Node start, Node cook1, Node cook2) {
        Boolean repeated = Boolean.FALSE;
        Node visitor = start;
        while (!repeated) {
            if (visitor == cook1) {
                System.out.print("(");
            } else if (visitor == cook2) {
                System.out.print("[");
            } else {
                System.out.print(" ");
            }

            System.out.print(visitor.value);

            if (visitor == cook1) {
                System.out.print(")");
            } else if (visitor == cook2) {
                System.out.print("]");
            } else {
                System.out.print(" ");
            }
            visitor = visitor.next;
            repeated = (visitor == start);
        }
        System.out.println();
    }

    static class Node {
        Integer value;
        Node next;
        Node prev;

        Node(Node prev, Integer value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public String toString() {
            return value.toString();
        }
    }
}
