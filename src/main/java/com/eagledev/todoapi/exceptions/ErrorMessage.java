package com.eagledev.todoapi.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Setter
@Getter
public class ErrorMessage {
    private String message;
    private int statusCode;
    private Date timestamp;
    private String description;
}
