package me.philcali.aoc.notification;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;

public interface Events {
    void newProblem(Problem problem);

    void updatedLeaders(Leaderboard leaderboard);
}
