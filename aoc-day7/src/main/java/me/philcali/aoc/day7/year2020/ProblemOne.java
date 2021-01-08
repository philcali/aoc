package me.philcali.aoc.day7.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Year(2020) @Day(7) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    private static final Pattern CONTAINER = Pattern.compile("^(\\w+ \\w+) bags? contain (.+)$");
    private static final Pattern COLOR = Pattern.compile("\\d+ (\\w+ \\w+) bags?");

    @Override
    public void run() {
        final Map<String, Set<String>> containedToContainer = new HashMap<>();
        for (final String line : readLines()) {
            final Matcher matcher = CONTAINER.matcher(line);
            if (matcher.matches()) {
                final String container = matcher.group(1);
                containedToContainer.putIfAbsent(container, new HashSet<>());
                final String contained = matcher.group(2);
                final Matcher colorMatcher = COLOR.matcher(contained);
                while (colorMatcher.find()) {
                    final String color = colorMatcher.group(1);
                    containedToContainer.compute(color, (c, set) -> {
                        if (Objects.isNull(set)) {
                            set = new HashSet<>();
                        }
                        set.add(container);
                        return set;
                    });
                }
            }
        }
        final Set<String> counted = new HashSet<>();
        Set<String> containers = containedToContainer.get("shiny gold");
        while (!containers.isEmpty()) {
            counted.addAll(containers);
            final Set<String> nextIteration = new HashSet<>();
            for (final String container : containers) {
                nextIteration.addAll(containedToContainer.get(container));
            }
            containers = nextIteration;
        }
        System.out.println("The answer is: " + counted.size());
    }
}
