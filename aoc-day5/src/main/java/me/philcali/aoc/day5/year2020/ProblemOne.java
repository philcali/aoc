package me.philcali.aoc.day5.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.List;

@Year(2020) @Day(5) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final List<Integer> seatIds = new BinaryPartitionFunction().apply(readLines());
        System.out.println("The answer is: " + seatIds.get(seatIds.size() - 1));
    }
}
