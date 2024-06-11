package SShop.mono.controler.product;

import SShop.mono.application.dto.product.ProductDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.application.service.user.ProductService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.mongodb.ProductEntity;
import SShop.mono.domain.entity.mongodb.ReviewEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController extends GenericCrudController<ProductEntity,ProductDto,String> implements ProductApi {
  private final ProductService productService;
  public ProductController(ProductService productService) {
    super(productService);
    this.productService = productService;
  }

  @Override
  public ResponseEntity<?> getReviewsOfProduct(String id, int pageSize, int pageNumber) {
    Pageable page = PageRequest.of(pageNumber,pageSize);
    Page<ReviewEntity> reviews = productService.getReviews(id,page);
    return ResponseEntity.ok(reviews);
  }

  @Override
  public ResponseEntity<?> getByIds(List<String> ids) {
    try {
          var result = productService.getByIds(ids);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }
}
