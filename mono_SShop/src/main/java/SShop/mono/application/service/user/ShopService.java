package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.ProductShowDto;
import SShop.mono.application.dto.product.ShopDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.mongodb.ProductEntity;
import SShop.mono.domain.entity.mongodb.ShopEntity;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ShopService extends GenericCrudService<ShopEntity,String, ShopDto> {

  List<ProductEntity> getProducts(String ownId);

  ShopEntity getByUser(String id);
}
