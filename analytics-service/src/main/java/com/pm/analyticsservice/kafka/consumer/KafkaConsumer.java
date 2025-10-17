package com.pm.analyticsservice.kafka.consumer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pm.analyticsservice.common.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaConsumer {
    @KafkaListener(topics = KafkaTopics.PATIENT)
    public void consumeEvent(byte[] event) {
        log.info("🎯 Received raw Kafka message ({} bytes)", event.length);

        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("✅ Parsed Patient Event: {}", patientEvent);
        } catch (InvalidProtocolBufferException e) {
            log.error("❌ Error parsing protobuf: {}", e.getMessage());
        }
    }




}
