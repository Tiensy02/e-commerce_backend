package SShop.mono.domain.repository.postgrerepo;

import SShop.mono.domain.entity.postgresql.CartEntity;
import SShop.mono.domain.entity.postgresql.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity,String> {
  CartItemEntity findByCartAndProductId(CartEntity cart, String productId);
}
