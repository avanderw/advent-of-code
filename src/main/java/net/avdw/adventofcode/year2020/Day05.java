package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day05 {

    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day05-dhiraj.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        int highestSeat = 990;
        int[] seats = new int[highestSeat];
        for (int i = 0; i < highestSeat; i++) {
            seats[i] = i;
        }
        int maxSeat = 0;
        while (scanner.hasNextLine()) {
            int lowR = 0;
            int hiR = 128;
            int lowC = 0;
            int hiC = 8;
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                int midR = (hiR - lowR) / 2;
                int midC = (hiC - lowC) / 2;
                switch (c) {
                    case 'F':
                        hiR = hiR - midR;
                        break;
                    case 'B':
                        lowR = lowR + midR;
                        break;
                    case 'L':
                        hiC = hiC - midC;
                        break;
                    case 'R':
                        lowC = lowC + midC;
                        break;
                }
            }

            int seat = lowR * 8 + lowC;
            maxSeat = Math.max(seat, maxSeat);
            System.out.printf("%s < %3s, %-3s > < %3s, %-3s > Seat %3s%n", line, hiR, lowR, hiC, lowC, seat);
            seats[seat] = -1;
        }

        System.out.printf("MAX Seat ( %s )%n", maxSeat); // 989
        System.out.println(Arrays.toString(seats)); // 548
    }
}
