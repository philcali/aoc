package me.philcali.aoc.notification;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.model.Channel;

public interface ChannelController<T extends Channel> {
    String metadata();

    Class<T> channelType();

    void sendNewProblem(T channel, Problem problem);

    void sendUpdatedLeaders(T channel, Leaderboard leaderboard);
}
