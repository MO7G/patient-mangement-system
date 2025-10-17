package com.pm.patientService.kafka.producer.model;

public class EventType  {


    // Patient Events
    public static final String PATIENT_CREATED = "patient_created";
    public static final String PATIENT_DELETED = "patient_deleted";
    public static final String PATIENT_UPDATED = "patient_updated";



    // Notification Events
    public static final String NOTIFICATION_EMAIL = "notification_email";

}
