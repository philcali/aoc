package me.philcali.aoc.day7.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Year(2020) @Day(7) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    private static final Pattern CONTAINER = Pattern.compile("^(\\w+ \\w+) bags? contain (.+)$");
    private static final Pattern COLOR = Pattern.compile("(\\d+) (\\w+ \\w+) bags?");

    @Override
    public void run() {
        final Map<String, Map<String, Integer>> containerToNumberContained = new HashMap<>();
        for (final String line : readLines()) {
            final Matcher matcher = CONTAINER.matcher(line);
            if (matcher.matches()) {
                final String container = matcher.group(1);
                containerToNumberContained.putIfAbsent(container, new HashMap<>());
                final String contained = matcher.group(2);
                final Matcher colorMatcher = COLOR.matcher(contained);
                while (colorMatcher.find()) {
                    final int number = Integer.parseInt(colorMatcher.group(1));
                    final String color = colorMatcher.group(2);
                    containerToNumberContained.get(container).put(color, number);
                }
            }
        }
        System.out.println("The answer is: " + countContained(containerToNumberContained, "shiny gold"));
    }

    private long countContained(
            final Map<String, Map<String, Integer>> containers,
            final String color) {
        final Map<String, Integer> containedNumber = containers.get(color);
        return containedNumber.entrySet().stream()
                .map(entry -> (countContained(containers, entry.getKey()) * entry.getValue()) + entry.getValue())
                .reduce(0L, (left, right) -> left + right);
    }
}
