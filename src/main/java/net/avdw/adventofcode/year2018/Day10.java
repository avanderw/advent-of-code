package net.avdw.adventofcode.year2018;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.geometry.Point;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URL inputUrl = Day08.class.getResource("day10-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));

        List<Star> stars = new ArrayList<>();
        Pattern linePattern = Pattern.compile("position=<\\s?(-?[\\d]+), \\s?(-?[\\d]+)> velocity=<\\s?(-?[\\d]+), \\s?(-?[\\d]+)>");
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = linePattern.matcher(line);
            if (matcher.matches()) {
                String xPos = (matcher.group(1));
                String yPos = (matcher.group(2));
                String xVel = (matcher.group(3));
                String yVel = (matcher.group(4));
                Vector2D position = new Vector2D(Integer.parseInt(xPos), Integer.parseInt(yPos));
                Vector2D velocity = new Vector2D(Integer.parseInt(xVel), Integer.parseInt(yVel));
                Star star = new Star(position, velocity);
                stars.add(star);
            }
        }

        Long minArea = area(stars);
        Long newArea = move(stars);
        Integer count = 0;
        while (newArea < minArea) {
            count++;
            minArea = newArea;
            newArea = move(stars);
        }
        undo(stars);
        System.out.println();
        print(stars);
        System.out.println(String.format("%ss",count));
    }

    private static void undo(List<Star> stars) {
        stars.stream().forEach(star -> star.position = star.position.subtract(star.velocity));
    }

    static Long area(List<Star>stars) {
        Integer minY = stars.stream().mapToInt(p->(int)p.position.getY()).min().getAsInt();
        Integer maxY = stars.stream().mapToInt(p->(int)p.position.getY()).max().getAsInt()+1;
        Integer minX = stars.stream().mapToInt(p->(int)p.position.getX()).min().getAsInt();
        Integer maxX = stars.stream().mapToInt(p->(int)p.position.getX()).max().getAsInt()+1;
        Integer width = maxX - minX;
        Integer height = maxY - minY;
        return width.longValue()*height.longValue();
    }

    static Long move(List<Star> stars) {
        stars.stream().forEach(star -> star.position = star.position.add(star.velocity));
        Long area = area(stars);
        System.out.println(String.format("area=%s", area));
        return area;
    }

    static void print(List<Star> stars) {
        StringBuilder sb=new StringBuilder();
        List<Vector2D> positions = stars.stream().sorted(Comparator.<Star>comparingDouble(s->s.position.getY()).thenComparingDouble(s->s.position.getX())).map(s->s.position).collect(Collectors.toList());
        Integer minY = positions.stream().mapToInt(p->(int)p.getY()).min().getAsInt();
        Integer maxY = positions.stream().mapToInt(p->(int)p.getY()).max().getAsInt()+1;
        Integer minX = positions.stream().mapToInt(p->(int)p.getX()).min().getAsInt();
        Integer maxX = positions.stream().mapToInt(p->(int)p.getX()).max().getAsInt()+1;
        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                if (positions.contains(new Vector2D(x, y))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    static class Star {
        Vector2D position;
        Vector2D velocity;

        Star(Vector2D position, Vector2D velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        public String toString() {
            return String.format("position=%s", position);
        }
    }
}
