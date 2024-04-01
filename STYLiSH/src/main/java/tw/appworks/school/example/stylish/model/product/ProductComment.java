package tw.appworks.school.example.stylish.model.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="rating")
@Data
@NoArgsConstructor
public class ProductComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="userid",nullable = false)
    private Long userId;

    @Column(name="pid",nullable = false)
    private Long productId;

    @Column(name="rate",nullable = false)
    private int rate;

    @Column(name="comment",nullable = false)
    private String comment;

    @Override
    public String toString() {
        return "ProductComment{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
