package tw.appworks.school.example.stylish.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.appworks.school.example.stylish.data.form.CommentForm;
import tw.appworks.school.example.stylish.model.product.ProductComment;
import tw.appworks.school.example.stylish.repository.product.CommentRepository;
import tw.appworks.school.example.stylish.service.CommentService;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl (CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
    @Override
    public void saveComment(CommentForm commentForm) {

        ProductComment productComment =new ProductComment();

        productComment.setUserId(commentForm.getUserId());
        productComment.setProductId(commentForm.getProductId());
        productComment.setRate(commentForm.getRate());
        productComment.setComment(commentForm.getComment());

        commentRepository.save(productComment);
    }
}
