package tw.appworks.school.example.stylish.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.example.stylish.data.StylishResponse;
import tw.appworks.school.example.stylish.data.dto.ProductDto;
import tw.appworks.school.example.stylish.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/1.0/products")
@Tag(name = "Products", description = "Endpoints for fetching products")
public class ProductsController {

    //pushing testing
    private final ProductService productService;

    @Value("${product.paging.size}")
    private int pagingSize;

    public static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Get products by category",
            description = "Get product details by it's category,\n" +
                    "please type in the correct category to find the product you want,\n" +
                    "only women, men, accessories, all 4 types of categories are supported).",
            tags = {"Products"},
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
    @GetMapping("/{category}")
    @ResponseBody
    public ResponseEntity<?> getProducts(@PathVariable String category,
                                         @RequestParam(name = "paging") Optional<Integer> paging) throws ServletRequestBindingException {

        if (!category.matches("^(women|men|accessories|all)$"))
            throw new ServletRequestBindingException("Wrong Request");

        List<ProductDto> ret = productService.getProducts(category, paging.orElse(0));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new StylishResponse<>(ret.stream().limit(pagingSize).toList(),
                        ret.size() > pagingSize ? paging.orElse(0) + 1 : null));
    }


    @Operation(
            summary = "Finds products details",
            description = "Finds a product details by it's Id.",
            tags = {"Products"},
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
    @GetMapping("/details")
    @ResponseBody
    public ResponseEntity<?> getProductDetail(@RequestParam(name = "id") long id) {
        ProductDto ret = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(new StylishResponse<>(ret));
    }


    @Operation(
            summary = "Search products details",
            description = "Search a product details by it's keyword,\n" +
                    "please type in the keyword to find the product you want.",
            tags = {"Products"},
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
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> searchProducts(@RequestParam(name = "keyword") String keyword,
                                            @RequestParam(name = "paging") Optional<Integer> paging) {
        List<ProductDto> ret = productService.searchProducts(keyword, paging.orElse(0));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new StylishResponse<>(ret.stream().limit(pagingSize).toList(),
                        ret.size() > pagingSize ? paging.orElse(0) + 1 : null));
    }
}
