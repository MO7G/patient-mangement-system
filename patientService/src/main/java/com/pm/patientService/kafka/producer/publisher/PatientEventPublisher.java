package com.pm.patientService.kafka.producer.publisher;

import com.pm.patientService.kafka.producer.KafkaEventPublisher;
import com.pm.patientService.kafka.producer.model.EventType;
import com.pm.patientService.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PatientEventPublisher {

    private final KafkaEventPublisher kafkaPublisher;

    public PatientEventPublisher(KafkaEventPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    public void publishPatientCreated(Patient patient) {
        String correlationId = UUID.randomUUID().toString();
        log.info("🧾 Publishing '{}' (correlationId={}) for {}", EventType.PATIENT_CREATED, correlationId, patient.getEmail());

        kafkaPublisher.publish(EventType.PATIENT_CREATED, patient);
    }

    public void publishPatientUpdated(Patient patient) {
        String correlationId = UUID.randomUUID().toString();
        log.info("🧾 Publishing '{}' (correlationId={}) for {}", EventType.PATIENT_UPDATED, correlationId, patient.getEmail());

        kafkaPublisher.publish(EventType.PATIENT_UPDATED, patient);
    }
}



