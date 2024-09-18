package com.eagledev.timemanagement.models.listcategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ListCategoryRequest(
        @NotNull(message = "Title field is required")
        @Size(min = 1, max = 100 , message = "title should not exceed 100")
        String title ,

        @Size(max = 500, message = "Description should not exceed 500")
        String description
) {
}
