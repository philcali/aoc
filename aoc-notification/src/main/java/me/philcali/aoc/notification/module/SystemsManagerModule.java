package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;

import dagger.Module;
import dagger.Provides;

@Module
public class SystemsManagerModule {
    @Provides
    @Singleton
    static AWSSimpleSystemsManagement providesSSMClient() {
        return AWSSimpleSystemsManagementClientBuilder.defaultClient();
    }
}
