package tw.appworks.school.example.stylish.repository.product;

import java.math.BigDecimal;

public interface IProductProjection {

    Long getId();

    String getCategory();

    String getTitle();

    String getDescription();

    Integer getPrice();

    String getTexture();

    String getWash();

    String getPlace();

    String getNote();

    String getStory();

    String getMainImage();

    String getSize();

    Integer getStock();

    String getImage();

    String getColorCode();

    String getColorName();

//    BigDecimal getRate();
}
