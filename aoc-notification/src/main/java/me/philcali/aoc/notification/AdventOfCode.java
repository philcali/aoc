package me.philcali.aoc.notification;

import java.util.List;
import java.util.Optional;

import me.philcali.aoc.notification.exception.AdventOfCodeException;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;
import me.philcali.aoc.notification.model.LeaderboardSession;

public interface AdventOfCode {
    Optional<Leaderboard> leaderboard(int year, LeaderboardSession session) throws AdventOfCodeException;

    List<ProblemSummary> summaries(int year) throws AdventOfCodeException;

    Optional<Problem> details(ProblemSummary summary) throws AdventOfCodeException;
}
