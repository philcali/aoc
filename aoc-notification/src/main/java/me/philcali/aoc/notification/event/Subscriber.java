package me.philcali.aoc.notification.event;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;

public interface Subscriber {
    void onNewProblem(Problem problem);

    void onUpdatedLeaders(Leaderboard leaderboard);
}
