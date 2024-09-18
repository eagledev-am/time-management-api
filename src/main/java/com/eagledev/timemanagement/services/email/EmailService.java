package com.eagledev.timemanagement.services.email;

public interface EmailService {
    void send(String to , String subject, String body);
}
