package com.eagledev.todoapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
public class Response <T>{
    int status;
    String message;
    T data;
}
