package net.avdw.adventofcode.year2018;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.Frequency;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day06 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day06.class.getResource("day06-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        Point2D topLeft = null;
        Point2D topRight = null;
        Point2D botRight = null;
        Point2D botLeft = null;
        List<Point2D> pointList = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] coords = line.split(",");
            Point2D point = new Point(new Integer(coords[0].trim()), new Integer(coords[1].trim()));
            pointList.add(point);
            if (topLeft == null) {
                topLeft = point;
                topRight = point;
                botRight = point;
                botLeft = point;
            } else {
                if (topLeft.getX() > point.getX() && topLeft.getY() > point.getY()) {
                    topLeft = point;
                }

                if (topRight.getX() < point.getX() && topRight.getY() > point.getY()) {
                    topRight = point;
                }

                if (botRight.getX() < point.getX() && botRight.getY() < point.getY()) {
                    botRight = point;
                }

                if (botLeft.getX() > point.getX() && botLeft.getY() < point.getY()) {
                    botLeft = point;
                }
            }
        }

        List<Integer> checkIdxs = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            checkIdxs.add(i);
        }

        System.out.println(checkIdxs);

        System.out.println(String.format("%s, %s, %s, %s", topLeft, topRight, botRight, botLeft));
        Integer width = 400;//(int) Math.max(topRight.getX(), botRight.getX());
        Integer height = 400;//(int) Math.max(botLeft.getY(), botRight.getY());
        Integer[][] map = new Integer[height][width];
        System.out.println(String.format("%s, %s", width, height));
        System.out.println(pointList);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point2D point = new Point(x, y);
                Point2D closest = pointList.stream().min(Comparator.comparingDouble(p -> Math.abs(point.getY() - p.getY()) + Math.abs(point.getX() -p.getX()))).get();
                Point2D nextClosest = pointList.stream().filter(p -> !closest.equals(p)).min(Comparator.comparingDouble(p -> Math.abs(point.getY() - p.getY()) + Math.abs(point.getX() -p.getX()))).get();
//                System.out.println(String.format("%s, %s, %s", point, closest, point.distance(closest)));
//                System.out.println(String.format("%s, %s=%s, %s=%s", point, closest, Math.round(point.distance(closest)), nextClosest, Math.round(point.distance(nextClosest))));
                map[y][x] = (Math.abs(point.getY() - closest.getY()) + Math.abs(point.getX() -closest.getX())) != (Math.abs(point.getY() - nextClosest.getY()) + Math.abs(point.getX() -nextClosest.getX())) ? pointList.indexOf(closest) : -1;
                if (y == 0 || x == 0 || y == map.length-1 || x == map[0].length-1) {
                    checkIdxs.remove(map[y][x]);
                }
                System.out.print(pointList.contains(point) ?"  " : StringUtils.leftPad(map[y][x]+"", 2));
            }
            System.out.println();
        }
        System.out.println(checkIdxs);

        Frequency frequency = new Frequency();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (checkIdxs.contains(map[y][x])) {
                    frequency.addValue(map[y][x]);
                }
            }
        }

        System.out.println(frequency);
        System.out.println(String.format("%s=%s",frequency.getMode(),frequency.getCount(frequency.getMode().get(0))));

        Integer count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point2D thisPoint = new Point(x, y);
                Double distance = 0D;

                for (Point2D thatPoint : pointList) {
                    distance += Math.abs(thisPoint.getY() - thatPoint.getY()) + Math.abs(thisPoint.getX() -thatPoint.getX());
                }
                if (distance < 10_000) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
