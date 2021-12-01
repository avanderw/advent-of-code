package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day04 {

    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day04-test.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        scanner.useDelimiter(Pattern.compile("\\s\\s\\s"));

        int count = 0;
        while (scanner.hasNext()) {
            String passportData = scanner.next();
            Passport passport = new Passport();
            Scanner passportScanner = new Scanner(passportData);
            while (passportScanner.hasNext()) {
                String field = passportScanner.next();
                switch (field.substring(0, 3)) {
                    case "byr":
                        passport.byr = Integer.parseInt(field.substring(4));
                        break;
                    case "iyr":
                        passport.iyr = Integer.parseInt(field.substring(4));
                        break;
                    case "eyr":
                        passport.eyr = Integer.parseInt(field.substring(4));
                        break;
                    case "hgt":
                        passport.hgt = field.substring(4);
                        break;
                    case "hcl":
                        passport.hcl = field.substring(4);
                        break;
                    case "ecl":
                        passport.ecl = field.substring(4);
                        break;
                    case "pid":
                        passport.pid = field.substring(4);
                        break;
                    case "cid":
                        passport.cid = field.substring(4);
                        break;
                    default:
                        System.err.println(field);
                }
            }
            System.out.printf("%s:%s%n", passport, passport.isValid());
            count += passport.isValid() ? 1 : 0;
        }
        System.out.println(count);
    }

    private static class Passport {
        private static final Pattern hairColorPattern = Pattern.compile("#[0-9a-fA-F]{6}");
        public Integer byr;
        public String cid;
        public String ecl;
        public Integer eyr;
        public String hcl;
        public String hgt;
        public Integer iyr;
        public String pid;

        private boolean eclValid() {
            boolean valid = false;
            switch (ecl) {
                case "amb":
                case "blu":
                case "brn":
                case "gry":
                case "grn":
                case "hzl":
                case "oth":
                    valid = true;
            }
            return valid;
        }

        private boolean hgtValid() {
            if (hgt.endsWith("cm")) {
                int height = Integer.parseInt(hgt.substring(0, hgt.indexOf("cm")));
                return height >= 150 && height <= 193;
            } else if (hgt.endsWith("in")) {
                int height = Integer.parseInt(hgt.substring(0, hgt.indexOf("in")));
                return height >= 59 && height <= 76;
            }
            return false;
        }

//        public boolean isValid() {
//            boolean valid = true;
//            valid = valid && byr != null;
//            valid = valid && iyr != null;
//            valid = valid && eyr != null;
//            valid = valid && hgt != null;
//            valid = valid && hcl != null;
//            valid = valid && ecl != null;
//            valid = valid && pid != null;
//            return valid;
//        }

        public boolean isValid() {
            boolean valid = true;
            valid = valid && byr != null && byr >= 1920 && byr <= 2002;
            valid = valid && iyr != null && iyr >= 2010 && iyr <= 2020;
            valid = valid && eyr != null && eyr >= 2020 && eyr <= 2030;
            valid = valid && hgt != null && hgtValid();
            valid = valid && hcl != null && hairColorPattern.matcher(hcl).find();
            valid = valid && ecl != null && eclValid();
            valid = valid && pid != null && pid.length() == 9;
            return valid;
        }

        @Override
        public String toString() {
            return String.format("Passport{byr='%d', cid='%s', ecl='%s'[%s], eyr='%d', hcl='%s'[%s], hgt='%s'[%s], iyr='%d', pid='%s'}",
                    byr, cid, ecl, ecl != null && eclValid(), eyr, hcl, hcl != null && hairColorPattern.matcher(hcl).find(), hgt, hgt != null && hgtValid(), iyr, pid);
        }
    }
}
