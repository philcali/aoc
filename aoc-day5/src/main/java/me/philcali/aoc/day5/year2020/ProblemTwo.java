package me.philcali.aoc.day5.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

@Year(2020) @Day(5) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final List<Integer> seatIds = new BinaryPartitionFunction().apply(readLines());
        final Set<Integer> possibleNumbers = new HashSet<>();
        IntStream.range(seatIds.get(0), seatIds.get(seatIds.size() - 1)).forEach(possibleNumbers::add);
        final Iterator<Integer> iterator = seatIds.listIterator();
        while (iterator.hasNext()) {
            possibleNumbers.remove(iterator.next());
        }
        System.out.println("The answer is: " + possibleNumbers);
    }
}
