package com.eagledev.todoapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    String status;
    String message;
    T data;
    Object error;
}
