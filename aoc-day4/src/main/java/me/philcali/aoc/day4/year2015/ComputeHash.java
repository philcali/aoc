package me.philcali.aoc.day4.year2015;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputeHash implements BiFunction<String, Integer, Long> {

    @Override
    public Long apply(final String secret, final Integer zeros) {
        final char[] zero = new char[zeros];
        Arrays.fill(zero, '0');
        final Pattern lookup = Pattern.compile("^" + new String(zero) + ".+");
        final AtomicLong counter = new AtomicLong();
        do {
            final String converted = md5(secret + counter.getAndIncrement());
            final Matcher matcher = lookup.matcher(converted);
            if (matcher.matches()) {
                break;
            }
        } while (true);
        return counter.decrementAndGet();
    }

    private String md5(final String input) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(StandardCharsets.UTF_8));
            return String.format("%032x", new BigInteger(digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
