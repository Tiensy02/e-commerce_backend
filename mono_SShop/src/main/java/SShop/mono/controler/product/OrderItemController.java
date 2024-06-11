package SShop.mono.controler.product;

import SShop.mono.application.dto.product.OrderItemDto;
import SShop.mono.application.dto.product.OrderItemUpdateDto;
import SShop.mono.application.service.user.OrderItemService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.postgresql.OrderItemEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderItemController implements OrderItemApi {
  private final OrderItemService orderItemService;

  public OrderItemController(OrderItemService orderItemService) {
    this.orderItemService = orderItemService;
  }

  @Override
  public ResponseEntity<?> get(int pageSize, int pageNumber) {
   try {
     Pageable pageable = PageRequest.of(pageNumber,pageSize);
         var result = orderItemService.getAll(pageable);
         return ResponseEntity.ok(result);
       }catch (Exception e){
         return ResponseEntity.badRequest().body(e.getMessage());
       }
  }

  @Override
  public ResponseEntity<?> get(String id) {
    try {
          var result = orderItemService.get(id);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> add(OrderItemDto orderItemDto) {
    try {
          var result = orderItemService.save(orderItemDto);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> update(OrderItemUpdateDto orderItemUpdateDto) {

    try {
          var result = orderItemService.update(orderItemUpdateDto);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> delete(String id) {
    try {
        orderItemService.delete(id);
          return ResponseEntity.ok("success");
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }

  }

  @Override
  public ResponseEntity<?> getByOrderId(String id) {
    try {
          var result = orderItemService.getByOrderId(id);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> getByOwnShopId(String userId) {
    try {
          var result = orderItemService.getByOwnShopId(userId);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }
}
