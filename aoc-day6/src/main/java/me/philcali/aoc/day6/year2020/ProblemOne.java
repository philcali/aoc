package me.philcali.aoc.day6.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Year(2020) @Day(6) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final Set<Character> questionGroup = new HashSet<>();
        int counts = 0;
        for (final String line : readLines()) {
            if (line.isEmpty()) {
                counts += questionGroup.size();
                questionGroup.clear();
                continue;
            }
            for (final char question : line.toCharArray()) {
                questionGroup.add(question);
            }
        }
        counts += questionGroup.size();
        System.out.println("The answer is: " + counts);
    }
}
