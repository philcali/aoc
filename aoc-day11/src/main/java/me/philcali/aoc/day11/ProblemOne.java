package me.philcali.aoc.day11;

import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(11) @Problem(1) @Year(2018)
@Description("Chronal Charge")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent {
    private static final int SERIAL_NUMBER = 8772;
    private static final int SIZE = 300;
    private static final int TEST_SIZE = 3;

    @Override
    public void run() {
        final PowerCell[][] grid = new PowerCell[SIZE][SIZE];
        IntStream.range(0, SIZE).forEach(y -> {
            IntStream.range(0, SIZE).forEach(x -> {
                grid[y][x] = new PowerCellData(x, y);
            });
        });

        PowerCell largestCoordinate = null;
        int largestAreaValue = 0;
        for (int row = 0; row < SIZE - TEST_SIZE; row++) {
            for (int col = 0; col < SIZE - TEST_SIZE; col++) {
                // Test smaller grid
                int areaValue = 0;
                for (int y = 0; y < TEST_SIZE; y++) {
                    for (int x = 0; x < TEST_SIZE; x++) {
                        areaValue += grid[row + y][col + x].largestTotalPower(SERIAL_NUMBER);
                    }
                }
                if (areaValue > largestAreaValue) {
                    largestAreaValue = areaValue;
                    largestCoordinate = grid[row][col];
                }
            }
        }
        System.out.println("The largest 3x3 coordinate at " + largestAreaValue + " is: " + largestCoordinate);
    }
}
