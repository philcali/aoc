package me.philcali.aoc.day12.year2018;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2018) @Day(12) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    private static final Pattern INITIAL_STATE = Pattern.compile("initial state:\\s+(.+)");
    private static final Pattern GROWTH_PATTERN = Pattern.compile("([^\\s]+)\\s*=>\\s*(.+)");
    private static final int GENERATIONS = 20;
    private static final int PADDED_DIRECTIONS = 2;

    @Override
    public void run() {
        final List<String> lines = readLines();
        final Map<String, Character> growthPatterns = new HashMap<>();
        String pots = "";
        for (int index = 0; index < lines.size(); index++) {
            final String line = lines.get(index);
            final Matcher initialState = INITIAL_STATE.matcher(line);
            final Matcher growthPattern = GROWTH_PATTERN.matcher(line);
            if (initialState.matches()) {
                pots = initialState.group(1);
            } else if (growthPattern.matches()) {
                growthPatterns.put(growthPattern.group(1), growthPattern.group(2).toCharArray()[0]);
            }
        }


        for (int generation = 0; generation < GENERATIONS; generation++) {
            final StringBuilder builder = new StringBuilder();
            for (int window = 0; window < pots.length(); window++) {
                final StringBuilder groupBuilder = new StringBuilder()
                        .append("..")
                        .append(pots.charAt(window))
                        .append("..");
                for (int direction = 1; direction <= PADDED_DIRECTIONS; direction++) {
                    if (window + direction < pots.length()) {
                        groupBuilder.setCharAt(direction + PADDED_DIRECTIONS, pots.charAt(window + direction));
                    }
                    if (window - direction >= 0) {
                        groupBuilder.setCharAt(PADDED_DIRECTIONS - direction, pots.charAt(window - direction));
                    }
                }
                final String group = groupBuilder.toString();
                if (growthPatterns.containsKey(group)) {
                    final char result = growthPatterns.get(group);
                    builder.append(result);
                } else {
                    builder.append(".");
                }
            }
            pots = builder.insert(0, "..").append("..").toString();
        }

        int totalPlantsProduced = 0;
        for (int potIndex = 0; potIndex < pots.length(); potIndex++) {
            final int potNumber = potIndex - (GENERATIONS * PADDED_DIRECTIONS);
            if (pots.charAt(potIndex) == '#') {
                totalPlantsProduced += potNumber;
            }
        }
        System.out.println("Total plant producing pots: " + totalPlantsProduced);
    }
}
