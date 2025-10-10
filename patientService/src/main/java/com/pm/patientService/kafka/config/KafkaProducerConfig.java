package com.pm.patientService.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic patientTopic() {
        return new NewTopic("patient", 3, (short) 1);
    }

    @Bean
    public NewTopic notificationTopic() {
        return new NewTopic("notification", 3, (short) 1);
    }
}
