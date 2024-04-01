package tw.appworks.school.example.stylish.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.example.stylish.service.CouponService;
import tw.appworks.school.example.stylish.service.OrderService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/coupon")
@Tag(name = "Coupon", description = "Endpoints for fetching and updating coupon by user")
@Slf4j
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public ResponseEntity<?> updateCouponByUser(@RequestParam Integer num,
                                                @RequestParam Integer userId){
        try{
            Integer totalCoupon = couponService.updateCouponWithUser(num, userId);
            log.info(totalCoupon.toString());
            HashMap<String,Integer>resultMap = new HashMap<>();
            resultMap.put("coupon",totalCoupon);
            log.info(resultMap.toString());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(resultMap);
        }catch (Exception e){
            log.error("Error on updating coupon from user !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping(value = "/get")
    @ResponseBody
    public ResponseEntity<?> getTotalCouponByUser(@RequestParam Integer userId){
        try{
            Map<String,Object>resultMap = couponService.getCouponByUser(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(resultMap);
        }catch (Exception e){
            log.error("Error on getting coupon from user !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
