package me.philcali.aoc.day1.year2016;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepGenerator implements Iterator<List<Integer>> {
    private static final Pattern DIR_STEP = Pattern.compile("([LR])(\\d+)");
    private final List<String> sourceLines;
    private Iterator<String> lines;
    private Iterator<String> steps;
    private Iterator<List<Integer>> smallerSteps;
    private Direction currentDirection = Direction.NORTH;
    private List<Integer> currentPosition = Arrays.asList(0, 0);

    public StepGenerator(final List<String> sourceLines) {
        this.sourceLines = sourceLines;
    }

    private void refreshSteps() {
        final String[] smallSteps = lines.next().split("\\s*,\\s*");
        steps = Arrays.asList(smallSteps).iterator();
    }

    private void refreshSmallerSteps() {
        final Matcher matcher = DIR_STEP.matcher(steps.next());
        if (matcher.matches()) {
            currentDirection = currentDirection.rotate(matcher.group(1));
            final List<List<Integer>> coords = currentDirection.move(Integer.parseInt(matcher.group(2)));
            smallerSteps = coords.iterator();
        }
    }

    @Override
    public boolean hasNext() {
        if (Objects.isNull(lines)) {
            lines = sourceLines.iterator();
        }
        if ((Objects.isNull(steps) || !steps.hasNext()) && lines.hasNext()) {
            refreshSteps();
        }
        if ((Objects.isNull(smallerSteps) || !smallerSteps.hasNext()) && steps.hasNext()) {
            refreshSmallerSteps();
        }
        return smallerSteps.hasNext();
    }

    @Override
    public List<Integer> next() {
        return step(smallerSteps.next());
    }

    private List<Integer> step(final List<Integer> smallerStep) {
        currentPosition.set(0, currentPosition.get(0) + smallerStep.get(0));
        currentPosition.set(1, currentPosition.get(1) + smallerStep.get(1));
        return Arrays.asList(currentPosition.get(0), currentPosition.get(1));
    }
}
