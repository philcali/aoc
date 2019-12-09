package me.philcali.aoc.day8.year2019;

import java.util.ArrayList;
import java.util.List;

public class Layers {
    public static final int WIDTH = 25;
    public static final int HEIGHT = 6;

    public static List<String> fromInput(final List<String> lines) {
        final String line = lines.get(0);
        final List<String> layers = new ArrayList<>();
        int index = 0;
        while (index < line.length()) {
            final int size = WIDTH * HEIGHT;
            final String layer = line.substring(index, index + size);
            layers.add(layer);
            index += size;
        }
        return layers;
    }
}
