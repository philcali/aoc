package me.philcali.aoc.notification;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.model.LeaderboardSession;

public interface AdventOfCodeStorage extends AdventOfCode {
    void updateLeaders(LeaderboardSession session, Leaderboard leaderboard);

    void addProblem(Problem problem);
}
