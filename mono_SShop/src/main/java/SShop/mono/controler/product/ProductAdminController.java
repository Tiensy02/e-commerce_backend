package SShop.mono.controler.product;

import SShop.mono.application.dto.product.CategoryDto;
import SShop.mono.application.service.admin.product.ProductAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductAdminController implements ProductAdminApi {
  private final ProductAdminService productAdminService;

  public ProductAdminController(ProductAdminService productAdminService) {
    this.productAdminService = productAdminService;
  }

  @Override
  public ResponseEntity<?> createCategory(CategoryDto categoryDto) {
    try {
      productAdminService.createCategory(categoryDto);
      return ResponseEntity.ok("Category created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Override
  public ResponseEntity<?> addSubCategory(CategoryDto categoryDto) {
    try{
      productAdminService.addSubCategory(categoryDto);
      return ResponseEntity.ok("Sub category created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
