package me.philcali.aoc.day3;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent {
    private final Function<List<String>, Cell[][]> toMatrix;

    public ProblemTwo(final Function<List<String>, Cell[][]> toMatrix) {
        this.toMatrix = toMatrix;
    }

    public ProblemTwo() {
        this(new ClaimsToMatrixFunction());
    }

    @Override
    public int day() {
        return 3;
    }

    @Override
    public int problem() {
        return 2;
    }

    @Override
    public void run() {
        Arrays.stream(toMatrix.apply(readLines()))
        .flatMap(Arrays::stream)
        .filter(Objects::nonNull)
        .flatMap(cell -> cell.getClaims().stream())
        .filter(claim -> !claim.isOverlaps())
        .findFirst()
        .ifPresent(claim -> {
            System.out.println("Claim " + claim.id() + " does not overlap");
        });
    }
}
