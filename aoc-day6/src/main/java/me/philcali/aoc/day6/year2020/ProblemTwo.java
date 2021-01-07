package me.philcali.aoc.day6.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.*;

@Year(2020) @Day(6) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    private Integer consensus(final List<Set<Character>> uniquenessInGroup) {
        final Map<Character, Integer> questionCount = new HashMap<>();
        for (final Set<Character> questions : uniquenessInGroup) {
            questions.forEach(question -> {
                questionCount.compute(question, (q, count) -> {
                    if (Objects.isNull(count)) {
                        return 1;
                    }
                    return count + 1;
                });
            });
        }
        return (int) questionCount.values().stream()
                .filter(count -> count == uniquenessInGroup.size())
                .count();
    }

    @Override
    public void run() {
        final List<Set<Character>> uniquenessInGroup = new ArrayList<>();
        int counts = 0;
        for (final String line : readLines()) {
            if (line.isEmpty()) {
                counts += consensus(uniquenessInGroup);
                uniquenessInGroup.clear();
                continue;
            }
            final Set<Character> questionsForPerson = new HashSet<>();
            for (final char question : line.toCharArray()) {
                questionsForPerson.add(question);
            }
            uniquenessInGroup.add(questionsForPerson);
        }
        counts += consensus(uniquenessInGroup);
        System.out.println("The answer is: " + counts);
    }
}
