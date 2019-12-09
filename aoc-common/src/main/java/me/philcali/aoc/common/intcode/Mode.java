package me.philcali.aoc.common.intcode;

import java.util.List;

public enum Mode {
    Position(0),
    Immediate(1);

    int value;

    Mode(final int value) {
        this.value = value;
    }

    public static Mode fromValue(final int value) {
        for (final Mode mode : Mode.values()) {
            if (mode.value() == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException();
    }

    public int value() {
        return value;
    }

    public int read(final int referenceOrValue, final List<Integer> memory) {
        switch (this) {
        case Position:
            return memory.get(referenceOrValue);
        default:
            return referenceOrValue;
        }
    }

    public void write(final int reference, final int value, final List<Integer> memory) {
        switch (this) {
        case Position:
            memory.set(reference, value);
            break;
        default:
        }
    }
}
