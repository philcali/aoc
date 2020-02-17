package me.philcali.aoc.notification.channel;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;

public interface ChannelController<T extends Channel> {
    String metadata();

    Class<T> channelType();

    void sendNewProblem(T channel, Problem problem);

    void sendUpdatedLeaders(T channel, Leaderboard leaderboard);
}
