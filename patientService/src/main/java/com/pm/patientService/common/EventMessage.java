package com.pm.patientService.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMessage<T> {
    private String eventId;
    private String eventType;
    private String source;
    private String correlationId;
    private Instant timestamp;
    private T payload;
}

