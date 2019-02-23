package me.philcali.aoc.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public interface DailyInputEvent extends DailyEvent {
    default List<String> readLines() {
        final List<String> lines = new ArrayList<>();
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/" + day() + ".txt"), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line.trim());
                line = reader.readLine();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return lines;
    }
}
