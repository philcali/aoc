package me.philcali.aoc.day5.year2018;

import java.util.Stack;
import java.util.function.Predicate;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(5) @Problem(2) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final String alphabet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
        int reducedAmount = Integer.MAX_VALUE;
        for (int index = 0; index < alphabet.length(); index += 2) {
            final int i = index;
            final Predicate<Character> isTarget = c -> alphabet.charAt(i) == c || alphabet.charAt(i + 1) == c;
            final Stack<Character> backtrace = new StringToReactedPolimerFunction(isTarget.negate()).apply(streamInput());
            if (backtrace.size() < reducedAmount) {
                reducedAmount = backtrace.size();
            }
        }
        System.out.println("Reduced amount is " + reducedAmount);
    }
}
