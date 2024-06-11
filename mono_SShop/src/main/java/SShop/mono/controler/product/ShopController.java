package SShop.mono.controler.product;

import SShop.mono.application.dto.product.ShopDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.application.service.user.ShopService;
import SShop.mono.controler.GenericCrudController;
import SShop.mono.domain.entity.mongodb.ShopEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController extends GenericCrudController<ShopEntity, ShopDto,String> implements  ShopApi {
  private final ShopService shopService;

  public ShopController(ShopService shopService) {
    super(shopService);
    this.shopService = shopService;
  }

  @Override
  public ResponseEntity<?> getProducts(String ownId) {
    try {
          var result = shopService.getProducts(ownId);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> getOwnUser(String id) {
    try {
          var result = shopService.getByUser(id);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }
}
