package me.philcali.aoc.day3;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent {
    private final Function<List<String>, Cell[][]> toMatrix;

    public ProblemOne(final Function<List<String>, Cell[][]> toMatrix) {
        this.toMatrix = toMatrix;
    }

    public ProblemOne() {
        this(new ClaimsToMatrixFunction());
    }

    @Override
    public int day() {
        return 3;
    }

    @Override
    public int problem() {
        return 1;
    }

    @Override
    public void run() {
        int twosOrMore = 0;
        for (Cell[] x : toMatrix.apply(readLines())) {
            for (Cell y : x) {
                if (Objects.nonNull(y) && y.getClaims().size() >= 2) {
                    twosOrMore++;
                }
            }
        }
        System.out.println("There are " + twosOrMore + " square inches of fabrics that overlap.");
    }
}
