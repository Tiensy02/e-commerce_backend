package SShop.mono.domain.repository.postgrerepo;

import SShop.mono.domain.entity.postgresql.CartEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CartRepository extends JpaRepository<CartEntity, String> {
  CartEntity findByUserId(String userId);
}
