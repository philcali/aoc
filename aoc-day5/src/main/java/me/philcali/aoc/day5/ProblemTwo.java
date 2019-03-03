package me.philcali.aoc.day5;

import java.util.Stack;
import java.util.function.Predicate;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent {
    @Override
    public int day() {
        return 5;
    }

    @Override
    public int problem() {
        return 2;
    }

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
