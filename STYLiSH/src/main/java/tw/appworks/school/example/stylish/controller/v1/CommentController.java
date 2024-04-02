package tw.appworks.school.example.stylish.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.data.form.CommentForm;
import tw.appworks.school.example.stylish.error.ErrorResponse;
import tw.appworks.school.example.stylish.service.CommentService;
import tw.appworks.school.example.stylish.service.amazon.SnsService;

@Controller
@Slf4j
public class CommentController {

    private final CommentService commentService;

    private final SnsService snsService;

    public CommentController(CommentService commentService, SnsService snsService) {

        this.commentService = commentService;
        this.snsService = snsService;

    }

    @Operation(
            summary = "Post new comment for this product",
            description = "User post comment for this product",
            tags = {"Comment"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @ResponseBody
    @PostMapping("/api/1.0/comment/create")
    public ResponseEntity<Object> postComment(@RequestBody CommentForm commentForm) {

        try {
            commentService.saveComment(commentForm);
            log.info("You successfully added a comment on product" + commentForm.getProductId());

            Long productId = commentForm.getProductId();

            snsService.pubTopic("Check out the new comments for " + productId + " !");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }

    @Operation(
            summary = "Retrieve all comments for this product.",
            description = "Retrieve all comments for this product.",
            tags = {"Comment"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )

    @ResponseBody
    @GetMapping("/api/1.0/comments")
    public ResponseEntity<Object> getComment(
            @Parameter(
                    name = "id",
                    description = "Id of this product",
                    example = "202403300387",
                    required = true)
            @RequestParam(name = "id") long id) {

        return ResponseEntity.status(HttpStatus.OK).body(new StylishResponse<>(commentService.getComments(id)));

    }

}
