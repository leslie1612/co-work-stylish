package tw.appworks.school.example.stylish.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tw.appworks.school.example.stylish.data.form.CampaignForm;
import tw.appworks.school.example.stylish.data.form.ProductForm;
import tw.appworks.school.example.stylish.service.MarketingService;
import tw.appworks.school.example.stylish.service.ProductService;
import tw.appworks.school.example.stylish.service.StorageService;

@Controller
@RequestMapping("api/1.0/admin")
@Tag(name = "Admin", description = "Endpoints for administrator upload the new products and campaigns")
public class AdminApiController {

    public static final Logger logger = LoggerFactory.getLogger(AdminApiController.class);

    private final StorageService storageService;

    private final MarketingService marketingService;

    private final ProductService productService;

    public AdminApiController(StorageService storageService, MarketingService marketingService,
                              ProductService productService) {
        this.storageService = storageService;
        this.marketingService = marketingService;
        this.productService = productService;
    }

    @Operation(
            summary = "Upload new campaigns",
            description = "The endpoint for upload the new campaigns.",
            tags = {"Admin"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PostMapping(path = "/campaign", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<?> postCampaigns(@ModelAttribute CampaignForm campaignForm,
                                           RedirectAttributes redirectAttributes) {
        try {
            marketingService.saveCampaign(campaignForm);
            storageService.store(campaignForm.getMainImage(), "campaigns");
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + campaignForm.getMainImage().getOriginalFilename() + "!");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Upload new products",
            description = "The endpoint for upload the new products.",
            tags = {"Admin"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    @PostMapping(path = "/product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> postProduct(@ModelAttribute ProductForm productForm) {
        logger.info(productForm.toString());
        productService.saveProduct(productForm, storageService);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
