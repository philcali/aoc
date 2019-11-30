package com.myorg;

import software.amazon.awscdk.core.App;

import java.util.Arrays;

public class AocNotificationInfraApp {
    public static void main(final String argv[]) {
        App app = new App();

        new AocNotificationInfraStack(app, "AocNotificationInfraStack");

        app.synth();
    }
}
