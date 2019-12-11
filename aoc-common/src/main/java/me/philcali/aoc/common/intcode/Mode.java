package me.philcali.aoc.common.intcode;

import java.util.List;

public enum Mode {
    Position(0),
    Immediate(1),
    Relative(2);

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

    private long getOrExpandMemory(final int index, final List<Long> memory) {
        if (index >= memory.size()) {
            for (int times = memory.size() - 1; times < index; times++) {
                memory.add(0l);
            }
        }
        return memory.get(index);
    }

    // TODO: replace this with a program context
    public long read(final long referenceOrValue, final int relativeBase, final List<Long> memory) {
        switch (this) {
        case Position:
            return getOrExpandMemory((int) referenceOrValue, memory);
        case Relative:
            return getOrExpandMemory(relativeBase + (int) referenceOrValue, memory);
        default:
            return referenceOrValue;
        }
    }

    public long read(final long referenceOrValue, final List<Long> memory) {
        return read(referenceOrValue, 0, memory);
    }

    public void write(final long reference, final int relativeBase, final long value, final List<Long> memory) {
        switch (this) {
        case Position:
            getOrExpandMemory((int) reference, memory);
            memory.set((int) reference, value);
            break;
        case Relative:
            getOrExpandMemory(relativeBase + (int) reference, memory);
            memory.set(relativeBase + (int) reference, value);
            break;
        default:
        }
    }

    public void write(final long reference, final long value, final List<Long> memory) {
        write(reference, 0, value, memory);
    }
}
