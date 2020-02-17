package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import dagger.Module;
import dagger.Provides;
import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.s3.AdventOfCodeS3;
import me.philcali.aoc.notification.s3.S3EventNotificationRecordParser;

@Module
public class StorageModule {
    @Provides
    @Singleton
    static AdventOfCodeStorage providesAdventOfCodeStorage(final AdventOfCodeS3 storage) {
        return storage;
    }

    @Provides
    @Singleton
    static AmazonS3 providesAmazonS3() {
        return AmazonS3ClientBuilder.defaultClient();
    }

    @Provides
    @Singleton
    static S3EventNotificationRecordParser providesS3EventNotificationRecordParser(final AdventOfCodeS3 storage) {
        return storage;
    }
}
