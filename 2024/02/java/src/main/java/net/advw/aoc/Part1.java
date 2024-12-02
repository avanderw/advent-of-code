package net.advw.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part1 {
    public static String solve(String input) {
        // Split input into lines
        String[] lines = input.trim().split("\n");
        int safeCount = 0;

        // Process each line
        for (String line : lines) {
            // Convert line to array of integers
            String[] numberStrings = line.trim().split("\\s+");
            int[] levels = new int[numberStrings.length];
            for (int i = 0; i < numberStrings.length; i++) {
                levels[i] = Integer.parseInt(numberStrings[i]);
            }

            if (isSafe(levels)) {
                safeCount++;
            }
        }

        return String.valueOf(safeCount);
    }

    private static boolean isSafe(int[] levels) {
        if (levels.length < 2) return true;

        // Check first difference to determine if we should be increasing or decreasing
        boolean shouldIncrease = levels[1] > levels[0];

        // Check each adjacent pair
        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i-1];

            // Check if difference is valid (between 1 and 3 inclusive)
            if (shouldIncrease) {
                if (diff <= 0 || diff > 3) return false;
            } else {
                if (diff >= 0 || diff < -3) return false;
            }
        }

        return true;
    }
}