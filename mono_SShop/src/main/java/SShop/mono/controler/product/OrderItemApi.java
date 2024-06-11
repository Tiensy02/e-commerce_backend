package SShop.mono.controler.product;

import SShop.mono.application.dto.product.OrderItemDto;
import SShop.mono.application.dto.product.OrderItemUpdateDto;
import SShop.mono.controler.GenericCrudApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/order-item")
@RestController
@CrossOrigin
public interface OrderItemApi {
  @GetMapping()
  ResponseEntity<?> get(@RequestParam int pageSize, @RequestParam int pageNumber);

  @GetMapping("/{id}")
  ResponseEntity<?> get(@PathVariable String id);

  @PostMapping("/add")
  ResponseEntity<?> add(@RequestBody @Valid OrderItemDto orderItemDto);

  @PostMapping("/update")
  ResponseEntity<?> update(@RequestBody OrderItemUpdateDto orderItemUpdateDto);

  @PostMapping("/delete/{id}")
  ResponseEntity<?> delete(@PathVariable String id);

  @GetMapping("/order/{id}")
  ResponseEntity<?> getByOrderId(@PathVariable String id);

  @GetMapping("/shop")
  ResponseEntity<?> getByOwnShopId(@RequestParam String userId);
}
