package net.avdw.adventofcode.year2018;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day09 {
    public static void main(String[] args) {
        Integer totalMarbles = 25;
        Integer playerCount = 9;

        List<Integer> playerScores = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            playerScores.add(0);
        }
        List<Integer> marbleCircle = new ArrayList<>();
        marbleCircle.add(0);

        Integer playerIdx = 0;
        Integer markerIdx = 0;
        Integer marbleIdx = 0;
        while (marbleIdx <= totalMarbles) {
            marbleIdx++;

            markerIdx+=2;
            markerIdx%=marbleCircle.size();
            if (markerIdx == 0) {
                markerIdx = marbleCircle.size();
            }
            marbleCircle.add(markerIdx, marbleIdx);

            System.out.print(String.format("[%s] ", playerIdx));
            for (int i =0; i<marbleCircle.size(); i++) {
                if (i == markerIdx) {
                    System.out.print(String.format("(%s) ", marbleCircle.get(i)));
                } else {
                    System.out.print(String.format(" %s  ", marbleCircle.get(i)));
                }
            }
            System.out.println();

            playerIdx++;
            playerIdx%=playerCount;
        }
    }
}
