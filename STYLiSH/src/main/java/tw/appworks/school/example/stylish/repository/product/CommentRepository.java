package tw.appworks.school.example.stylish.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.appworks.school.example.stylish.model.product.ProductComment;

public interface CommentRepository extends JpaRepository<ProductComment, Integer> {
}
