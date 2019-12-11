package me.philcali.aoc.common.intcode;

public class ProgramHaltsException extends RuntimeException {
    private static final long serialVersionUID = -3031198642499364969L;

    public ProgramHaltsException() {
        super("Halting");
    }
}
