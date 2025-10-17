package com.pm.patientService.kafka.producer;

import com.pm.patientService.kafka.producer.handlers.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class KafkaEventPublisher {

    private final Map<String, EventHandler> eventHandlers;

    public KafkaEventPublisher(Map<String, EventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public void publish(String eventType, Object payload) {
        EventHandler handler = eventHandlers.get(eventType);

        if (handler == null) {
            log.warn("⚠️ No handler found for event type: {}", eventType);
            return;
        }

        try {
            handler.handle(payload);
        } catch (Exception e) {
            log.error("❌ Error while handling event {}: {}", eventType, e.getMessage(), e);
        }
    }
}
