package net.advw.aoc;

public class Part2 {
    public static String solve(String input) {

        // Parse input string into arrays
        String[] lines = input.trim().split("\n");
        int[] leftNumbers = new int[lines.length];
        int[] rightNumbers = new int[lines.length];

        // Fill arrays from input
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].trim().split("\\s+");
            leftNumbers[i] = Integer.parseInt(parts[0]);
            rightNumbers[i] = Integer.parseInt(parts[1]);
        }

        // Calculate similarity score
        int totalScore = 0;

        // For each number in left list
        for (int leftNum : leftNumbers) {
            // Count occurrences in right list
            int occurrences = 0;
            for (int rightNum : rightNumbers) {
                if (rightNum == leftNum) {
                    occurrences++;
                }
            }
            // Add to total score (number * occurrences)
            totalScore += leftNum * occurrences;
        }

        return String.valueOf(totalScore);
    }
}
