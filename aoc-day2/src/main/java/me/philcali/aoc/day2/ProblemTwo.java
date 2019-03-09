package me.philcali.aoc.day2;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent {
    @Override
    public int day() {
        return 2;
    }

    @Override
    public int problem() {
        return 2;
    }

    @Override
    public int year() {
        return 2018;
    }

    @Override
    public void run() {
        final List<String> previousBoxes = new ArrayList<>();
        String foundLetters = "";
        int min = Integer.MIN_VALUE;
        for (final String line : readLines()) {
            for (final String previousLine : previousBoxes) {
                final StringBuilder builder = new StringBuilder();
                for (int index = 0; index < previousLine.length(); index++) {
                    if (previousLine.charAt(index) == line.charAt(index)) {
                        builder.append(previousLine.charAt(index));
                    }
                }
                if (builder.length() > min) {
                    foundLetters = builder.toString();
                    min = foundLetters.length();
                }
            }
            previousBoxes.add(line);
        }
        System.out.println("Most common box: " + foundLetters);
    }
}
