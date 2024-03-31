package tw.appworks.school.example.stylish.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentForm {

    private Long userId;

    private Long productId;

    private Integer rate;

    private String comment;

}
