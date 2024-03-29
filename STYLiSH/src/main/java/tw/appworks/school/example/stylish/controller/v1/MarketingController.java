package tw.appworks.school.example.stylish.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.service.MarketingService;
import tw.appworks.school.example.stylish.service.ProductService;

@RestController
@RequestMapping("api/1.0/marketing")
@Tag(name = "Marketing", description = "Endpoints for fetching campaigns and hot products")
public class MarketingController {

    private final MarketingService marketingService;

    private final ProductService productService;

    public MarketingController(MarketingService marketingService, ProductService productService) {
        this.marketingService = marketingService;
        this.productService = productService;
    }

    @Operation(
            summary = "Fetch all campaigns data",
            description = "Fetch all campaigns from database.",
            tags = {"Marketing"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping("/campaigns")
    @ResponseBody
    public ResponseEntity<?> getCampaigns() {
        return ResponseEntity.status(HttpStatus.OK).body(new StylishResponse<>(marketingService.getCampaigns()));
    }

    @Operation(
            summary = "Fetch all hot products",
            description = "Fetch all hot products from database.",
            tags = {"Marketing"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping("/hots")
    @ResponseBody
    public ResponseEntity<?> getHots() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new StylishResponse<>(marketingService.getHots(productService)));
    }

}
