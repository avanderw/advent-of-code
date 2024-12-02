package net.advw.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part1 {
    public static String solve(String input) {
        // Parse input into two lists
        String[] lines = input.trim().split("\n");
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 2) {
                leftList.add(Integer.parseInt(parts[0]));
                rightList.add(Integer.parseInt(parts[1]));
            }
        }

        // Sort both lists
        Collections.sort(leftList);
        Collections.sort(rightList);

        // Calculate total distance
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            int distance = Math.abs(leftList.get(i) - rightList.get(i));
            totalDistance += distance;
        }

        return String.valueOf(totalDistance);
    }
}
