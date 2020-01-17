package me.philcali.aoc.notification;

import com.amazonaws.services.lambda.runtime.events.S3Event;

public class Notifications {
    public void updateChannels(final S3Event event) {
        System.out.println("Triggered");
    }
}
