package tw.appworks.school.example.stylish.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.appworks.school.example.stylish.repository.coupon.CouponRepository;
import tw.appworks.school.example.stylish.service.CouponService;

import java.util.Map;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Integer updateCouponWithUser(@NotNull Integer num, @NotNull Integer userID) {
        couponRepository.updateCoupontoUser(num, userID);
        return couponRepository.getTotalCouponByUser(userID);
    }

    @Override
    public Map<String, Object> getCouponByUser(@NotNull Integer userID) {
        return couponRepository.getCouponByUser(userID);
    }
}
