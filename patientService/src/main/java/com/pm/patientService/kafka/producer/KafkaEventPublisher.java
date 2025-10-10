package com.pm.patientService.kafka.producer;

import com.pm.patientService.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.*;

@Service
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPatientEvent(Patient patient, PatientEventType eventType) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType(eventType.name())
                .build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
            log.info("Sent Kafka event: {}", eventType);
        } catch (Exception e) {
            log.error("Error sending {} event: {}", eventType, e.getMessage());
        }
    }
}
