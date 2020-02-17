package me.philcali.aoc.notification.s3;

import java.util.Optional;

import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

import me.philcali.aoc.notification.event.Event;

public interface S3EventNotificationRecordParser {
    Optional<Event> parse(S3EventNotificationRecord record);
}
