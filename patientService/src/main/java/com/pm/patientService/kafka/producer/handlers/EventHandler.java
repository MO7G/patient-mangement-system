package com.pm.patientService.kafka.producer.handlers;

public interface EventHandler {
    void handle(Object payload);
}
