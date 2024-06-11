package SShop.mono.domain.repository.postgrerepo;

import SShop.mono.domain.entity.postgresql.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,String> {
  OrderEntity findByUserId(String userId);
}
