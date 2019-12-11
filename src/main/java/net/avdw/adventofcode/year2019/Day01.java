package net.avdw.adventofcode.year2019;

import net.avdw.adventofcode.LoggingActivator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Day01 {
    public static void main(String[] args) throws FileNotFoundException {
        LoggingActivator.activate();
        URL inputUrl = Day01.class.getResource("day01.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        Logger.info("total {}", getTotalFuel(14));
        Logger.info("total {}", getTotalFuel(1969));
        Logger.info("total {}", getTotalFuel(100756));

        Logger.getConfiguration().level(Level.INFO).activate();

        long totalFuel = 0l;
        while (scanner.hasNext()) {
            long mass = scanner.nextLong();
            totalFuel += getTotalFuel(mass);
        }

        Logger.info("total {}", totalFuel);
    }

    private static long getTotalFuel(long mass) {
        Logger.debug("mass {}", mass);
        long totalFuel = 0;
        long fuel = (long)Math.floor(mass / 3) - 2;
        if (fuel > 0) {
            totalFuel += fuel;
        }
        while (fuel > 0) {
            Logger.debug("fuel {}", fuel);
            fuel = (long)Math.floor(fuel / 3) - 2;
            if (fuel > 0) {
                totalFuel += fuel;
            }
        }
        return totalFuel;
    }
}
