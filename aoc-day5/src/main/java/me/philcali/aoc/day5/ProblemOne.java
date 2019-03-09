package me.philcali.aoc.day5;

import java.io.InputStream;
import java.util.Stack;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(5) @Problem(1) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    private final Function<InputStream, Stack<Character>> inputToBacktrace;

    public ProblemOne(final Function<InputStream, Stack<Character>> inputToBacktrace) {
        this.inputToBacktrace = inputToBacktrace;
    }

    public ProblemOne() {
        this(new StringToReactedPolimerFunction());
    }

    @Override
    public void run() {
        final Stack<Character> backtrace = inputToBacktrace.apply(streamInput());
        System.out.println(backtrace.size());
    }
}
