package net.advw.aoc;

public class Part2 {
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

            if (isSafeWithDampener(levels)) {
                safeCount++;
            }
        }

        return String.valueOf(safeCount);
    }

    private static boolean isSafeWithDampener(int[] levels) {
        // First check if it's safe without removing any level
        if (isSafe(levels)) {
            return true;
        }

        // Try removing each level one at a time
        for (int i = 0; i < levels.length; i++) {
            int[] modified = new int[levels.length - 1];
            // Copy all elements except the one at index i
            for (int j = 0, k = 0; j < levels.length; j++) {
                if (j != i) {
                    modified[k++] = levels[j];
                }
            }

            if (isSafe(modified)) {
                return true;
            }
        }

        return false;
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
