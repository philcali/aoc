package me.philcali.aoc.notification;

import java.util.List;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;

public interface AdventOfCode {
    Leaderboard leaderboard(int year, String boardId, String sessionId);

    List<ProblemSummary> summaries(int year);

    Problem details(ProblemSummary summary);
}
