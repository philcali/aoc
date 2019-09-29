package me.philcali.aoc.day15;

public class BattleFinishedEarlyException extends RuntimeException {
    private static final long serialVersionUID = 5559544374237786495L;

    public BattleFinishedEarlyException(final String message) {
        super(message);
    }
}
