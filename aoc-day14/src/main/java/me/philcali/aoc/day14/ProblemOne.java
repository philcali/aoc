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

@Day(14) @Problem(1) @Year(2018)
@Description("Chocolate Charts")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent {
    private static final int FIRST_ELF = 3;
    private static final int SECOND_ELF = 7;
    private static final int TARGET_RECIPES = 503761;
    private static final int RECIPE_NUMBERS = 10;

    @Override
    public void run() {
        final StringBuilder recipes = new StringBuilder().append(FIRST_ELF).append(SECOND_ELF);
        final List<Integer> elves = Arrays.asList(0, 1);
        while (recipes.length() < (RECIPE_NUMBERS + TARGET_RECIPES)) {
            Scoreboard.brew(elves, recipes);
        }
        System.out.println(recipes.toString().substring(TARGET_RECIPES, TARGET_RECIPES + RECIPE_NUMBERS));
    }
}
