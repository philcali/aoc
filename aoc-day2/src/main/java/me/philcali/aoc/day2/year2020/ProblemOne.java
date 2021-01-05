package me.philcali.aoc.day2.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Year(2020) @Day(2) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        AtomicInteger validPasswords = new AtomicInteger();
        for (final String input : readLines()) {
            PasswordPolicy.fromInput(input).ifPresent(policy -> {
                final Pattern passwordPattern = Pattern.compile(String.format("(%s)", policy.character()));
                final Matcher passwordMatcher = passwordPattern.matcher(policy.password());
                int hits = 0;
                while (passwordMatcher.find()) {
                    hits++;
                }
                if (hits >= policy.firstPosition() && hits <= policy.secondPosition()) {
                    validPasswords.incrementAndGet();
                }
            });
        }
        System.out.println("The answer is: " + validPasswords.get());
    }
}
