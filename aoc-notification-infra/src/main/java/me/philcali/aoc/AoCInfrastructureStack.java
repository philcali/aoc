package me.philcali.aoc;

import java.util.UUID;

import software.amazon.awscdk.core.CfnParameter;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.Schedule;
import software.amazon.awscdk.services.events.targets.LambdaFunction;
import software.amazon.awscdk.services.lambda.CfnParametersCodeProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SingletonFunction;

public class AoCInfrastructureStack extends Stack {
    public AoCInfrastructureStack(final Construct scope, final String id) {
        super(scope, id);

        final CfnParameter codeBucket = CfnParameter.Builder.create(this, "DeploymentBucket")
                .description("S3 bucket for the built artifact")
                .type("String")
                .build();

        final CfnParameter codeKey = CfnParameter.Builder.create(this, "DeploymentKey")
                .description("S3 key for the built artifact")
                .type("String")
                .build();

        final SingletonFunction cron = SingletonFunction.Builder.create(this, "ScheduleTrigger")
                .runtime(Runtime.JAVA_8)
                .handler("me.philcali.aoc.notification.ScheduledTrigger::execute")
                .code(Code.fromCfnParameters(CfnParametersCodeProps.builder()
                        .bucketNameParam(codeBucket)
                        .objectKeyParam(codeKey)
                        .build()))
                .timeout(Duration.minutes(1))
                .memorySize(1024)
                .uuid(UUID.randomUUID().toString())
                .build();

        Rule schedule = Rule.Builder.create(this, "NotifierRule")
                .enabled(true)
                .description("Runs every hour")
                .schedule(Schedule.expression("rate(1 hour)"))
                .build();

        schedule.addTarget(new LambdaFunction(cron));
    }
}
