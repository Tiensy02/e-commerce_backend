package SShop.mono.controler.product;

import SShop.mono.application.dto.product.ShopDto;
import SShop.mono.controler.GenericCrudApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop")
public interface ShopApi extends GenericCrudApi<ShopDto,String> {

  /**
   * @param ownId id of user who own shop
   */
  @GetMapping("/products")
  ResponseEntity<?> getProducts(@RequestParam String ownId);

  @GetMapping("/user/{id}")
  ResponseEntity<?> getOwnUser(@PathVariable String id);
}
