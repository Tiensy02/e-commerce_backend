package SShop.mono.controler.product;

import SShop.mono.application.dto.product.OrderDto;
import SShop.mono.controler.GenericCrudApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/order")
@RestController
@CrossOrigin
public interface OrderApi extends GenericCrudApi<OrderDto,String> {
  @GetMapping("/user/{id}")
  ResponseEntity<?> getByUserId(@PathVariable String id);
}
