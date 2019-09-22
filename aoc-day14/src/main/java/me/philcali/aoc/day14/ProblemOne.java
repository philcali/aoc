package me.philcali.aoc.day14;

import java.util.ArrayList;
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
        final List<Integer> recipes = new ArrayList<>();
        recipes.add(FIRST_ELF);
        recipes.add(SECOND_ELF);

        int elf1 = 0;
        int elf2 = 1;

        while (recipes.size() < (RECIPE_NUMBERS + TARGET_RECIPES)) {
            final int elf1Recipe = recipes.get(elf1);
            final int elf2Recipe = recipes.get(elf2);
            final int brew = elf1Recipe + elf2Recipe;
            Integer.toString(brew).chars().map(Character::getNumericValue).forEach(recipes::add);

            elf1 += elf1Recipe + 1;
            if (elf1 >= recipes.size()) {
                elf1 = elf1 % recipes.size();
            }

            elf2 += elf2Recipe + 1;
            if (elf2 >= recipes.size()) {
                elf2 = elf2 % recipes.size();
            }
        }
        recipes.stream().skip(TARGET_RECIPES).limit(RECIPE_NUMBERS).forEach(System.out::print);
        System.out.println();
    }
}
