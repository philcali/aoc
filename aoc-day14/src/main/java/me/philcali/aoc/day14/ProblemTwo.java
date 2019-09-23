package me.philcali.aoc.day14;

import java.util.Arrays;
import java.util.List;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Problem(2) @Day(14) @Year(2018)
@Description("Chocolate Charts: Part Two")
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent {
    private static final int FIRST_ELF = 3;
    private static final int SECOND_ELF = 7;
    private static final String PUZZLE_INPUT = "503761";

    private int indexOfRecipe(final StringBuilder recipes) {
        if (recipes.length() <= PUZZLE_INPUT.length()) {
            return -1;
        } else if (recipes.substring(recipes.length() - (PUZZLE_INPUT.length() + 1)).contains(PUZZLE_INPUT)) {
            return recipes.lastIndexOf(PUZZLE_INPUT);
        } else {
            return -1;
        }
    }

    @Override
    public void run() {
        final List<Integer> elves = Arrays.asList(0, 1);
        final StringBuilder recipes = new StringBuilder().append(FIRST_ELF).append(SECOND_ELF);

        int attempts = 0;
        int firstIndex = -1;
        while (firstIndex == -1) {
            Scoreboard.brew(elves, recipes);
            firstIndex = indexOfRecipe(recipes);
            if (++attempts % 10000 == 0) {
                System.out.println("Ran it " + attempts + " times ... still no dice.");
            }
        }
        System.out.println(firstIndex);
    }
}
