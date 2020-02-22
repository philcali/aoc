package me.philcali.aoc.day10.year2019;

import java.util.Set;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.geometry.Point;

@Year(2019) @Day(10) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    public static void main(final String[] args) {
        //System.setProperty("INPUT", "test");
        new ProblemOne().run();
    }

    @Override
    public void run() {
        final Set<Point> asteroids = Starfield.asteroids(readLines());

        System.out.println("Astroids " + asteroids.size());
        final MonitoringStation station = MonitoringStation.optimal(asteroids);
        System.out.println("Chosen base is " + station.asteroid() + " with " + station.visibleAsteroids() + " asteroids");
    }
}
