package tw.appworks.school.example.stylish.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.appworks.school.example.stylish.data.form.CommentForm;
import tw.appworks.school.example.stylish.service.CommentService;

@Controller
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
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
