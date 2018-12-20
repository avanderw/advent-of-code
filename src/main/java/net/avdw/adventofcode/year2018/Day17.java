package net.avdw.adventofcode.year2018;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        Configurator.defaultConfig().level(Level.TRACE).formatPattern("{level}:\t{message}").activate();

        Pattern linePattern = Pattern.compile("([x|y])=(\\d+), ([x|y])=(\\d+)..(\\d+)");
        URL inputUrl = Day17.class.getResource("day17-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));
        List<Vector2D> clayPoints = new ArrayList();
        Integer minX = Integer.MAX_VALUE;
        Integer minY = Integer.MAX_VALUE;
        Integer maxX = Integer.MIN_VALUE;
        Integer maxY = Integer.MIN_VALUE;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = linePattern.matcher(line);
            if (matcher.matches()) {
                String fixed = matcher.group(1);
                String fixedValue = matcher.group(2);
                String variable = matcher.group(3);
                String variableStart = matcher.group(4);
                String variableEnd = matcher.group(5);

                if (fixed.equals("x")) {
                    int x = Integer.parseInt(fixedValue);
                    minX = Math.min(x, minX);
                    maxX = Math.max(x, maxX);
                    Integer start = Integer.parseInt(variableStart);
                    Integer end = Integer.parseInt(variableEnd);
                    for (int y = start; y <= end; y++) {
                        clayPoints.add(new Vector2D(x, y));
                        minY = Math.min(y, minY);
                        maxY = Math.max(y, maxY);
                    }
                } else {
                    int y = Integer.parseInt(fixedValue);
                    minY = Math.min(y, minY);
                    maxY = Math.max(y, maxY);
                    Integer start = Integer.parseInt(variableStart);
                    Integer end = Integer.parseInt(variableEnd);
                    for (int x = start; x <= end; x++) {
                        clayPoints.add(new Vector2D(x, y));
                        minX = Math.min(x, minX);
                        maxX = Math.max(x, maxX);
                    }
                }
            }
        }
        Vector2D topLeft = new Vector2D(minX, minY);
        Vector2D bottomRight = new Vector2D(maxX, maxY);
        Vector2D box = bottomRight.subtract(topLeft);
        clayPoints.sort(Comparator.<Vector2D>comparingDouble(c -> c.getY()).thenComparingDouble(c -> c.getX()));
        Logger.debug(clayPoints);
        Logger.info("identified {} clay points", clayPoints.size());
        Logger.info("grid top left = {}, bottom right = {}, width = {}, height = {}", topLeft, bottomRight, box.getX(), box.getY());

        Character[][] map = new Character[(int) box.getY() + 1][(int) box.getX() + 1];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (clayPoints.contains(new Vector2D(x + minX, y + minY))) {
                    map[y][x] = '#';
                } else {
                    map[y][x] = '.';
                }
            }
        }

        int count = 0;
        List<Vector2D> waterPoints = new ArrayList<>();
        waterPoints.add(new Vector2D(500-minX, 0));
        while (!waterPoints.isEmpty() && count < 10) {
            for (int i = 0; i < waterPoints.size(); i++) {
                Vector2D waterPoint = waterPoints.get(i);
                int y = (int) waterPoint.getY();
                int x = (int) waterPoint.getX();
                map[y][x] = '|';
                if (map[y + 1][x] == '#' || map[y + 1][x] == '~') {

                } else {
                    map[y + 1][x] = '|';
                    waterPoints.set(i, new Vector2D(x, y + 1));
                    System.out.println(waterPoint);
                }
            }
            count++;
        }
    }
}
