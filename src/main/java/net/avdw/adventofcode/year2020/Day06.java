package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day06 {

    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day06.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        scanner.useDelimiter(Pattern.compile("\\s\\s"));

        int sum = 0;
        List<Map<Character, Integer>> groupAnswerList = new ArrayList<>();
        while (scanner.hasNext()) {
            Map<Character, Integer> characterMap = new HashMap<>();
            String group = scanner.next().trim();

            Scanner groupScanner = new Scanner(group);
            int members = 0;
            while (groupScanner.hasNextLine()) {
                members++;
                String line = groupScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter("");
                while (lineScanner.hasNext()) {
                    Character character = lineScanner.next().charAt(0);
                    if (!character.equals('\n') && !character.equals('\r')) {
                        characterMap.putIfAbsent(character, 0);
                        characterMap.put(character, characterMap.get(character) + 1);
                    }
                }

            }

            int finalMembers = members;
            sum += characterMap.values().stream().filter(i -> {
//                System.out.printf("%s, %s%n", i, finalMembers);
                return i == finalMembers;
            }).count();
            groupAnswerList.add(characterMap);
        }
        System.out.println(groupAnswerList);
        System.out.println(sum);
    }
}
