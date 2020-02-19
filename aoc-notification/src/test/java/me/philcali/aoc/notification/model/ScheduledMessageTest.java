package me.philcali.aoc.notification.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ScheduledMessageTest {
    private ScheduledMessage message;

    @Before
    public void setUp() {
        message = ScheduledMessageData.builder()
                .withDay(6)
                .withYear(2019)
                .build();
    }

    @Test
    public void testDay() {
        assertEquals(6, message.dayOrCurrent(10));
    }

    @Test
    public void testYear() {
        assertEquals(2019, message.yearOrCurrent(2020));
    }

    @Test
    public void testCurrentDay() {
        assertEquals(10, ScheduledMessageData.builder()
                .build()
                .dayOrCurrent(10));
    }

    @Test
    public void testCurrentYear() {
        assertEquals(2020, ScheduledMessageData.builder()
                .build()
                .yearOrCurrent(2020));
    }
}
