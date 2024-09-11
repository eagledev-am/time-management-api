package com.eagledev.timemanagement.models.attachment;

public record AttachmentModel(
        String filename,
        String contentType,
        String fileUrl
) {
}
