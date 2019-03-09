package me.philcali.aoc.day5;

import java.io.InputStream;
import java.util.Stack;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent {
    private final Function<InputStream, Stack<Character>> inputToBacktrace;

    public ProblemOne(final Function<InputStream, Stack<Character>> inputToBacktrace) {
        this.inputToBacktrace = inputToBacktrace;
    }

    public ProblemOne() {
        this(new StringToReactedPolimerFunction());
    }

    @Override
    public int day() {
        return 5;
    }

    @Override
    public int problem() {
        return 1;
    }

    @Override
    public int year() {
        return 2018;
    }

    @Override
    public void run() {
        final Stack<Character> backtrace = inputToBacktrace.apply(streamInput());
        System.out.println(backtrace.size());
    }
}
