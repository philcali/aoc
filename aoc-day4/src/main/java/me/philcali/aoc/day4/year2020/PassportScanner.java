package me.philcali.aoc.day4.year2020;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data @Builder
public interface PassportScanner {
    Map<String, Predicate<String>> codes();

    default boolean isValid(final Map<String, String> passport) {
        final Set<String> keyValidation = new HashSet<>(codes().keySet());
        keyValidation.remove("cid");
        return passport.keySet().containsAll(keyValidation) && passport.entrySet().stream()
                .map(entry -> codes().get(entry.getKey()).test(entry.getValue()))
                .reduce(true, (left, right) -> left && right);
    }

    default List<Map<String, String>> scanBatchFile(final List<String> lines) {
        final Pattern codeRegex = Pattern.compile("(" + codes().keySet().stream()
                .collect(Collectors.joining("|")) + "):([^\\s]+)");
        List<Map<String, String>> passports = new ArrayList<>();
        Map<String, String> currentPassport = new HashMap<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                if (isValid(currentPassport)) {
                    passports.add(currentPassport);
                }
                currentPassport = new HashMap<>();
            }
            final Matcher matcher = codeRegex.matcher(line);
            while (matcher.find()) {
                currentPassport.put(matcher.group(1), matcher.group(2));
            }
        }
        if (isValid(currentPassport)) {
            passports.add(currentPassport);
        }
        return Collections.unmodifiableList(passports);
    }
}
