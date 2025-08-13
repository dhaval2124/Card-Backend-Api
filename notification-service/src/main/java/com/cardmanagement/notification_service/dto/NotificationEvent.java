package com.cardmanagement.notification_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationEvent {
    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("timestamp")
    private String timestamp;

    // Constructors
    public NotificationEvent() {}

    public NotificationEvent(String customerId, String email, String eventType, Object data, String timestamp) {
        this.customerId = customerId;
        this.email = email;
        this.eventType = eventType;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "customerId='" + customerId + '\'' +
                ", email='" + email + '\'' +
                ", eventType='" + eventType + '\'' +
                ", data=" + data +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}