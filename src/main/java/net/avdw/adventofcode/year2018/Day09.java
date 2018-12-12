package net.avdw.adventofcode.year2018;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day09 {
    public static void main(String[] args) {
        Integer totalMarbles = 70769*100;
        Integer playerCount = 418;

        List<Long> playerScores = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            playerScores.add(0L);
        }
        Node start = new Node(null, 0, null);
        start.next = start;
        start.prev = start;

        Node currentMarble = start;
        Integer playerIdx = 0;
        Integer marbleIdx = 0;
        while (marbleIdx < totalMarbles) {
            marbleIdx++;
            currentMarble = currentMarble.next;

            if (marbleIdx % 23 != 0) {
                Node insert = new Node(currentMarble, marbleIdx, currentMarble.next);
                currentMarble.next.prev = insert;
                currentMarble.next = insert;
                currentMarble = insert;
            } else{
                playerScores.set(playerIdx, playerScores.get(playerIdx) + marbleIdx);
                for (int i = 0; i < 8;i++) {
                    currentMarble = currentMarble.prev;
                }
                currentMarble.prev.next = currentMarble.next;
                currentMarble.next.prev = currentMarble.prev;
                playerScores.set(playerIdx, playerScores.get(playerIdx) + currentMarble.value);
                currentMarble = currentMarble.next;
            }

//            Node loop = start;
//            Boolean started= false;
//            System.out.print(String.format("[%s] ", playerIdx));
//            while (loop != start || !started) {
//                started= true;
//                if (loop == currentMarble) {
//                    System.out.print(String.format("(%s) ", loop.value));
//                } else {
//                    System.out.print(String.format(" %s  ", loop.value));
//                }
//                loop = loop.next;
//            }
//            System.out.println();

            playerIdx++;
            playerIdx%=playerCount;
        }

        System.out.println(playerScores.stream().mapToLong(s->s).max());
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
