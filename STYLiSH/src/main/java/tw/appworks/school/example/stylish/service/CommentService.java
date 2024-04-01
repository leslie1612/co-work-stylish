package tw.appworks.school.example.stylish.service;

import tw.appworks.school.example.stylish.data.dto.CommentDto;
import tw.appworks.school.example.stylish.data.form.CommentForm;

import java.util.List;

public interface CommentService {

    void saveComment(CommentForm commentForm);

    List<CommentDto> getComments(long productId);

}
