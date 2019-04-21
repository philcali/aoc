package me.philcali.aoc.day11;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface PowerCell {
    @NonNull
    int x();

    @NonNull
    int y();

    default int rackId() {
        return 10 + x();
    }

    default int powerLevel() {
        return rackId() * y();
    }

    default int largestTotalPower(final int serialNumber) {
        final int increasedPower = powerLevel() + serialNumber;
        final int newPowerLevel = increasedPower * rackId();
        final String stringPowerLevel = Integer.toString(newPowerLevel);
        final int rowId = newPowerLevel < 100 ? 0 : Character.digit(stringPowerLevel.charAt(stringPowerLevel.length() - 3), 10);
        return rowId - 5;
    }
}
