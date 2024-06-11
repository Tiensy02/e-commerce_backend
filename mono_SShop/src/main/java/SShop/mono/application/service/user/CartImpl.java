package SShop.mono.application.service.user;

import SShop.mono.application.dto.product.CartDto;
import SShop.mono.application.service.GenericCrudImplJpa;
import SShop.mono.application.utils.ObjectUtils;
import SShop.mono.domain.entity.postgresql.CartEntity;
import SShop.mono.domain.repository.postgrerepo.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartImpl extends GenericCrudImplJpa<CartEntity,String, CartDto> implements
    CartService {
  private final CartRepository cartRepository;

  public CartImpl(CartRepository cartRepository) {
    super(cartRepository);
    this.cartRepository = cartRepository;
  }

  @Override
  public CartEntity convertDtoToEntity(CartDto dto) {
    CartEntity cart = new CartEntity();
    ObjectUtils.copyNonNullProperties(dto,cart);
    return cart;
  }

  @Override
  public CartDto convertEntityToDto(CartEntity entity) {
    CartDto cartDto = new CartDto();
    ObjectUtils.copyNonNullProperties(entity,cartDto);
    return cartDto;
  }

  @Override
  public CartEntity getByUser(String userId) {
    CartEntity cart = cartRepository.findByUserId(userId);
    return cart;
  }
}
