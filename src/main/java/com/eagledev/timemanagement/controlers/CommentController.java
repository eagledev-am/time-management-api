package com.eagledev.timemanagement.controlers;

import com.eagledev.timemanagement.models.Response;
import com.eagledev.timemanagement.models.comment.CommentModel;
import com.eagledev.timemanagement.models.comment.CommentRequest;
import com.eagledev.timemanagement.services.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            tags = {"comments"},
            summary = "Get A comment" ,
            description = "This endpoint used to get a comment"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200" , description = "comment retrieved successfully") ,
                    @ApiResponse(responseCode = "404" , description = "Comment not found") ,
                    @ApiResponse(responseCode = "403" , description = "FORBIDDEN")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response<CommentModel>> getComment(@PathVariable int id) {
        Response<CommentModel> response = new Response<>(
                "success" , "comment retrieved successfully" , commentService.getComment(id) , null
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
            tags = {"comments"},
            summary = "Update comment" ,
            description = "This endpoint used to update a comment"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200" , description = "comment updated successfully") ,
                    @ApiResponse(responseCode = "404" , description = "Comment not found") ,
                    @ApiResponse(responseCode = "403" , description = "FORBIDDEN"),
                    @ApiResponse(responseCode = "401" , description = "unauthorized access")
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("#commentRequest.authorId() == principal.uuid")
    public ResponseEntity<Response<CommentModel>> updateComment(@PathVariable int id, @Valid @RequestBody CommentRequest commentRequest) {
        Response<CommentModel> response = new Response<>(
                "success" , "comment updated successfully" , commentService.updateComment(id , commentRequest) , null
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
            tags = {"comments"},
            summary = "delete comment" ,
            description = "This endpoint used to delete a comment"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201" , description = "NO CONTENT") ,
                    @ApiResponse(responseCode = "404" , description = "Comment not found") ,
                    @ApiResponse(responseCode = "403" , description = "FORBIDDEN") ,
                    @ApiResponse(responseCode = "401" , description = "unauthorized access")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            tags = {"comments"},
            summary = "reply a comment" ,
            description = "This endpoint used to  reply a comment"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200" , description = "") ,
                    @ApiResponse(responseCode = "404" , description = "Comment not found") ,
                    @ApiResponse(responseCode = "403" , description = "FORBIDDEN") ,
            }
    )
    @PostMapping("/{id}/reply")
    @PreAuthorize("#commentRequest.authorId() == principal.uuid")
    ResponseEntity<Response<CommentModel>> replyToComment(@PathVariable int id ,@RequestBody @Valid CommentRequest commentRequest){
        Response<CommentModel> response = new Response<>(
                "success" , "comment reply created successfully" , commentService.replyComment(id , commentRequest) , null
        );
        return ResponseEntity.ok(response);
    }
}
