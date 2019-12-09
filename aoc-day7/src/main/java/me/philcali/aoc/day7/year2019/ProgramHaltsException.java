package me.philcali.aoc.day7.year2019;

public class ProgramHaltsException extends RuntimeException {
    private static final long serialVersionUID = -3031198642499364969L;

    public ProgramHaltsException() {
        super("Halting");
    }
}
