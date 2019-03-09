package me.philcali.aoc.day3.year2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

public class DirectionsToHouses implements Function<InputStream, Map<House, Integer>> {
    private final List<House> santas;

    public DirectionsToHouses(final List<House> santas) {
        this.santas = santas;
    }

    public static DirectionsToHouses uniqueSantas(final int numberOfSantas) {
        final List<House> santas = new ArrayList<>(numberOfSantas);
        IntStream.range(0, numberOfSantas).forEach(index -> {
            santas.add(new HouseData(0, 0));
        });
        return new DirectionsToHouses(santas);
    }

    @Override
    public Map<House, Integer> apply(final InputStream stream) {
        final Map<House, Integer> houses = new HashMap<>();
        final Map<Character, Function<House, House>> functions = new HashMap<>();
        functions.put('^', house -> new HouseData(house.latitude() - 1, house.longitude()));
        functions.put('v', house -> new HouseData(house.latitude() + 1, house.longitude()));
        functions.put('>', house -> new HouseData(house.latitude(), house.longitude() + 1));
        functions.put('<', house -> new HouseData(house.latitude(), house.longitude() - 1));
        int santaIndex = 0;
        try (final Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            houses.put(santas.get(santaIndex), 1);
            int letter = reader.read();
            while (letter != -1) {
                char direction = (char) letter;
                final House currentSanta = functions.get(direction).apply(santas.get(santaIndex));
                houses.compute(currentSanta, (h, presents) -> Objects.isNull(presents) ? 1 : presents + 1);
                santas.set(santaIndex, currentSanta);
                letter = reader.read();
                if (++santaIndex == santas.size()) {
                    santaIndex = 0;
                }
            }
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
        return houses;
    }
}
