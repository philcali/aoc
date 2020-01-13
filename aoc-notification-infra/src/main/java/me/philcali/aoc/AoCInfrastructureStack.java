package me.philcali.aoc;

import software.amazon.awscdk.core.CfnParameter;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.StreamViewType;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.Schedule;
import software.amazon.awscdk.services.events.targets.LambdaFunction;
import software.amazon.awscdk.services.lambda.CfnParametersCodeProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.StartingPosition;
import software.amazon.awscdk.services.lambda.eventsources.DynamoEventSource;
import software.amazon.awscdk.services.lambda.eventsources.DynamoEventSourceProps;

public class AoCInfrastructureStack extends Stack {
    public AoCInfrastructureStack(final Construct scope, final String id) {
        super(scope, id);

        final CfnParameter codeBucket = CfnParameter.Builder.create(this, "DeploymentBucket")
                .description("S3 bucket for the built artifact")
                .type("String")
                .defaultValue("philcali-builds")
                .build();

        final CfnParameter codeKey = CfnParameter.Builder.create(this, "DeploymentKey")
                .description("S3 key for the built artifact")
                .type("String")
                .defaultValue("local/aoc/notification.jar")
                .build();

        final Table problems = Table.Builder.create(this, "DailyProblems")
                .partitionKey(Attribute.builder()
                        .type(AttributeType.NUMBER)
                        .name("year")
                        .build())
                .sortKey(Attribute.builder()
                        .type(AttributeType.NUMBER)
                        .name("day")
                        .build())
                .readCapacity(1)
                .writeCapacity(1)
                .stream(StreamViewType.NEW_AND_OLD_IMAGES)
                .build();

        final Code singletonCode = Code.fromCfnParameters(CfnParametersCodeProps.builder()
                .bucketNameParam(codeBucket)
                .objectKeyParam(codeKey)
                .build());

        final Function cron = Function.Builder.create(this, "ScheduleTrigger")
                .runtime(Runtime.JAVA_8)
                .handler("me.philcali.aoc.notification.ScheduledTrigger::execute")
                .code(singletonCode)
                .timeout(Duration.minutes(1))
                .memorySize(512)
                .build();

        final Function leaderTrigger = Function.Builder.create(this, "LeaderTrigger")
                .runtime(Runtime.JAVA_8)
                .handler("me.philcali.aoc.notification.ScheduledTrigger::leaderboard")
                .code(singletonCode)
                .timeout(Duration.minutes(1))
                .memorySize(512)
                .build();

        final Function problemTrigger = Function.Builder.create(this, "ProblemTrigger")
                .runtime(Runtime.JAVA_8)
                .handler("me.philcali.aoc.notification.ProblemTrigger::execute")
                .code(singletonCode)
                .timeout(Duration.seconds(30))
                .memorySize(512)
                .build();

        problemTrigger.addEventSource(new DynamoEventSource(problems, DynamoEventSourceProps.builder()
                .batchSize(1)
                .startingPosition(StartingPosition.TRIM_HORIZON)
                .build()));

        final Rule schedule = Rule.Builder.create(this, "ProblemNotifierRule")
                .enabled(true)
                .description("Runs every hour")
                .schedule(Schedule.expression("rate(1 hour)"))
                .build();

        schedule.addTarget(new LambdaFunction(cron));

        final Rule leaderSchedule = Rule.Builder.create(this, "LeaderNotifierRule")
                .enabled(true)
                .description("Runs every 15 minutes to update changes in leaders")
                .schedule(Schedule.expression("rate(15 minutes)"))
                .build();

        leaderSchedule.addTarget(new LambdaFunction(leaderTrigger));
    }
}
