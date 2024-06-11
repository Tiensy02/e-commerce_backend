package SShop.mono.controler.product;

import SShop.mono.application.dto.product.ProductDto;
import SShop.mono.controler.GenericCrudApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin
public interface ProductApi extends GenericCrudApi<ProductDto,String> {
  @GetMapping("/review/{id}")
  ResponseEntity<?> getReviewsOfProduct(@PathVariable String id, @RequestParam int pageSize, @RequestParam int pageNumber);
  @PostMapping("/ids")
  ResponseEntity<?> getByIds(@RequestBody List<String> ids);
}
  