package SShop.mono.controler.product;

import SShop.mono.application.dto.product.CartItemDto;
import SShop.mono.controler.GenericCrudApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/cart/cart-item")
@RestController
@CrossOrigin
public interface CartItemApi extends GenericCrudApi<CartItemDto, String> {

  @PostMapping("/{id}")
  ResponseEntity<?> updateQuantity(@PathVariable String id, @RequestParam int quantity);
}
