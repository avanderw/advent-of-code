package net.avdw.adventofcode.year2019;

import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Day04 {
    public static void main(String[] args) {
        meetsCriteria(111111);
        meetsCriteria(112233);
        meetsCriteria(111122);
        meetsCriteria(123444);
        meetsCriteria(223450);
        meetsCriteria(123789);

        Logger.getConfiguration().level(Level.INFO).activate();

        int lower = 193651;
        int higher = 649729;
        List<Integer> validPasswordList = createPasswordList(lower, higher);
        Logger.info("---< Part 1 (1605) & 2 >---");
        Logger.info("Valid passwords in range [{}, {}] are {}", lower, higher, validPasswordList.size());
    }

    private static List<Integer> createPasswordList(final int lower, final int higher) {
        List<Integer> validPasswordList = new ArrayList<>();
        for (int current = lower; current <= higher; current++) {
            if (meetsCriteria(current)) {
                validPasswordList.add(current);
            }
        }
        return validPasswordList;
    }

    private static boolean meetsCriteria(final int password) {
        return decreaseCriteria(password) && pairedAdjacentCriteria(password);
    }

    private static boolean pairedAdjacentCriteria(final int password) {
        Map<String, List<Integer>> characterMap = new HashMap<>();
        String pass = "" + password;
        for (int i = 0; i < pass.length(); i++) {
            String current = pass.substring(i, i + 1);
            characterMap.putIfAbsent(current, new ArrayList<>());
            characterMap.get(current).add(i);
        }

        AtomicBoolean adjacent = new AtomicBoolean(false);
        characterMap.forEach((key, value) -> {
            if (value.size() == 2) {
                adjacent.set(true);
            }
        });

        if (adjacent.get()) {
            Logger.debug("Password {} passes paired adjacent criteria", password);
            return true;
        } else {
            Logger.debug("Password {} fails paired adjacent criteria", password);
            return false;
        }
    }

    private static boolean decreaseCriteria(final int password) {
        String pass = "" + password;
        for (int i = 0; i < pass.length() - 1; i++) {
            if (Integer.parseInt(pass.substring(i, i + 1)) > Integer.parseInt(pass.substring(i + 1, i + 2))) {
                Logger.debug("Password {} fails decrease criteria", password);
                return false;
            }
        }
        Logger.debug("Password {} passes decrease criteria", password);
        return true;
    }
}
