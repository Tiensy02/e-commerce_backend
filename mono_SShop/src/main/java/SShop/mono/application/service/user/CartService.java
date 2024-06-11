package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.CartDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.postgresql.CartEntity;

public interface CartService extends GenericCrudService<CartEntity,String, CartDto> {

  CartEntity getByUser(String userId);
}
