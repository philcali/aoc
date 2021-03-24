package me.philcali.aoc.day8.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Problem((1)) @Day(8) @Year(2020)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    private static final Pattern INSTRUCTION = Pattern.compile("([a-z]{3})\\s+(\\-|\\+)(.+)");

    @Override
    public void run() {
        long accumulator = 0;
        final Set<Integer> visited = new HashSet<>();
        final List<String> lines = readLines();
        for (int index = 0; index < lines.size(); index++) {
            final String line = lines.get(index);
            System.out.println("line: " + line + ", Index: " + index);
            if (!visited.add(index)) {
                break;
            }
            final Matcher matcher = INSTRUCTION.matcher(line);
            if (matcher.matches()) {
                final String operation = matcher.group(1);
                final String sign = matcher.group(2);
                final long value = Long.parseLong(matcher.group(3));
                switch (operation) {
                    case "acc":
                        accumulator += sign.equals("+") ? value : -1 * value;
                        break;
                    case "jmp":
                        long offset = value;
                        if (sign.equals("+")) {
                            offset -= 1;
                        } else {
                            offset = (offset * -1) - 1;
                        }
                        index += offset;
                        break;
                    case "nop":
                        break;
                }
            }
        }
        System.out.println("Answer: " + accumulator);
    }
}
