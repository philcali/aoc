package me.philcali.aoc.day2.year2015;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(2) @Problem(2) @Year(2015)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final int totalRibbon = readLines().stream().map(Prism::fromLine).reduce(0,
                (left, right) -> left + product(right) + ribbon(right),
                (left, right) -> left);
        System.out.println("Total ribbon: " + totalRibbon);
    }

    private int product(final Prism prism) {
        return prism.width() * prism.height() * prism.length();
    }

    private int ribbon(final Prism prism) {
        final Side smallestSide = prism.sides().get(0);
        return (smallestSide.height() * 2) + (smallestSide.width() * 2);
    }
}
