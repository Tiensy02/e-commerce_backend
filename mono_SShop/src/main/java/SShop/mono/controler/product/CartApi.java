package SShop.mono.controler.product;

import SShop.mono.application.dto.product.CartDto;
import SShop.mono.controler.GenericCrudApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/cart")
public interface CartApi extends GenericCrudApi<CartDto,String> {
  @GetMapping("/user")
  ResponseEntity<?> getByUser(@RequestParam String userId);

}
