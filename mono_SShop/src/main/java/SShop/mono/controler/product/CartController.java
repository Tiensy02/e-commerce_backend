package SShop.mono.controler.product;

import SShop.mono.application.dto.product.CartDto;
import SShop.mono.application.service.user.CartService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.postgresql.CartEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController extends GenericCrudController<CartEntity, CartDto,String> implements CartApi{
  private final CartService cartService;

  public CartController(CartService cartService ) {
    super(cartService);
    this.cartService = cartService;
  }

  @Override
  public ResponseEntity<?> getByUser(String userId) {
    try {
          var result = cartService.getByUser(userId);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }
}
