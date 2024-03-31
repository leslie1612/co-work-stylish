package tw.appworks.school.example.stylish.service;

import jakarta.annotation.Nonnull;

import java.util.Map;

public interface CouponService {
    Integer updateCouponWithUser(@Nonnull Integer num, @Nonnull Integer userID);

    Map<String, Object> getCouponByUser(@Nonnull Integer userID);
//    Integer getCouponByUser(@Nonnull Integer userID);
}
