package tw.appworks.school.example.stylish.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductDetailsDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("category")
    private String category;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("texture")
    private String texture;

    @JsonProperty("wash")
    private String wash;

    @JsonProperty("place")
    private String place;

    @JsonProperty("note")
    private String note;

    @JsonProperty("story")
    private String story;

    @JsonProperty("main_image")
    private String mainImage;

    @JsonProperty("images")
    private Set<String> images;

    @JsonProperty("variants")
    private Set<VariantsDto> variants;

    @JsonProperty("colors")
    private Set<ColorDto> colors;

    @JsonProperty("sizes")
    private Set<String> sizes;

    @JsonProperty("rating")
    private Double rating;

}
