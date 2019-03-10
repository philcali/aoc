package me.philcali.aoc.day4.year2018;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface GuardRotationEvent extends Comparable<GuardRotationEvent> {
    enum Action {
        BEGINS_SHIFT("begins shift"),
        WAKES_UP("wakes up"),
        FALLS_ASLEEP("falls asleep");

        private String friendlyAction;

        Action(final String friendlyAction) {
            this.friendlyAction = friendlyAction;
        }

        public String getFriendlyAction() {
            return friendlyAction;
        }

        public static Action fromLine(final String line) {
            return Arrays.stream(Action.values())
                    .filter(action -> line.endsWith(action.friendlyAction))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Line " + line + " does not contain an action!"));
        }
    }

    @NonNull
    LocalDateTime timestamp();

    Optional<String> guardId();

    Action action();

    @Override
    default public int compareTo(final GuardRotationEvent event) {
        return timestamp().compareTo(event.timestamp());
    }
}
