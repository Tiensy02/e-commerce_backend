package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.CartItemDto;
import SShop.mono.application.service.GenericCrudService;
import SShop.mono.domain.entity.postgresql.CartItemEntity;

public interface CartItemService extends
    GenericCrudService<CartItemEntity,String, CartItemDto> {

  CartItemEntity updateQuantity(String id, int quantity);
  void removeCartItem(CartItemEntity cartItem);
}
