package SShop.mono.domain.repository.postgrerepo;

import SShop.mono.domain.entity.postgresql.OrderEntity;
import SShop.mono.domain.entity.postgresql.OrderItemEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity,String> {
  List<OrderItemEntity> findAllByOrder(OrderEntity order);

  @Query("SELECT o FROM OrderItemEntity o WHERE o.ownProductId = :ownProductId")
  List<OrderItemEntity> findByOwnProductId(@Param("ownProductId") String ownProductId);
}
