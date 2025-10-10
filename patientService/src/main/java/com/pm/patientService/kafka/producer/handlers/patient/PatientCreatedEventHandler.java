package com.pm.patientService.kafka.producer.handlers.patient;

import com.pm.patientService.kafka.producer.handlers.EventHandler;
import com.pm.patientService.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import patient.events.PatientEvent;

@Slf4j
@Component("patient_created")
public class PatientEventHandler implements EventHandler {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public PatientEventHandler(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void handle(Object payload) {
        Patient patient = (Patient) payload;

        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("patient_created")
                .build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
            log.info("✅ Sent 'patient_created' event for {}", patient.getEmail());
        } catch (Exception e) {
            log.error("❌ Failed to send 'patient_created' event: {}", e.getMessage());
        }
    }
}
