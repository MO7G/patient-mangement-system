package com.pm.patientService.kafka.producer.handlers;

import patient.events.PatientEvent;
import common.events.EventEnvelope;
import com.pm.patientService.model.Patient;

import java.time.Instant;
import java.util.UUID;

public class EventEnvelopeBuilder {


    // Static helper (or make it non-static if you want to inject dependencies)
    public static EventEnvelope buildPatientEventEnvelope(Patient patient, String eventType, String source) {
        // Build PatientEvent
        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType(eventType)
                .build();

        // Serialize PatientEvent to bytes
        byte[] payloadBytes = patientEvent.toByteArray();


        // Build EventEnvelope
        return EventEnvelope.newBuilder()
                .setEventId(UUID.randomUUID().toString())
                .setCorrelationId(UUID.randomUUID().toString())
                .setSource(source)
                .setEventType(eventType)
                .setTimestamp(Instant.now().toEpochMilli())
                .setPayload(com.google.protobuf.ByteString.copyFrom(payloadBytes))
                .build();
    }
}
