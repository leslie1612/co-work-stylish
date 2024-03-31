package tw.appworks.school.example.stylish.repository.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProductProjectionRepositoryImpl implements ProductProjectionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings({"unchecked", "SqlSourceToSinkFlow"})
    @Override
    public List<ProductProjection> fetchAllProductsByCategory(@Param("category") String category,
                                                              @Param("pagingSize") int pagingSize, @Param("currentOffset") int offset) {

        String query = """
                    SELECT p.id, p.category, p.title, p.description, p.price, p.texture,
                        p.wash, p.place, p.note, p.story, p.main_image as mainImage,
                        v.size, v.stock, i.image, c.code as colorCode, c.name as colorName,
                        round(AVG(r.rate),1) as rate
                    FROM
                    (SELECT * FROM product %s ORDER BY id DESC LIMIT %d OFFSET %d) p
                    LEFT JOIN variant v ON p.id = v.product_id
                    LEFT JOIN color c ON v.color_id = c.id
                    LEFT JOIN product_images i ON v.product_id = i.product_id
                    LEFT JOIN rating r ON p.id = r.pid
                    GROUP BY
                        p.id, p.category, p.title, p.description, p.price, p.texture,
                        p.wash, p.place, p.note, p.story, p.main_image,
                        v.size, v.stock, i.image, c.code, c.name
                """;
        String condition = category.equals("all") ? "" : "WHERE category = '" + category + "'";

        return entityManager.createNativeQuery(query.formatted(condition, pagingSize, offset), ProductProjection.class)
                .getResultList();


    }

}
