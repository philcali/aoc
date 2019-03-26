package me.philcali.aoc.day9.year2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@AutoService(DailyEvent.class)
@Year(2018) @Day(9) @Problem(1)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    private static final Pattern INPUT_PATTERN = Pattern.compile("(\\d+) players; last marble is worth (\\d+) points");

    private static class PlayerIterator implements Iterator<Integer> {
        private final int maxPlayers;
        private int currentPlayer = 0;

        public PlayerIterator(final int maxPlayers) {
            this.maxPlayers = maxPlayers;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            currentPlayer++;
            if (currentPlayer > maxPlayers) {
                currentPlayer = 1;
            }
            return currentPlayer;
        }
    }

    private int moveClockwise(final List<Integer> circle, final int currentIndex, final int spaces) {
        int index = currentIndex;
        for (int space = 0; space < spaces; space++) {
            if (index++ >= circle.size()) {
                index = 1;
            }
        }
        return index;
    }

    private int moveCounterClockwise(final List<Integer> circle, final int currentIndex, final int spaces) {
        int index = currentIndex;
        for (int space = 0; space < spaces; space++) {
            if (index-- <= 0) {
                index = circle.size() - 1;
            }
        }
        return index;
    }

    @Override
    public void run() {
        for (final String line : readLines()) {
            final Matcher matcher = INPUT_PATTERN.matcher(line);
            if (matcher.matches()) {
                final Map<Integer, Integer> scores = new HashMap<>();
                final Iterator<Integer> players = new PlayerIterator(Integer.parseInt(matcher.group(1)));
                final int lastMarble = Integer.parseInt(matcher.group(2));
                final List<Integer> circle = new ArrayList<>(Arrays.asList(0));
                int currentIndex = 0;
                for (int marble = 1; marble < lastMarble; marble++) {
                    final int player = players.next();
                    if (marble % 23 == 0) {
                        currentIndex = moveCounterClockwise(circle, currentIndex, 7);
                        final int marbleAt = circle.remove(currentIndex) + marble;
                        currentIndex = Math.min(currentIndex, circle.size() - 1);
                        scores.compute(player, (p, score) -> {
                            return Objects.isNull(score) ? marbleAt : score + marbleAt;
                        });
                    } else {
                        currentIndex = moveClockwise(circle, currentIndex, 2);
                        currentIndex = Math.min(currentIndex, circle.size());
                        circle.add(currentIndex, marble);
                    }
                }
                System.out.println("Highest score is: " + scores.values().stream()
                        .reduce(0, Math::max, Math::max));
            }
        }
    }
}
