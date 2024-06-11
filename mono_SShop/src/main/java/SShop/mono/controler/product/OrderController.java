package SShop.mono.controler.product;

import SShop.mono.application.dto.product.OrderDto;
import SShop.mono.application.service.user.OrderService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.postgresql.OrderEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController extends GenericCrudController<OrderEntity, OrderDto, String> implements OrderApi {

  private final OrderService orderService;
  public OrderController(OrderService orderService) {
    super(orderService);
    this.orderService = orderService;
  }

  @Override
  public ResponseEntity<?> getByUserId(String id) {
   try {
         var result = orderService.getByUserId(id);
         return ResponseEntity.ok(result);
       }catch (Exception e){
         return ResponseEntity.badRequest().body(e.getMessage());
       }
  }
}
