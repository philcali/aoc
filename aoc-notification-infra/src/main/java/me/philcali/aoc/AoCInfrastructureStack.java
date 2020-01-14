package me.philcali.aoc;

import java.util.Arrays;

import software.amazon.awscdk.core.Arn;
import software.amazon.awscdk.core.ArnComponents;
import software.amazon.awscdk.core.CfnParameter;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.events.CronOptions;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.Schedule;
import software.amazon.awscdk.services.events.targets.LambdaFunction;
import software.amazon.awscdk.services.iam.AccountRootPrincipal;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyDocument;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.kms.Key;
import software.amazon.awscdk.services.lambda.CfnParametersCodeProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.eventsources.S3EventSource;
import software.amazon.awscdk.services.lambda.eventsources.S3EventSourceProps;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.EventType;
import software.amazon.awscdk.services.s3.NotificationKeyFilter;

public class AoCInfrastructureStack extends Stack {
    private PolicyStatement objects(final Bucket bucket, final String path, final String...actions) {
        return PolicyStatement.Builder.create()
                .effect(Effect.ALLOW)
                .actions(Arrays.asList(actions))
                .resources(Arrays.asList(bucket.getBucketArn() + path))
                .build();
    }

    private PolicyStatement controlObjects(final Bucket bucket, final String path) {
        return objects(bucket, path, "s3:GetObject", "s3:PutObject");
    }

    private PolicyStatement readParameters(final String path) {
        return PolicyStatement.Builder.create()
                .effect(Effect.ALLOW)
                .actions(Arrays.asList(
                        "ssm:GetParameter",
                        "ssm:GetParameters",
                        "ssm:GetParametersByPath"))
                .resources(Arrays.asList(
                        Arn.format(ArnComponents.builder()
                                .service("ssm")
                                .resource("parameter").sep("/").resourceName(path)
                                .build(), this)))
                .build();
    }

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

        final Bucket aocBucket = Bucket.Builder.create(this, "advent-of-code")
                .build();

        final PolicyStatement listBucket = PolicyStatement.Builder.create()
                .effect(Effect.ALLOW)
                .actions(Arrays.asList("s3:ListBucket"))
                .resources(Arrays.asList(aocBucket.getBucketArn()))
                .build();

        final Code singletonCode = Code.fromCfnParameters(CfnParametersCodeProps.builder()
                .bucketNameParam(codeBucket)
                .objectKeyParam(codeKey)
                .build());

        final Function checkProblems = Function.Builder.create(this, "CheckProblems")
                .runtime(Runtime.JAVA_8)
                .handler("me.philcali.aoc.notification.Monitors::checkProblems")
                .description("Function that checks the current problems with the problem list.")
                .code(singletonCode)
                .timeout(Duration.minutes(1))
                .memorySize(512)
                .initialPolicy(Arrays.asList(listBucket, controlObjects(aocBucket, "/problems/*")))
                .build();

        final Function checkLeaders = Function.Builder.create(this, "CheckLeaders")
                .runtime(Runtime.JAVA_8)
                .handler("me.philcali.aoc.notification.Monitors::checkLeaders")
                .code(singletonCode)
                .description("Function that checks the current leaderboards.")
                .timeout(Duration.minutes(1))
                .memorySize(512)
                .initialPolicy(Arrays.asList(listBucket, controlObjects(aocBucket, "/leaderboards/*")))
                .build();

        checkLeaders.addToRolePolicy(readParameters("aoc/sessions/*"));

        final Function updateChannels = Function.Builder.create(this, "UpdateChannels")
                .runtime(Runtime.JAVA_8)
                .handler("me.philcali.aoc.notification.Notifications::updateChannels")
                .description("Sends an event notification to the channels paths.")
                .code(singletonCode)
                .timeout(Duration.minutes(1))
                .memorySize(512)
                .initialPolicy(Arrays.asList(objects(aocBucket, "/*", "s3:GetObject")))
                .build();

        updateChannels.addToRolePolicy(readParameters("aoc/channels/*"));

        final Key secureKey = Key.Builder.create(this, "SSMOwnedKey")
                .alias("aoc/secure")
                .enabled(true)
                .enableKeyRotation(true)
                .description("Managed key for handling secure parameters")
                .policy(PolicyDocument.Builder.create()
                        .assignSids(true)
                        .statements(Arrays.asList(
                                PolicyStatement.Builder.create()
                                        .effect(Effect.ALLOW)
                                        .principals(Arrays.asList(new AccountRootPrincipal()))
                                        .actions(Arrays.asList("kms:*"))
                                        .resources(Arrays.asList("*"))
                                        .build(),
                                PolicyStatement.Builder.create()
                                        .effect(Effect.ALLOW)
                                        .principals(Arrays.asList(
                                                checkLeaders.getRole(),
                                                updateChannels.getRole()))
                                        .actions(Arrays.asList("kms:Decrypt", "kms:DescribeKey"))
                                        .resources(Arrays.asList("*"))
                                        .build()))
                        .build())
                .build();

        updateChannels.addEnvironment("KEY_ID", secureKey.getKeyId());

        checkLeaders.addEnvironment("KEY_ID", secureKey.getKeyId());

        updateChannels.addEventSource(new S3EventSource(aocBucket, S3EventSourceProps.builder()
                .events(Arrays.asList(EventType.OBJECT_CREATED))
                .filters(Arrays.asList(
                        NotificationKeyFilter.builder()
                                .prefix("leaderboards")
                                .suffix("current.json")
                                .build()))
                .build()));

        updateChannels.addEventSource(new S3EventSource(aocBucket, S3EventSourceProps.builder()
                .events(Arrays.asList(EventType.OBJECT_CREATED))
                .filters(Arrays.asList(
                        NotificationKeyFilter.builder()
                                .prefix("problems")
                                .build()))
                .build()));

        final Rule problemSchedule = Rule.Builder.create(this, "CheckProblemRule")
                .enabled(true)
                .description("Runs every day to check updated problems.")
                .schedule(Schedule.cron(CronOptions.builder()
                        .hour("0")
                        .minute("0")
                        .day("*")
                        .month("*")
                        .year("*")
                        .build()))
                .build();

        problemSchedule.addTarget(new LambdaFunction(checkProblems));

        final Rule leaderSchedule = Rule.Builder.create(this, "CheckLeadersRule")
                .enabled(true)
                .description("Runs every 15 minutes to update changes in leaders.")
                .schedule(Schedule.expression("rate(15 minutes)"))
                .build();

        leaderSchedule.addTarget(new LambdaFunction(checkLeaders));
    }
}
