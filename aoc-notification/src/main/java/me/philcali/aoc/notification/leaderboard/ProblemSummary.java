package me.philcali.aoc.notification.leaderboard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.EqualsAndHashCode;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @EqualsAndHashCode @ToString
@JsonDeserialize(builder = ProblemSummaryData.Builder.class)
public interface ProblemSummary extends Comparable<ProblemSummary> {
    int day();

    int year();

    @Override
    default int compareTo(final ProblemSummary other) {
        final int yearCompare = Integer.compare(year(), other.year());
        if (yearCompare == 0) {
            return Integer.compare(day(), other.day());
        }
        return yearCompare;
    }
}
