package tw.appworks.school.example.stylish.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.data.form.CommentForm;
import tw.appworks.school.example.stylish.service.CommentService;

@Controller
@Slf4j
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @ResponseBody
    @PostMapping("/api/1.0/comment/create")
    public ResponseEntity<Object> postComment(@RequestBody CommentForm commentForm){

        try {
            commentService.saveComment(commentForm);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ResponseBody
    @GetMapping("/api/1.0/comments")
    public ResponseEntity<Object> getComment(@RequestParam(name = "id") long productId){

        return ResponseEntity.status(HttpStatus.OK).body(new StylishResponse<>(commentService.getComments(productId)));

    }

}
