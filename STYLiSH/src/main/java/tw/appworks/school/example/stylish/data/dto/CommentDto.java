package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto implements Serializable {

    @Id
    @JsonProperty("id")
    private long id;

//    @JsonProperty("productId")
//    private Long productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("rate")
    private Integer rate;

    @JsonProperty("comment")
    private String comment;
}
