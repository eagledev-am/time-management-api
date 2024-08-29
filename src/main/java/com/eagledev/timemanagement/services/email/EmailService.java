package com.eagledev.todoapi.services.email;

public interface EmailService {
    void send(String to , String subject, String body);
}
