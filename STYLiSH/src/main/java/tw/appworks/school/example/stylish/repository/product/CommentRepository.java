package tw.appworks.school.example.stylish.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.appworks.school.example.stylish.data.dto.CommentDto;
import tw.appworks.school.example.stylish.model.product.ProductComment;

import java.util.List;
import java.util.Map;

public interface CommentRepository extends JpaRepository<ProductComment, Integer> {
    @Query(value="SELECT r.id, u.id as userId, u.name ,r.rate, r.comment FROM (SELECT * FROM rating WHERE pid = :pid) r JOIN user u ON r.userid = u.id ORDER BY r.id DESC",nativeQuery = true)
    List<Map<String,Object>> getCommentsByProductId (@Param("pid") long productId);
}