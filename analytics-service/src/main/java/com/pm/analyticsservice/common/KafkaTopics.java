package com.pm.analyticsservice.common;

public final class KafkaTopics {

    private KafkaTopics() {
        // private constructor to prevent instantiation
    }

    public static final String PATIENT = "patient";
    public static final String NOTIFICATION = "notification";
    public static final String ANALYTICS = "analytics";
}
