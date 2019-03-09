package me.philcali.aoc.cli;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import me.philcali.aoc.common.DailyEvent;

public class App {
    private static final Options OPTIONS;
    static {
        OPTIONS = new Options();
        OPTIONS.addOption("h", "help", false, "Prints out this help menu.");
        OPTIONS.addOption("l", "list", false, "List all of the 'Advent of Code' days.");
        OPTIONS.addOption("t", "test", false, "Flag to use the test input.");
        OPTIONS.addOption(Option.builder()
                .argName("day")
                .desc("Run a specific 'Advent of Code' problem for a day")
                .longOpt("day")
                .hasArg()
                .type(Integer.class)
                .required(false)
                .build());
        OPTIONS.addOption(Option.builder()
                .argName("problem")
                .longOpt("problem")
                .desc("Run a specific 'Advent of Code' sub problem for a day")
                .hasArg()
                .type(Integer.class)
                .required(false)
                .build());
        OPTIONS.addOption(Option.builder()
                .argName("year")
                .longOpt("year")
                .desc("Run a specific 'Advent of Code' problem for a year")
                .hasArg()
                .type(Integer.class)
                .required(false)
                .build());
    }

    public static void main(String[] args) {
        final HelpFormatter formatter = new HelpFormatter();
        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine line = parser.parse(OPTIONS, args);
            if (line.hasOption("list")) {
                System.out.println("Listing available problems:");
                days().filter(event -> filterEvent(event, line)).forEach(event -> {
                    System.out.println(String.format("%d: Day %d: problem %d", event.year(), event.day(), event.problem()));
                });
            } else if (line.hasOption("day") && line.hasOption("problem") && line.hasOption("year")) {
                if (line.hasOption("test")) {
                    System.setProperty("INPUT", "test");
                }
                final Optional<DailyEvent> dailyEvent = days().filter(event -> filterEvent(event, line)).findFirst();
                if (dailyEvent.isPresent()) {
                    dailyEvent.ifPresent(event -> {
                        System.out.println("Running day " + event.day() + " problem " + event.problem() + ":");
                        event.run();
                    });
                } else {
                    System.out.println("Could not find a problem! Try listing the problems using --list");
                }
            } else {
                formatter.printHelp("aoc-cli", OPTIONS);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    private static boolean filterEvent(final DailyEvent event, final CommandLine line) {
        return Optional.ofNullable(line.getOptionValue("year")).map(year -> Integer.parseInt(year) == event.year()).orElse(true)
                && Optional.ofNullable(line.getOptionValue("day")).map(day -> Integer.parseInt(day) == event.day()).orElse(true)
                && Optional.ofNullable(line.getOptionValue("problem")).map(p -> Integer.parseInt(p) == event.problem()).orElse(true);
    }

    private static Stream<DailyEvent> days() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                ServiceLoader.load(DailyEvent.class).iterator(), Spliterator.NONNULL), false);
    }
}
