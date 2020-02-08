package me.philcali.aoc.notification.module;

import java.util.Calendar;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnvironmentModule {
    public static final String ADVENT_YEAR = "ADVENT_YEAR";
    public static final String ADVENT_DAY = "ADVENT_DAY";
    public static final String ROOM_URL = "ROOM_URL";
    public static final String SESSION_ID = "SESSION_ID";
    public static final String BUCKET_NAME = "BUCKET_NAME";
    public static final String SESSIONS_PREFIX = "SESSIONS_PREFIX";

    @Provides
    @Singleton
    @Named(SESSIONS_PREFIX)
    static String providesSessionsPrefix() {
        return System.getenv(SESSIONS_PREFIX);
    }

    @Provides
    @Singleton
    @Named(BUCKET_NAME)
    static String providesBucketName() {
        return System.getenv(BUCKET_NAME);
    }

    @Provides
    @Singleton
    static Calendar providesCurrentCalendar() {
        return Calendar.getInstance();
    }

    @Provides
    @Singleton
    @Named(ADVENT_YEAR)
    static int providesAdventYear(final Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    @Provides
    @Singleton
    @Named(ADVENT_DAY)
    static int providesAdventDay(final Calendar calendar) {
        return Calendar.DAY_OF_MONTH;
    }

    @Provides
    @Singleton
    @Named(ROOM_URL)
    static String providesRoomUrl() {
        return System.getenv(ROOM_URL);
    }

    @Provides
    @Singleton
    @Named(SESSION_ID)
    static String providesSessionId() {
        return System.getenv(SESSION_ID);
    }
}
