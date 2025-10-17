package com.pm.patientService.kafka.producer.handlers.patient;


import com.pm.patientService.common.EventMessage;
import com.pm.patientService.common.KafkaTopics;
import com.pm.patientService.kafka.producer.handlers.EventEnvelopeBuilder;
import com.pm.patientService.kafka.producer.handlers.EventHandler;
import com.pm.patientService.kafka.producer.model.EventType;
import com.pm.patientService.model.Patient;
import common.events.EventEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import patient.events.PatientEvent;
import java.time.Instant;
import java.util.UUID;




@Slf4j
@Component(EventType.PATIENT_CREATED)
public class PatientCreatedEventHandler implements EventHandler {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final Environment environment;

    public PatientCreatedEventHandler(KafkaTemplate<String, byte[]> kafkaTemplate, Environment environment) {
        this.kafkaTemplate = kafkaTemplate;
        this.environment = environment;
    }

    @Override
    public void handle(Object payload) {
        Patient patient = (Patient) payload;

       EventEnvelope patientEvent =  EventEnvelopeBuilder.buildPatientEventEnvelope(
               patient,
               EventType.PATIENT_CREATED,
               environment.getProperty("spring.application.name"));
        try {
            kafkaTemplate.send(KafkaTopics.PATIENT, patientEvent.toByteArray());
            log.info("✅ Sent {} event for {}",EventType.PATIENT_CREATED, patient.getEmail());
        } catch (Exception e) {
            log.error("❌ Failed to send {} event: {}",EventType.PATIENT_CREATED, e.getMessage(), e);
        }
    }
}
