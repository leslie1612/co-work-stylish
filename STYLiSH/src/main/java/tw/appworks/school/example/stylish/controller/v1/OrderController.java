package tw.appworks.school.example.stylish.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.data.form.OrderForm;
import tw.appworks.school.example.stylish.error.ErrorResponse;
import tw.appworks.school.example.stylish.service.OrderService;
import tw.appworks.school.example.stylish.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("api/1.0/order")
@Tag(name = "Orders", description = "Endpoints for fetching orders")
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Operation(
            summary = "Checkout the order",
            description = "Checkout the order and insert the payment .",
            tags = {"Orders"},
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
    @PostMapping(value = "/checkout", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> checkout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                      @RequestBody OrderForm orderForm) {
        String token = authorization.split(" ")[1].trim();
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new StylishResponse<>(Map.of("number",
                            orderService.payByPrime(orderForm, token, userService).getOrder().getNumber())));
        } catch (JsonProcessingException | UserService.UserNotExistException
                 | OrderService.PaymentNotSuccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

}
