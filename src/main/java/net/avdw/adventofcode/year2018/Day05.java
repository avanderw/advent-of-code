package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Day05 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Day04.class.getResource("day05-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        String original = scanner.nextLine();

        String toRemove = "abcdefghijklmnopqrstuvwxyz";
        for (int remIdx = 0; remIdx < toRemove.length(); remIdx++) {
            String polymer = original;
            polymer = polymer.replaceAll(Character.toLowerCase(toRemove.charAt(remIdx)) + "", "");
            polymer = polymer.replaceAll(Character.toUpperCase(toRemove.charAt(remIdx)) + "", "");
            Boolean changed = Boolean.TRUE;
            while (changed) {
                changed = Boolean.FALSE;
                String reducedPolymer = "";
//            System.out.println("ASDFDSA   " +polymer);
                for (int idx = 0; idx < polymer.length(); idx++) {
                    Character character = polymer.charAt(idx);
                    Boolean isUppercase = character == Character.toUpperCase(character);

                    if (idx < polymer.length() - 1) {
                        Character nextCharacter = polymer.charAt(idx + 1);
                        if (Character.toLowerCase(character) == Character.toLowerCase(nextCharacter)) {
                            if (isUppercase) {
                                if (nextCharacter == Character.toLowerCase(nextCharacter)) {
                                    changed = Boolean.TRUE;
                                    reducedPolymer = polymer.substring(0, idx) + polymer.substring(idx + 2);
//                                System.out.println(reducedPolymer);
//                                idx++;
//                                System.out.println(String.format("reducing %s:%s", character, nextCharacter));
                                    break;
                                }
                            } else {
                                if (nextCharacter == Character.toUpperCase(nextCharacter)) {
                                    changed = Boolean.TRUE;
                                    reducedPolymer = polymer.substring(0, idx) + polymer.substring(idx + 2);
//                                System.out.println(reducedPolymer);
//                                idx++;
//                                System.out.println(String.format("reducing %s:%s", character, nextCharacter));
                                    break;
                                }
                            }
                        }
//                    else {
//                        reducedPolymer += character;
//                    }
                    }
//                else {
//                    reducedPolymer += character;
//                }
                }
                polymer = reducedPolymer.length() != 0 ? reducedPolymer : polymer;
            }

//        System.out.println(polymer);
            System.out.println(polymer.length());
        }
    }
}
