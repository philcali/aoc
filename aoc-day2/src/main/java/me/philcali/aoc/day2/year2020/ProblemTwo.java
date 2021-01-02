package me.philcali.aoc.day2.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.concurrent.atomic.AtomicInteger;

@Year(2020) @Day(2) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        AtomicInteger validPasswords = new AtomicInteger();
        for (final String input : readLines()) {
            PasswordPolicy.fromInput(input).ifPresent(policy -> {
                int found = 0;
                if (policy.password().charAt(policy.firstPosition() - 1) == policy.character().charAt(0)) {
                    found++;
                }
                if (policy.password().charAt(policy.secondPosition() - 1) == policy.character().charAt(0)) {
                    found++;
                }
                if (found == 1) {
                    validPasswords.incrementAndGet();
                }
            });
        }
        System.out.println("The answer is: " + validPasswords.get());
    }
}
