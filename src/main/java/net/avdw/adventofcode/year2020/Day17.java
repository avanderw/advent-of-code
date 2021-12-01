package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day17 {
    static Set<String> mem = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day17.txt");
        Scanner s = new Scanner(new File(inputUrl.getFile()));
        int x = 0;
        int y = 0;
        int z = 0;
        int w = 0;

        int cycles = 6;

        int width = 0;
        int height = 0;

        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (char i : line.toCharArray()) {
                if (i == '#') {
                    mem.add(x + "." + y + "." + z + "." + w);
                }
                x++;
            }
            x = 0;
            y++;
            width = line.length();
            height = y;
        }

        for (int i = 0; i < cycles; i++){
            Set<String> newMem = new HashSet<>();
            for (int j = (-cycles - 1); j <= (width + cycles+1); j++){
                for (int k = (-cycles-1); k <= (height + cycles + 1); k++){
                    for (int m = (-cycles-1); m <= (cycles+1); m++){
                        for (int n = (-cycles-1); n <= (cycles+1); n++) {
                            String abc = j + "." + k + "." + m + "." + n;
                            int nCount = neighbourCount(abc);
                            boolean oldA = (mem.contains(abc));
                            boolean newA;
                            if (oldA) {
                                newA = (nCount == 2 || nCount == 3);
                            } else {
                                newA = (nCount == 3);
                            }
                            if (newA) {
                                newMem.add(abc);
                            }
                        }
                    }
                }
            }
            mem = newMem;
        }
        System.out.println(mem.size());
    }

    public static int neighbourCount(String xyz) {
        String[] abc = xyz.split("\\.");
        int x = Integer.parseInt(abc[0]);
        int y = Integer.parseInt(abc[1]);
        int z = Integer.parseInt(abc[2]);
        int w = Integer.parseInt(abc[3]);
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (!(i == 0 && j == 0 && k == 0 && l == 0)) {
                            String niw = (x + i) + "." + (y + j) + "." + (z + k) + "." + (w + l);
                            if (mem.contains(niw)) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}