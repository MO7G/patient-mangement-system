package com.pm.analyticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEvent {
    private String type;         // e.g., "ANALYTICS_SUMMARY_CREATED"
    private String description;  // human-readable text
    private String source;       // e.g., "analytics-service"
}
