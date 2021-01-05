package me.philcali.aoc.day4.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Year(2020) @Day(4) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    private Predicate<String> length(int rule) {
        return value -> value.length() == rule;
    }

    private Predicate<String> isBetween(int start, int end) {
        return value -> {
            try {
                int parsedYear = Integer.parseInt(value);
                return parsedYear >= start && parsedYear <= end;
            } catch (Exception ex) {
                return false;
            }
        };
    }

    private Predicate<String> height(String unit, int min, int max) {
        return value -> {
            final Pattern pattern = Pattern.compile("(\\d+)" + unit);
            final Matcher matcher = pattern.matcher(value);
            if (matcher.matches()) {
                return isBetween(min, max).test(matcher.group(1));
            }
            return false;
        };
    }

    private Predicate<String> regex(String pattern) {
        return value -> Pattern.matches(pattern, value);
    }

    private Predicate<String> inSet(String...values) {
        return value -> Arrays.stream(values).anyMatch(value::equals);
    }

    @Override
    public void run() {
        final PassportScanner scanner = PassportScannerData.builder()
                .putCodes("byr", length(4).and(isBetween(1920, 2002)))
                .putCodes("iyr", length(4).and(isBetween(2010, 2020)))
                .putCodes("eyr", length(4).and(isBetween(2020, 2030)))
                .putCodes("hgt", height("cm", 150, 193).or(height("in", 59, 76)))
                .putCodes("hcl", regex("#[0-9a-f]{6}"))
                .putCodes("ecl", inSet("amb", "blu", "brn", "gry", "grn", "hzl", "oth"))
                .putCodes("pid", regex("\\d{9}"))
                .putCodes("cid", value -> true)
                .build();
        final List<Map<String, String>> passports = scanner.scanBatchFile(readLines());
        System.out.println("The answer is: " + passports.size());
    }
}
