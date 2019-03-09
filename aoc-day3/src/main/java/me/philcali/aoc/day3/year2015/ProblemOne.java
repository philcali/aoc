package me.philcali.aoc.day3.year2015;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(3) @Problem(1) @Year(2015)
@Description("Perfectly Spherical Houses in a Vacuum")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        System.out.println("Houses with at least one present: " + DirectionsToHouses.uniqueSantas(1).apply(streamInput()).keySet().size());
    }
}
