package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.example.stylish.model.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("picture")
    private String picture;

    @JsonProperty("color")
    private String favorColor;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("coupon")
    private Integer coupon;
    public static UserDto from(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setProvider(user.getProvider());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPicture(user.getPicture());
        dto.setFavorColor(user.getFavorColor());
        dto.setBirthday(user.getBirthday());
        dto.setGender(user.getGender());
        dto.setCoupon(user.getCoupon());
        return dto;
    }

}
