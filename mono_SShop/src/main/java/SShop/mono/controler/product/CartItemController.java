package SShop.mono.controler.product;

import SShop.mono.application.dto.product.CartItemDto;
import SShop.mono.application.service.user.CartItemService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.postgresql.CartItemEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartItemController extends GenericCrudController<CartItemEntity, CartItemDto,String> implements CartItemApi {

  private final CartItemService cartItemService;
  public CartItemController(CartItemService cartItemService) {
    super(cartItemService);
    this.cartItemService = cartItemService;
  }

  @Override
  public ResponseEntity<?> updateQuantity(String id, int quantity) {
    try {
          var result = cartItemService.updateQuantity(id,quantity);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }
}
