package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.entities.Attachment;
import com.eagledev.timemanagement.services.attachment.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Operation(
            tags = "attachments" ,
            summary = "GET Attachment By filename" ,
            description = "This endpoint used to get attachment entity data"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Resource Not found")
            }
    )
    @PreAuthorize("@attachmentAuthorization.canGetResource(authentication , #filename)")
    @GetMapping("/{filename}")
    public ResponseEntity<Attachment> getAttachment(@PathVariable String filename) {
        return new ResponseEntity<>(attachmentService.getAttachment(filename) , HttpStatus.OK);
    }


    @Operation(
            tags = "attachments" ,
            summary = "DELETE Attachment By filename" ,
            description = "This endpoint used to delete an attachment entity"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "404", description = "Resource Not found") ,
                    @ApiResponse(responseCode = "401" , description = "UnAuthorized Access")

            }
    )
    @PreAuthorize("@attachmentAuthorization.canManageAttachment(authentication , #filename)")
    @DeleteMapping("/file/{filename}")
    public ResponseEntity<?> deleteAttachment(@PathVariable String filename) throws IOException {
        attachmentService.deleteAttachmentClient(filename);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            tags = "attachments" ,
            summary = "GET Resource By its name" ,
            description = "This endpoint used to get resource(file) "
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200" , content = @Content(
                            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE , schema = @Schema(implementation = Resource.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "File Not found") ,
            }
    )
    @PreAuthorize("@attachmentAuthorization.canGetResource(authentication , #filename)")
    @GetMapping(path = "/file/{filename}" , produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getAttachmentByFilename(@PathVariable String filename) throws MalformedURLException, FileNotFoundException {
        return attachmentService.getAttachmentResource(filename);
    }
}
