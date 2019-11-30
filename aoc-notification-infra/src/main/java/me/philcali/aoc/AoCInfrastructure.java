package me.philcali.aoc;

import software.amazon.awscdk.core.App;

public class AoCInfrastructure {
    public static void main(final String argv[]) {
        App app = new App();

        new AoCInfrastructureStack(app, AoCInfrastructureStack.class.getSimpleName());

        app.synth();
    }
}
