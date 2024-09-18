package com.eagledev.timemanagement.models.project;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Set;

public record ProjectRequest(

        @NotNull(message = "title is required")
        @Size(min = 1 , max = 100 , message = "length must not exceed 100")
        String title ,

        @NotNull(message = "starting date is required")
        @FutureOrPresent(message = "starting date must not be in the past")
        Instant startDate ,

        @NotNull(message = "ending date is required")
        @Future(message = "ending date must not be in the past")
        Instant endDate ,

        @Size(max = 500 ,  message = "description must not exceed 500")
        String description
) {
}
