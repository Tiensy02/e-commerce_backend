package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.CartItemDto;
import SShop.mono.application.service.GenericCrudImplJpa;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.domain.entity.postgresql.CartEntity;
import SShop.mono.domain.entity.postgresql.CartItemEntity;
import SShop.mono.domain.repository.postgrerepo.CartItemRepository;
import SShop.mono.domain.repository.postgrerepo.CartRepository;
import jakarta.ws.rs.NotFoundException;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemImpl extends GenericCrudImplJpa<CartItemEntity,String, CartItemDto> implements
    CartItemService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;

  public CartItemImpl(CartItemRepository cartItemRepository, CartRepository cartRepository) {
    super(cartItemRepository);
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
  }

  @Override
  public CartItemEntity convertDtoToEntity(CartItemDto dto) {
    CartItemEntity cartItem = new CartItemEntity();
    ObjectUtils.copyNonNullProperties(dto,cartItem);
    return cartItem;
  }

  @Override
  public CartItemDto convertEntityToDto(CartItemEntity entity) {
    CartItemDto cartItemDto = new CartItemDto();
    ObjectUtils.copyNonNullProperties(entity,cartItemDto);
    return cartItemDto;
  }
  @Override
  @Transactional
  public CartItemEntity save(CartItemDto cartItemDto) {

    CartEntity cart =  cartRepository.findById(cartItemDto.getCartId()).orElseThrow(()-> new NotFoundException());

    CartItemEntity cartItem = cartItemRepository.findByCartAndProductId(cart,cartItemDto.getProductId());
    if(cartItem == null ) {

    CartItemEntity entity = new CartItemEntity();
    ObjectUtils.copyNonNullProperties(cartItemDto, entity);
    entity.setCart(cart);
    var resut =  repository.save(entity);
    updateCartItemCount(cart, 1);
    return resut;
    }else {
      Map<String,Object> customField = cartItem.getCustomField();
      customField.put("quantity", (Integer) customField.get("quantity") + 1);
      cartItem.setCustomField(customField);
      var resut =  repository.save(cartItem);
      return resut;
    }


  }

  @Override
  @Transactional
  public void removeCartItem(CartItemEntity cartItemEntity) {
    CartEntity cart = cartItemEntity.getCart();
    repository.delete(cartItemEntity);
    updateCartItemCount(cart, -1);
  }

  private void updateCartItemCount(CartEntity cart, int quantity) {
    if (cart != null) {
      int itemCount = cart.getCartItems().size();
      cart.setQuantityItem(itemCount+quantity);
      cartRepository.save(cart);
    }
  }

  @Override
  @Transactional
  public CartItemEntity updateQuantity(String id, int quantity) {
    CartItemEntity cartItem = cartItemRepository.findById(id).orElseThrow(()-> new NotFoundException());
    if(quantity > 0 ) {
    Map<String,Object> custom = cartItem.getCustomField();
    custom.put("quantity", quantity);
    cartItem.setCustomField(custom);
    return cartItemRepository.save(cartItem);
    }else {
      removeCartItem(cartItem);
      return null;
    }
  }
}
