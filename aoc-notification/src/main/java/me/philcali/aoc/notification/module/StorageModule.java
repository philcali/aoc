package me.philcali.aoc.notification.module;

import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.Module;
import dagger.Provides;
import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.s3.AdventOfCodeS3;

@Module
public class StorageModule {
    @Provides
    @Singleton
    static AdventOfCodeStorage providesAdventOfCodeStorage(
            @Named(EnvironmentModule.BUCKET_NAME) final String bucketName,
            final AmazonS3 s3,
            final ObjectMapper mapper) {
        return new AdventOfCodeS3(bucketName, s3, mapper);
    }

    @Provides
    @Singleton
    static AmazonS3 providesAmazonS3() {
        return AmazonS3ClientBuilder.defaultClient();
    }
}
