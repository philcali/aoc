package me.philcali.aoc.notification;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

import me.philcali.aoc.notification.event.Event;
import me.philcali.aoc.notification.event.Events;
import me.philcali.aoc.notification.module.NotificationComponent;
import me.philcali.aoc.notification.s3.S3EventNotificationRecordParser;

@RunWith(MockitoJUnitRunner.class)
public class NotificationsTest {
    private Notifications notifications;
    @Mock
    private NotificationComponent component;
    private S3Event event;
    @Mock
    private S3EventNotificationRecord recordA;
    @Mock
    private S3EventNotificationRecord recordB;
    @Mock
    private Event eventA;
    @Mock
    private Events events;
    @Mock
    private S3EventNotificationRecordParser parser;

    @Before
    public void setUp() {
        notifications = new Notifications(component);
        event = new S3Event(Arrays.asList(recordA, recordB));
    }

    @Test
    public void testUpdateChannels() {
        doReturn(events).when(component).eventBus();
        doReturn(parser).when(component).parser();
        doReturn(Optional.of(eventA)).when(parser).parse(eq(recordA));
        doReturn(Optional.empty()).when(parser).parse(eq(recordB));
        notifications.updateChannels(event);
        verify(eventA).writeTo(eq(events));
    }
}
