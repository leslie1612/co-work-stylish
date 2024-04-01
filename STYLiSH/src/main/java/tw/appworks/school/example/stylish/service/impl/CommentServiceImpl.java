package tw.appworks.school.example.stylish.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.data.dto.CommentDto;
import tw.appworks.school.example.stylish.data.form.CommentForm;
import tw.appworks.school.example.stylish.model.product.ProductComment;
import tw.appworks.school.example.stylish.repository.product.CommentRepository;
import tw.appworks.school.example.stylish.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl (CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
    @Override
    public void saveComment(CommentForm commentForm) {

        ProductComment productComment = new ProductComment();

        productComment.setUserId(commentForm.getUserId());
        productComment.setProductId(commentForm.getProductId());
        productComment.setRate(commentForm.getRate());
        productComment.setComment(commentForm.getComment());

        commentRepository.save(productComment);
    }

    @Override
    public List<CommentDto> getComments(Long productId) {

        List<Map<String,Object>> comments = commentRepository.getCommentsByProductId(productId);

        List<CommentDto> commentDtoList = new ArrayList<CommentDto>();

        for (Map<String,Object> comment : comments) {

            CommentDto commentDto = new CommentDto();

            commentDto.setId((Long) comment.get("id"));
            commentDto.setName((String) comment.get("name"));
            commentDto.setRate((Integer) comment.get("rate"));
            commentDto.setComment((String) comment.get("comment"));

            commentDtoList.add(commentDto);
//            log.info((String) comment.get("id"));
//            System.out.println(comment.get("id"));
//            System.out.println(comment.get("name"));
//            System.out.println(comment.get("rate"));
//            System.out.println( comment.get("comment")) ;
        }


        return commentDtoList;
    }

}
