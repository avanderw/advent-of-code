package net.advw.aoc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public static String solve(String input) {
        // Pattern to match mul(X,Y) where X and Y are 1-3 digits
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Matcher matcher = pattern.matcher(input);

        int sum = 0;

        // Find all valid multiplication instructions
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            sum += x * y;
        }

        return String.valueOf(sum);
    }
}