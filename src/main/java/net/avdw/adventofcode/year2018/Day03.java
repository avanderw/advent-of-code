package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) throws FileNotFoundException {
        String[][] fabric = new String[1000][1000];
        for (Integer y = 0; y < 1000; y++) {
            for (Integer x = 0; x < 1000; x++) {
                fabric[y][x] = ".";
            }
        }

        List<String> cleanIds = new ArrayList();
        Pattern pattern = Pattern.compile("#([\\d]+) @ ([\\d]+),([\\d]+): ([\\d]+)x([\\d]+)");
        URL inputUrl = Day03.class.getResource("day03-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        while (scanner.hasNext()) {
            String claim = scanner.nextLine();
            Matcher matcher = pattern.matcher(claim);
            if (matcher.find()) {
                String test = (matcher.group(0));
                String id = (matcher.group(1));
                cleanIds.add(id);
                Integer xPos = new Integer(matcher.group(2));
                Integer yPos = new Integer(matcher.group(3));
                Integer width = new Integer(matcher.group(4));
                Integer height = new Integer(matcher.group(5));

                for (Integer y = yPos; y < yPos + height; y++) {
                    for (Integer x = xPos; x < xPos + width; x++) {
                        if (fabric[y][x] != ".") {
                            cleanIds.remove(fabric[y][x]);
                            cleanIds.remove(id);
                            fabric[y][x] = "X";
                        } else {
                            fabric[y][x] = id;
                        }
                    }
                }
            } else {
                System.out.println(claim);
            }
        }
        System.out.println(cleanIds);

        Integer xCount = 0;
        for (Integer y = 0; y < 1000; y++) {
            for (Integer x = 0; x < 1000; x++) {
                if (fabric[y][x] == "X") {
                    xCount++;
                }
            }
        }

        System.out.println(xCount);
        //print(fabric);
    }

    public static void print(Character[][] fabric) {
        for (Integer y = 0; y < 1000; y++) {
            for (Integer x = 0; x < 1000; x++) {
                System.out.print(fabric[y][x]);
            }
            System.out.println();
        }
    }
}
