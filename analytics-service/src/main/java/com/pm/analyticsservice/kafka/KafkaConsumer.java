package com.pm.analyticsservice.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaConsumer {
    @KafkaListener(topics="patient", groupId ="analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // perform any business related to analytics here



            log.info("Received Patient Event: {}", patientEvent);
        } catch (InvalidProtocolBufferException e) {
            log.error("Error while parsing patient_created event: {}:{}" , event , e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
