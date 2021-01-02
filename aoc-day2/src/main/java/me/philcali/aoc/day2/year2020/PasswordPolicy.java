package me.philcali.aoc.day2.year2020;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public interface PasswordPolicy {
    Pattern POLICY_PATTERN = Pattern.compile("^(\\d+)-(\\d+)\\s*(\\w):\\s*(.+)");

    @NonNull
    String password();

    @NonNull
    String character();

    @NonNull
    int firstPosition();

    @NonNull
    int secondPosition();

    static Optional<PasswordPolicy> fromInput(final String input) {
        final Matcher matcher = POLICY_PATTERN.matcher(input);
        if (matcher.matches()) {
            return Optional.of(new PasswordPolicyData(
                    matcher.group(3),
                    matcher.group(4),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(1))
            ));
        }
        return Optional.empty();
    }
}
