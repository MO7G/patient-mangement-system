package com.pm.patientService.kafka.producer.handlers.notification;

import com.pm.patientService.kafka.producer.handlers.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("notification_email") // must match EventType.NOTIFICATION_EMAIL
public class NotificationEmailEventHandler implements EventHandler {

    @Override
    public void handle(Object payload) {

    }
}
