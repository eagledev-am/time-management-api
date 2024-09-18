package com.eagledev.timemanagement.models.project;

import java.time.Instant;

public record ProjectPageDto(
        int id ,
        String title ,
        String description ,
        Instant startDate ,
        Instant endDate
) {
}
