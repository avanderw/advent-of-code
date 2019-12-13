package net.avdw.adventofcode;

import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

public class LoggingActivator {
    public static void activate() {
        Logger.getConfiguration()
                .formatPattern("[{level}] ({thread}) {message}")
                .level(Level.TRACE)
                .activate();
    }
}
