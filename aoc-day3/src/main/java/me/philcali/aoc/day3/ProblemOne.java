package me.philcali.aoc.day3;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(3) @Problem(1) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    private final Function<List<String>, Cell[][]> toMatrix;

    public ProblemOne(final Function<List<String>, Cell[][]> toMatrix) {
        this.toMatrix = toMatrix;
    }

    public ProblemOne() {
        this(new ClaimsToMatrixFunction());
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
