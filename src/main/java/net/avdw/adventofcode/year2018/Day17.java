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
//        URL inputUrl = Day17.class.getResource("day17-input-test.txt");
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
        Vector2D topLeft = new Vector2D(minX-1, minY);
        Vector2D bottomRight = new Vector2D(maxX+1, maxY);
        Vector2D box = bottomRight.subtract(topLeft);
        clayPoints.sort(Comparator.comparingDouble(Vector2D::getY).thenComparingDouble(Vector2D::getX));
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

        growDown(map, new Vector2D(500 - minX, 0));

        int count1 = 0, count2=0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (y != 0 && map[y][x] == '|' && map[y-1][x] == '~') {
                    map[y][x] = '~';
                }
                System.out.print(map[y][x]);
                if (map[y][x] == '|') {
                    count1++;
                } else if (map[y][x] == '~') {
                    count2++;
                }
            }
            System.out.println();
        }
        System.out.println(String.format("%s + %s = %s", count1, count2, count1 + count2));
    }

    private static void growDown(Character[][] map, Vector2D p) {
        int x = (int) p.getX();
        int y = (int) p.getY();
        map[y][x] = '|';

        if (y + 1 < map.length) {
            switch (map[y + 1][x]) {
                case '.':
                    growDown(map, new Vector2D(x, y + 1));
                    break;
                case '~':
                case '#':
                    Vector2D left = null;
                    boolean hasLeftWall = false;
                    for (int xx = x - 1; xx >= 0; xx--) {
                        if (map[y][xx] == '#' && (map[y + 1][xx] == '#' || (map[y + 1][xx] == '~'))) {
                            hasLeftWall = true;
                            left = new Vector2D(xx, y);
                            break;
                        } else if ((map[y][xx] == '.' || map[y + 1][xx] == '|') && (map[y + 1][xx] == '.' || map[y + 1][xx] == '|')) {
                            left = new Vector2D(xx, y);
                            break;
                        }
                    }
                    Vector2D right = null;
                    boolean hasRightWall = false;
                    for (int xx = x + 1; xx < map[y].length; xx++) {
                        if (map[y][xx] == '#' && (map[y + 1][xx] == '#' || (map[y + 1][xx] == '~'))) {
                            right = new Vector2D(xx, y);
                            hasRightWall = true;
                            break;
                        } else if ((map[y][xx] == '.' || map[y + 1][xx] == '|') && (map[y + 1][xx] == '.' || map[y + 1][xx] == '|')) {
                            right = new Vector2D(xx, y);
                            break;
                        }
                    }

                    if (hasLeftWall && hasRightWall) {
                        map[y][x] = '~';
                        for (int xx = (int) left.getX() + 1; xx < (int) right.getX(); xx++) {
                            map[y][xx] = '~';
                        }
                        growDown(map, new Vector2D(x, y - 1));
                    }

                    if (!hasLeftWall && hasRightWall) {
                        for (int xx = (int) left.getX() + 1; xx < (int) right.getX(); xx++) {
                            map[y][xx] = '|';
                        }
                        growDown(map, left);
                    }
                    if (hasLeftWall && !hasRightWall) {
                        for (int xx = (int) left.getX() + 1; xx < (int) right.getX(); xx++) {
                            map[y][xx] = '|';
                        }
                        growDown(map, right);
                    }
                    if (!hasLeftWall && !hasRightWall) {
                        if (left != null) {
                            growDown(map, left);
                        } else {
                            left = new Vector2D(0, y);
                        }
                        if (right != null) {
                            growDown(map, right);
                        } else {
                            right = new Vector2D(map[y].length,y);
                        }
                        for (int xx = (int) left.getX(); xx < (int) right.getX(); xx++) {
                                map[y][xx] = '|';
                            }
                    }
                    break;
            }
        }
    }
}
