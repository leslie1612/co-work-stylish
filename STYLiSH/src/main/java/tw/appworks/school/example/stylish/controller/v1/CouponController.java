package tw.appworks.school.example.stylish.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Update coupon number by userId",
            description = "Update an user's coupon quantity by using userId,\n" +
                    "each new user start with 0 coupon,\n" +
                    "once they get a new coupon or use a coupon, you can type -1 or 1 in the num parameter.",
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
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
    @Operation(
            summary = "Get coupon number by userId",
            description = "Get an user's total coupon quantity by using userId." ,
            tags = {"Coupon"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
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
